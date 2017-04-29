package fr.elephantasia.realm;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by seb on 29/04/2017.
 */


public class Elephant extends RealmObject {
  public State state = new State();
  //Profil
  public String name;
  public String nickName;
  public Sex sex = new Sex();
  public Location currentLoc = new Location();
  public String birthDate;
  public Location birthLoc;
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
  // Parentage
  public Elephant father;
  public Elephant mother;
  @PrimaryKey
  private String id = UUID.randomUUID().toString();
  private RealmList<Elephant> children;

  // Owner
  private String owners;
}
