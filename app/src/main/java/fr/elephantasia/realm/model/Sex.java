package fr.elephantasia.realm.model;

import org.parceler.Parcel;

import io.realm.LocationRealmProxy;
import io.realm.RealmObject;
import io.realm.SexRealmProxy;
import io.realm.annotations.Ignore;

/**
 * Created by seb on 29/04/2017.
 */


@Parcel(implementations = {SexRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { Sex.class })
public class Sex extends RealmObject {
  @Ignore
  public final static String MALE = "male";
  @Ignore
  public final static String FEMALE = "female";
  public String gender;

  public void setSex(String sex) {
    this.gender = sex;
  }
}
