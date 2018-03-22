package fr.elephantasia.activities.sync;

import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.network.JsonAuthRequest;

import static fr.elephantasia.database.RealmDB.insertOrUpdateElephant;


public class SyncActivity extends AppCompatActivity {

  // Instance fields
  AccountManager accountManager;

  final private String getElephantByLastUpdate = "/elephants";

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.rep) TextView tv;

  @OnClick(R.id.download)
  public void downloadData(View view) {
    RequestQueue queue = Volley.newRequestQueue(this);
    JsonAuthRequest req =
        new JsonAuthRequest(this, Request.Method.GET,
            getElephantByLastUpdate,
            null,
            createOnSuccessListener(),
            createOnErrorListener());
    queue.add(req);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sync_activity);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    accountManager = AccountManager.get(this);
  }

  private Response.Listener<JSONArray> createOnSuccessListener() {
    return new Response.Listener<JSONArray>() {
      @Override
      public void onResponse(JSONArray response) {
        try {
          insertOrUpdateElephant(response);
          tv.setText(response.getString(1));
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    };
  }

  private Response.ErrorListener createOnErrorListener() {
    return new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Error during the request try again", Toast.LENGTH_SHORT).show();
      }
    };
  }
}


