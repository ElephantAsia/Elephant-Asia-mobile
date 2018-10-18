package fr.elephantasia.activities.sync.network;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.network.RequestAsyncTask;

public class SyncFromServerAsyncRequest extends RequestAsyncTask<Boolean> {

  static private final String URL = "/sync/download/%s";

  private String lastSync;
  private Listener listener;
  private State state; // not sure if it's useful - wait & see

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
    this.state = State.Downloading;

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
    this.state = State.Saving;

    listener.onSaving();
    DatabaseController dbController = new DatabaseController();
    try {
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
          dbController.insertOrUpdate(e);
        } else {
          // if (e.dbState == null && e.syncState == null) {
          // existing elephant in our local db

          /* newE.id = e.id;
          newE.lastVisited = e.lastVisited;
          databaseController.insertOrUpdate(newE); */
          e.syncState = Elephant.SyncState.Downloaded.name(); // accepted ?
          e.dbState = null;
          e.copy(newE);
          dbController.insertOrUpdate(e);
        }
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

  private enum State {
    Downloading,
    Saving
  }

  public interface Listener {
    String getAuthToken() throws Exception;
    void onDownloading();
    void onSaving();
    void onProgress(int p);
    void onSuccess(JSONObject jsonObject);
    void onError(Integer code, @Nullable JSONObject jsonObject);
  }

}
