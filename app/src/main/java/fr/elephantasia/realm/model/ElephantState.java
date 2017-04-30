package fr.elephantasia.realm.model;

import org.parceler.Parcel;

import io.realm.ElephantStateRealmProxy;
import io.realm.RealmObject;

/**
 * Created by seb on 29/04/2017.
 */

@Parcel(implementations = { ElephantStateRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { ElephantState.class })
public class ElephantState extends RealmObject {
  public boolean pending = false;
  public boolean deleted = false;
  public boolean local = false;
  public boolean draft = false;
}
