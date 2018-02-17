package fr.elephantasia.network;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import fr.elephantasia.auth.Constants;

/**
 * Created by Stephane on 09/01/2018.
 */

public class GetElephantsAsyncRequest extends RequestAsyncTask<String> {

  private static final String URL = "/login";
  private AccountManager accountManager;
  private Account account;
  private Listener listener;
  private Activity activity;

  AccountManagerCallback<Bundle> onTokenAcquired;

  public GetElephantsAsyncRequest(final Activity callingActivity, Listener listener) {
    this.accountManager = AccountManager.get(callingActivity);
    accountManager.getAuthToken(
        account,
        Constants.AUTHTOKEN_TYPE,
        null,
        null,
        onTokenAcquired, null);
    this.account = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE)[0];
    this.listener = listener;

    onTokenAcquired = new AccountManagerCallback<Bundle>() {
      @Override
      public void run(AccountManagerFuture<Bundle> future) {
        try {
          Bundle bundle = future.getResult();
          String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);

          if (authToken != null && !authToken.isEmpty()) {
            Toast.makeText(callingActivity.getApplicationContext(), "Auth token is." + authToken, Toast.LENGTH_LONG).show();

            Log.i("", "run: token: " + authToken);
            return;
          }
          Toast.makeText(callingActivity.getApplicationContext(), "Auth token is null.", Toast.LENGTH_LONG).show();
          Log.i("", "run: token failed: ");
        } catch (Throwable e) {
          Toast.makeText(callingActivity.getApplicationContext(), "Auth token ERROR", Toast.LENGTH_LONG).show();
          Log.i("", "run: token failed with erro: ", e);
        }
      }
    };
  }


  @Nullable
  @Override
  protected String doInBackground(@Nullable Void... params) {
    HashMap<String, String> requestParams = new HashMap<>();
    POSTUrlEncoded(URL, requestParams);
//    accountManager = AccountManager.get(getContext());
//    accountManager.getAuthToken(
//        account,
//        Constants.AUTHTOKEN_TYPE,
//        null,
//        null,
//        onTokenAcquired, null);
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
