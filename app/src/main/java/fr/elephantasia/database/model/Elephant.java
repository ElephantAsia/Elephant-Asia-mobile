package fr.elephantasia.database.model;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

import fr.elephantasia.database.parceler.ContactParcelConverter;
import fr.elephantasia.database.parceler.ElephantParcelConverter;
import fr.elephantasia.utils.DateHelpers;
import fr.elephantasia.utils.JsonHelpers;
import fr.elephantasia.utils.TextHelpers;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.fr_elephantasia_database_model_ElephantRealmProxy;

@Parcel(implementations = {fr_elephantasia_database_model_ElephantRealmProxy.class})
public class Elephant extends RealmObject
{
  // IMPORTANT: Columns' names must match attributes' names
  // ID
  @Ignore public static final String ID = "id";
  @Ignore public static final String CUID = "cuid";

  // Profile
  @Ignore public static final String NAME = "name";
  @Ignore public static final String CHIPS1 = "chips1";
  @Ignore public static final String SEX = "sex";

  // Registration
  @Ignore public static final String MTE_OWNER = "mteOwner";
  @Ignore public static final String MTE_NUMBER = "mteNumber";

  // Sync
  @Ignore public static final String DRAFT = "draft";
  @Ignore public static final String SYNC_STATE = "syncState";
  @Ignore public static final String DB_STATE = "dbState";

  // Metadata
  @Ignore public static final String LAST_VISITED = "lastVisited";

  @Ignore public static final String JOURNAL_STATE = "journalState";

  public enum SyncState {
    Downloaded,
    Pending,
  }

  public enum DbState {
    Created,
    Edited,
    Deleted
  }

  public enum JournalState {
    NoteAdded
  }

  @PrimaryKey
  public Integer id = -1;

  //Profil
  public String name;
  public String nickName;
  public String sex; // TODO: enum instead of String
  public Location currentLoc = new Location();
  public String temperament;
  public Date birthDate;
  public Integer estimatedBirthYear = null;
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
  public String tusk = "None";
  public String nailsFrontLeft = "0"; //int
  public String nailsFrontRight = "0"; //int
  public String nailsRearLeft = "0"; //int
  public String nailsRearRight = "0"; //int
  public String weight = "0"; //int
  public String girth;
  public String weightUnit;
  public String height = "0"; //int
  public String heightUnit;

  // Parentage
  public Elephant father;
  public Elephant mother;

  @ParcelPropertyConverter(ElephantParcelConverter.class)
  public RealmList<Elephant> children = new RealmList<>();
  @ParcelPropertyConverter(ContactParcelConverter.class)
  public RealmList<Contact> contacts = new RealmList<>();

  // Sync
  public boolean draft = false;
  public String syncState;
  public String dbState;
  public String journalState;

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
    temperament = JsonHelpers.getString(e, "temperament");
    estimatedBirthYear = e.optInt("estimated_birth_year");
    currentLoc = null;
    birthLoc = null;
    birthDate = DateHelpers.BuildDateFromStringWithoutHours(JsonHelpers.getString(e, "birth_date"));

    // Registration
    earTag = JsonHelpers.getBoolean(e, "ear_tag");
    eyeD = JsonHelpers.getBoolean(e, "eye_d");
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
        && birthDate == null
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

  public boolean hasChildren() {
    return children != null && !children.isEmpty();
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

  public String getBirthDateText() {
    if (birthDate != null ){
      return DateHelpers.FriendlyUserStringDateWithoutHours(birthDate);
    }
    return "";
  }

  public String getTemperamentText() {
    if (temperament != null) {
      return temperament;
    }
    return "-";
  }

  public String getHeightText() {

    if (!TextUtils.isEmpty(height)) {
      return height + " cm"; // + heightUnit;
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

    if (birthDate != null) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(birthDate);
      Integer year = cal.get(Calendar.YEAR);
      Integer age = Calendar.getInstance().get(Calendar.YEAR) - year;
      res = age.toString();
    }
    return res;
  }

  public String getBirthdateText() {
    String res = "-";

    if (birthDate != null) {
      res = DateHelpers.FriendlyUserStringDateWithoutHours(birthDate);
    }
    return res;
  }

  public String getRegIDText() {
    if (!TextUtils.isEmpty(regID)) {
      return regID;
    }
    return "-";
  }

  public String getChips1Text() {
    if (!TextUtils.isEmpty(chips1)) {
      return chips1;
    }
    return "-";
  }

  public String getNicknameText() {
    if (!TextUtils.isEmpty(nickName)) {
      return nickName;
    }
    return "-";
  }

  public boolean isPresentInServerDb() {
    return cuid != null;
  }

  public void copy(Elephant e) {
    name = e.name;
    nickName = e.nickName;
    sex = e.sex;
    cuid = e.cuid;
    temperament = e.temperament;
    birthDate = e.birthDate;
    estimatedBirthYear = e.estimatedBirthYear;
    weight = e.weight;
    height = e.height;
    nailsFrontLeft = e.nailsFrontLeft;
    nailsFrontRight = e.nailsFrontRight;
    nailsRearLeft = e.nailsRearLeft;
    nailsRearRight = e.nailsRearRight;
    earTag = e.earTag;
    eyeD = e.eyeD;
    chips1 = e.chips1;
    chips2 = e.chips2;
    chips3 = e.chips3;
    tusk = e.tusk;
  }

  public JSONObject toJsonObject(@Nullable Map<String, Contact> added) throws JSONException {
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("name", name);
    jsonObject.put("nickname", nickName);
    jsonObject.put("sex", sex);
    jsonObject.put("cuid", cuid);
    jsonObject.put("temperament", temperament);
    jsonObject.put("birth_date", birthDate);
    jsonObject.put("estimated_birth_year", estimatedBirthYear);
    jsonObject.put("weight", weight);
    jsonObject.put("height", height);
    jsonObject.put("nail_front_left", nailsFrontLeft);
    jsonObject.put("nail_front_right", nailsFrontRight);
    jsonObject.put("nail_rear_left", nailsRearLeft);
    jsonObject.put("nail_rear_right", nailsRearRight);
    jsonObject.put("ear_tag", earTag);
    jsonObject.put("eye_d", eyeD);
    jsonObject.put("microchip_1", chips1);
    jsonObject.put("microchip_2", chips2);
    jsonObject.put("microchip_3", chips3);
    jsonObject.put("tusk", tusk);

    /* JSONArray contactsArray = new JSONArray();
    for (Contact contact : contacts) {
      JSONObject contactObj = new JSONObject();
      // contact.setSyncState(Pending) => upload response (done?)
      // contact.setSyncState(null) => download
      if (added != null) {
        added.put(contact.getCuid(), contact);
      }
      contactObj.put("cuid", contact.getCuid());
      contactsArray.put(contactObj);
    }
    jsonObject.put("contacts", contactsArray);*/
    return jsonObject;
  }

  public void wasUploaded(final String cuid) {
    if (!isPresentInServerDb()) {
      try {
        this.cuid = cuid;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    // syncState = Elephant.SyncState.Pending.name();
    syncState = null;
    dbState = null;
    for (Contact contact : contacts) {
      Log.w("wasuploaded_contact", contact.getFullName());
      contact.setSyncState(Contact.SyncState.Pending);
      contact.setDbState(null);
    }
  }

  public void edit() {
    syncState = Elephant.SyncState.Pending.name();
    dbState = null;
    for (Contact contact : contacts) {
      Log.w("wasuploaded_contact", contact.getFullName());
      contact.setSyncState(Contact.SyncState.Pending);
      contact.setDbState(null);
    }
  }

  /* public void addContact(Contact contact) {
    contacts.add(contact);
  } */

}
