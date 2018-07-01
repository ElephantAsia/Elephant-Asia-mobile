package fr.elephantasia.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import fr.elephantasia.utils.TextHelpers;

/**
 * Created by Stephane on 08/01/2018.
 */

class Request {

  private static final String BASE_URL = "https://elephant-asia.herokuapp.com/api%s";

  private static final Integer SECONDE = 1000;
  private static final Integer GET_CONNECTION_TIMEOUT = 3 * SECONDE;
  private static final Integer GET_READ_TIMEOUT = 10 * SECONDE;
  private static final Integer POST_CONNECTION_TIMEOUT = 10 * SECONDE;
  private static final Integer POST_READ_TIMEOUT = 10 * SECONDE;

  private HttpsURLConnection httpsURLConnection;
  private Integer httpResponseCode = null;
  private JSONObject json = null;
  private JSONObject jsonError = null;

//  private AccountManager accountManager = null;
//  private Account account = null;
//
//  AccountManagerCallback<Bundle> onTokenAcquired = new AccountManagerCallback<Bundle>() {
//    @Override
//    public void run(AccountManagerFuture<Bundle> future) {
//      try {
//        Bundle bundle = future.getResult();
//        String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
//        if (authToken != null && !authToken.isEmpty()) {
//          Log.i("", "run: token: " + authToken);
//        }
//      } catch (Throwable e) {
//        Log.i("", "run: token failed with erro: ", e);
//      }
//    }
//  };

  protected void GET(@NonNull String url,
                     @Nullable Map<String, String> header,
                     @Nullable Map<String, String> params) {

    try {
      initHttp(String.format(BASE_URL, url));

      if (header != null) {
        for (Map.Entry<String, String> entry : header.entrySet()) {
          String key = entry.getKey();
          String value = entry.getValue();
          httpsURLConnection.setRequestProperty(key, value);
        }
      }
      httpsURLConnection.setConnectTimeout(GET_CONNECTION_TIMEOUT);
      httpsURLConnection.setReadTimeout(GET_READ_TIMEOUT);
      httpsURLConnection.setRequestMethod("GET");
      httpsURLConnection.setDoInput(true);
      httpsURLConnection.setDoOutput(false);
      httpsURLConnection.setUseCaches(false);
      try {
        getResponse();
        closeHttpClient();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    Log.i(getClass().getName(), String.format("GET %s", String.format(BASE_URL, url)));
    Log.i(getClass().getName(), String.format("BODY RESPONSE CODE %s", getResponseCode()));
    Log.i(getClass().getName(), String.format("BODY JSON %s", getJson()));
    Log.i(getClass().getName(), String.format("BODY ERROR JSON %s", getJsonError()));
  }

  void POSTUrlEncoded(String url, Map<String, String> params) {
    try {
      initHttp(String.format(BASE_URL, url));

      httpsURLConnection.setConnectTimeout(POST_CONNECTION_TIMEOUT);
      httpsURLConnection.setReadTimeout(POST_READ_TIMEOUT);
      httpsURLConnection.setRequestMethod("POST");
      httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      httpsURLConnection.setDoInput(true);
      httpsURLConnection.setDoOutput(true);
      httpsURLConnection.setUseCaches(false);

      // Map<String, String> params = getParams();
      String paramsEncoded = TextHelpers.UrlEncoder(params);
      DataOutputStream wr = new DataOutputStream(httpsURLConnection.getOutputStream());
      wr.write(paramsEncoded.getBytes("UTF-8"));
      wr.flush();
      wr.close();

      try {
        getResponse();
        closeHttpClient();
      } catch (Exception e) {
        e.printStackTrace();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    Log.i(getClass().getName(), String.format("POST %s", String.format(BASE_URL, url)));
    Log.i(getClass().getName(), String.format("BODY RESPONSE CODE %s", getResponseCode()));
    Log.i(getClass().getName(), String.format("BODY JSON %s", getJson()));
    Log.i(getClass().getName(), String.format("BODY ERROR JSON %s", getJsonError()));
  }

  private void getResponse() {
    try {
      httpResponseCode = httpsURLConnection.getResponseCode();

      // Log.i("get", "size: " + httpsURLConnection.getHeaderField("Content-Length"));
      // Log.i("get", "headerfields: " + httpsURLConnection.getHeaderFields());

      InputStream input = httpsURLConnection.getInputStream();
      if (input != null) {
        try {
          StringBuilder responseStrBuilder = new StringBuilder();
          BufferedReader streamReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
          String inputStr;

          while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
          }
          if (httpResponseCode == 200) {
            json = (JSONObject) (new JSONTokener(responseStrBuilder.toString()).nextValue());
          } else {
            jsonError = new JSONObject(responseStrBuilder.toString());
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        input.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initHttp(String path) throws IOException {
    try {
      Log.i("initHttp", "url : " + path);
      URL url = new URL(path);
      httpsURLConnection = (HttpsURLConnection) url.openConnection();
    } catch (IOException e) {
      throw e;
    }
  }

  void closeHttpClient() {
    try {
      if (httpsURLConnection != null) {
        httpsURLConnection.disconnect();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  Integer getResponseCode() {
    return httpResponseCode;
  }

  JSONObject getJson() {
    return json;
  }

  JSONObject getJsonError() {
    return jsonError;
  }

}
