package fr.elephantasia.utils;

import android.text.TextUtils;

import fr.elephantasia.realm.model.Location;

/**
 * Created by seb on 29/04/2017.
 */

public class LocationHelpers {

  public static String formatProvince(String p) {
    if (!TextUtils.isEmpty(p)) {
      p = p.length() > 3 ? p : p.substring(0, 3);
      p = p.toUpperCase();
    }
    return p;
  }

  public static String concat(Location loc) {
    String p = formatProvince(loc.provinceName);
    String d = loc.districtName;
    String c = loc.cityName;

    String res = TextUtils.isEmpty(p) ? "" : p + " - ";
    res += TextUtils.isEmpty(d) ? "" : d + " - ";
    res += TextUtils.isEmpty(c) ? "" : c + " - ";

    res = res.length() > 3 ? res.substring(0, res.length() - 3) : res;

    return res;
  }
}
