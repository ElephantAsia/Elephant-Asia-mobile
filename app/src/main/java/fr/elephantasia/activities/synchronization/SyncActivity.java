package fr.elephantasia.activities.synchronization;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.BaseApplication;
import fr.elephantasia.R;
import fr.elephantasia.activities.synchronization.network.SyncFromServerAsyncRequest;
import fr.elephantasia.auth.Constants;
import fr.elephantasia.database.DatabaseController;
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
    String lastSync = Preferences.GetLastDownloadSync(this);
    dialog = dialog.getBuilder()
      .progress(true, 0)
      .content(R.string.downloading_data)
      .show();

    new SyncFromServerAsyncRequest(lastSync, new SyncFromServerAsyncRequest.Listener() {
      @Override
      public String getAuthToken() throws Exception {
        AccountManager accountManager = AccountManager.get(SyncActivity.this);
        Account account = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE)[0];
        return accountManager.blockingGetAuthToken(account, Constants.AUTHTOKEN_TYPE, true);
      }
      @Override
      public void onDownloading() {
        dialog.setContent("Downloading elephants ...");
      }
      @Override
      public void onSaving() {
        dialog.setContent("Saving to local database ...");
      }
      @Override
      public void onProgress(int p) {
        dialog.setProgress(p);
      }
      @Override
      public void onSuccess(JSONObject jsonObject) {
        dialog.dismiss();
        String date = DateHelpers.GetCurrentStringDate();
        Preferences.SetLastDownloadSync(SyncActivity.this, date);
        Toast.makeText(getApplicationContext(), "Syncing done succesfully", Toast.LENGTH_SHORT).show();
        refresh();
      }
      @Override
      public void onError(Integer code, @Nullable JSONObject jsonObject) {
        dialog.dismiss();
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
      }
    }).execute();
  }

  public void startUploadActivity() {
    final Intent intent = new Intent(this, UploadActivity.class);
    startActivity(intent);
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
    Long count = databaseController.getElephantsReadyToSyncCount();

    elephantsReady.setText(getResources().getString(R.string.elephants_ready_to_be_upload, count));
  }

  private void refreshOutdatedDb() {
    try {
      String lastSync = Preferences.GetLastDownloadSync(this);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      Date now = Calendar.getInstance().getTime();
      Date date = format.parse(lastSync);

      long yearDiff = DateHelpers.getNbYearsDiff(date.getTime(), now.getTime());
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
      String lastSync = DateHelpers.FriendlyUserStringDate(Preferences.GetLastDownloadSync(this));
      dateLastDl.setText(getResources().getString(R.string.date_label, lastSync));
      dateLastDlStatus.setVisibility(View.VISIBLE);
    }
  }

  private void refreshLastUploadSync() {
    if (Preferences.IsLastUploadSyncNeverHappened(this)) {
      dateLastUp.setText(getResources().getString(R.string.date_label, "Never"));
      dateLastUpStatus.setVisibility(View.GONE);
    } else {
      String lastSync = DateHelpers.FriendlyUserStringDate(Preferences.GetLastUploadSync(this));
      dateLastUp.setText(getResources().getString(R.string.date_label, lastSync));
      dateLastUpStatus.setVisibility(View.VISIBLE);
    }
  }

}


