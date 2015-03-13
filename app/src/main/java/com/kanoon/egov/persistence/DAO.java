package com.kanoon.egov.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kanoon.egov.models.Action;
import com.kanoon.egov.models.Category;
import com.kanoon.egov.models.Document;

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
            int i = 0;
            while(true) {
                c.moveToNext();

                String name = c.getString(c.getColumnIndexOrThrow("actionName"));
                String description = c.getString(c.getColumnIndexOrThrow("actionDescription"));
                long actionId = c.getLong(c.getColumnIndexOrThrow("actionId"));
                long categoryId = c.getLong(c.getColumnIndexOrThrow("categoryId"));

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

    public Document getDocumentByName(String documentName) {
        SQLiteDatabase db = sqliteConnector.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Document WHERE Document.name = \'" + documentName + "\';",new String[0]);

        if (c.getCount() == 0) return null;

        c.moveToFirst();
        String name = c.getString(c.getColumnIndexOrThrow("name"));
        String description = c.getString(c.getColumnIndexOrThrow("description"));
        String photo_path = c.getString(c.getColumnIndexOrThrow("photo_path"));

        Document document = new Document();
        document.name = name;
        document.description = description;
        document.photo_path = photo_path;

        return document;
    }

    public Category getCategoryByActionName(String actionName) {
        SQLiteDatabase db = sqliteConnector.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Category.name as name FROM Category, Action WHERE Category.id = Action.category_id and Action.name = \'" + actionName + "\';",new String[0]);

        if (c.getCount() == 0) return null;

        c.moveToFirst();
        String name = c.getString(c.getColumnIndexOrThrow("name"));

        Category category = new Category();
        category.name = name;

        return category;
    }

    public List<Document> getDocument(String actionName){
        List<Document> listDocs = new ArrayList<Document>();
        SQLiteDatabase db = sqliteConnector.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Document.name as name,Document.photo_path as photo_path,Document.id as id, Document.description as description FROM Document,Action,Requirement " +
                "Where Requirement.action_id = Action.id and Requirement.document_id = Document.id and Action.name = \'" + actionName +
                "\';",new String[0]);

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
}