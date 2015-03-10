package com.kanoon.egov.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.kanoon.egov.http.DownloadFileTask;
import com.kanoon.egov.http.GetTransactionTask;

import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * Patcher is the controller that updates the information corresponding to information from server.
 *
 * Created by mapfap on 2/27/15.
 */
public class Patcher {

    private SqliteConnector sqliteConnector;
    private static final String IMAGE_URL = "http://128.199.85.120/images/upload/";

    public Patcher(Context context) {
        sqliteConnector = new SqliteConnector(context);

        File folder = new File("/data/data/com.kanoon.egov/files/");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    /**
     * Get the current version of database.
     * @return current version of database.
     */
    public long getCurrentVersion() {
        return getLastTransactionCode();
    }

    /**
     * Get the Reference code of last transaction stored in the database.
     * @return Reference code of last transaction stored in the database.
     */
    private long getLastTransactionCode() {
        SQLiteDatabase db = sqliteConnector.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT code FROM mTransaction ORDER BY code DESC LIMIT 1;", new String[0]);
        if (c.getCount() == 0) {
            // No data.
            return 0;
        } else {
            c.moveToFirst();
            Long code = c.getLong(c.getColumnIndexOrThrow("code"));
            return code;
        }
    }

    /**
     * Process patch by retrieving data from server.
     */
    public void patch() {
        Long lastTransactionCode = getLastTransactionCode();
        GetTransactionTask task = new GetTransactionTask(this, lastTransactionCode);
        task.execute();
    }

    private long randomID() {
        return Math.abs(((int)Math.floor(Math.random() * 10000)+((int) Calendar.getInstance().getTimeInMillis())));
    }

    /**
     * Apply given transaction to database.
     * @param transaction transaction to be applied.
     */
    private boolean applyTransaction(Transaction transaction) {
        SQLiteDatabase db = sqliteConnector.getWritableDatabase();

        db.beginTransaction();
        try {

            if (transaction.type.equals(Transaction.TYPE_SQL)) {
                db.execSQL(transaction.content);
                Log.d("Patcher", "Added " + transaction.content);
            } else if (transaction.type.equals(Transaction.TYPE_FILE)) {
                retrieveFile(transaction.content);
                Log.d("Patcher", "Loaded " + transaction.content);
            } else {
                Log.e("Patcher", "Unknown transaction type #" + transaction.code + ": " + transaction.content);
            }

            ContentValues values = new ContentValues();
            values.put("code", transaction.code);
            values.put("type", transaction.type);
            values.put("content", transaction.content);
            db.insert("mTransaction", null, values);

            db.setTransactionSuccessful();
        } catch(SQLiteException ex) {
            Log.e("Patcher", ex.toString());
            return false;
        } catch(Exception ex2) {
            Log.e("Patcher", ex2.toString());
            return false;
        } finally {
            db.endTransaction();
        }
        db.close();
        Log.d("Patcher", "Transaction applied successfully");
        return true;
    }

    private boolean retrieveFile(String content) throws Exception {
        if (content == "") return true;
        DownloadFileTask task = new DownloadFileTask(this, IMAGE_URL + content, content);
        task.execute();
        return true;
    }

    /**
     * This is callback from DownloadFileTask.
     * @param isSuccess
     * @return
     */
    public boolean onReceiveFile(boolean isSuccess) {
        // TODO: How to handle if file missing???
        return isSuccess;
    }

    /**
     * This is callback for GetTransactionTask.
     * @param transactions data loaded.
     * @return true if success; false otherwise.
     */
    public boolean onReceiveData(List<Transaction> transactions) {
//        Log.d("Patcher", "size = " + transactions.size());
        if(transactions == null) return false;
        for (Transaction t: transactions) {
            if (!applyTransaction(t)) {
                return false;
            }
        }

        return true;

    }
}
