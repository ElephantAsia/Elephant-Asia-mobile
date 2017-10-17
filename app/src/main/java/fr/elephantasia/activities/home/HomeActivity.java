package fr.elephantasia.activities.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.elephantasia.R;
import fr.elephantasia.activities.LoginActivity;
import fr.elephantasia.activities.searchElephant.SearchElephantActivity;
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.adapter.HomeDrawerListAdapter;
import fr.elephantasia.utils.Preferences;
import io.realm.Realm;
import jp.wasabeef.blurry.Blurry;

public class HomeActivity extends AppCompatActivity {

  public static final int REQUEST_ADD_ELEPHANT = 1;
  private static final String EXTRA_FRAGMENT = "main.fragment";
  private static final int FRAGMENT_HOME_PAGE = 0;
  //private static final int FRAGMENT_SEARCH = 1;
  private static final int FRAGMENT_CURRENT_ANIMAL = 1;
  private static final int FRAGMENT_LOCATIONS = 2;
  private static final int FRAGMENT_REPORTS = 3;
  private static final int FRAGMENT_FAVORITES = 4;
  private static final int FRAGMENT_SETTINGS = 5;
  private static final int FRAGMENT_OFFICIALS = 6;
  private static final int FRAGMENT_SUPPORT = 7;
  private static final int FRAGMENT_DISCONNECT = 8;

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.main_drawer_pic_profil_blurred) ImageView profilPicBlurred;
  @BindView(R.id.main_drawer_pic_profil) CircleImageView profilPic;
  @BindView(R.id.main_drawer) DrawerLayout drawer;
  @BindView(R.id.main_drawer_list) ListView drawerList;
  @BindView(R.id.home_page_fab) FloatingActionButton fab;

  // Listeners Binding
  @OnClick(R.id.home_page_fab)
  public void addElephant() {
    Intent intent = new Intent(this, AddElephantActivity.class);
    startActivityForResult(intent, REQUEST_ADD_ELEPHANT);
  }

  // Attr
  private HomeDrawerListAdapter drawerListAdapter;
  private Realm realm;


  public static int getFragment(Intent intent) {
    return intent.getIntExtra(EXTRA_FRAGMENT, FRAGMENT_HOME_PAGE);
  }

  public static void setFragment(Intent intent, int value) {
    intent.putExtra(EXTRA_FRAGMENT, value);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home_activity);
    ButterKnife.bind(this);

    fab.setImageDrawable(new IconicsDrawable(this)
        .icon(MaterialDesignIconic.Icon.gmi_plus)
        .color(Color.WHITE)
        .sizeDp(24));

    setSupportActionBar(toolbar);
    initActionBarDrawer();
    initActionBarDrawerList();

    refreshFragment();
    realm = Realm.getDefaultInstance();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    realm.close();
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
        drawerListAdapter.notifyDataSetChanged();

        drawer.closeDrawer(GravityCompat.START);

        setFragment(getIntent(), position);
        refreshFragment();
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

    drawerListAdapter.setSelection(FRAGMENT_HOME_PAGE);
    drawerListAdapter.notifyDataSetChanged();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_ADD_ELEPHANT) {
      if (resultCode == AddElephantActivity.RESULT_DRAFT) {
        Toast.makeText(this, R.string.elephant_draft_added, Toast.LENGTH_SHORT).show();
      } else if (resultCode == AddElephantActivity.RESULT_VALIDATE) {
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
              Blurry.with(HomeActivity.this).radius(25).sampling(2).capture(profilPic).into(profilPicBlurred);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
    }, 10);
  }

  private void refreshFragment() {
    switch (getFragment(getIntent())) {
      case FRAGMENT_HOME_PAGE:
        setHomePageFragment();
        break;
      case FRAGMENT_DISCONNECT:
        setDisconnectFragment();
        break;
      default:
    }
  }

  private void setHomePageFragment() {
    HomeRecentFragment recentFragment = new HomeRecentFragment();
    recentFragment.setArguments(new Bundle());
    getSupportFragmentManager().beginTransaction().replace(R.id.recent_fragment, recentFragment).commit();

    HomeOverviewFragment fragment = new HomeOverviewFragment();
    fragment.setArguments(new Bundle());
    getSupportFragmentManager().beginTransaction().replace(R.id.data_fragment, fragment).commit();
  }

  private void setDisconnectFragment() {
    Preferences.setUsername(getApplicationContext(), null);
    Preferences.setPassword(getApplicationContext(), null);

    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
    finish();
  }

  public Realm getRealm() {
    return this.realm;
  }
}
