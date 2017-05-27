package fr.elephantasia.database.model;

import org.apache.http.util.TextUtils;
import org.parceler.Parcel;

import io.realm.LocationRealmProxy;
import io.realm.RealmObject;

/**
 * Created by seb on 29/04/2017.
 */

@Parcel(implementations = {LocationRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {Location.class})
public class Location extends RealmObject {
  public String cityName;
  public String districtName;
  public String provinceName;
  public String streetName;

  public boolean isEmpty() {
    return TextUtils.isEmpty(cityName)
        && TextUtils.isEmpty(districtName)
        && TextUtils.isEmpty(provinceName)
        && TextUtils.isEmpty(streetName);
  }

  public String format() {
    String p = formatProvince();

    String res = TextUtils.isEmpty(p) ? "" : p + " - ";
    res += TextUtils.isEmpty(districtName) ? "" : districtName + " - ";
    res += TextUtils.isEmpty(cityName) ? "" : cityName + " - ";

    res = res.length() > 3 ? res.substring(0, res.length() - 3) : res;

    return res;
  }

  private String formatProvince() {
    String p = provinceName;

    if (!android.text.TextUtils.isEmpty(provinceName)) {

      p = p.length() > 3 ? p.substring(0, 3) : p;
      p = p.toUpperCase();
    }
    return p;
  }


}
