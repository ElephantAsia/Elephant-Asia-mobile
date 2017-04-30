package fr.elephantasia.realm.model;

import io.realm.RealmObject;

/**
 * Created by seb on 29/04/2017.
 */

public class Location extends RealmObject {
  public String cityName;
  public String districtName;
  public String provinceName;
}
