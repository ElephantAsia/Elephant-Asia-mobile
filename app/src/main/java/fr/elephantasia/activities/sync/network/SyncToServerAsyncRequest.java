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
import fr.elephantasia.database.model.ElephantNote;
import fr.elephantasia.network.RequestAsyncTask;
import fr.elephantasia.utils.JsonHelpers;

public class SyncToServerAsyncRequest extends RequestAsyncTask<Boolean> {

  static private final String URL = "/sync/upload";

  private List<Elephant> elephants;
  private Map<String, Contact> contacts = new HashMap<>();
  private Listener listener;
  // private JSONObject serialized;
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
      header.put("Content-Type", "application/javascript");
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
    uploadElephantNotes();
    return true;
  }

  private void serialize() {
    this.listener.onSerializing();

    // JSONArray elephantsJsonArray = new JSONArray();
    for (int i = 0 ; i < elephants.size() ; ++i) {
      publishProgress(i);
      Elephant elephant = elephants.get(i);

      // a note was added but the elephant was not modified
      if (elephant.journalState != null && elephant.dbState == null)
        continue;

      try {
        Thread.sleep(250); // demo
        String action = ((elephant.dbState.equals(Elephant.DbState.Created.name())) ? "CREATE" : "EDIT");
        JSONObject obj = elephant.toJsonObject(contacts);
        obj.put("action", action);
        obj.put("type", "elephant");
        // elephantsJsonArray.put(obj);
        serialized.put(obj);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    DatabaseController db = new DatabaseController();
    List<Contact> contacts = db.getContactsReadyToSync();
    for (Contact contact : contacts) {
        try {
            Log.w("contacts", contact.getFullName());
            JSONObject obj = contact.toJsonObject();
            obj.put("type", "contact");
            obj.put("action", "CREATE");
            serialized.put(obj);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    /* for (Map.Entry<String, Contact> entry : contacts.entrySet()) {
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
    } */
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
      Elephant elephant = elephants.get(i);

      if (elephant.journalState != null && elephant.dbState == null)
        continue;

      try {
        Thread.sleep(250); // demo
        String cuid = JsonHelpers.getString(response.getJSONObject(i), "cuid");
        if (cuid != null) {
          elephant.wasUploaded(cuid);
        } else {
          elephant.edit();
        }
        // elephant.wasUploaded(response.getJSONObject(i).getString("cuid"));
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

    List<Contact> contacts = dbController.getContactsReadyToSync();
    for (Contact contact : contacts) {
      // contact.syncState = null;
      contact.dbState = null;
      dbController.insertOrUpdate(contact);
    }

    publishProgress(elephants.size());
    dbController.commitTransaction();
    try {
      Thread.sleep(500); // demo
    } catch (Exception e) {}
  }

  // push the journal added on the selected elephants
  private void uploadElephantNotes() {
    try {
      String authToken = listener.getAuthToken();

      for (int i = 0; i < elephants.size(); i++) {
        Elephant e = elephants.get(i);

        if (e.journalState == null)
          continue;

        DatabaseController db = new DatabaseController();
        List<ElephantNote> notes = db.getElephantNotesReadyToPushByElephantId(e.id);
        db.beginTransaction();
        for (int j = 0; j < notes.size(); j++) {
          ElephantNote note = notes.get(j);

          Log.w("upload note", "#" + i + " pushing " + note.getDescription());
          AddNoteRequest req = new AddNoteRequest(authToken, e.cuid, note);
          String rep = req.execute();
          Log.w("upload note", "reponse: " + rep);

          note.setDbState(null);
          db.insertOrUpdate(note);
        }
        e.journalState = null;
        db.insertOrUpdate(e);
        db.commitTransaction();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
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
