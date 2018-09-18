package fr.elephantasia.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelpers {
  public static String getString(JSONObject json, String field) throws JSONException {
    return json.isNull(field) ? null : json.getString(field);
  }
}
