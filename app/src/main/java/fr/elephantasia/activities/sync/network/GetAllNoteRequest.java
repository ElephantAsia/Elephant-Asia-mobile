package fr.elephantasia.activities.sync.network;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import fr.elephantasia.network.RequestSyncTask;

public class GetAllNoteRequest extends RequestSyncTask<JSONArray> {

  private static final String URL = "/journal/%s";

  private String authToken;
  private String cuid;

  public GetAllNoteRequest(String authToken, String cuid) {
    this.authToken = authToken;
    this.cuid = cuid;
  }

  private Map<String, String> getHeader() {
    Map<String, String> header = new HashMap<>();
    try {
      header.put("Api-Key", authToken);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return header;
  }

  @Override
  @Nullable
  public JSONArray execute() {
    String url = String.format(URL, cuid);

    GET(url, getHeader(), null);
    return getJsonArrayResponse();
  }
}
