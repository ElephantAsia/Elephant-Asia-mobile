package fr.elephantasia.activities.sync;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.auth.Constants;
import fr.elephantasia.network.JsonAuthRequest;


public class SyncActivity extends AppCompatActivity {

  // Instance fields
  AccountManager accountManager;

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.rep) TextView tv;

  @OnClick(R.id.download)
  public void downloadData(View view) {
    RequestQueue queue = Volley.newRequestQueue(this);
    String url = "http://elephant-asia.herokuapp.com/api/users";
    JsonAuthRequest req = new JsonAuthRequest(this, Request.Method.GET, url, null, createOnSuccessListener(), createOnErrorListener());
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
          tv.setText(response.getString(0));
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


