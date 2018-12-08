package fr.elephantasia.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelpers {

  static public String getString(JSONObject json, String field) throws JSONException {
    return json.isNull(field) ? null : json.getString(field);
  }

  static public boolean getBoolean(JSONObject jsonObject, String field) throws JSONException {
    return !jsonObject.isNull(field) && jsonObject.getBoolean(field);
  }

}
