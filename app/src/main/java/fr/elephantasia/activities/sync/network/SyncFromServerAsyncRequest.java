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
    return getResponseCode() != null && getResponseCode() == 200;
  }

  private boolean save() {
    this.state = State.Saving;

    listener.onSaving();
    DatabaseController dbController = new DatabaseController();
    try {
      JSONObject result = getJsonObject();
      JSONArray elephants = result.getJSONArray("elephants");
      dbController.beginTransaction();
      for (int i = 0 ; i < elephants.length() ; ++i) {
        publishProgress(i * 100 / elephants.length());
        Thread.sleep(25); // demo

        Elephant newE = new Elephant(elephants.getJSONObject(i));
        Elephant e = dbController.getElephantByCuid(newE.cuid);
        if (e == null) {
          // new elephant in our local db
          newE.syncState = Elephant.SyncState.Downloaded.name();
          dbController.insertOrUpdate(newE);
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
    } catch (Exception e) {
      e.printStackTrace();
      dbController.cancelTransaction();
      return false;
    }
    dbController.commitTransaction();
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
