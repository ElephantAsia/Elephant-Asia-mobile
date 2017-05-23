package fr.elephantasia.database.model;

import org.parceler.Parcel;

import java.util.UUID;

import io.realm.ContactRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by seb on 30/04/2017.
 */

@Parcel(implementations = { ContactRealmProxy.class })
public class Contact extends RealmObject {
  //Columns' names must match attributes' names
  @Ignore public static final String LASTNAME = "lastName";
  @Ignore public static final String FIRSTNAME = "firstName";
  @Ignore public static final String EMAIL = "email";
  @Ignore public static final String PHONE = "phone";
  @Ignore public static final String OWNER = "owner";
  @Ignore public static final String CORNAC = "cornac";
  @Ignore public static final String VET = "vet";

  @PrimaryKey
  public String id = UUID.randomUUID().toString();
  public String firstName;
  public String lastName;

  public String email;
  public String phone;
  public Location address = new Location();

  public boolean owner = false;
  public boolean cornac = false;
  public boolean vet = false;

  public String getFullName() {
    return firstName + " " + lastName;
  }
}