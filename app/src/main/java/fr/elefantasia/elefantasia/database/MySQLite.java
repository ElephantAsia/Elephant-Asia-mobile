package fr.elefantasia.elefantasia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Stephane on 12/11/2016.
 */

/**
 * Cette classe a pour but de gérer la création / mis à jour de la base de donnée
 */

public class MySQLite extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Elefants";

    public static final String COL_ID = "ID";
    public static final int NUM_COL_ID = 0;

    public static final String COL_NAME = "name";
    public static final int NUM_COL_NAME = 1;


    private static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL);";

    /**
     * @param context Le contexte
     * @param name Le nom du fichier de la BDD
     * @param factory Personnalisation de la classe Cursor
     * @param version La version de la base de donnée
     */
    public MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME + ";");
        onCreate(db);
    }


}
