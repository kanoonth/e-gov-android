package com.mapfap.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.clientWS.HttpRequestTask;

import java.util.Calendar;
import java.util.List;

/**
 * Patcher is the controller that updates the information corresponding to information from server.
 *
 * Created by mapfap on 2/27/15.
 */
public class Patcher {

    private SqliteConnector sqliteConnector;

    public Patcher(Context context) {
         sqliteConnector = new SqliteConnector(context);
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
        HttpRequestTask task = new HttpRequestTask(this, lastTransactionCode);
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

            if (transaction.type == Transaction.TYPE_SQL) {
                db.execSQL(transaction.content);
                Log.d("Patcher", "Added " + transaction.content);
            } else {
                retrieveFile(transaction.content);
                Log.d("Patcher", "Loaded " + transaction.content);
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
        throw new Exception("Couldn't retrieve file");
//        return false;
    }

    public boolean onRecieveData(List<Transaction> transactions) {
        Log.d("Patcher", "size = " + transactions.size());

        for (Transaction t: transactions) {
            if (!applyTransaction(t)) {
                return false;
            }
        }

        return true;

    }
}