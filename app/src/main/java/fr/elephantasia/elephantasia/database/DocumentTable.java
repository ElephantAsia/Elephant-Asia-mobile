package fr.elephantasia.elephantasia.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import fr.elephantasia.elephantasia.utils.DocumentInfo;

public class DocumentTable {

    static final String TABLE_NAME = "Documents";

    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;

    private static final String COL_TITLE = "title";
    private static final int NUM_COL_TITLE = 1;

    private static final String COL_NAME = "title";
    private static final int NUM_COL_NAME = 2;

    static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITLE + " TEXT NOT NULL, "
            + COL_NAME + " TEXT NOT NULL);";

    static long insert(SQLiteDatabase database, DocumentInfo document) {
        return (database.insert(TABLE_NAME, null, getContentValues(document)));
    }

    static int update(SQLiteDatabase database, DocumentInfo document) {
        return (database.update(TABLE_NAME, getContentValues(document), COL_ID + " = " + document.id, null));
    }

    /*static int delete(SQLiteDatabase database, DocumentInfo document) {
        ContentValues values = getContentValues(document);
        values.put(ElephantTable.COL_STATE, ElephantInfo.State.DELETED.toString());

        return (database.update(TABLE_NAME, values, COL_ID + " = " + elephant.id, null));
    }*/

    private static ContentValues getContentValues(DocumentInfo document) {
        ContentValues values = new ContentValues();

        values.put(COL_TITLE, document.title);
        values.put(COL_NAME, document.name);

        return (values);
    }
}
