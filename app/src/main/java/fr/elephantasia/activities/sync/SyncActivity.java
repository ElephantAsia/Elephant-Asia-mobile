package fr.elephantasia.activities.sync;

import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.database.RealmDB;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.network.JsonAuthRequest;
import io.realm.Realm;

import static fr.elephantasia.database.model.Elephant.CUID;
import static fr.elephantasia.database.model.Elephant.ID;


public class SyncActivity extends AppCompatActivity {

  // Instance fields
  AccountManager accountManager;
  public JSONArray syncFromServerResponse;

  final private String getElephantByLastUpdate = "/elephants";

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
    JsonAuthRequest req =
        new JsonAuthRequest(SyncActivity.this, Request.Method.GET,
            getElephantByLastUpdate,
            null,
            createOnSuccessListener(),
            createOnErrorListener());
    queue.add(req);
  }

  private Response.Listener<JSONArray> createOnSuccessListener() {
    return new Response.Listener<JSONArray>() {;
      @Override
      public void onResponse(JSONArray response) {
        syncFromServerResponse = response;
        SyncFromServerTask task = new SyncFromServerTask();
        task.execute();
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

  private class SyncFromServerTask extends AsyncTask<URL, Integer, Boolean> {
    protected Boolean doInBackground(URL... urls) {
      final Realm realm = Realm.getDefaultInstance();
      realm.beginTransaction();

      for (int i = 0; i < syncFromServerResponse.length(); i++) {
        publishProgress(i);
        try {
          Elephant newE = new Elephant(syncFromServerResponse.getJSONObject(i));
          Elephant e = realm.where(Elephant.class).equalTo(CUID, newE.cuid).findFirst();

          if (e == null) {
            newE.id = RealmDB.getNextId(realm, Elephant.class, ID);
            realm.insertOrUpdate(newE);
          } else if (e.dbState == null && e.syncState == null) {
            newE.id = e.id;
            newE.lastVisited = e.lastVisited;
            realm.insertOrUpdate(newE);
          }
        } catch (JSONException e1) {
          e1.printStackTrace();
          return false;
        }
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
      int i = progress[0] > 0 ? (int) (((double) progress[0] / (double) syncFromServerResponse.length()) * 100) : 0;
      progressBar.setProgress(i);
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


