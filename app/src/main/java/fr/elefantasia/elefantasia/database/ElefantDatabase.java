package fr.elefantasia.elefantasia.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
    private static final int VERSION_BDD = 1;

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
    public long insertElefant(Elefant elefant) {
        ContentValues values = new ContentValues();
        values.put(MySQLite.COL_NAME, elefant.name);

        return (database.insert(MySQLite.TABLE_NAME, null, values));
    }

    /**
     * Met à jour un éléphant
     *
     * @param id L'ID de l'éléphant à mettre à jour
     * @param elefant La ou les nouvelle(s) valeur(s)
     * @return Code d'erreur
     */
    public int updateElefant(int id, Elefant elefant) {
        ContentValues values = new ContentValues();
        values.put(MySQLite.COL_NAME, elefant.name);

        return (database.update(MySQLite.TABLE_NAME, values, MySQLite.COL_ID + " = " + id, null));
    }

    /**
     * Supprime un éléphant de la base de donnée
     * @param id L'ID de l'éléphant à supprimer
     * @return Code d'erreur
     */
    public int removeElefant(int id) {
        return (database.delete(MySQLite.TABLE_NAME, MySQLite.COL_ID + " = "  + id, null));
    }

    /**
     * Retourne l'éléphant associé à un nom
     *
     * @param name Le nom de l'éléphant à récupéré
     * @return Une liste d'éléphant
     */
    public List<Elefant> getElefantWithName(String name) {
        String request = "SELECT * FROM " + MySQLite.TABLE_NAME + " WHERE " + MySQLite.COL_NAME + " = ?";
        List<Elefant> results = new ArrayList<>();

        Cursor cursor = database.rawQuery(request, new String[] {name});

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                results.add(cursorToElefant(cursor));
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
    private Elefant cursorToElefant(Cursor cursor){
        Elefant elefant = new Elefant();
        elefant.id = cursor.getInt(MySQLite.NUM_COL_ID);
        elefant.name = cursor.getString(MySQLite.NUM_COL_NAME);

        return (elefant);
    }

}


/*Cursor c = database.query(MySQLite.TABLE_NAME, new String[] {MySQLite.COL_ID, MySQLite.COL_NAME},
        MySQLite.COL_NAME + " LIKE \"" + name +"\"", null, null, null, null);

return (cursorToELefant(c));*/