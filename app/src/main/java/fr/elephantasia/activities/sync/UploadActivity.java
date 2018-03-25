package fr.elephantasia.activities.sync;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.sync.fragment.UploadRecyclerViewAdapter;
import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;
import io.realm.RealmResults;

import static fr.elephantasia.database.model.Elephant.DB_STATE;
import static fr.elephantasia.database.model.Elephant.SYNC_STATE;
import static fr.elephantasia.database.model.Elephant.SyncState;


public class UploadActivity extends AppCompatActivity {

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.list) RecyclerView mRecyclerView;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.upload_button) FloatingActionButton btUpload;

  // Listener
  @OnClick(R.id.upload_button)
  public void showConfirmationDialog() {
    new MaterialDialog.Builder(this)
        .title(R.string.confirmation_sync)
        .positiveText(R.string.UPLOAD)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            syncToServer();
          }
        })
        .dismissListener(new DialogInterface.OnDismissListener() {
          @Override
          public void onDismiss(DialogInterface dialog) {
          }
        })
        .negativeText(R.string.CANCEL)
        .show();
  }

  // Attributes
  private UploadRecyclerViewAdapter mAdapter;
  private SparseBooleanArray selections;
  List<Elephant> editedElephants;
  RealmResults<Elephant> editedElephantsLive;
  private Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.upload_activity);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    btUpload.setImageDrawable(
        new IconicsDrawable(this)
            .icon(MaterialDesignIconic.Icon.gmi_cloud_upload)
            .color(Color.WHITE).sizeDp(24)
    );


    mRecyclerView.setHasFixedSize(true);

    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    mRecyclerView.setLayoutManager(mLayoutManager);

    realm = Realm.getDefaultInstance();

//    editedElephantsLive =
//        realm.where(Elephant.class)
//            .isNotNull(DB_STATE)
//            .findAll();
////    realm.executeTransaction(new Realm.Transaction() {
////      @Override
////      public void execute(@NonNull Realm realm) {
////        editedElephantsLive =
////            realm.where(Elephant.class)
////                .isNotNull(DB_STATE)
////                .findAll();
////        for (Elephant el : editedElephantsLive) {
////          el.syncState = null;
////        }
////      }
////    });

    editedElephantsLive =
        realm.where(Elephant.class)
            .isNotNull(DB_STATE)
            .isNull(SYNC_STATE)
            .findAll();
    mAdapter = new UploadRecyclerViewAdapter(editedElephantsLive);
    mRecyclerView.setAdapter(mAdapter);
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

  void syncToServer() {
    SyncToServerTask task = new SyncToServerTask();
    task.execute();
  }

  private class SyncToServerTask extends AsyncTask<URL, Integer, Boolean> {
    private int totalSelected = 0;

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      selections = mAdapter.getSelectedElephants();
      editedElephants = realm.copyFromRealm(editedElephantsLive);

      for (int i = 0; i < selections.size(); i++) {
        totalSelected = selections.valueAt(i) ? totalSelected + 1 : totalSelected;
      }

      progressBar.setVisibility(View.VISIBLE);
      mRecyclerView.setVisibility(View.GONE);
    }

    protected Boolean doInBackground(URL... urls) {
      for (int i = 0; i < selections.size(); i++) {

        publishProgress(i);

        boolean isSelected = selections.valueAt(i);

        // TODO: Check if the request is syncrhonous,
        // TODO: call api to add or update elephant
        // TODO: handle error during request (do not update sync state)

        if (isSelected) {
          Elephant e = editedElephants.get(selections.keyAt(i));
          e.syncState = SyncState.pending.name();
          // add new elephant
          if (e.cuid == null) {

          } else {
            // update new elephant
          }
        }
      }
      return true;
    }

    protected void onPostExecute(Boolean result) {
      if (result) {
        realm.executeTransaction(new Realm.Transaction() {
          @Override
          public void execute(@NonNull Realm realm) {
            realm.insertOrUpdate(editedElephants);
            editedElephantsLive =
                realm.where(Elephant.class)
                    .isNotNull(DB_STATE)
                    .isNull(SYNC_STATE)
                    .findAll();
          }
        });
        mAdapter.resetSelection();

        Toast.makeText(getApplicationContext(), "Syncing done succesfully", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(getApplicationContext(), "Error during sync, please try again", Toast.LENGTH_SHORT).show();
      }

      progressBar.setVisibility(View.GONE);
      mRecyclerView.setVisibility(View.VISIBLE);
    }

    protected void onProgressUpdate(Integer... progress) {
      int i = progress[0] > 0 ? (int) (((double) progress[0] / (double) totalSelected) * 100) : 0;
      progressBar.setProgress(i);
    }
  }
}


