package fr.elephantasia.realm.model;

import org.parceler.Parcel;

import java.util.UUID;

import io.realm.ContactRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by seb on 30/04/2017.
 */

@Parcel(implementations = { ContactRealmProxy.class })
public class Contact extends RealmObject {
  @PrimaryKey
  public String id = UUID.randomUUID().toString();
  public String name;

  public String email;
  public String phone;
  public Location address = new Location();

  public boolean owner = false;
  public boolean cornac = false;
  public boolean vet = false;
}
