package fr.elephantasia.database.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import javax.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.fr_elephantasia_database_model_ContactRealmProxy;

@Parcel(implementations = {fr_elephantasia_database_model_ContactRealmProxy.class})
public class Contact extends RealmObject
{
  //Columns' names must match attributes' names
  // ID
  @Ignore public static final String ID = "id";
  @Ignore public static final String CUID = "cuid";

  // Profile
  @Ignore public static final String LASTNAME = "lastName";
  @Ignore public static final String FIRSTNAME = "firstName";
  @Ignore public static final String EMAIL = "email";
  @Ignore public static final String PHONE = "phone";
  @Ignore public static final String OWNER = "owner";
  @Ignore public static final String CORNAC = "cornac";
  @Ignore public static final String VET = "vet";

  // Sync
  @Ignore public static final String SYNC_STATE = "syncState";
  @Ignore public static final String DB_STATE = "dbState";

  public enum SyncState {
    Downloaded,
    Pending,
   }

  public enum DbState {
    Created,
    Edited,
    Deleted,
  }

  // ID
  @PrimaryKey public Integer id = -1;
  public String cuid;

  // Profile
  public String firstName;
  public String lastName;
  public String email;
  public String phone;
  public Location address = new Location();
  public boolean owner = false;
  public boolean cornac = false;
  public boolean vet = false;

  // Sync
  public String syncState;
  public String dbState;

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public String getStatus() {
    String res = "";

    res += owner ? "Owner " : "";
    res += cornac ? "Cornac " : "";
    res += vet ? "Vet" : "";

    // Add separator between status
    res = res.replace("r C", "r / C");
    res = res.replace("c V", "c / V");
    return res;
  }

  public void setCuid(String cuid) {
    this.cuid = cuid;
  }

  public void setSyncState(@Nullable SyncState syncState) {
    if (syncState == null) {
      this.syncState = null;
    } else {
      this.syncState = syncState.name();
    }
  }

  public void setDbState(@Nullable DbState dbState) {
    if (dbState == null) {
      this.dbState = null;
    } else {
      this.dbState = dbState.name();
    }
  }

  public JSONObject toJsonObject() throws JSONException {
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("cuid", cuid);
    jsonObject.put("first_name", firstName);
    jsonObject.put("last_name", lastName);
    jsonObject.put("mobile_phone", "911");
    jsonObject.put("home_phone", "911");
    jsonObject.put("street_name", "that street");
    jsonObject.put("email", firstName + "@hotmail.br");
    return jsonObject;
  }

  public String getCuid() {
    return cuid;
  }

  public String getSyncState() {
    return syncState;
  }

  public String getDbState() {
    return dbState;
  }

  public boolean isCreated() {
    return dbState != null && dbState.equals(DbState.Created.name());
  }

  public boolean isEdited() {
    return dbState != null && dbState.equals(DbState.Edited.name());
  }

}
