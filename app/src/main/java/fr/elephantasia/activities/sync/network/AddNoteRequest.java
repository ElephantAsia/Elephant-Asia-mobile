package fr.elephantasia.activities.sync.network;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import fr.elephantasia.database.model.ElephantNote;
import fr.elephantasia.network.RequestSyncTask;

public class AddNoteRequest extends RequestSyncTask<String> {

  private static final String URL = "/journal/%s";

  private String authToken;
  private String cuid;
  private ElephantNote note;

  public AddNoteRequest(String authToken, String cuid, ElephantNote note) {
    this.authToken = authToken;
    this.cuid = cuid;
    this.note = note;
  }

  private Map<String, String> getHeader() {
    Map<String, String> header = new HashMap<>();
    try {
      header.put("Api-Key", authToken);
      header.put("Content-Type", "application/json");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return header;
  }

  @Override
  @Nullable
  public String execute() {
    String url = String.format(URL, cuid);
    JSONObject jsonObject = new JSONObject();

    try {
      jsonObject.put("created_at", note.getCreatedAt());
      jsonObject.put("label", note.getCategory());
      jsonObject.put("content", note.getDescription());
      jsonObject.put("priority", ElephantNote.Priority.valueOf(note.getPriority()).toString());

      POSTJSON(url, getHeader(), jsonObject.toString().getBytes());

      if (getJsonObjectResponse() != null) {
        return getJsonObjectResponse().toString();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


}
