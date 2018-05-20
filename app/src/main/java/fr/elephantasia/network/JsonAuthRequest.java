package fr.elephantasia.network;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import fr.elephantasia.auth.Constants;

/**
 * Created by seb on 17/02/2018.
 */

public class JsonAuthRequest extends JsonArrayRequest {
  private static String BASE_URL = "http://elephant-asia.herokuapp.com/api";
  private String authToken = null;


  public JsonAuthRequest(final Activity ac, int method, String endPoint, JSONArray jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
    super(method, BASE_URL + endPoint, jsonRequest, listener, errorListener);
    AccountManager accountManager = AccountManager.get(ac);
    accountManager.getAuthToken(
        accountManager.getAccountsByType(Constants.ACCOUNT_TYPE)[0],
        Constants.AUTHTOKEN_TYPE,
        null,
        null,
        new AccountManagerCallback<Bundle>() {
          @Override
          public void run(AccountManagerFuture<Bundle> future) {
            try {
              Bundle bundle = future.getResult();
              authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            } catch (Throwable e) {
              Toast.makeText(ac.getApplicationContext(), "Auth token ERROR", Toast.LENGTH_LONG).show();
              Log.i("", "run: token failed with erro: ", e);
            }
          }
        },
        null);
  }

  @Override
  public Map<String, String> getHeaders() throws AuthFailureError {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/x-www-form-urlencoded");
    headers.put("Api-key", this.authToken);
    return headers;
  }

  @Override
  protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
    int mStatusCode = response.statusCode;
    Log.i("", "parseNetworkResponse: " + mStatusCode);
    return super.parseNetworkResponse(response);
  }
}