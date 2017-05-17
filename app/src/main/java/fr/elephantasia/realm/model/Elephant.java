package fr.elephantasia.realm.model;

import android.text.TextUtils;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.Calendar;
import java.util.UUID;

import fr.elephantasia.realm.parceler.ContactParcelConverter;
import fr.elephantasia.realm.parceler.ElephantParcelConverter;
import io.realm.ElephantRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by seb on 29/04/2017.
 */

@Parcel(implementations = { ElephantRealmProxy.class })
public class Elephant extends RealmObject {
  //Columns' names must match attributes' names
  @Ignore public static final String NAME = "name";
  @Ignore public static final String CHIPS1 = "chips1";
  @Ignore public static final String MALE = "male";
  @Ignore public static final String FEMALE = "female";
  @Ignore public static final String REGISTRATION_LOC = "registrationLoc";

  @PrimaryKey
  public String id = UUID.randomUUID().toString();
  public ElephantState state = new ElephantState();

  //Profil
  public String name;
  public String nickName;
  public boolean male = false;
  public boolean female = false;
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

  @ParcelPropertyConverter(ElephantParcelConverter.class)
  public RealmList<Elephant> children;

  // Contact
  @ParcelPropertyConverter(ContactParcelConverter.class)
  public RealmList<Contact> contacts;

  /**
   * Used to check if an elephant should be saved as draft before
   * the end of AddElephantActivity.
   *
   * @return true if all relevant fields are empty.
   */
  public boolean isEmpty() {
    return TextUtils.isEmpty(name)
        && TextUtils.isEmpty(nickName)
        && currentLoc.isEmpty()
        && TextUtils.isEmpty(birthDate)
        && birthLoc.isEmpty()
        && TextUtils.isEmpty(chips1)
        && TextUtils.isEmpty(regID)
        && registrationLoc.isEmpty()
        && TextUtils.isEmpty(tusk)
        && TextUtils.isEmpty(nailsFrontLeft)
        && TextUtils.isEmpty(nailsFrontRight)
        && TextUtils.isEmpty(nailsRearLeft)
        && TextUtils.isEmpty(nailsRearRight)
        && TextUtils.isEmpty(weight)
        && TextUtils.isEmpty(height)
        && contacts == null
        && father == null
        && mother == null
        && children == null;
  }

  public String getSex() {
    return male ? "Male" : "Female";
  }

  public String getAge() {
    String res = null;

    if (!TextUtils.isEmpty(birthDate)) {
      Integer year = Integer.parseInt(birthDate.substring(0, 4));
      Integer age = Calendar.getInstance().get(Calendar.YEAR) - year;
      res = age.toString();
    }
    return res;
  }
}
