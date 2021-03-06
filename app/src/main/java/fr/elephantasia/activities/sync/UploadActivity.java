package fr.elephantasia.activities.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.activities.sync.adapters.SelectElephantsAdapter;
import fr.elephantasia.activities.sync.network.SyncToServerAsyncRequest;
import fr.elephantasia.auth.Constants;
import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.DateHelpers;
import fr.elephantasia.utils.Preferences;

public class UploadActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.list) RecyclerView recyclerView;
  @BindView(R.id.empty_layout) View emptyLayout;

  private SelectElephantsAdapter adapter;
  private SyncToServerAsyncRequest request;

  private MaterialDialog dialog;
  private MenuItem upload;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.upload_activity);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(mLayoutManager);

    dialog = new MaterialDialog
      .Builder(UploadActivity.this)
      .title("Progress dialog")
      .build();
    initAdapter();
  }

  @Override
  public void onResume() {
    super.onResume();
    refreshList(false);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.sync_upload_activity_menu, menu);

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
    if (item.getItemId() == R.id.upload) {
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

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  private void initAdapter() {
    adapter = new SelectElephantsAdapter(new SelectElephantsAdapter.Listener() {
      @Override
      public void onSelectButtonClick(boolean selected, Elephant elephant) {
      }
      @Override
      public void onConsultationButtonClick(Elephant elephant) {
        Intent intent = new Intent(UploadActivity.this, ShowElephantActivity.class);
        ShowElephantActivity.SetExtraElephantId(intent, elephant.id);
        startActivity(intent);
      }
    });
    recyclerView.setAdapter(adapter);
  }

  void startUploadSync() {
    if (!isUploading()) {
      if (adapter.countElephantsSelected() > 0) {
        displayUploadConfirmation();
      } else {
        new MaterialDialog.Builder(this)
          .title("Error")
          .content("No elephant selected.")
          .neutralText("OK")
          .show();
      }
    }
  }

  void displayUploadConfirmation() {
    new MaterialDialog.Builder(this)
      .title("Confirmation")
      .content("Do you really want to upload the selected elephant(s) ?")
      .positiveText("Yes")
      .onPositive(new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
          syncToServer();
        }
      })
      .negativeText("No")
      .show();
  }

  void refreshList(boolean closeIfEmpty) {
    DatabaseController dbController = new DatabaseController();
    List<Elephant> editedElephants = dbController.getElephantsReadyToUpload();
    dbController.close();

    adapter.setElephants(editedElephants);
    adapter.resetSelection();
    if (editedElephants != null && !editedElephants.isEmpty()) {
      emptyLayout.setVisibility(View.GONE);
    } else {
      emptyLayout.setVisibility(View.VISIBLE);
      if (closeIfEmpty) {
        setResult(RESULT_OK);
        finish();
      }
    }
  }

  void syncToServer() {
    dialog = dialog.getBuilder()
      .progress(false, 100, true)
      .title("Upload")
      .content("0/3 Starting ...")
      .cancelable(false)
      .show();

    List<Elephant> selectedElephants = adapter.getSelectedElephants();
    request = new SyncToServerAsyncRequest(selectedElephants, new SyncToServerAsyncRequest.Listener() {
      @Override
      public String getAuthToken() throws Exception {
        AccountManager accountManager = AccountManager.get(UploadActivity.this);
        Account account = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE)[0];
        return accountManager.blockingGetAuthToken(account, Constants.AUTHTOKEN_TYPE, true);
      }
      @Override
      public void onSerializing() {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            dialog.setContent("1/3 Serializing data ...\nDO NOT CLOSE the application");
          }
        });
      }
      @Override
      public void onUploading() {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            dialog.setContent("2/3 Uploading data ...\nDO NOT CLOSE the application");
          }
        });
      }
      @Override
      public void onUpdatingLocalDb() {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            dialog.setContent("3/3 Updating local database ...\nDO NOT CLOSE the application");
          }
        });
      }
      @Override
      public void onProgress(int p) {
        dialog.setProgress(p);
      }
      @Override
      public void onSuccess() {
        dialog.dismiss();
        adapter.resetSelection();
        String date = DateHelpers.GetCurrentStringDate();
        Preferences.SetLastUploadSync(UploadActivity.this, date);
        new MaterialDialog.Builder(UploadActivity.this)
          .title(dialog.getTitleView().getText())
          .content("Upload done successfully !")
          .neutralText("OK")
          .dismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
              refreshList(true);
            }
          })
          .show();
      }
      @Override
      public void onError(Integer code, @Nullable JSONObject jsonObject) {
        dialog.dismiss();
        Toast.makeText(getApplicationContext(), "Error during the upload", Toast.LENGTH_SHORT).show();
        if (jsonObject != null) {
          Log.w(getClass().getName(), "error response: " + jsonObject.toString());
        }
      }
    });
    request.execute();
  }

  private boolean isUploading() {
    return request != null && request.isRunning();
  }

}


