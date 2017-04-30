package fr.elephantasia.realm.model;

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
}
