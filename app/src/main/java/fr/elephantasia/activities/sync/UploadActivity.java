package fr.elephantasia.activities.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.activities.sync.adapters.UploadRecyclerViewAdapter;
import fr.elephantasia.auth.Constants;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.DateHelpers;
import fr.elephantasia.utils.Preferences;
import io.realm.Realm;
import io.realm.RealmResults;

import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_ELEPHANT_ID;
import static fr.elephantasia.database.model.Elephant.DB_STATE;
import static fr.elephantasia.database.model.Elephant.SYNC_STATE;
import static fr.elephantasia.database.model.Elephant.SyncState;

public class UploadActivity extends AppCompatActivity {

  // Instance fields
  private MaterialDialog dialog;
  private MenuItem upload;

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.list) RecyclerView mRecyclerView;
  @BindView(R.id.empty_layout) View emptyLayout;

  // Attributes
  private UploadRecyclerViewAdapter mAdapter;
  private RealmResults<Elephant> editedElephantsLive;
  private Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.upload_activity);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(mLayoutManager);

    realm = Realm.getDefaultInstance();
    dialog = new MaterialDialog.Builder(UploadActivity.this).title("Progress dialog").build();

    refreshList();
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
    realm.close();
  }

  void startUploadSync() {
    if (mAdapter.countElephantsSelected() > 0) {
      displayUploadConfirmation();
    } else {
      new MaterialDialog.Builder(this)
        .title("Error")
        .content("No elephant selected.")
        .neutralText("OK")
        .show();
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

  void refreshList() {
    editedElephantsLive = realm.where(Elephant.class)
      .isNotNull(DB_STATE)
      .isNull(SYNC_STATE)
      .findAll();

    if (mAdapter == null) {
      mAdapter = new UploadRecyclerViewAdapter(editedElephantsLive, new UploadRecyclerViewAdapter.Listener() {
        @Override
        public void onSelectButtonClick(boolean selected, Elephant elephant) {
        }

        @Override
        public void onConsultationButtonClick(Elephant elephant) {
          Intent intent = new Intent(UploadActivity.this, ShowElephantActivity.class);
          intent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
          startActivity(intent);
        }
      });
      mRecyclerView.setAdapter(mAdapter);
    }
    mAdapter.resetSelection();
    if (editedElephantsLive != null && editedElephantsLive.size() > 0) {
      emptyLayout.setVisibility(View.GONE);
    } else {
      emptyLayout.setVisibility(View.VISIBLE);
    }
  }

  void syncToServer() {
    dialog = dialog.getBuilder().progress(false, 100, true).content("Serializing data").show();

    SyncToServerTask task = new SyncToServerTask(
      realm.copyFromRealm(editedElephantsLive),
      mAdapter.getSelectedElephants(),
      new SyncToServerTask.Listener() {
        @Override
        public void onPreExecute() {
        }

        @Override
        public void onProgressUpdate(int p) {
          dialog.setProgress(p);
        }

        @Override
        public void onFinish(boolean result, final List<Elephant> editedElephants, JSONArray serialized) {
          if (result) {
            dialog.dismiss();
            dialog = dialog.getBuilder().progress(true, 0).content("Uploading data").show();
            startUpload(serialized, editedElephants);
            Log.w("upload", serialized.toString());
          } else {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error during sync, please try again", Toast.LENGTH_SHORT).show();
          }
        }
      }
    );
    task.execute();
  }

  /*
   * network upload progress ?
   */
  private void startUpload(final JSONArray data, final List<Elephant> editedElephants) {
    RequestQueue queue = Volley.newRequestQueue(UploadActivity.this);

    String url =  "https://elephant-asia.herokuapp.com/api/sync/upload";
    JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST, url, null,
      createOnSuccessListener(editedElephants),
      createOnErrorListener()) {
        @Override
        public Map<String, String> getHeaders() {
          AccountManager accountManager = AccountManager.get(UploadActivity.this);
          Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
          Map<String, String> headers = new HashMap<>();

          try {
            String authToken = accountManager.blockingGetAuthToken(accounts[0], Constants.AUTHTOKEN_TYPE, true);
            Log.w("upload", "auth_token=" + authToken);

            headers.put("Api-Key", authToken);
            headers.put("Content-Type", "application/javascript");
          } catch (Exception e) {
            e.printStackTrace();
          }
          return headers;
        }

        @Override
        public byte[] getBody() {
          return data.toString().getBytes();
        }
      };
    queue.add(req);
  }

  private Response.Listener<JSONArray> createOnSuccessListener(final List<Elephant> editedElephants) {
    return new Response.Listener<JSONArray>() {
      @Override
      public void onResponse(JSONArray response) {
        for (int i = 0 ; i < editedElephants.size() ; ++i) {
          Elephant e = editedElephants.get(i);
          e.syncState = SyncState.Pending.name();
          e.dbState = null;
          if (!e.isPresentInServerDb()) {
            try {
              e.cuid = response.getJSONObject(i).getString("cuid");
            } catch (Exception er) {
              er.printStackTrace();
            }
          }
        }
        realm.executeTransaction(new Realm.Transaction() {
          @Override
          public void execute(@NonNull Realm realm) {
            realm.insertOrUpdate(editedElephants);
            refreshList();
          }
        });

        dialog.dismiss();
        mAdapter.resetSelection();

        String date = DateHelpers.GetCurrentStringDate();
        Preferences.SetLastUploadSync(UploadActivity.this, date);
        Toast.makeText(getApplicationContext(), "Upload successfull", Toast.LENGTH_SHORT).show();
        Log.w("upload", "success response: " + response);
      }
    };
  }

  private Response.ErrorListener createOnErrorListener() {
    return new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        dialog.dismiss();
        Toast.makeText(getApplicationContext(), "Error during the upload", Toast.LENGTH_SHORT).show();
        if (error != null && error.networkResponse != null) {
          String response = new String(error.networkResponse.data);
          Log.w("upload", "error response: " + response);
        }
      }
    };
  }

  static private class SyncToServerTask extends AsyncTask<URL, Integer, Boolean> {

    private int totalSelected = 0;
    private List<Elephant> selectedElephant = new ArrayList<>();
    private Listener listener;
    private JSONArray serialized = new JSONArray();

    SyncToServerTask(List<Elephant> editedElephants, SparseBooleanArray selections, Listener listener) {
      this.listener = listener;

      for (int i = 0; i < selections.size(); i++) {
        boolean isSelected = selections.valueAt(i);
        if (isSelected) {
          selectedElephant.add(editedElephants.get(selections.keyAt(i)));
        }
      }
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      listener.onPreExecute();
    }

    protected Boolean doInBackground(URL... urls) {
      for (int i = 0; i < selectedElephant.size(); i++) {
        publishProgress(i);

          try {
            Elephant e = selectedElephant.get(i);
            String action = ((e.dbState.equals(Elephant.DbState.Created.name())) ? "CREATE" : "EDIT");
            JSONObject obj = e.toJsonObject();
            obj.put("type", "elephant");
            obj.put("action", action);
            serialized.put(obj);
          } catch (Exception e) {
            e.printStackTrace();
          }
      }
      return true;
    }

    protected void onPostExecute(Boolean result) {
      // super.onPostExecute(result);
      listener.onFinish(result, selectedElephant, serialized);
    }

    protected void onProgressUpdate(Integer... progress) {
      int i = progress[0] > 0 ? (int) (((double) progress[0] / (double) totalSelected) * 100) : 0;
      listener.onProgressUpdate(i);
    }

    interface Listener {
      void onPreExecute();
      void onProgressUpdate(int p);
      void onFinish(boolean result, List<Elephant> editedElephants, JSONArray serialized);
    }
  }
}


