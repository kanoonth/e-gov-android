package com.kanoon.egov.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kanoon.egov.models.Action;
import com.kanoon.egov.models.Category;
import com.kanoon.egov.models.Document;
import com.kanoon.egov.models.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aof on 2/28/2015 AD.
 */
public class DAO {

    private SqliteConnector sqliteConnector;

    private static DAO singleton = new DAO( );

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private DAO() {}

    /* Static 'instance' method */
    public static DAO getInstance( ) {
        return singleton;
    }


    public void setContent(Context context){
        sqliteConnector = new SqliteConnector(context);
    }

    /**
     * Get the Reference code of last transaction stored in the database.
     * @return Reference code of last transaction stored in the database.
     */
    public List<Category> getCategories(int limitNumber) {
        List<Category> categories = new ArrayList<Category>();
        SQLiteDatabase db = sqliteConnector.getReadableDatabase();

        Cursor ca = db.rawQuery("SELECT * FROM Category LIMIT " + limitNumber + ";",new String[0]);
        if(limitNumber > ca.getCount()){
            limitNumber = ca.getCount();
        }
        ca.moveToFirst();
        for(int i=0;i<limitNumber;i++) {
            Category category = new Category();
            long categoryID = ca.getInt(ca.getColumnIndexOrThrow("id"));
            String categoryName = ca.getString(ca.getColumnIndexOrThrow("name"));
            category.id = categoryID;
            category.name = categoryName;
            categories.add(category);
            ca.moveToNext();
        }
        Cursor c = db.rawQuery("SELECT Category.name as categoryName, Category.id as categoryId, Acsrr.name as actionName, Acsrr.id as actionId, Acsrr.description as actionDescription FROM Category, (SELECT * FROM Action where category_id IN ( SELECT id FROM Category LIMIT "+limitNumber+" )) as Acsrr Where Category.id = Acsrr.category_id;;", new String[0]);
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

    public List<Document> getDocument(String actionName){
//        Log.d("aaaaa",actionName);
        List<Document> listDocs = new ArrayList<Document>();
        SQLiteDatabase db = sqliteConnector.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Document.name as name,Document.photo_path as photo_path,Document.id as id, Document.description as description FROM Document,Action,Requirement " +
                "Where Requirement.action_id == Action.id and Requirement.document_id == Document.id " +
                ";",new String[0]);


        if(c.getCount() != 0){
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