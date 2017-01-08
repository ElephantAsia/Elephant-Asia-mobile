package fr.elefantasia.elefantasia.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.elefantasia.elefantasia.utils.ElephantInfo;

/**
 * Created by Stephane on 31/10/2016.
 */

/**
 * Cette classe a pour but de gérer la base donnée Elefant
 */

public class ElefantDatabase {

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
    public ElefantDatabase(Context context) {
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
     * @param elefant l'éléphant à insérer
     * @return Code d'erreur
     */
    public long insertElephant(ElephantInfo elefant) {
        Log.i("registraction village", elefant.registrationVillage);
        Log.i("registraction province", elefant.registrationProvince);

        ContentValues values = new ContentValues();
        values.put(MySQLite.COL_NAME, elefant.name);
        values.put(MySQLite.COL_NICKNAME, elefant.nickName);
        values.put(MySQLite.COL_SEX, String.valueOf(elefant.sex));
        values.put(MySQLite.COL_EAR_TAG, String.valueOf(elefant.earTag));
        values.put(MySQLite.COL_EYED, String.valueOf(elefant.eyeD));
        values.put(MySQLite.COL_BIRTH_DATE, elefant.birthDate);
        values.put(MySQLite.COL_BIRTH_VILLAGE, elefant.birthVillage);
        values.put(MySQLite.COL_BIRTH_DISTRICT, elefant.birthDistrict);
        values.put(MySQLite.COL_BIRTH_PROVINCE, elefant.birthProvince);
        values.put(MySQLite.COL_CHIPS, elefant.chips.get(0));
        values.put(MySQLite.COL_REGISTRATION_ID, elefant.registrationID);
        values.put(MySQLite.COL_REGISTRATION_VILLAGE, elefant.registrationVillage);
        values.put(MySQLite.COL_REGISTRATION_DISTRICT, elefant.registrationDistrict);
        values.put(MySQLite.COL_REGISTRATION_PROVINCE, elefant.registrationProvince);

        return (database.insert(MySQLite.TABLE_NAME, null, values));
    }

    /**
     * Met à jour un éléphant
     *
     * @param id L'ID de l'éléphant à mettre à jour
     * @param elefant La ou les nouvelle(s) valeur(s)
     * @return Code d'erreur
     */
    public int updateElephant(int id, ElephantInfo elefant) {
        ContentValues values = new ContentValues();
        values.put(MySQLite.COL_NAME, elefant.name);
        values.put(MySQLite.COL_NICKNAME, elefant.nickName);
        values.put(MySQLite.COL_SEX, String.valueOf(elefant.sex));
        values.put(MySQLite.COL_EAR_TAG, String.valueOf(elefant.earTag));
        values.put(MySQLite.COL_EYED, String.valueOf(elefant.eyeD));
        values.put(MySQLite.COL_BIRTH_DATE, elefant.birthDate);
        values.put(MySQLite.COL_BIRTH_VILLAGE, elefant.birthVillage);
        values.put(MySQLite.COL_BIRTH_DISTRICT, elefant.birthDistrict);
        values.put(MySQLite.COL_BIRTH_PROVINCE, elefant.birthProvince);
        values.put(MySQLite.COL_CHIPS, elefant.chips.get(0));
        values.put(MySQLite.COL_REGISTRATION_ID, elefant.registrationID);
        values.put(MySQLite.COL_REGISTRATION_VILLAGE, elefant.registrationVillage);
        values.put(MySQLite.COL_REGISTRATION_DISTRICT, elefant.registrationDistrict);
        values.put(MySQLite.COL_REGISTRATION_PROVINCE, elefant.registrationProvince);

        return (database.update(MySQLite.TABLE_NAME, values, MySQLite.COL_ID + " = " + id, null));
    }

    /**
     * Supprime un éléphant de la base de donnée
     * @param id L'ID de l'éléphant à supprimer
     * @return Code d'erreur
     */
    public int removeElephant(int id) {
        return (database.delete(MySQLite.TABLE_NAME, MySQLite.COL_ID + " = "  + id, null));
    }

    /**
     * Retourne l'éléphant associé à un nom
     *
     * @param name Le nom de l'éléphant à récupéré
     * @return Une liste d'éléphant
     */
    public List<ElephantInfo> getElephantByName(String name) {
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
    }

    /**
     * Récuperer les infos d'un cursor et les convertis en Elefant
     *
     * @param cursor Le curseur
     * @return L'éléphant
     */
    private ElephantInfo cursorToElephant(Cursor cursor){
        ElephantInfo elefant = new ElephantInfo();
        elefant.id = cursor.getInt(MySQLite.NUM_COL_ID);
        elefant.name = cursor.getString(MySQLite.NUM_COL_NAME);
        elefant.nickName = cursor.getString(MySQLite.NUM_COL_NICKNAME);
        elefant.sex = ElephantInfo.Gender.valueOf(cursor.getString(MySQLite.NUM_COL_SEX));
        elefant.earTag = Boolean.valueOf(cursor.getString(MySQLite.NUM_COL_EAR_TAG));
        elefant.eyeD = Boolean.valueOf(cursor.getString(MySQLite.NUM_COL_EYED));
        elefant.birthDate = cursor.getString(MySQLite.NUM_COL_BIRTH_DATE);
        elefant.birthVillage = cursor.getString(MySQLite.NUM_COL_BIRTH_VILLAGE);
        elefant.birthDistrict = cursor.getString(MySQLite.NUM_COL_BIRTH_DISTRICT);
        elefant.birthProvince = cursor.getString(MySQLite.NUM_COL_BIRTH_PROVINCE);
        elefant.addChips(cursor.getString(MySQLite.NUM_COL_CHIPS));
        elefant.registrationID = cursor.getString(MySQLite.NUM_COL_REGISTRATION_ID);
        elefant.registrationVillage = cursor.getString(MySQLite.NUM_COL_REGISTRATION_VILLAGE);
        elefant.registrationDistrict = cursor.getString(MySQLite.NUM_COL_REGISTRATION_DISTRICT);
        elefant.registrationProvince = cursor.getString(MySQLite.NUM_COL_REGISTRATION_PROVINCE);
        return (elefant);
    }

}


/*Cursor c = database.query(MySQLite.TABLE_NAME, new String[] {MySQLite.COL_ID, MySQLite.COL_NAME},
        MySQLite.COL_NAME + " LIKE \"" + name +"\"", null, null, null, null);

return (cursorToELefant(c));*/