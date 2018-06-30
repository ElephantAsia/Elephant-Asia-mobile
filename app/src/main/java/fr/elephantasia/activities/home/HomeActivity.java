package fr.elephantasia.activities.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.elephantasia.BaseApplication;
import fr.elephantasia.R;
import fr.elephantasia.activities.home.adapters.HomeDrawerListAdapter;
import fr.elephantasia.activities.home.fragments.HomeOverviewFragment;
import fr.elephantasia.activities.home.fragments.HomeRecentFragment;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.activities.searchElephant.SearchElephantActivity;
import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.DateHelpers;
import fr.elephantasia.utils.Preferences;
import io.realm.RealmResults;
import jp.wasabeef.blurry.Blurry;

public class HomeActivity extends AppCompatActivity {

  public static final int REQUEST_ADD_ELEPHANT = 1;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.main_drawer_pic_profil_blurred) ImageView profilPicBlurred;
  @BindView(R.id.main_drawer_pic_profil) CircleImageView profilPic;
  @BindView(R.id.main_drawer) DrawerLayout drawer;
  @BindView(R.id.main_drawer_list) ListView drawerList;
  @BindView(R.id.main_drawer_last_sync) TextView lastSyncTextView;

  private DatabaseController databaseController;
  private HomeDrawerListAdapter drawerListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home_activity);
    ButterKnife.bind(this);

    databaseController = ((BaseApplication)getApplication()).getDatabaseController();

    setSupportActionBar(toolbar);

    initActionBarDrawer();
    initActionBarDrawerList();

    setFragment();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  private void initActionBarDrawer() {
    ActionBarDrawerToggle actionBarDrawer = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
    actionBarDrawer.setDrawerIndicatorEnabled(false);
    actionBarDrawer.setHomeAsUpIndicator(
        new IconicsDrawable(this)
            .icon(MaterialDesignIconic.Icon.gmi_menu)
            .color(Color.WHITE)
            .sizeDp(24));
    actionBarDrawer.syncState();
    actionBarDrawer.setToolbarNavigationClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
          drawer.closeDrawer(GravityCompat.START);
        } else {
          drawer.openDrawer(GravityCompat.START);
        }

        refreshProfilPicBlurred();
      }
    });
  }

  private void initActionBarDrawerList() {
    drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        drawerListAdapter.setSelection(position);
        view.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.primary_light));
        drawerListAdapter.notifyDataSetChanged();
        drawer.closeDrawer(GravityCompat.START);
      }
    });

    drawerListAdapter = new HomeDrawerListAdapter(getApplicationContext());
    drawerList.setAdapter(drawerListAdapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    menu.findItem(R.id.main_menu_search).setIcon(
        new IconicsDrawable(this)
            .icon(MaterialDesignIconic.Icon.gmi_search)
            .color(Color.WHITE)
            .sizeDp(22)
    );
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.main_menu_search) {
      Intent search = new Intent(this, SearchElephantActivity.class);
      startActivity(search);
      overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
      return true;
    }
    return false;
  }

  @Override
  public void onResume() {
    super.onResume();
    refreshProfilPicBlurred();

    if (Preferences.IsLastDownloadSyncNeverHappened(this)) {
      lastSyncTextView.setText(getResources().getString(R.string.last_sync, "Never"));
    } else {
      String lastSync = DateHelpers.FriendlyUserStringDate(Preferences.GetLastDownloadSync(this));
      lastSyncTextView.setText(getResources().getString(R.string.last_sync, lastSync));
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_ADD_ELEPHANT) {
      if (resultCode == ManageElephantActivity.RESULT_DRAFT) {
        Toast.makeText(this, R.string.elephant_draft_added, Toast.LENGTH_SHORT).show();
      } else if (resultCode == ManageElephantActivity.RESULT_VALIDATE) {
        Toast.makeText(this, R.string.elephant_local_added, Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void refreshProfilPicBlurred() {
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            try {
              Blurry.with(HomeActivity.this)
                .radius(25)
                .sampling(2)
                .capture(profilPic)
                .into(profilPicBlurred);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
    }, 10);
  }

  private void setFragment() {
    HomeRecentFragment recentFragment = new HomeRecentFragment();
    recentFragment.setArguments(new Bundle());
    getSupportFragmentManager().beginTransaction().replace(R.id.recent_fragment, recentFragment).commit();

    HomeOverviewFragment fragment = new HomeOverviewFragment();
    fragment.setArguments(new Bundle());
    getSupportFragmentManager().beginTransaction().replace(R.id.overview_fragment, fragment).commit();
  }

  public Long getElephantsCount() {
    return databaseController.getElephantsCount();
  }

  public Long getElephantsSyncStatePendingCount() {
    return databaseController.getElephantsSyncStatePendingCount();
  }

  public Long getElephantsReadyToSyncCount() {
    return databaseController.getElephantsReadyToSyncCount();
  }

  public Long getElephantsInDraftCount() {
    return databaseController.getElephantsDraftCount();
  }

  public RealmResults<Elephant> getLastVisitedElephants() {
    return databaseController.getLastVisitedElephant();
  }

}
