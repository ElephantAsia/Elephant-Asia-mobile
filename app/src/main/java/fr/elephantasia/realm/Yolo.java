package fr.elephantasia.realm;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.YoloRealmProxy;


/**
 * Created by seb on 30/04/2017.
 */


@Parcel(implementations = { YoloRealmProxy.class },
    value = Parcel.Serialization.BEAN,
    analyze = { Yolo.class })
public class Yolo extends RealmObject {
  public String name;

//  public String getName() {
//    return this.name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
}
