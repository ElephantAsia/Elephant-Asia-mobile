package fr.elephantasia.elephantasia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import fr.elephantasia.elephantasia.utils.ElephantInfo;

public class Database {

    private static final int VERSION_BDD = 6;
    private static final String NAME_BDD = "elephantasia.db";
    private SQLiteDatabase database;
    private MySQLite mySQLite;

    public Database(Context context) {
        mySQLite = new MySQLite(context, NAME_BDD, null, VERSION_BDD);
    }

    public void open() {
        database = mySQLite.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public long insert(ElephantInfo elephant) {
        return ElephantTable.insert(database, elephant);
    }

    public int update(ElephantInfo elephant) {
        return ElephantTable.update(database, elephant);
    }

    public int delete(ElephantInfo elephant) {
        return ElephantTable.delete(database, elephant);
    }

    /*public ElephantInfo getElephantByID(int id) {
        return ElephantTable.getElephantByID(database, id);
    }*/

    public List<ElephantInfo> getDrafts() {
        return ElephantTable.getDrafts(database);
    }

    public List<ElephantInfo> search(ElephantInfo info) {
        return ElephantTable.search(database, info);
    }

}
