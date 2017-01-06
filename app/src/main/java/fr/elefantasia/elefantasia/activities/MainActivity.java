package fr.elefantasia.elefantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.adapter.MainActivityDrawerListAdapter;
import fr.elefantasia.elefantasia.utils.Preferences;
import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_FRAGMENT = "main.fragment";

    private static final int FRAGMENT_HOME_PAGE = 0;
    private static final int FRAGMENT_SEARCH = 1;
    private static final int FRAGMENT_CURRENT_ANIMAL = 2;
    private static final int FRAGMENT_LOCATIONS = 3;
    private static final int FRAGMENT_REPORTS = 4;
    private static final int FRAGMENT_FAVORITES = 5;
    private static final int FRAGMENT_SETTINGS = 6;
    private static final int FRAGMENT_OFFICIALS = 7;
    private static final int FRAGMENT_SUPPORT = 8;
    private static final int FRAGMENT_DISCONNECT = 9;

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    private ImageView profilPicBlurred;
    private CircleImageView profilPic;

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

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        profilPicBlurred = (ImageView)findViewById(R.id.main_drawer_pic_profil_blurred);
        profilPic = (CircleImageView)findViewById(R.id.main_drawer_pic_profil);

        setSupportActionBar(toolbar);

        drawer = (DrawerLayout)findViewById(R.id.main_drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.mipmap.hamburger);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout)findViewById(R.id.main_drawer);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }

                refreshProfilPicBlurred();
            }
        });

        drawerList = (ListView)findViewById(R.id.main_drawer_list);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerListAdapter.setSelection(position);
                drawerListAdapter.notifyDataSetChanged();

                setFragment(getIntent(), position);
                refreshFragment();
            }
        });

        drawerListAdapter = new MainActivityDrawerListAdapter(getApplicationContext());
        drawerList.setAdapter(drawerListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshProfilPicBlurred();
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
        switch (getFragment(getIntent()))
        {
            // TODO: set fragment here
            case FRAGMENT_DISCONNECT:
                disconnect();
                break;
            default:
        }
    }

    private void disconnect() {
        Preferences.setUsername(getApplicationContext(), null);
        Preferences.setPassword(getApplicationContext(), null);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
