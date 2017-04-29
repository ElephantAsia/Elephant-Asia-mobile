package fr.elephantasia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class MySQLite extends SQLiteOpenHelper {

  MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(ElephantTable.TABLE);
    db.execSQL(UserTable.TABLE);
    db.execSQL(DocumentTable.TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE " + ElephantTable.TABLE_NAME + ";");
    db.execSQL("DROP TABLE " + UserTable.TABLE_NAME + ";");
    db.execSQL("DROP TABLE " + DocumentTable.TABLE_NAME + ";");
    onCreate(db);
  }

}
