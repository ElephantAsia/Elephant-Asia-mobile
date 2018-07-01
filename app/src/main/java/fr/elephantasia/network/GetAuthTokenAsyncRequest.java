package fr.elephantasia.network;

import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Stephane on 09/01/2018.
 */

public class GetAuthTokenAsyncRequest extends RequestAsyncTask<String> {

  static private final String URL = "/login";

  private String username;
  private String password;
  private Listener listener;

  public GetAuthTokenAsyncRequest(String username, String password, Listener listener) {
    this.username = username;
    this.password = password;
    this.listener = listener;
  }

  @Nullable
  @Override
  protected String doInBackground(@Nullable Void... params) {
    HashMap<String, String> requestParams = new HashMap<>();

    requestParams.put("username", username);
    requestParams.put("password", password);
    POSTUrlEncoded(URL, requestParams);
    return null;
  }

  @Override
  protected void onPostExecute(final String result) {
    super.onPostExecute(result);
    if (getResponseCode() != null && getResponseCode() == 200) {
      listener.onSuccess(getJson());
    } else {
      listener.onError(getResponseCode(), null);
    }
  }

  @Override
  protected void onCancelled() {
    super.onCancelled();
    listener.onError(getResponseCode(), getJsonError());
  }

  public interface Listener {
    void onSuccess(JSONObject json);

    void onError(Integer code, JSONObject json);
  }

}
