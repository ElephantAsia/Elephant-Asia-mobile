package fr.elephantasia.auth.network;

import android.support.annotation.Nullable;

import org.json.JSONException;

import java.util.HashMap;

import fr.elephantasia.network.RequestSyncTask;

/**
 * Created by Stephane on 05/01/2018.
 */

public class GetAuthTokenRequest extends RequestSyncTask<String> {

  private static final String URL = "/login";

  private String username;
  private String password;

  public GetAuthTokenRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  @Nullable
  public String execute() {
    HashMap<String, String> requestParams = new HashMap<>();

    requestParams.put("username", username);
    requestParams.put("password", password);

    POSTUrlEncoded(URL, requestParams);

    try {
      return getJsonObjectResponse().getString("token");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

}
