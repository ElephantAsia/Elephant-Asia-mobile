package fr.elephantasia.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.utils.ElephantInfo;

class ElephantTable {

  static final String TABLE_NAME = "Elephants";

  // Auto set
  private static final String ID = "ID";
  private static final String STATE = "state";

  // Profil
  private static final String NAME = "name";
  private static final String NICKNAME = "nickname";
  private static final String SEX = "sex";
  private static final String CURRENT_CITY = "current_city";
  private static final String CURRENT_DISTRICT = "current_district";
  private static final String CURRENT_PROVINCE = "current_province";
  private static final String BIRTH_DATE = "birthdate";
  private static final String BIRTH_CITY = "birthdate_city";
  private static final String BIRTH_DISTRICT = "birthdate_district";
  private static final String BIRTH_PROVINCE = "birthdate_province";

  // Registration
  private static final String EAR_TAG = "ear_tag";
  private static final String EYED = "eyed";
  private static final String CHIPS = "chips";
  private static final String REGISTRATION_ID = "registration_id";
  private static final String REGISTRATION_CITY = "registration_city";
  private static final String REGISTRATION_DISTRICT = "registration_district";
  private static final String REGISTRATION_PROVINCE = "registration_province";

  // Description
  private static final String TUSKS = "tusks";
  private static final String NAILS_FRONT_LEFT = "nails_front_left";
  private static final String NAILS_FRONT_RIGHT = "nails_front_right";
  private static final String NAILS_REAR_LEFT = "nails_rear_left";
  private static final String NAILS_REAR_RIGHT = "nails_rear_right";
  private static final String WEIGHT = "weight";
  private static final String HEIGHT = "height";

  // Owners
  private static final String OWNERS = "owners";

  // Parentage
  private static final String FATHER_ID = "father_id";
  private static final String MOTHER_ID = "mother_id";
  private static final String CHILDREN_ID = "children_id";

  // Documents
//  private static final String DOCUMENTS_ID = "documents_id";

  static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("

      // Auto set
      + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + STATE + " INTEGER DEFAULT 0, "

      // Profil
      + NAME + " TEXT NOT NULL, "
      + NICKNAME + " TEXT NOT NULL, "
      + SEX + " TEXT NOT NULL, "
      + CURRENT_CITY + " TEXT NOT NULL, "
      + CURRENT_DISTRICT + " TEXT NOT NULL, "
      + CURRENT_PROVINCE + " TEXT NOT NULL, "
      + BIRTH_DATE + " TEXT NOT NULL, "
      + BIRTH_CITY + " TEXT NOT NULL, "
      + BIRTH_DISTRICT + " TEXT NOT NULL, "
      + BIRTH_PROVINCE + " TEXT NOT NULL, "

      // Registration
      + EAR_TAG + " TEXT NOT NULL, "
      + EYED + " TEXT NOT NULL, " + CHIPS + " TEXT NOT NULL, "
      + REGISTRATION_ID + " TEXT NOT NULL, "
      + REGISTRATION_CITY + " TEXT NOT NULL, "
      + REGISTRATION_DISTRICT + " TEXT NOT NULL, "
      + REGISTRATION_PROVINCE + " TEXT NOT NULL, "

      // Description
      + TUSKS + " TEXT NOT NULL,"
      + NAILS_FRONT_LEFT + " TEXT NOT NULL,"
      + NAILS_FRONT_RIGHT + " TEXT NOT NULL,"
      + NAILS_REAR_LEFT + " TEXT NOT NULL,"
      + NAILS_REAR_RIGHT + " TEXT NOT NULL,"
      + WEIGHT + " TEXT NOT NULL,"
      + HEIGHT + " TEXT NOT NULL,"

      // Owner
      + OWNERS + " TEXT NOT NULL, "

      // Parentage
      + FATHER_ID + " TEXT NOT NULL, "
      + MOTHER_ID + " TEXT NOT NULL, "
      + CHILDREN_ID + " TEXT NOT NULL"

      // Documents
//      + DOCUMENTS_ID + " TEXT NOT NULL, "

      + ");";

  static long insert(SQLiteDatabase database, ElephantInfo elephant) {
    return (database.insert(TABLE_NAME, null, getContentValues(elephant)));
  }

  static int update(SQLiteDatabase database, ElephantInfo elephant) {
    return (database.update(TABLE_NAME, getContentValues(elephant), ID + " = " + elephant.id, null));
  }

  static int delete(SQLiteDatabase database, ElephantInfo elephant) {
    ContentValues values = getContentValues(elephant);
    values.put(ElephantTable.STATE, ElephantInfo.State.DELETED.toString());

    return (database.update(TABLE_NAME, values, ID + " = " + elephant.id, null));
  }

    /*static ElephantInfo getElephantByID(SQLiteDatabase database, int id) {
        ElephantInfo info = null;
        Cursor c = database.query(TABLE_NAME, new String[] {"*"}, ID, new String[] {id + ""}, null, null, null);
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
    String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + STATE + " = " + ElephantInfo.State.DRAFT;
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

    // Auto set
    values.put(STATE, elephant.state.toString());

    // Profil
    values.put(NAME, elephant.name);
    values.put(NICKNAME, elephant.nickName);
    values.put(SEX, String.valueOf(elephant.sex));
    values.put(CURRENT_CITY, elephant.currentCity);
    values.put(CURRENT_DISTRICT, elephant.currentDistrict);
    values.put(CURRENT_PROVINCE, elephant.currentProvince);
    values.put(BIRTH_DATE, elephant.birthDate);
    values.put(BIRTH_CITY, elephant.birthCity);
    values.put(BIRTH_DISTRICT, elephant.birthDistrict);
    values.put(BIRTH_PROVINCE, elephant.birthProvince);

    // Registration
    values.put(EAR_TAG, String.valueOf(elephant.earTag));
    values.put(EYED, String.valueOf(elephant.eyeD));
    values.put(CHIPS, elephant.chips1);
    values.put(REGISTRATION_ID, elephant.regID);
    values.put(REGISTRATION_CITY, elephant.regCity);
    values.put(REGISTRATION_DISTRICT, elephant.regDistrict);
    values.put(REGISTRATION_PROVINCE, elephant.regProvince);

    //Description
    values.put(TUSKS, elephant.tusk);
    values.put(NAILS_FRONT_LEFT, elephant.nailsFrontLeft);
    values.put(NAILS_FRONT_RIGHT, elephant.nailsFrontRight);
    values.put(NAILS_REAR_LEFT, elephant.nailsRearLeft);
    values.put(NAILS_REAR_RIGHT, elephant.nailsRearRight);
    values.put(WEIGHT, elephant.weight);
    values.put(HEIGHT, elephant.height);

    // Owners
    values.put(OWNERS, elephant.getOwners());

    // Parentage
    values.put(FATHER_ID, elephant.father);
    values.put(MOTHER_ID, elephant.mother);
    values.put(CHILDREN_ID, elephant.getChildren());

    return (values);
  }

  private static ElephantInfo cursorToValue(Cursor cursor) {
    ElephantInfo e = new ElephantInfo();

    // Auto set
    e.state = ElephantInfo.State.valueOf(cursor.getString(cursor.getColumnIndex(STATE)).toUpperCase());

    // Profil
    e.id = cursor.getInt(cursor.getColumnIndex(ID));
    e.name = cursor.getString(cursor.getColumnIndex(NAME));
    e.nickName = cursor.getString(cursor.getColumnIndex(NICKNAME));
    e.sex = ElephantInfo.Gender.valueOf(cursor.getString(cursor.getColumnIndex(SEX)));
    e.currentCity = cursor.getString(cursor.getColumnIndex(CURRENT_CITY));
    e.currentDistrict = cursor.getString(cursor.getColumnIndex(CURRENT_DISTRICT));
    e.currentProvince = cursor.getString(cursor.getColumnIndex(CURRENT_PROVINCE));
    e.birthDate = cursor.getString(cursor.getColumnIndex(BIRTH_DATE));
    e.birthCity = cursor.getString(cursor.getColumnIndex(BIRTH_CITY));
    e.birthDistrict = cursor.getString(cursor.getColumnIndex(BIRTH_DISTRICT));
    e.birthProvince = cursor.getString(cursor.getColumnIndex(BIRTH_PROVINCE));

    // Registration
    e.earTag = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(EAR_TAG)));
    e.eyeD = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(EYED)));
    e.chips1 = cursor.getString(cursor.getColumnIndex(CHIPS));
    e.regID = cursor.getString(cursor.getColumnIndex(REGISTRATION_ID));
    e.regCity = cursor.getString(cursor.getColumnIndex(REGISTRATION_CITY));
    e.regDistrict = cursor.getString(cursor.getColumnIndex(REGISTRATION_DISTRICT));
    e.regProvince = cursor.getString(cursor.getColumnIndex(REGISTRATION_PROVINCE));

    //Description
    e.tusk = cursor.getString(cursor.getColumnIndex(TUSKS));
    e.nailsFrontLeft = cursor.getString(cursor.getColumnIndex(NAILS_FRONT_LEFT));
    e.nailsFrontRight = cursor.getString(cursor.getColumnIndex(NAILS_FRONT_RIGHT));
    e.nailsRearLeft = cursor.getString(cursor.getColumnIndex(NAILS_REAR_LEFT));
    e.nailsRearRight = cursor.getString(cursor.getColumnIndex(NAILS_REAR_RIGHT));
    e.weight = cursor.getString(cursor.getColumnIndex(WEIGHT));
    e.height = cursor.getString(cursor.getColumnIndex(HEIGHT));

    // Owners
    e.setOwners(cursor.getString(cursor.getColumnIndex(OWNERS)));

    // Parentage
    e.father = cursor.getString(cursor.getColumnIndex(FATHER_ID));
    e.mother = cursor.getString(cursor.getColumnIndex(MOTHER_ID));
    e.setChildren(cursor.getString(cursor.getColumnIndex(CHILDREN_ID)));

    return (e);
  }

  private static String getQuerySearchRestriction(ElephantInfo info) {
    List<String> restriction = new ArrayList<>();

    if (!info.name.isEmpty())
      restriction.add(" WHERE " + ElephantTable.NAME + " LIKE '" + info.name + "%'");
    if (!info.chips1.isEmpty())
      restriction.add(" WHERE " + ElephantTable.CHIPS + " = '" + info.chips1 + "'");
    if (info.sex != ElephantInfo.Gender.UNKNOWN)
      restriction.add(" WHERE " + ElephantTable.SEX + " = '" + info.sex + "'");
    if (!info.regProvince.isEmpty())
      restriction.add(" WHERE " + ElephantTable.REGISTRATION_PROVINCE + " = '" + info.regProvince + "'");
    if (!info.regDistrict.isEmpty())
      restriction.add(" WHERE " + ElephantTable.REGISTRATION_DISTRICT + " = '" + info.regDistrict + "'");
    if (!info.regProvince.isEmpty())
      restriction.add(" WHERE " + ElephantTable.REGISTRATION_PROVINCE + " = '" + info.regProvince + "'");
    if (!info.regCity.isEmpty())
      restriction.add(" WHERE " + ElephantTable.REGISTRATION_CITY + " = '" + info.regCity + "'");

    restriction.add(" WHERE " + ElephantTable.STATE + " != '" + ElephantInfo.State.DELETED.toString() + "'");
    restriction.add(" WHERE " + ElephantTable.STATE + " != '" + ElephantInfo.State.DRAFT.toString() + "'");

    for (int i = 0; i < restriction.size(); i++) {
      if (i > 0) {
        restriction.set(i, restriction.get(i).replace("WHERE", "AND"));
      }
    }

    String ret = TextUtils.join(" ", restriction);
    Log.i("query_restriction", ret);
    return ret;
  }
}