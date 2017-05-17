package fr.elephantasia.realm.model;

import org.apache.http.util.TextUtils;
import org.parceler.Parcel;

import io.realm.LocationRealmProxy;
import io.realm.RealmObject;

/**
 * Created by seb on 29/04/2017.
 */

@Parcel(implementations = {LocationRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { Location.class })
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
}
