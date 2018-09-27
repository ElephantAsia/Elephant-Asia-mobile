package fr.elephantasia.activities.sync.network;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.network.RequestAsyncTask;

public class SyncToServerAsyncRequest extends RequestAsyncTask<Boolean> {

  static private final String URL = "/sync/upload";

  private List<Elephant> elephants;
  private Map<String, Contact> contacts = new HashMap<>();
  private Listener listener;
  private JSONArray serialized;

  public SyncToServerAsyncRequest(List<Elephant> elephants, Listener listener) {
    this.elephants = elephants;
    this.listener = listener;
    this.serialized = new JSONArray();
  }

  @Override
  protected Map<String, String> getHeader() {
    Map<String, String> header = new HashMap<>();
    try {
      String authToken = listener.getAuthToken();
      header.put("Api-Key", authToken);
      Log.w(getClass().getName(), "auth_token=" + authToken);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return header;
  }

  @Override
  protected byte[] getBody() {
    return serialized.toString().getBytes();
  }

  @Nullable
  @Override
  protected Boolean doInBackground(@Nullable Void... params) {
    serialize();
    if (!upload())
      return false;
    updateLocalDb();
    return true;
  }

  private void serialize() {
    this.listener.onSerializing();

    for (int i = 0 ; i < elephants.size() ; ++i) {
      publishProgress(i);
      try {
        Thread.sleep(250); // demo
      } catch (Exception e) {}
      try {
        Elephant e = elephants.get(i);
        String action = ((e.dbState.equals(Elephant.DbState.Created.name())) ? "CREATE" : "EDIT");
        JSONObject obj = e.toJsonObject(contacts);
        obj.put("action", action);
        obj.put("type", "elephant");
        serialized.put(obj);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    for (Map.Entry<String, Contact> entry : contacts.entrySet()) {
      Contact c = entry.getValue();
      String action = ((c.isCreated()) ? "CREATE" : (c.isEdited()) ? "EDIT" : null);
      if (c.getSyncState() == null && action != null) {
        try {
          JSONObject obj = c.toJsonObject();
          obj.put("action", action);
          obj.put("type", "contact");
          serialized.put(obj);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    Log.w("serialized", serialized.toString());
    publishProgress(elephants.size());
    try {
      Thread.sleep(500); // demo
    } catch (Exception e) {}
  }

  private boolean upload() {
    this.listener.onUploading();

    POSTJSON(URL);
    if (getResponseCode() != null && getResponseCode() ==  200) {
      try {
        Thread.sleep(500); // demo
      } catch (Exception e) {}
      return true;
    }
    return false;
  }

  private void updateLocalDb() {
    DatabaseController dbController = new DatabaseController();
    JSONArray response = getJsonArray();

    listener.onUpdatingLocalDb();
    dbController.beginTransaction();
    Log.w("reponse", response.toString());
    for (int i = 0 ; i < elephants.size() ; ++i) {
      publishProgress(i);
      try {
        Thread.sleep(250); // demo
        Elephant elephant = elephants.get(i);
        elephant.wasUploaded(response.getJSONObject(i).getString("cuid"));
        dbController.insertOrUpdate(elephant);
      } catch (Exception e) {
        e.printStackTrace();
      }
//      elephant.syncState = Elephant.SyncState.Pending.name();
//      elephant.dbState = null;
//      if (!elephant.isPresentInServerDb()) {
//        try {
//          elephant.cuid = response.getJSONObject(i).getString("cuid");
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//      }
    }
    // for each contact in contacts
    //  contact.setSyncState(Contact.SyncState.Pending);
    //  contact.setDbState(null);
    //  insertOrUpdate(contact)
    publishProgress(elephants.size());
    dbController.commitTransaction();
    try {
      Thread.sleep(500); // demo
    } catch (Exception e) {}
  }

  @Override
  protected void onProgressUpdate(Integer... progress) {
    super.onProgressUpdate(progress);
    int i = 0;
    int type = 0;
    if (progress.length > 1) {
      type = progress[1];
    }
    Integer value = progress[0];
    switch (type) {
      case PROGRESS_UPLOAD:
        i = value;
        break;
      case PROGRESS_RESPONSE:
        break;
      default:
        i = (progress[0] > 0) ? (int) (((double) progress[0] / (double) elephants.size()) * 100) : 0;
        // + contacts.size()
    }
    listener.onProgress(i);
  }

  @Override
  protected void onPostExecute(Boolean result) {
    super.onPostExecute(result);
    if (result && getResponseCode() != null && getResponseCode() == 200) {
      listener.onSuccess();
    } else {
      listener.onError(getResponseCode(), getJsonError());
    }
  }

  public interface Listener {
    String getAuthToken() throws Exception;
    void onSerializing();
    void onUploading();
    void onUpdatingLocalDb();
    void onProgress(int p);
    void onSuccess();
    void onError(Integer code, @Nullable JSONObject jsonObject);
  }

}
