package fr.elephantasia.activities.sync.network;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.database.model.ElephantNote;
import fr.elephantasia.network.RequestAsyncTask;
import fr.elephantasia.utils.DateHelpers;

public class SyncFromServerAsyncRequest extends RequestAsyncTask<Boolean> {

  static private final String URL = "/sync/download/%s";

  private String lastSync;
  private Listener listener;
  // private State state; // not sure if it's useful - wait & see

  public SyncFromServerAsyncRequest(String lastSync, Listener listener) {
    this.lastSync = lastSync;
    this.listener = listener;
  }

  @Override
  protected Map<String, String> getHeader() {
    Map<String, String> header = new HashMap<>();
    try {
      String authToken = listener.getAuthToken();
      header.put("Api-Key", authToken);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return header;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Nullable
  @Override
  protected Boolean doInBackground(@Nullable Void... params) {
    if (!download())
      return false;
    if (!save())
      return false;
    // testDownloadContact();
    return true;
  }

  @Override
  protected void onProgressUpdate(Integer... progress) {
    super.onProgressUpdate(progress);
    try {
      // waiting for HTTP header fields 'Current-Length'
      listener.onProgress(progress[0]);
    } catch (Exception e) {}
  }

  @Override
  protected void onPostExecute(Boolean result) {
    super.onPostExecute(result);
    if (result && getResponseCode() != null && getResponseCode() == 200) {
      listener.onSuccess(getJsonObject());
    } else {
      listener.onError(getResponseCode(), getJsonError());
    }
  }

  private boolean download() {
    String url = String.format(URL, lastSync);
    // this.state = State.Downloading;

    listener.onDownloading();
    GET(url);
    if (getResponseCode() != null && getResponseCode() ==  200) {
      try {
        Thread.sleep(500); // demo
      } catch (Exception e) {}
      return true;
    }
    return false;
  }

  private void testDownloadContact() {
    // for each c in contacts (contacts array in json)
    //   hashmap.put(c.cuid, c)
    // for each e in elephants (elephants array in json)
    //   if e not existing
    //   endif
    //   else
    //   endelse
    //   for each c in e.getContact()
    //     c.setSyncState(null)
    //     c.setDbState(null)
    //     e.addContact(hashmap.get(c.cuid));
  }

  private boolean save() {
    // this.state = State.Saving;

    listener.onSaving();
    DatabaseController dbController = new DatabaseController();
    try {
      String authToken = listener.getAuthToken();
      JSONObject result = getJsonObject();

//      int maxLogSize = 200;
//      for (int i = 0 ; i < result.toString().length() / maxLogSize ; i++) {
//        int start = i * maxLogSize;
//        int end = (i+1) * maxLogSize;
//        end = (end > result.toString().length()) ? result.toString().length() : end;
//        Log.w("downloaded"+i, result.toString().substring(start, end));
//      }

      // JSONArray contacts = result.getJSONArray("contacts");
      // Log.w("contacts_dl", ""+ contacts.length());

      JSONArray elephants = result.getJSONArray("elephants");
      if (elephants.length() == 0) {
        return true;
      }
      // Log.i("Download", elephants.length() + " elephants downloaded");
      dbController.beginTransaction();
      for (int i = 0 ; i < elephants.length() ; ++i) {
        publishProgress(i * 100 / elephants.length());
        Thread.sleep(25); // demo

        Elephant newE = new Elephant(elephants.getJSONObject(i));
        Elephant e = dbController.getElephantByCuid(newE.cuid);
        if (e == null) {
          // new elephant in our local db
          e = newE;
          e.syncState = Elephant.SyncState.Downloaded.name();
          // Log.i("inserting1", e.getNameText() + " cuid:" + e.cuid);
          dbController.insertOrUpdate(e);
        } else {
          // Log.i("inserting2", e.getNameText() + " cuid:" + e.cuid);
          // if (e.dbState == null && e.syncState == null) {
          // existing elephant in our local db

          /* newE.id = e.id;
          newE.lastVisited = e.lastVisited;
          databaseController.insertOrUpdate(newE); */
          e.syncState = Elephant.SyncState.Downloaded.name(); // accepted ?
          e.dbState = null;
          // e.journalState = null;
          e.copy(newE);
          dbController.insertOrUpdate(e);
        }
      }

      JSONArray notesArray = result.getJSONArray("journals");
      Log.w("journal", "response: " + notesArray.toString());
      for (int j = 0 ; j < notesArray.length() ; ++j) {
        JSONObject obj = notesArray.getJSONObject(j);
        try {
          Elephant e = dbController.getElephantByCuid(obj.getString("elephant_cuid"));
          if (e != null) {
            ElephantNote note = new ElephantNote();
            note.setElephantId(e.id);
            note.setDescription(obj.getString("content"));
            note.setCategory(ElephantNote.Category.valueOf(obj.getString("label")));
            note.setPriority(ElephantNote.Priority.valueOf(obj.getString("priority")));
            note.setCreatedAt(obj.getString("createdAt"));
            note.setDbState(null);
            Log.w("add note", "adding note: " + note.getDescription() + " " + note.getCreatedAt());
            dbController.insertOrUpdate(note);
          }
        } catch (Exception er) {
          er.printStackTrace();
        }
      }

      JSONArray contactsArray = result.getJSONArray("contacts");
      Log.w("contact", "response: " + contactsArray.toString());
      for (int j = 0 ; j < contactsArray.length(); ++j) {
        JSONObject obj = contactsArray.getJSONObject(j);
        dbController.insertOrUpdate(new Contact(obj));
      }

      dbController.commitTransaction();
    } catch (Exception e) {
      e.printStackTrace();
      dbController.cancelTransaction();
      return false;
    }

    publishProgress(100);
    try { Thread.sleep(250); } catch (Exception e) {}
    return  true;
  }

  /*private enum State {
    Downloading,
    Saving
  }*/

  public interface Listener {
    String getAuthToken() throws Exception;
    void onDownloading();
    void onSaving();
    void onProgress(int p);
    void onSuccess(JSONObject jsonObject);
    void onError(Integer code, @Nullable JSONObject jsonObject);
  }

}
