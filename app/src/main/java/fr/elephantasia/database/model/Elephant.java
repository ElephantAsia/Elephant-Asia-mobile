package fr.elephantasia.database.model;

import android.text.TextUtils;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.Calendar;
import java.util.Date;

import fr.elephantasia.database.parceler.ContactParcelConverter;
import fr.elephantasia.database.parceler.ElephantParcelConverter;
import fr.elephantasia.utils.TextHelpers;
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
  public static final String STATE = "state";
  @Ignore
  public static final String NAME = "name";
  @Ignore
  public static final String CHIPS1 = "chips1";
  @Ignore
  public static final String MALE = "male";
  @Ignore
  public static final String FEMALE = "female";
  @Ignore
  public static final String LAST_VISITED = "lastVisited";


  public enum StateValue {
    draft, // saved locally as draft
    saved, // saved locally
    modified, // elephant sync from server modified
    pending, // creation / modification wating for approval
    rejected, // creation / modification rejected
    validated, // creation / modification accepted,
    synced, // elephant unchanged from server
    deleted // elephant synced from server deleted locally
  }

  @PrimaryKey
  public Integer id = -1;
  // State possible values are detailled in StateValue enum
  public String state;

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

  public String getNameText() {
    return TextHelpers.capitalize(name);
  }

  public String getGenderText() {
    return male ? "Male" : "Female";
  }

  public String getHeightText() {

    if (!TextUtils.isEmpty(height)) {
      return height + " " + heightUnit;
    }

    return "-";
  }

  public String getWeightText() {

    if (!TextUtils.isEmpty(weight)) {
      return weight + " kg";
    }

    return "-";
  }

  public String getAgeText() {
    String res = "-";

    if (!TextUtils.isEmpty(birthDate)) {
      Integer year = Integer.parseInt(birthDate.substring(0, 4));
      Integer age = Calendar.getInstance().get(Calendar.YEAR) - year;
      res = age.toString() + "y";
    }
    return res;
  }

  public String getRegIDText() {
    if (!TextUtils.isEmpty(regID)) {
      return regID;
    }
    return "-";
  }

  public String getStateText() {
    if (state.equals(StateValue.draft.name())) {
      return StateValue.draft.name();
    } else if (state.equals(StateValue.saved.name())) {
      return StateValue.saved.name();
    } else if (state.equals(StateValue.modified.name())) {
      return StateValue.modified.name();
    } else if (state.equals(StateValue.pending.name())) {
      return StateValue.pending.name();
    } else if (state.equals(StateValue.rejected.name())) {
      return StateValue.rejected.name();
    } else if (state.equals(StateValue.validated.name())) {
      return StateValue.validated.name();
    } else if (state.equals(StateValue.deleted.name())) {
      return StateValue.deleted.name();
    }
    return "synced";
  }
}
