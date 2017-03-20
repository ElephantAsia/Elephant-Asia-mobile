package fr.elephantasia.elephantasia.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.elephantasia.utils.UserInfo;


class UserTable {

    static final String TABLE_NAME = "Users";

    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;

    private static final String COL_NAME = "name";
    private static final int NUM_COL_NAME = 1;

    private static final String COL_STATUS = "status";
    private static final int NUM_COL_STATUS = 2;

    private static final String COL_EMAIL = "email";
    private static final int NUM_COL_EMAIL = 3;

    private static final String COL_ADDRESS = "address";
    private static final int NUM_COL_ADDRESS = 4;

    static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_STATUS + " TEXT NOT NULL, "
            + COL_EMAIL + " TEXT NOT NULL, "
            + COL_ADDRESS + " TEXT NOT NULL);";

    static List<UserInfo> getUsers(SQLiteDatabase database) {
        String query = "SELECT * FROM " + TABLE_NAME;
        List<UserInfo> results = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                results.add(cursorToValue(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();

        return (results);
    }

    static ContentValues getContentValues(UserInfo user) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, user.id);
        values.put(COL_NAME, user.name);
        values.put(COL_STATUS, String.valueOf(user.status));
        values.put(COL_EMAIL, user.email);
        values.put(COL_ADDRESS, user.address);
        return (values);
    }

    private static UserInfo cursorToValue(Cursor cursor){
        UserInfo user = new UserInfo();
        user.id = cursor.getInt(NUM_COL_ID);
        user.name = cursor.getString(NUM_COL_NAME);
        user.status = UserInfo.Status.valueOf(cursor.getString(NUM_COL_STATUS));
        user.email = cursor.getString(NUM_COL_EMAIL);
        user.address = cursor.getString(NUM_COL_ADDRESS);
        return (user);
    }

}
