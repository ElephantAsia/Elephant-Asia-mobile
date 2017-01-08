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

    public static final String COL_SEX = "sex";
    public static final int NUM_COL_SEX = 3;

    public static final String COL_EAR_TAG = "ear_tag";
    public static final int NUM_COL_EAR_TAG = 4;

    public static final String COL_EYED = "eyed";
    public static final int NUM_COL_EYED = 5;

    public static final String COL_BIRTH_DATE = "birthdate";
    public static final int NUM_COL_BIRTH_DATE = 6;

    public static final String COL_BIRTH_VILLAGE = "birthdate_village";
    public static final int NUM_COL_BIRTH_VILLAGE = 7;

    public static final String COL_BIRTH_DISTRICT = "birthdate_district";
    public static final int NUM_COL_BIRTH_DISTRICT = 8;

    public static final String COL_BIRTH_PROVINCE = "birthdate_province";
    public static final int NUM_COL_BIRTH_PROVINCE = 9;

    public static final String COL_CHIPS = "chips";
    public static final int NUM_COL_CHIPS = 10;

    public static final String COL_REGISTRATION_ID = "registration_id";
    public static final int NUM_COL_REGISTRATION_ID = 11;

    public static final String COL_REGISTRATION_VILLAGE = "registration_village";
    public static final int NUM_COL_REGISTRATION_VILLAGE = 12;

    public static final String COL_REGISTRATION_DISTRICT = "registration_district";
    public static final int NUM_COL_REGISTRATION_DISTRICT = 13;

    public static final String COL_REGISTRATION_PROVINCE = "registration_province";
    public static final int NUM_COL_REGISTRATION_PROVINCE = 14;


    private static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_NICKNAME + " TEXT NOT NULL, "
            + COL_SEX + " TEXT NOT NULL, "
            + COL_EAR_TAG + " TEXT NOT NULL, "
            + COL_EYED + " TEXT NOT NULL, "
            + COL_BIRTH_DATE + " TEXT NOT NULL, "
            + COL_BIRTH_VILLAGE + " TEXT NOT NULL, "
            + COL_BIRTH_DISTRICT + " TEXT NOT NULL, "
            + COL_BIRTH_PROVINCE + " TEXT NOT NULL, "
            + COL_CHIPS + " TEXT NOT NULL, "
            + COL_REGISTRATION_ID + " TEXT NOT NULL, "
            + COL_REGISTRATION_VILLAGE + " TEXT NOT NULL, "
            + COL_REGISTRATION_DISTRICT + " TEXT NOT NULL, "
            + COL_REGISTRATION_PROVINCE + " TEXT NOT NULL);";

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
