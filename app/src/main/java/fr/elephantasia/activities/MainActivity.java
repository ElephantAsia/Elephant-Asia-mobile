package fr.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
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

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.R;
import fr.elephantasia.adapter.MainActivityDrawerListAdapter;
import fr.elephantasia.fragment.HomePageFragment;
import fr.elephantasia.utils.Preferences;
import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {

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
  private ActionBarDrawerToggle toggle;
  private DrawerLayout drawer;

  private ImageView profilPicBlurred;
  private CircleImageView profilPic;

  private FloatingActionButton addFab;

  private ListView drawerList;
  private MainActivityDrawerListAdapter drawerListAdapter;

  public static int getFragment(Intent intent) {
    return intent.getIntExtra(EXTRA_FRAGMENT, FRAGMENT_HOME_PAGE);
  }

  public static void setFragment(Intent intent, int value) {
    intent.putExtra(EXTRA_FRAGMENT, value);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
    profilPicBlurred = (ImageView) findViewById(R.id.main_drawer_pic_profil_blurred);
    profilPic = (CircleImageView) findViewById(R.id.main_drawer_pic_profil);

    setSupportActionBar(toolbar);

    drawer = (DrawerLayout) findViewById(R.id.main_drawer);
    toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
    toggle.setDrawerIndicatorEnabled(false);
    toggle.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
    toggle.syncState();

    toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
          drawer.closeDrawer(GravityCompat.START);
        } else {
          drawer.openDrawer(GravityCompat.START);
        }

        refreshProfilPicBlurred();
      }
    });

    drawerList = (ListView) findViewById(R.id.main_drawer_list);
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

    drawerListAdapter = new MainActivityDrawerListAdapter(getApplicationContext());
    drawerList.setAdapter(drawerListAdapter);

    addFab = (FloatingActionButton) findViewById(R.id.home_page_fab);
    addFab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addElephant();
      }
    });

    refreshFragment();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
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
              Blurry.with(MainActivity.this).radius(25).sampling(2).capture(profilPic).into(profilPicBlurred);
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
    HomePageFragment fragment = new HomePageFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
  }

  private void setDisconnectFragment() {
    Preferences.setUsername(getApplicationContext(), null);
    Preferences.setPassword(getApplicationContext(), null);

    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
    finish();
  }

  public void addElephant() {
    Intent intent = new Intent(this, AddElephantActivity.class);
    startActivityForResult(intent, REQUEST_ADD_ELEPHANT);
  }
}
