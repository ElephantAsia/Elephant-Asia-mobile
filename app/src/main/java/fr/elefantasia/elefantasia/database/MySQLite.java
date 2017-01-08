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

    public static final String COL_NICKNAME = "nickname";
    public static final int NUM_COL_NICKNAME = 2;

    public static final String COL_REGISTRATION_NUMBER = "id_number";
    public static final int NUM_COL_REGISTRATION_NUMBER = 3;

    public static final String COL_CHIPS = "chips";
    public static final int NUM_COL_CHIPS = 4;

    public static final String COL_SEX = "sex";
    public static final int NUM_COL_SEX = 5;

    public static final String COL_BIRTHDATE = "birthdate";
    public static final int NUM_COL_BIRTHDATE = 6;


    private static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_NICKNAME + " TEXT NOT NULL, "
            + COL_REGISTRATION_NUMBER + " TEXT NOT NULL, "
            + COL_CHIPS + " TEXT NOT NULL, "
            + COL_SEX + " TEXT NOT NULL, "
            + COL_BIRTHDATE + " TEXT NOT NULL);";

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
