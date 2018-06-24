package fr.elephantasia.activities.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.BaseApplication;
import fr.elephantasia.R;
import fr.elephantasia.auth.Constants;
import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.DateHelpers;
import fr.elephantasia.utils.DeviceHelpers;
import fr.elephantasia.utils.Preferences;


public class SyncActivity extends AppCompatActivity {

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.connectivity) TextView connectivityTextView;
  @BindView(R.id.battery) TextView batteryTextView;
  @BindView(R.id.db_outdated) TextView dbOutdatedTextView;
  @BindView(R.id.date_last_dl) TextView dateLastDl;
  @BindView(R.id.date_last_dl_status) TextView dateLastDlStatus;
  @BindView(R.id.date_last_up) TextView dateLastUp;
  @BindView(R.id.date_last_up_status) TextView dateLastUpStatus;
  @BindView(R.id.elephants_ready_to_be_uploaded) TextView elephantsReady;

  private DatabaseController databaseController;

  // Instance fields
  private MaterialDialog dialog;
  private MenuItem download;
  private MenuItem upload;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sync_activity);
    ButterKnife.bind(this);

    databaseController = ((BaseApplication)getApplication()).getDatabaseController();

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    dialog = new MaterialDialog.Builder(SyncActivity.this).title("Progress dialog").build();
    refresh();
  }

  @Override
  public void onResume() {
    super.onResume();

    refresh();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.sync_activity_menu, menu);

    download = menu.findItem(R.id.download);
    download.setIcon(
      new IconicsDrawable(this)
        .icon(MaterialDesignIconic.Icon.gmi_cloud_download)
        .color(Color.WHITE)
        .sizeDp(22)
    );

    upload = menu.findItem(R.id.upload);
    upload.setIcon(
      new IconicsDrawable(this)
      .icon(MaterialDesignIconic.Icon.gmi_cloud_upload)
      .color(Color.WHITE)
      .sizeDp(22)
    );

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.download) {
      // disableMenuItems();
      startDownloadSync();
      return true;
    } else if (item.getItemId() == R.id.upload) {
      startUploadSync();
      return true;
    }
    return false;
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  private void startDownloadSync() {
    if (valid()) {
      displayDownloadConfirmation();
    } else {
      new MaterialDialog.Builder(this)
        .title("Error")
        .neutralText("OK")
        .content("Please check your internet connection or recharge your device.")
        .show();
    }
  }

  void displayDownloadConfirmation() {
    new MaterialDialog.Builder(this)
      .title("Confirmation")
      .content("Do you really want to update your local database ?")
      .positiveText("Yes")
      .onPositive(new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
          syncFromServer();
        }
      })
      .negativeText("No")
      .show();
  }

  private void startUploadSync() {
    startUploadActivity();
  }

  private void syncFromServer() {
    dialog = dialog.getBuilder().progress(true, 0).content(R.string.downloading_data).show();

    RequestQueue queue = Volley.newRequestQueue(SyncActivity.this);
    String lastSync = Preferences.GetLastDownloadSync(this);
    String url = "https://elephant-asia.herokuapp.com/api/sync/download/" + lastSync; // TODO: use api URL in constants
    JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
      createOnSuccessListener(),
      createOnErrorListener()) {
        @Override
        public Map<String, String> getHeaders() {
          AccountManager accountManager = AccountManager.get(SyncActivity.this);
          Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
          Map<String, String> headers = new HashMap<>();

          try {
              String authToken = accountManager.blockingGetAuthToken(accounts[0], Constants.AUTHTOKEN_TYPE, true);
              headers.put("Api-Key", authToken);
          } catch (Exception e) {
              e.printStackTrace();
          }
          return headers;
        }
    };

    queue.add(req);
  }

  public void startUploadActivity() {
    final Intent intent = new Intent(this, UploadActivity.class);
    startActivity(intent);
  }

  private Response.Listener<JSONObject> createOnSuccessListener() {
    return new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        SyncFromServerTask task = new SyncFromServerTask(response, new SyncFromServerTask.Listener() {
          @Override
          public void onPreExecute() {
            dialog.dismiss();
            dialog = dialog.getBuilder().progress(false, 100, true).content(R.string.updating_local).show();
          }

          @Override
          public void onProgress(int p) {
            dialog.setProgress(p);
          }

          @Override
          public void onFinish() {
            dialog.dismiss();
            // enableMenuItems();
          }

          @Override
          public void onSuccess() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String date = df.format(Calendar.getInstance().getTime());
            Preferences.SetLastDownloadSync(SyncActivity.this, date);
            Toast.makeText(getApplicationContext(), "Syncing done succesfully", Toast.LENGTH_SHORT).show();
            refresh();
          }

          @Override
          public void onFailure() {
            Toast.makeText(getApplicationContext(), "Error during sync, please try again", Toast.LENGTH_SHORT).show();
            refresh();
          }
        });
        task.execute();

        Log.w("onResponse", response.toString());
      }
    };
  }

  private Response.ErrorListener createOnErrorListener() {
    return new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        dialog.dismiss();
        // enableMenuItems();
        Toast.makeText(getApplicationContext(), "Error during the request try again", Toast.LENGTH_SHORT).show();
      }
    };
  }

  private boolean valid() {
    boolean b = DeviceHelpers.GetBatteryPercent(this) > 20 || DeviceHelpers.IsBatteryCharging(this);
    boolean c = DeviceHelpers.IsConnected(this);
    return b && c;
  }

  private void refresh() {
    /* Updating device state */
    refreshConnectivity();
    refreshBattery();
    refreshElephantsReadyToBeUploaded();
    refreshOutdatedDb();

    /* Updating download sync */
    refreshLastDownloadSync();

    /* Updating upload sync */
    refreshLastUploadSync();
  }

  private void refreshConnectivity() {
    IconicsDrawable icon;
    if (DeviceHelpers.IsConnected(this)) {
      connectivityTextView.setText("Connected to internet");
      icon = new IconicsDrawable(this)
        .icon(MaterialDesignIconic.Icon.gmi_check)
        .color(ContextCompat.getColor(this, R.color.md_green))
        .sizeDp(14);
    } else {
      connectivityTextView.setText("Not connected to internet");
      icon = new IconicsDrawable(this)
        .icon(MaterialDesignIconic.Icon.gmi_alert_triangle)
        .color(ContextCompat.getColor(this, R.color.md_red))
        .sizeDp(14);
    }

    connectivityTextView.setCompoundDrawablePadding(8);
    connectivityTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null);
  }

  private void refreshBattery() {
    IconicsDrawable icon;
    if (DeviceHelpers.GetBatteryPercent(this) > 20 || DeviceHelpers.IsBatteryCharging(this)) {
      batteryTextView.setText("Battery charged");
      icon = new IconicsDrawable(this)
        .icon(MaterialDesignIconic.Icon.gmi_check)
        .color(ContextCompat.getColor(this, R.color.md_green))
        .sizeDp(14);
    } else {
      batteryTextView.setText("Battery level too low");
      icon = new IconicsDrawable(this)
        .icon(MaterialDesignIconic.Icon.gmi_alert_triangle)
        .color(ContextCompat.getColor(this, R.color.md_red))
        .sizeDp(14);
    }

    batteryTextView.setCompoundDrawablePadding(8);
    batteryTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null);
  }

  private void refreshElephantsReadyToBeUploaded() {
    // Realm realm = Realm.getDefaultInstance();
    /* List<Elephant> elephants =
      realm.where(Elephant.class)
        .isNotNull(DB_STATE)
        .isNull(SYNC_STATE)
        .findAll(); */

    Long count = databaseController.getElephantsReadyToSyncCount();

    elephantsReady.setText(getResources().getString(R.string.elephants_ready_to_be_upload, count));
    // realm.close();
  }

  private void refreshOutdatedDb() {
    try {
      String lastSync = Preferences.GetLastDownloadSync(this);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      Date now = Calendar.getInstance().getTime();
      Date date = format.parse(lastSync);

      long yearDiff = DateHelpers.getYearDiff(date.getTime(), now.getTime());
      if (yearDiff > 0) {
        dbOutdatedTextView.setCompoundDrawablePadding(16);
        dbOutdatedTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
          new IconicsDrawable(this)
            .icon(MaterialDesignIconic.Icon.gmi_info)
            .color(ContextCompat.getColor(this, R.color.grey_light_50))
            .sizeDp(12), null, null, null);
        dbOutdatedTextView.setVisibility(View.VISIBLE);
      } else {
        dbOutdatedTextView.setVisibility(View.GONE);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void refreshLastDownloadSync() {
    if (Preferences.IsLastDownloadSyncNeverHappened(this)) {
      dateLastDl.setText(getResources().getString(R.string.date_label, "Never"));
      dateLastDlStatus.setVisibility(View.GONE);
    } else {
      String lastSync = Preferences.GetLastDownloadSync(this);
      try {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = format.parse(lastSync);

        SimpleDateFormat displayedFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String displayedDate = displayedFormat.format(date);

        dateLastDl.setText(getResources().getString(R.string.date_label, displayedDate));
        dateLastDlStatus.setVisibility(View.VISIBLE);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void refreshLastUploadSync() {
    if (Preferences.IsLastUploadSyncNeverHappened(this)) {
      dateLastUp.setText(getResources().getString(R.string.date_label, "Never"));
      dateLastUpStatus.setVisibility(View.GONE);
    } else {
      String lastSync = Preferences.GetLastUploadSync(this);
      try {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = format.parse(lastSync);

        SimpleDateFormat displayedFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String displayedDate = displayedFormat.format(date);

        dateLastUp.setText(getResources().getString(R.string.date_label, displayedDate));
        dateLastUpStatus.setVisibility(View.VISIBLE);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /* private void disableMenuItems() {
    if (download != null && upload != null) {
      download.setEnabled(false);
      upload.setEnabled(false);
    }
  }

  private void enableMenuItems() {
    if (download != null && upload != null) {
      download.setEnabled(true);
      upload.setEnabled(true);
    }
  } */

  static private class SyncFromServerTask extends AsyncTask<URL, Integer, Boolean> {

    private JSONObject syncFromServerResponse;
    private Listener listener;

    SyncFromServerTask(JSONObject syncFromServerResponse, Listener listener) {
      this.syncFromServerResponse = syncFromServerResponse;
      this.listener = listener;
    }

    protected Boolean doInBackground(URL... urls) {
      //final Realm realm = Realm.getDefaultInstance();
      // realm.beginTransaction();
      DatabaseController databaseController = new DatabaseController();
      databaseController.beginTransaction();

      try {
        JSONArray elephants = syncFromServerResponse.getJSONArray("elephants");
        for (int i = 0 ; i < elephants.length() ; i++) {
          publishProgress(i);
          Thread.sleep(25); // Pour la démo

          Elephant newE = new Elephant(elephants.getJSONObject(i));
          // Elephant e = realm.where(Elephant.class).equalTo(CUID, newE.cuid).findFirst();
          Elephant e = databaseController.getElephantByCuid(newE.cuid);
          if (e == null) {
            // new elephant in our local db
            //newE.id = RealmDB.getNextId(realm, Elephant.class, ID);
            newE.syncState = Elephant.SyncState.Downloaded.name();
            //realm.insertOrUpdate(newE);
            databaseController.insertOrUpdate(newE);
          } else if (/* e.dbState == null && */ e.syncState == null) {
            // existing elephant in our local db
            newE.id = e.id;
            newE.lastVisited = e.lastVisited;
            databaseController.insertOrUpdate(newE);
            //realm.insertOrUpdate(newE);
          }

        }
      } catch (Exception e) {
        e.printStackTrace();
        databaseController.cancelTransaction();
        return false;
      }

      databaseController.commitTransaction();
      return true;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      listener.onPreExecute();
    }

    protected void onProgressUpdate(Integer... progress) {
      try {
        JSONArray elephants = syncFromServerResponse.getJSONArray("elephants");
        int i = progress[0] > 0 ? (int) (((double) progress[0] / (double) elephants.length()) * 100) : 0;
        listener.onProgress(i);
      } catch (Exception e) {}
    }

    protected void onPostExecute(Boolean result) {
      listener.onFinish();
      if (result) {
        listener.onSuccess();
      } else {
        listener.onFailure();
      }
    }

    interface Listener {
      void onPreExecute();
      // void onReceiveElephant(Elephant newE);
      void onProgress(int p);
      void onFinish();
      void onSuccess();
      void onFailure();
    }
  }

}


