package fr.elephantasia.activities.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.auth.Constants;
import fr.elephantasia.database.RealmDB;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.Preferences;
import io.realm.Realm;

import static fr.elephantasia.database.model.Elephant.CUID;
import static fr.elephantasia.database.model.Elephant.ID;


public class SyncActivity extends AppCompatActivity {

  // Instance fields
  private AccountManager accountManager;

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.rep) TextView tv;
  @BindView(R.id.tv_downloading) TextView tvDownloading;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.download) Button btDownload;
  @BindView(R.id.upload) Button btUpload;

  @OnClick(R.id.download)
  public void showConfirmationDialog() {
    new MaterialDialog.Builder(this)
        .title(R.string.confirmation_sync)
        .positiveText(R.string.DOWNLOAD)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            syncFromServer();
          }
        })
        .dismissListener(new DialogInterface.OnDismissListener() {
          @Override
          public void onDismiss(DialogInterface dialog) {}
        })
        .negativeText(R.string.CANCEL)
        .show();
  }

  @OnClick(R.id.upload)
  public void startUploadActivity() {
    final Intent intent = new Intent(this, UploadActivity.class);
    startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sync_activity);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    accountManager = AccountManager.get(this);
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  private void syncFromServer() {
    tvDownloading.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.VISIBLE);
    btDownload.setVisibility(View.INVISIBLE);
    btUpload.setVisibility(View.INVISIBLE);

    RequestQueue queue = Volley.newRequestQueue(SyncActivity.this);

    String lastSync = Preferences.GetLastSync(this);
    Log.w("date", lastSync);
    String url = "https://elephant-asia.herokuapp.com/api/sync/download/" + lastSync;
    JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
            createOnSuccessListener(),
            createOnErrorListener()) {
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
        Map<String, String> headers = new HashMap<>();

        try {
            String authToken = accountManager.blockingGetAuthToken(accounts[0], Constants.AUTHTOKEN_TYPE, true);
            Log.w("authToken", authToken);
            headers.put("Api-Key", authToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headers;
      }
    };

    queue.add(req);
  }

  private Response.Listener<JSONObject> createOnSuccessListener() {
    return new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String date = df.format(Calendar.getInstance().getTime());
        Preferences.SetLastSync(SyncActivity.this, date);
        Log.w("lastSync", "now " + date);

        SyncFromServerTask task = new SyncFromServerTask(response);
        task.execute();

        Log.w("onResponse", response.toString());
      }
    };
  }

  private Response.ErrorListener createOnErrorListener() {
    return new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        tvDownloading.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        btDownload.setVisibility(View.VISIBLE);
        btUpload.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "Error during the request try again", Toast.LENGTH_SHORT).show();
      }
    };
  }

  // TODO: add listener class and put SyncFromServerTask static
  private class SyncFromServerTask extends AsyncTask<URL, Integer, Boolean> {

    private JSONObject syncFromServerResponse;

    SyncFromServerTask(JSONObject syncFromServerResponse) {
      this.syncFromServerResponse = syncFromServerResponse;
    }

    protected Boolean doInBackground(URL... urls) {
      final Realm realm = Realm.getDefaultInstance();
      realm.beginTransaction();

      try {
        JSONArray elephants = syncFromServerResponse.getJSONArray("elephants");
        for (int i = 0 ; i < elephants.length() ; i++) {
          publishProgress(i);
          Elephant newE = new Elephant(elephants.getJSONObject(i));
          Elephant e = realm.where(Elephant.class).equalTo(CUID, newE.cuid).findFirst();

          if (e == null) {
            newE.id = RealmDB.getNextId(realm, Elephant.class, ID);
            realm.insertOrUpdate(newE);
            Log.w("sync_elephants", "lol");
          } else if (e.dbState == null && e.syncState == null) {
            newE.id = e.id;
            newE.lastVisited = e.lastVisited;
            Log.w("sync_elephants", "xd");
            realm.insertOrUpdate(newE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      realm.commitTransaction();
      return true;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      tvDownloading.setText(R.string.updating_local);
      tvDownloading.setVisibility(View.INVISIBLE);
      tvDownloading.setVisibility(View.VISIBLE);
      progressBar.setIndeterminate(false);
    }

    protected void onProgressUpdate(Integer... progress) {
      try {
        JSONArray elephants = syncFromServerResponse.getJSONArray("elephants");
        int i = progress[0] > 0 ? (int) (((double) progress[0] / (double) elephants.length()) * 100) : 0;
        progressBar.setProgress(i);
      } catch (Exception e) {}
    }

    protected void onPostExecute(Boolean result) {
      progressBar.setIndeterminate(true);
      tvDownloading.setText(R.string.downloading_data);
      tvDownloading.setVisibility(View.INVISIBLE);
      progressBar.setVisibility(View.INVISIBLE);
      btDownload.setVisibility(View.VISIBLE);
      btUpload.setVisibility(View.VISIBLE);

      if (result) {
        Toast.makeText(getApplicationContext(), "Syncing done succesfully", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(getApplicationContext(), "Error during sync, please try again", Toast.LENGTH_SHORT).show();
      }
    }
  }

}


