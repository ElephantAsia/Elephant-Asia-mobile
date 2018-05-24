package fr.elephantasia.database.model;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.Calendar;
import java.util.Date;

import fr.elephantasia.database.parceler.ContactParcelConverter;
import fr.elephantasia.database.parceler.ElephantParcelConverter;
import fr.elephantasia.utils.JsonHelpers;
import fr.elephantasia.utils.TextHelpers;
import io.realm.ElephantRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

@Parcel(implementations = {ElephantRealmProxy.class})
public class Elephant extends RealmObject {
  // IMPORTANT: Columns' names must match attributes' names

  // Profil
  @Ignore
  public static final String ID = "id";
  @Ignore
  public static final String NAME = "name";
  @Ignore
  public static final String CHIPS1 = "chips1";
  @Ignore
  public static final String SEX = "sex";

  // Registration
  @Ignore
  public static final String MTE_OWNER = "mteOwner";
  @Ignore
  public static final String MTE_NUMBER = "mteNumber";

  // Sync
  @Ignore
  public static final String DRAFT = "draft";
  @Ignore
  public static final String SYNC_STATE = "syncState";
  @Ignore
  public static final String DB_STATE = "dbState";

  // Metadata
  @Ignore
  public static final String LAST_VISITED = "lastVisited";

  // Server data
  @Ignore
  public static final String CUID = "cuid";

  public enum SyncState {
    // Created,
    // Edited,
    Pending,
    Accepted,
    Rejected,
  }

  public enum DbState {
    Created,
    Edited,
    Deleted,
  }

  @PrimaryKey
  public Integer id = -1;

  //Profil
  public String name;
  public String nickName;
  public String sex; // TODO: enum instead of String
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

  // Sync
  public boolean draft = false;
  public String syncState;
  public String dbState;

  // Metadata
  @Index
  public Date lastVisited;

  // Server data
  public String cuid;

  public Elephant() {}

  public Elephant(JSONObject e) throws JSONException {
    // Profil
    name = JsonHelpers.getString(e, "name");
    nickName = JsonHelpers.getString(e, "nickname");
    sex = JsonHelpers.getString(e, "sex");
    currentLoc = null;
    birthLoc = null;
    birthDate = JsonHelpers.getString(e, "birth_date");

    // Registration
    earTag = JsonHelpers.getString(e, "ear_tag") != null;
    eyeD = JsonHelpers.getString(e, "eye_d") != null;
    mteOwner = false;
    mteNumber = null;
    chips1 = JsonHelpers.getString(e, "microchip_1");
    chips2 = JsonHelpers.getString(e, "microchip_2");
    chips3 = JsonHelpers.getString(e, "microchip_3");
    regID = null;
    registrationLoc = null;

    // Description
    tusk = JsonHelpers.getString(e, "tusk");
    nailsFrontLeft = JsonHelpers.getString(e, "nail_front_left");
    nailsFrontRight = JsonHelpers.getString(e, "nail_front_right");
    nailsRearLeft = JsonHelpers.getString(e, "nail_rear_left");
    nailsRearRight = JsonHelpers.getString(e, "nail_rear_right");
    weight = JsonHelpers.getString(e, "weight");
    girth = null;
    weightUnit = null;
    height = JsonHelpers.getString(e, "height");
    heightUnit = null;

    // Server data:
    cuid = JsonHelpers.getString(e, "cuid");
  }

  //TODO: update this method when we are done modyfing elephant attributes
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
    if (sex != null) {
      return sex.equals("M") ? "Male" : "Female";
    }
    return "";
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
      res = age.toString();
    }
    return res;
  }

  public String getRegIDText() {
    if (!TextUtils.isEmpty(regID)) {
      return regID;
    }
    return "-";
  }

  public JSONObject toJsonObject() throws JSONException {
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("name", name);
    jsonObject.put("nickname", nickName);
    jsonObject.put("id", id);
    jsonObject.put("sex", sex);
    jsonObject.put("cuid", "ez");
    return jsonObject;
  }
}
