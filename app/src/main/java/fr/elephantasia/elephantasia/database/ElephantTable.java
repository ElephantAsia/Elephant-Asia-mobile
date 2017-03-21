package fr.elephantasia.elephantasia.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.elephantasia.utils.ElephantInfo;

class ElephantTable {

    static final String TABLE_NAME = "Elephants";

    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;

    // Registration
    private static final String COL_NAME = "name";
    private static final int NUM_COL_NAME = 1;

    private static final String COL_NICKNAME = "nickname";
    private static final int NUM_COL_NICKNAME = 2;

    private static final String COL_SEX = "sex";
    private static final int NUM_COL_SEX = 3;

    private static final String COL_EAR_TAG = "ear_tag";
    private static final int NUM_COL_EAR_TAG = 4;

    private static final String COL_EYED = "eyed";
    private static final int NUM_COL_EYED = 5;

    private static final String COL_BIRTH_DATE = "birthdate";
    private static final int NUM_COL_BIRTH_DATE = 6;

    private static final String COL_BIRTH_CITY = "birthdate_city";
    private static final int NUM_COL_BIRTH_CITY = 7;

    private static final String COL_BIRTH_DISTRICT = "birthdate_district";
    private static final int NUM_COL_BIRTH_DISTRICT = 8;

    private static final String COL_BIRTH_PROVINCE = "birthdate_province";
    private static final int NUM_COL_BIRTH_PROVINCE = 9;

    private static final String COL_CHIPS = "chips";
    private static final int NUM_COL_CHIPS = 10;

    private static final String COL_REGISTRATION_ID = "registration_id";
    private static final int NUM_COL_REGISTRATION_ID = 11;

    private static final String COL_REGISTRATION_CITY = "registration_city";
    private static final int NUM_COL_REGISTRATION_CITY = 12;

    private static final String COL_REGISTRATION_DISTRICT = "registration_district";
    private static final int NUM_COL_REGISTRATION_DISTRICT = 13;

    private static final String COL_REGISTRATION_PROVINCE = "registration_province";
    private static final int NUM_COL_REGISTRATION_PROVINCE = 14;

    // Description
    private static final String COL_TUSKS = "tusks";
    private static final int NUM_COL_TUSKS = 15;

    private static final String COL_NAILS_FRONT_LEFT = "nails_front_left";
    private static final int NUM_COL_NAILS_FRONT_LEFT = 16;

    private static final String COL_NAILS_FRONT_RIGHT = "nails_front_right";
    private static final int NUM_COL_NAILS_FRONT_RIGHT = 17;

    private static final String COL_NAILS_REAR_LEFT = "nails_rear_left";
    private static final int NUM_COL_NAILS_REAR_LEFT = 18;

    private static final String COL_NAILS_REAR_RIGHT = "nails_rear_right";
    private static final int NUM_COL_NAILS_REAR_RIGHT = 19;

    private static final String COL_WEIGHT = "weight";
    private static final int NUM_COL_WEIGHT = 20;

    private static final String COL_HEIGHT = "height";
    private static final int NUM_COL_HEIGHT = 21;

    // Owners
    private static final String COL_OWNERS = "owners";
    private static final int NUM_COL_OWNERS = 22;

    // Parentage
    private static final String COL_FATHER_ID = "father_id";
    private static final int NUM_COL_FATHER_ID = 23;

    private static final String COL_MOTHER_ID = "mother_id";
    private static final int NUM_COL_MOTHER_ID = 24;

    private static final String COL_CHILDREN_ID = "children_id";
    private static final int NUM_COL_CHILDREN_ID = 25;

    // Documents
    private static final String COL_DOCUMENTS_ID = "documents_id";
    private static final int NUM_COL_DOCUMENTS_ID = 26;

    //Other
    private static final String COL_STATE = "state";
    private static final int NUM_COL_STATE = 27;

    static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

            // Registration
            + COL_NAME + " TEXT NOT NULL, "
            + COL_NICKNAME + " TEXT NOT NULL, "
            + COL_SEX + " TEXT NOT NULL, "
            + COL_EAR_TAG + " TEXT NOT NULL, "
            + COL_EYED + " TEXT NOT NULL, "
            + COL_BIRTH_DATE + " TEXT NOT NULL, "
            + COL_BIRTH_CITY + " TEXT NOT NULL, "
            + COL_BIRTH_DISTRICT + " TEXT NOT NULL, "
            + COL_BIRTH_PROVINCE + " TEXT NOT NULL, "
            + COL_CHIPS + " TEXT NOT NULL, "
            + COL_REGISTRATION_ID + " TEXT NOT NULL, "
            + COL_REGISTRATION_CITY + " TEXT NOT NULL, "
            + COL_REGISTRATION_DISTRICT + " TEXT NOT NULL, "
            + COL_REGISTRATION_PROVINCE + " TEXT NOT NULL, "

            // Description
            + COL_TUSKS + " TEXT NOT NULL,"
            + COL_NAILS_FRONT_LEFT + " TEXT NOT NULL,"
            + COL_NAILS_FRONT_RIGHT + " TEXT NOT NULL,"
            + COL_NAILS_REAR_LEFT + " TEXT NOT NULL,"
            + COL_NAILS_REAR_RIGHT + " TEXT NOT NULL,"
            + COL_WEIGHT + " TEXT NOT NULL,"
            + COL_HEIGHT + " TEXT NOT NULL,"

            // Owner
            + COL_OWNERS + " TEXT NOT NULL, "

            // Parentage
            + COL_FATHER_ID + " TEXT NOT NULL, "
            + COL_MOTHER_ID + " TEXT NOT NULL, "
            + COL_CHILDREN_ID + " TEXT NOT NULL,"

            // Documents
            + COL_DOCUMENTS_ID + " TEXT NOT NULL, "

            // Other
            + COL_STATE + " INTEGER DEFAULT 0 "

            + ");";

    static long insert(SQLiteDatabase database, ElephantInfo elephant) {
        return (database.insert(TABLE_NAME, null, getContentValues(elephant)));
    }

    static int update(SQLiteDatabase database, ElephantInfo elephant) {
        return (database.update(TABLE_NAME, getContentValues(elephant), COL_ID + " = " + elephant.id, null));
    }

    static int delete(SQLiteDatabase database, ElephantInfo elephant) {
        ContentValues values = getContentValues(elephant);
        values.put(ElephantTable.COL_STATE, ElephantInfo.State.DELETED.toString());

        return (database.update(TABLE_NAME, values, COL_ID + " = " + elephant.id, null));
    }

    /*static ElephantInfo getElephantByID(SQLiteDatabase database, int id) {
        ElephantInfo info = null;
        Cursor c = database.query(TABLE_NAME, new String[] {"*"}, COL_ID, new String[] {id + ""}, null, null, null);
        if (c.getCount() > 0) {
            info = cursorToValue(c);
        }
        c.close();
        return info;
    }*/

    static List<ElephantInfo> search(SQLiteDatabase database, ElephantInfo info) {
        String query = "SELECT * FROM " + TABLE_NAME + getQuerySearchRestriction(info);
        List<ElephantInfo> results = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);

        Log.i("search_query", "query: " + query);
        Log.i("search_query", "nb result: " + cursor.getCount() + "");
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

    static List<ElephantInfo> getDrafts(SQLiteDatabase database) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_STATE + " = " + ElephantInfo.State.DRAFT;
        List<ElephantInfo> results = new ArrayList<>();
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

    private static ContentValues getContentValues(ElephantInfo elephant) {
        ContentValues values = new ContentValues();

        // Registration
        values.put(COL_NAME, elephant.name);
        values.put(COL_NICKNAME, elephant.nickName);
        values.put(COL_SEX, String.valueOf(elephant.sex));
        values.put(COL_EAR_TAG, String.valueOf(elephant.earTag));
        values.put(COL_EYED, String.valueOf(elephant.eyeD));
        values.put(COL_BIRTH_DATE, elephant.birthDate);
        values.put(COL_BIRTH_CITY, elephant.birthCity);
        values.put(COL_BIRTH_DISTRICT, elephant.birthDistrict);
        values.put(COL_BIRTH_PROVINCE, elephant.birthProvince);
        values.put(COL_CHIPS, elephant.chips1);
        values.put(COL_REGISTRATION_ID, elephant.regID);
        values.put(COL_REGISTRATION_CITY, elephant.regCity);
        values.put(COL_REGISTRATION_DISTRICT, elephant.regDistrict);
        values.put(COL_REGISTRATION_PROVINCE, elephant.regProvince);

        //Description
        values.put(COL_TUSKS, elephant.tusk);
        values.put(COL_NAILS_FRONT_LEFT, elephant.nailsFrontLeft);
        values.put(COL_NAILS_FRONT_RIGHT, elephant.nailsFrontRight);
        values.put(COL_NAILS_REAR_LEFT, elephant.nailsRearLeft);
        values.put(COL_NAILS_REAR_RIGHT, elephant.nailsRearRight);
        values.put(COL_WEIGHT, elephant.weight);
        values.put(COL_HEIGHT, elephant.height);

        // Owners
        values.put(COL_OWNERS, elephant.getOwners());

        // Parentage
        values.put(COL_FATHER_ID, elephant.father);
        values.put(COL_MOTHER_ID, elephant.mother);
        values.put(COL_CHILDREN_ID, elephant.getChildren());

        // Other
        values.put(COL_STATE, elephant.state.toString());

        return (values);
    }

    private static ElephantInfo cursorToValue(Cursor cursor){

        // Registration
        ElephantInfo elephant = new ElephantInfo();
        elephant.id = cursor.getInt(NUM_COL_ID);
        elephant.name = cursor.getString(NUM_COL_NAME);
        elephant.nickName = cursor.getString(NUM_COL_NICKNAME);
        elephant.sex = ElephantInfo.Gender.valueOf(cursor.getString(NUM_COL_SEX));
        elephant.earTag = Boolean.valueOf(cursor.getString(NUM_COL_EAR_TAG));
        elephant.eyeD = Boolean.valueOf(cursor.getString(NUM_COL_EYED));
        elephant.birthDate = cursor.getString(NUM_COL_BIRTH_DATE);
        elephant.birthCity = cursor.getString(NUM_COL_BIRTH_CITY);
        elephant.birthDistrict = cursor.getString(NUM_COL_BIRTH_DISTRICT);
        elephant.birthProvince = cursor.getString(NUM_COL_BIRTH_PROVINCE);
        elephant.chips1 = cursor.getString(NUM_COL_CHIPS);
        elephant.regID = cursor.getString(NUM_COL_REGISTRATION_ID);
        elephant.regCity = cursor.getString(NUM_COL_REGISTRATION_CITY);
        elephant.regDistrict = cursor.getString(NUM_COL_REGISTRATION_DISTRICT);
        elephant.regProvince = cursor.getString(NUM_COL_REGISTRATION_PROVINCE);

        //Description
        elephant.tusk = cursor.getString(NUM_COL_TUSKS);
        elephant.nailsFrontLeft = cursor.getString(NUM_COL_NAILS_FRONT_LEFT);
        elephant.nailsFrontRight = cursor.getString(NUM_COL_NAILS_FRONT_RIGHT);
        elephant.nailsRearLeft = cursor.getString(NUM_COL_NAILS_REAR_LEFT);
        elephant.nailsRearRight = cursor.getString(NUM_COL_NAILS_REAR_RIGHT);
        elephant.weight = cursor.getString(NUM_COL_WEIGHT);
        elephant.height = cursor.getString(NUM_COL_HEIGHT);

        // Owners
        elephant.setOwners(cursor.getString(NUM_COL_OWNERS));

        // Parentage
        elephant.father = cursor.getString(NUM_COL_FATHER_ID);
        elephant.mother = cursor.getString(NUM_COL_MOTHER_ID);
        elephant.setChildren(cursor.getString(NUM_COL_CHILDREN_ID));

        elephant.state = ElephantInfo.State.valueOf(cursor.getString(NUM_COL_STATE).toUpperCase());
        return (elephant);
    }

    private static String getQuerySearchRestriction(ElephantInfo info) {
        List<String> restriction = new ArrayList<String>();

        if (!info.name.isEmpty())
            restriction.add(" WHERE " + ElephantTable.COL_NAME + " LIKE '" + info.name + "%'");
        if (!info.chips1.isEmpty())
            restriction.add(" WHERE " + ElephantTable.COL_CHIPS + " = '" + info.chips1 + "'");
        if (info.sex != ElephantInfo.Gender.UNKNOWN)
            restriction.add(" WHERE " + ElephantTable.COL_SEX + " = '" + info.sex + "'");
        if (!info.regProvince.isEmpty())
            restriction.add(" WHERE " + ElephantTable.COL_REGISTRATION_PROVINCE + " = '" + info.regProvince + "'");
        if (!info.regDistrict.isEmpty())
            restriction.add(" WHERE " + ElephantTable.COL_REGISTRATION_DISTRICT + " = '" + info.regDistrict + "'");
        if (!info.regProvince.isEmpty())
            restriction.add(" WHERE " + ElephantTable.COL_REGISTRATION_PROVINCE + " = '" + info.regProvince + "'");
        if (!info.regCity.isEmpty())
            restriction.add(" WHERE " + ElephantTable.COL_REGISTRATION_CITY + " = '" + info.regCity + "'");

        restriction.add(" WHERE " + ElephantTable.COL_STATE + " != '" + ElephantInfo.State.DELETED.toString() + "'");
        restriction.add(" WHERE " + ElephantTable.COL_STATE + " != '" + ElephantInfo.State.DRAFT.toString() + "'");

        for (int i = 0;  i < restriction.size(); i++) {
            if (i > 0) {
                restriction.set(i, restriction.get(i).replace("WHERE", "AND"));
            }
        }

        String ret = TextUtils.join(" ", restriction);
        Log.i("query_restriction", ret);
        return ret;
    }

}
