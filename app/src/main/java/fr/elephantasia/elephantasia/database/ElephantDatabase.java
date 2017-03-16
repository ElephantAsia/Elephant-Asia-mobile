package fr.elephantasia.elephantasia.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.elephantasia.utils.ElephantInfo;

/**
 * Created by Stephane on 31/10/2016.
 */

/**
 * Cette classe a pour but de gérer la base donnée Elefant
 */

public class ElephantDatabase {

    /**
     * Version de la base de donnée
     */
    private static final int VERSION_BDD = 4;

    /**
     * Nom du fichier de la base de donnée
     */
    private static final String NOM_BDD = "elefants.db";

    /**
     * La base de donnée SQLite
     */
    private SQLiteDatabase database;

    /**
     * Notre classe permettant de créer plus facilement une BDD
     */
    private MySQLite mySQLite;


    /**
     * Crée une base de donnée
     *
     * @param context Le contexte
     */
    public ElephantDatabase(Context context) {
        mySQLite = new MySQLite(context, NOM_BDD, null, VERSION_BDD);

    }

    /**
     * Ouvre la base de donnée en écriture
     */
    public void open() {
        database = mySQLite.getWritableDatabase();
    }

    /**
     * Ferme la base de donnée
     */
    public void close() {
        database.close();
    }

    /**
     * Insert un éléphant dans la base de donnée
     *
     * @param elephant l'éléphant à insérer
     * @return Code d'erreur
     */
    public long insertElephant(ElephantInfo elephant) {
        ContentValues values = new ContentValues();
        values.put(MySQLite.COL_NAME, elephant.name);
        values.put(MySQLite.COL_NICKNAME, elephant.nickName);
        values.put(MySQLite.COL_SEX, String.valueOf(elephant.sex).toUpperCase());
        values.put(MySQLite.COL_EAR_TAG, String.valueOf(elephant.earTag));
        values.put(MySQLite.COL_EYED, String.valueOf(elephant.eyeD));
        values.put(MySQLite.COL_BIRTH_DATE, elephant.birthDate);
        values.put(MySQLite.COL_BIRTH_VILLAGE, elephant.birthVillage);
        values.put(MySQLite.COL_BIRTH_DISTRICT, elephant.birthDistrict);
        values.put(MySQLite.COL_BIRTH_PROVINCE, elephant.birthProvince);
        values.put(MySQLite.COL_CHIPS, elephant.chips1);
        values.put(MySQLite.COL_REGISTRATION_ID, elephant.regID);
        values.put(MySQLite.COL_REGISTRATION_VILLAGE, elephant.regVillage);
        values.put(MySQLite.COL_REGISTRATION_DISTRICT, elephant.regDistrict);
        values.put(MySQLite.COL_REGISTRATION_PROVINCE, elephant.regProvince);
        values.put(MySQLite.COL_STATE, elephant.state.toString());
        return (database.insert(MySQLite.TABLE_NAME, null, values));
    }

    /**
     * Met à jour un éléphant
     *
     * @param elephant La ou les nouvelle(s) valeur(s)
     * @return Code d'erreur
     */
    public int updateElephant(ElephantInfo elephant) {
        ContentValues values = new ContentValues();
        values.put(MySQLite.COL_NAME, elephant.name);
        values.put(MySQLite.COL_NICKNAME, elephant.nickName);
        values.put(MySQLite.COL_SEX, String.valueOf(elephant.sex).toUpperCase());
        values.put(MySQLite.COL_EYED, String.valueOf(elephant.eyeD));
        values.put(MySQLite.COL_BIRTH_DATE, elephant.birthDate);
        values.put(MySQLite.COL_BIRTH_VILLAGE, elephant.birthVillage);
        values.put(MySQLite.COL_BIRTH_DISTRICT, elephant.birthDistrict);
        values.put(MySQLite.COL_BIRTH_PROVINCE, elephant.birthProvince);
        values.put(MySQLite.COL_CHIPS, elephant.chips1);
        values.put(MySQLite.COL_REGISTRATION_ID, elephant.regID);
        values.put(MySQLite.COL_REGISTRATION_VILLAGE, elephant.regVillage);
        values.put(MySQLite.COL_REGISTRATION_DISTRICT, elephant.regDistrict);
        values.put(MySQLite.COL_REGISTRATION_PROVINCE, elephant.regProvince);
        values.put(MySQLite.COL_STATE, elephant.state.toString());

        return (database.update(MySQLite.TABLE_NAME, values, MySQLite.COL_ID + " = " + elephant.id, null));
    }

    /**
     * Supprime virtuellement un elephant en settant le flag state à DELETED
     * @param elephant L'éléphant à supprimer
     * @return Code d'erreur
     */
    public int deleteElephant(ElephantInfo elephant) {
        ContentValues values = new ContentValues();
        values.put(MySQLite.COL_NAME, elephant.name);
        values.put(MySQLite.COL_NICKNAME, elephant.nickName);
        values.put(MySQLite.COL_SEX, String.valueOf(elephant.sex).toUpperCase());
        values.put(MySQLite.COL_EYED, String.valueOf(elephant.eyeD));
        values.put(MySQLite.COL_BIRTH_DATE, elephant.birthDate);
        values.put(MySQLite.COL_BIRTH_VILLAGE, elephant.birthVillage);
        values.put(MySQLite.COL_BIRTH_DISTRICT, elephant.birthDistrict);
        values.put(MySQLite.COL_BIRTH_PROVINCE, elephant.birthProvince);
        values.put(MySQLite.COL_CHIPS, elephant.chips1);
        values.put(MySQLite.COL_REGISTRATION_ID, elephant.regID);
        values.put(MySQLite.COL_REGISTRATION_VILLAGE, elephant.regVillage);
        values.put(MySQLite.COL_REGISTRATION_DISTRICT, elephant.regDistrict);
        values.put(MySQLite.COL_REGISTRATION_PROVINCE, elephant.regProvince);
        values.put(MySQLite.COL_STATE, ElephantInfo.State.DELETED.toString());

        return (database.update(MySQLite.TABLE_NAME, values, MySQLite.COL_ID + " = " + elephant.id, null));
        //return (database.delete(MySQLite.TABLE_NAME, MySQLite.COL_ID + " = "  + id, null));
    }

    /*public List<ElephantInfo> getElephantByName(String name) {
        String request = "SELECT * FROM " + MySQLite.TABLE_NAME + " WHERE " + MySQLite.COL_NAME + " LIKE '" + name + "%'";
        List<ElephantInfo> results = new ArrayList<>();

        Cursor cursor = database.rawQuery(request, null);
        //Cursor cursor = database.rawQuery(request, new String[] {name});

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                results.add(cursorToElephant(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();

        return (results);
    }*/

    public List<ElephantInfo> getElephantsDrafts() {
        String query = "SELECT * FROM " + MySQLite.TABLE_NAME
                + " WHERE " + MySQLite.COL_STATE + " = " + ElephantInfo.State.DRAFT;
        List<ElephantInfo> results = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);

        Log.i("draft_query", "query: " + query);
        Log.i("draft_query", "nb result: " + cursor.getCount() + "");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                results.add(cursorToElephant(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();

        return (results);
    }

    public List<ElephantInfo> getElephantFromSearch(ElephantInfo info) {
        String query = "SELECT * FROM " + MySQLite.TABLE_NAME + GetElephantQueryRestriction(info);
        List<ElephantInfo> results = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);

        Log.i("search_query", "query: " + query);
        Log.i("search_query", "nb result: " + cursor.getCount() + "");
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                results.add(cursorToElephant(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();

        return (results);
    }

    private String GetElephantQueryRestriction(ElephantInfo info) {
        List<String> restriction = new ArrayList<String>();

        if (!info.name.isEmpty())
            restriction.add(" WHERE " + MySQLite.COL_NAME + " LIKE '" + info.name + "%'");
        if (!info.chips1.isEmpty())
            restriction.add(" WHERE " + MySQLite.COL_CHIPS + " = '" + info.chips1 + "'");
        if (info.sex != ElephantInfo.Gender.UNKNOWN)
            restriction.add(" WHERE " + MySQLite.COL_SEX + " = '" + info.sex + "'");
        if (!info.regProvince.isEmpty())
            restriction.add(" WHERE " + MySQLite.COL_REGISTRATION_PROVINCE + " = '" + info.regProvince + "'");
        if (!info.regDistrict.isEmpty())
            restriction.add(" WHERE " + MySQLite.COL_REGISTRATION_DISTRICT + " = '" + info.regDistrict + "'");
        if (!info.regProvince.isEmpty())
            restriction.add(" WHERE " + MySQLite.COL_REGISTRATION_PROVINCE + " = '" + info.regProvince + "'");
        if (!info.regVillage.isEmpty())
            restriction.add(" WHERE " + MySQLite.COL_REGISTRATION_VILLAGE + " = '" + info.regVillage + "'");

        restriction.add(" WHERE " + MySQLite.COL_STATE + " != '" + ElephantInfo.State.DELETED.toString() + "'");
        restriction.add(" WHERE " + MySQLite.COL_STATE + " != '" + ElephantInfo.State.DRAFT.toString() + "'");

        for (int i = 0;  i < restriction.size(); i++) {
            if (i > 0) {
                restriction.set(i, restriction.get(i).replace("WHERE", "AND"));
            }
        }

        String ret = TextUtils.join(" ", restriction);
        Log.i("query_restriction", ret);
        return ret;
    }

    /**
     * Récuperer les infos d'un cursor et les convertis en Elefant
     *
     * @param cursor Le curseur
     * @return L'éléphant
     */
    private ElephantInfo cursorToElephant(Cursor cursor){
        ElephantInfo elephant = new ElephantInfo();
        elephant.id = cursor.getInt(MySQLite.NUM_COL_ID);
        elephant.name = cursor.getString(MySQLite.NUM_COL_NAME);
        elephant.nickName = cursor.getString(MySQLite.NUM_COL_NICKNAME);
        elephant.sex = ElephantInfo.Gender.valueOf(cursor.getString(MySQLite.NUM_COL_SEX).toUpperCase());
        elephant.earTag = Boolean.valueOf(cursor.getString(MySQLite.NUM_COL_EAR_TAG));
        elephant.eyeD = Boolean.valueOf(cursor.getString(MySQLite.NUM_COL_EYED));
        elephant.birthDate = cursor.getString(MySQLite.NUM_COL_BIRTH_DATE);
        elephant.birthVillage = cursor.getString(MySQLite.NUM_COL_BIRTH_VILLAGE);
        elephant.birthDistrict = cursor.getString(MySQLite.NUM_COL_BIRTH_DISTRICT);
        elephant.birthProvince = cursor.getString(MySQLite.NUM_COL_BIRTH_PROVINCE);
        elephant.chips1 = cursor.getString(MySQLite.NUM_COL_CHIPS);
        elephant.regID = cursor.getString(MySQLite.NUM_COL_REGISTRATION_ID);
        elephant.regVillage = cursor.getString(MySQLite.NUM_COL_REGISTRATION_VILLAGE);
        elephant.regDistrict = cursor.getString(MySQLite.NUM_COL_REGISTRATION_DISTRICT);
        elephant.regProvince = cursor.getString(MySQLite.NUM_COL_REGISTRATION_PROVINCE);
        elephant.state = ElephantInfo.State.valueOf(cursor.getString(MySQLite.NUM_COL_STATE).toUpperCase());

        return (elephant);
    }

}
