package fr.elephantasia.realm.model;

import org.parceler.Parcel;

import java.util.UUID;

import io.realm.ElephantRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by seb on 29/04/2017.
 */

@Parcel(implementations = { ElephantRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { Elephant.class })
public class Elephant extends RealmObject {
  @PrimaryKey
  public String id = UUID.randomUUID().toString();
  public ElephantState state = new ElephantState();
  //Profil
  public String name;
  public String nickName;
  public Sex sex = new Sex();
  public Location currentLoc = new Location();
  public String birthDate;
  public Location birthLoc = new Location();
  // Registration
  public boolean earTag = false;
  public boolean eyeD = false;
  public String chips1;
  public String chips2;
  public String chips3;
  public String regID;
  public Location registrationLoc = new Location();
  // Description
  public String tusk;
  public String nailsFrontLeft;
  public String nailsFrontRight;
  public String nailsRearLeft;
  public String nailsRearRight;
  public String weight;
  public String height;
  public String heightUnit;

  // Parentage
  public Elephant father;
  public Elephant mother;
  public RealmList<Elephant> children;

  // Contact
  public RealmList<Contact> contacts;
}
