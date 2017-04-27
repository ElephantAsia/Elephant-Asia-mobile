package fr.elephantasia.elephantasia.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import fr.elephantasia.elephantasia.utils.DocumentInfo;

public class DocumentTable {

    static final String TABLE_NAME = "Documents";

    private static final String ID = "ID";
    private static final String TITLE = "title";
    private static final String NAME = "name";

    static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE + " TEXT NOT NULL, "
            + NAME + " TEXT NOT NULL);";

    static long insert(SQLiteDatabase database, DocumentInfo document) {
        return (database.insert(TABLE_NAME, null, getContentValues(document)));
    }

    static int update(SQLiteDatabase database, DocumentInfo document) {
        return (database.update(TABLE_NAME, getContentValues(document), ID + " = " + document.id, null));
    }

    /*static int delete(SQLiteDatabase database, DocumentInfo document) {
        ContentValues values = getContentValues(document);
        values.put(ElephantTable.STATE, ElephantInfo.State.DELETED.toString());

        return (database.update(TABLE_NAME, values, ID + " = " + elephant.id, null));
    }*/

    private static ContentValues getContentValues(DocumentInfo document) {
        ContentValues values = new ContentValues();

        values.put(TITLE, document.title);
        values.put(NAME, document.name);

        return (values);
    }
}
