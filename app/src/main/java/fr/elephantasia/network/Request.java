package fr.elephantasia.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
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
  private Integer httpResponseCode;
  private JSONObject jsonObject;
  private JSONArray jsonArray;
  private JSONObject jsonError;

  private Listener listener;

  Request(@Nullable Listener listener) {
    this.listener = listener;
  }

  protected void GET(@NonNull String url,
                     @Nullable Map<String, String> header,
                     @Nullable Map<String, String> params) {
    try {
      initHttp(String.format(BASE_URL, url));
      setHeader(header);
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
    Log.i(getClass().getName(), String.format("RESPONSE CODE %s", getResponseCode()));
    Log.i(getClass().getName(), String.format("JSON OBJECT RESPONSE %s", getJsonObjectResponse()));
    Log.i(getClass().getName(), String.format("JSON ARRAY RESPONSE %s", getJsonArrayResponse()));
    Log.i(getClass().getName(), String.format("ERROR JSON RESPONSE %s", getJsonErrorResponse()));
  }

  protected void POSTUrlEncoded(String url, Map<String, String> params) {
    try {
      initHttp(String.format(BASE_URL, url));

      httpsURLConnection.setConnectTimeout(POST_CONNECTION_TIMEOUT);
      httpsURLConnection.setReadTimeout(POST_READ_TIMEOUT);
      httpsURLConnection.setRequestMethod("POST");
      httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      httpsURLConnection.setDoInput(true);
      httpsURLConnection.setDoOutput(true);
      httpsURLConnection.setUseCaches(false);
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
      Log.i(getClass().getName(), String.format("POST %s", String.format(BASE_URL, url)));
      Log.i(getClass().getName(), String.format("BODY %s", paramsEncoded));
      Log.i(getClass().getName(), String.format("RESPONSE CODE %s", getResponseCode()));
      Log.i(getClass().getName(), String.format("JSON OBJECT RESPONSE %s", getJsonObjectResponse()));
      Log.i(getClass().getName(), String.format("JSON ARRAY RESPONSE %s", getJsonArrayResponse()));
      Log.i(getClass().getName(), String.format("ERROR JSON RESPONSE %s", getJsonErrorResponse()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void POSTJSON(String url, Map<String, String> header, byte[] body) {
    try {
      initHttp(String.format(BASE_URL, url));
      setHeader(header);
      httpsURLConnection.setConnectTimeout(POST_CONNECTION_TIMEOUT);
      httpsURLConnection.setReadTimeout(POST_READ_TIMEOUT);
      httpsURLConnection.setRequestMethod("POST");
      httpsURLConnection.setDoInput(true);
      httpsURLConnection.setDoOutput(true);
      httpsURLConnection.setUseCaches(false);
      DataOutputStream wr = new DataOutputStream(httpsURLConnection.getOutputStream());
      int offset = 0;
      int buflen = body.length / 100;
      for (int i = 0 ; i < 100 ; i++) {
        wr.write(body, offset,  buflen);
        offset += buflen;
        if (listener != null) {
          listener.uploadProgressUpdate(i);
        }
        try {
          Thread.sleep(1);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      wr.write(body, offset, body.length % 100);
      if (listener != null) {
        listener.uploadProgressUpdate(100);
      }
      wr.flush();
      wr.close();
      try {
        getResponse();
        closeHttpClient();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Log.i(getClass().getName(), String.format("POST %s", String.format(BASE_URL, url)));
      Log.i(getClass().getName(), String.format("BODY %s", new String(body)));
      Log.i(getClass().getName(), String.format("RESPONSE CODE %s", getResponseCode()));
      Log.i(getClass().getName(), String.format("JSON OBJECT RESPONSE %s", getJsonObjectResponse()));
      Log.i(getClass().getName(), String.format("JSON ARRAY RESPONSE %s", getJsonArrayResponse()));
      Log.i(getClass().getName(), String.format("ERROR JSON RESPONSE %s", getJsonErrorResponse()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void getResponse() {
    try {
      httpResponseCode = httpsURLConnection.getResponseCode();
      Integer contentLength = null;
      try {
        contentLength = Integer.valueOf(httpsURLConnection.getHeaderField("File-Size"));
      } catch (Exception e) {
        Log.i("contentLength", "null");
      }
      InputStream input = httpsURLConnection.getInputStream();

      if (input != null) {
        try {
          StringBuilder responseStrBuilder = new StringBuilder();
          InputStreamReader isr = new InputStreamReader(input, "UTF-8");
          BufferedReader streamReader = new BufferedReader(isr);
          String inputStr;
          while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
            if (listener != null && contentLength != null) {
              listener.responseProgressUpdate((inputStr.length() * 100) / contentLength);
            }

            try {
              Thread.sleep(100); // demo
            } catch (Exception e) {
              }
          }
          if (httpResponseCode == 200) {
            try {
              jsonObject = (JSONObject)(new JSONTokener(responseStrBuilder.toString()).nextValue());
            } catch (Exception e) {}
            try {
              jsonArray = (JSONArray)(new JSONTokener(responseStrBuilder.toString()).nextValue());
            } catch (Exception e) {}
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
      URL url = new URL(path);
      httpsURLConnection = (HttpsURLConnection) url.openConnection();
    } catch (IOException e) {
      throw e;
    }
  }

  private void setHeader(Map<String, String> header) {
    if (header != null) {
      for (Map.Entry<String, String> entry : header.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        httpsURLConnection.setRequestProperty(key, value);
      }
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

  protected Integer getResponseCode() {
    return httpResponseCode;
  }

  protected JSONObject getJsonObjectResponse() {
    return jsonObject;
  }

  protected JSONArray getJsonArrayResponse() {
    return jsonArray;
  }

  protected JSONObject getJsonErrorResponse() {
    return jsonError;
  }

  public interface Listener {
    void uploadProgressUpdate(int currentProgress);
    void responseProgressUpdate(int currentProgress);
  }

}
