package fr.elephantasia.elephantasia.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.elephantasia.utils.UserInfo;


class UserTable {

    static final String TABLE_NAME = "Users";

    private static final String ID = "ID";
    private static final String NAME = "name";
    private static final String STATUS = "status";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";

    static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, "
            + STATUS + " TEXT NOT NULL, "
            + EMAIL + " TEXT NOT NULL, "
            + ADDRESS + " TEXT NOT NULL);";

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
        values.put(ID, user.id);
        values.put(NAME, user.name);
        values.put(STATUS, String.valueOf(user.status));
        values.put(EMAIL, user.email);
        values.put(ADDRESS, user.address);
        return (values);
    }

    private static UserInfo cursorToValue(Cursor cursor){
        UserInfo user = new UserInfo();
        user.id = cursor.getInt(cursor.getColumnIndex(ID));
        user.name = cursor.getString(cursor.getColumnIndex(NAME));
        user.status = UserInfo.Status.valueOf(cursor.getString(cursor.getColumnIndex(STATUS)));
        user.email = cursor.getString(cursor.getColumnIndex(EMAIL));
        user.address = cursor.getString(cursor.getColumnIndex(ADDRESS));
        return (user);
    }

}
