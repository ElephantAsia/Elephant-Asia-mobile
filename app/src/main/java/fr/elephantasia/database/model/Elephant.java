package fr.elephantasia.database.model;

import android.text.TextUtils;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.Calendar;
import java.util.Date;

import fr.elephantasia.database.parceler.ContactParcelConverter;
import fr.elephantasia.database.parceler.ElephantParcelConverter;
import io.realm.ElephantRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by seb on 29/04/2017.
 */

@Parcel(implementations = {ElephantRealmProxy.class})
public class Elephant extends RealmObject {
  // Columns' names must match attributes' names
  @Ignore
  public static final String ID = "id";
  @Ignore
  public static final String STATE_DRAFT = "state.draft";
  @Ignore
  public static final String STATE_PENDING = "state.pending";
  @Ignore
  public static final String STATE_DELETED = "state.deleted";
  @Ignore
  public static final String STATE_LOCAL = "state.local";
  @Ignore
  public static final String STATE_REFUSED = "state.refused";
  @Ignore
  public static final String NAME = "name";
  @Ignore
  public static final String CHIPS1 = "chips1";
  @Ignore
  public static final String MALE = "male";
  @Ignore
  public static final String FEMALE = "female";
  @Ignore
  public static final String REGISTRATION_LOC = "registrationLoc";
  @Ignore
  public static final String LAST_VISITED = "lastVisited";


  @PrimaryKey
  public Integer id = -1;
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
  public boolean mteOwner = false;
  public String mteNumber;
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
  public String girth;
  public String weightUnit;
  public String height;
  public String heightUnit;

  // Parentage
  public Elephant father;
  public Elephant mother;

  @ParcelPropertyConverter(ElephantParcelConverter.class)
  public RealmList<Elephant> children = new RealmList<>();

  // Contact
  @ParcelPropertyConverter(ContactParcelConverter.class)
  public RealmList<Contact> contacts = new RealmList<>();

  // Metadata
  @Index
  public Date lastVisited;

  /**
   * Used to check if an elephant should be saved as draft before
   * the end of EditElephantActivity.
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
        && TextUtils.isEmpty(weight)
        && TextUtils.isEmpty(height)
        && contacts.isEmpty()
        && father == null
        && mother == null
        && children.isEmpty();
  }

  /**
   * BW =  (21.11 x G ) – 4,425
   * http://www.asianelephantresearch.com/about-elephant-health-care-p2.php
   *
   * @param newGirth the new girth set
   */
  public void setWeight(String newGirth) {

    if (girth != null) {
      if (girth.isEmpty()) {
        weight = null;
        girth = null;
      } else {
        weight = Integer.toString((18 * Integer.parseInt(newGirth)) - 3336);
      }
    }
  }

  public String getGenderText() {
    return male ? "Male" : "Female";
  }

  public String getHeightText() {

    if (!TextUtils.isEmpty(height)) {
      return height + " " + heightUnit;
    }

    return "N/A";
  }

  public String getWeightText() {

    if (!TextUtils.isEmpty(weight)) {
      return weight + " kg";
    }

    return "N/A";
  }

  public String getAgeText() {
    String res = null;

    if (!TextUtils.isEmpty(birthDate)) {
      Integer year = Integer.parseInt(birthDate.substring(0, 4));
      Integer age = Calendar.getInstance().get(Calendar.YEAR) - year;
      res = age.toString();
    }
    return res;
  }

  public String getRegIDText() {
    if (!TextUtils.isEmpty(regID)) {
      return regID;
    }
    return "N/A";
  }

  public String getStateText() {
    if (state.pending) {
      return "sync pending";
    } else if (state.draft) {
      return "draft";
    } else if (state.local) {
      return "not sync";
    } else if (state.refused) {
      return "sync refused";
    }
    return "synced";
  }
}
