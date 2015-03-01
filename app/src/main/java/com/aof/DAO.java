package com.aof;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aof.models.Action;
import com.aof.models.Category;
import com.aof.models.Document;
import com.aof.models.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aof on 2/28/2015 AD.
 */
public class DAO {

    private SQLiteOpenHelper sqliteConnector;

    /**
     * Get the Reference code of last transaction stored in the database.
     * @return Reference code of last transaction stored in the database.
     */
    public List<Category> getCategories(int limitNumber) {
        List<Category> categories = new ArrayList<Category>();
        SQLiteDatabase db = sqliteConnector.getReadableDatabase();

        Cursor ca = db.rawQuery("SELECT * FROM Category LIMIT " + limitNumber + ";",new String[0]);
        ca.moveToFirst();
        for(int i=0;i<limitNumber;i++) {
            Category category = new Category();
            long categoryID = ca.getInt(ca.getColumnIndexOrThrow("id"));
            category.id = categoryID;
            categories.add(category);
            ca.moveToNext();
        }
        Cursor c = db.rawQuery("SELECT name as categoryName, id as categoryId, (Acs.name) as actionName, Acs.id as actionId, (Acs.description) as actionDescription " +
                "FROM Category,(SELECT * FROM Action where category_id IN " +
                "(SELECT id FROM Category LIMIT "+ limitNumber +")as Cs)as Acs Where Category.id = Acs.category_id;", new String[0]);
        if(c.getCount() == 0) {
            // No data.
        } else {
            while(true) {
                c.moveToNext();
                String name = c.getString(c.getColumnIndexOrThrow("actionName"));
                String description = c.getString(c.getColumnIndexOrThrow("actionDescription"));
                long actionId = c.getLong(c.getColumnIndexOrThrow("actionId"));
                long categoryId = c.getLong(c.getColumnIndexOrThrow("categoryId"));
                int i = 0; // i is the number that use to check from list of category to see it is same one or other one
                Action action = new Action();
                action.id = actionId;
                action.description = description;
                action.name = name;
                if(categories.get(i).id!=categoryId){
                    i++;
                }
                categories.get(i).actions.add(action);
                if(c.isLast()){
                    break;
                }
            }
        }
        return categories;
    }

//    public List<Category> getCategories() {
//        List<Category> categories = new ArrayList<Category>();
//        SQLiteDatabase db = sqliteConnector.getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT name as categoryName, id as categoryId, (Acs.name) as actionName, Acs.id as actionId, (Acs.description) as actionDescription FROM Action,Category where Action.category_id = Action.id;", new String[0]);
//        if (c.getCount() == 0) {
//            // No data.
//        } else {
//            c.moveToFirst();
//            Long code = c.getLong(c.getColumnIndexOrThrow(""));
//
//        }
//    }

    public List<Document> getDocument(){
        List<Document> listDocs = new ArrayList<Document>();
        SQLiteDatabase db = sqliteConnector.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Document;",new String[0]);
        c.moveToFirst();
        while(true){
            Document doc = new Document();
            doc.description = c.getString(c.getColumnIndexOrThrow("description"));
            doc.id = c.getLong(c.getColumnIndexOrThrow("id"));
            doc.name = c.getString(c.getColumnIndexOrThrow("name"));
            doc.photo_path = c.getString(c.getColumnIndexOrThrow("photo_path"));
            listDocs.add(doc);
            if(c.isLast()){
                break;
            }
            c.moveToNext();
        }
        return listDocs;
    }

    public List<Ticket> getTicket(){
        List<Ticket> tickets = new ArrayList<Ticket>();
        SQLiteDatabase db = sqliteConnector.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Ticket;",new String[0]);
        c.moveToFirst();
        while(true){
            Ticket ticket = new Ticket();
            ticket.id = c.getLong(c.getColumnIndexOrThrow("id"));
            ticket.code = c.getString(c.getColumnIndexOrThrow("code"));
            tickets.add(ticket);
            if(c.isLast()){
                break;
            }
            c.moveToNext();
        }

        return tickets;
    }
}
