package fr.elephantasia.elephantasia.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.ViewPagerAdapter;
import fr.elephantasia.elephantasia.database.ElephantDatabase;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantConsultationFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantDescriptionFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantDocumentFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantLocationFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantOwnershipFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantParentageFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantRegistrationFragment;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.StaticTools;

public class AddElephantActivity extends AppCompatActivity {

    private ElephantDatabase database;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ElephantInfo elephantInfo;

    public AddElephantActivity() {
        elephantInfo = new ElephantInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_elephant_activity);

        final View activityRootView = findViewById(R.id.add_elephant_activity);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean keyboardIsUp = StaticTools.keyboardIsDisplay(activityRootView);
                FloatingActionButton button = (FloatingActionButton) findViewById(R.id.elephant_registration_fab);

                if (button != null && keyboardIsUp) {
                    button.hide();
                } else if (button != null) {
                    button.show();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        database = new ElephantDatabase(this);
        database.open();
    }

    @Override
    public boolean onSupportNavigateUp() {
        confirmFinish(true);
        return true;
    }

    @Override
    public void onBackPressed() {
        confirmFinish(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void nextPage() {
        /*if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }*/

        elephantInfo.displayAttr();
        database.insertElephant(elephantInfo);
        setResult(RESULT_OK);
        finish();
    }

    public ElephantInfo getElephantInfo() {
        return this.elephantInfo;
    }

    private void confirmFinish(final boolean confirm) {
        if (confirm) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.put_drafts)
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            elephantInfo.state = ElephantInfo.State.DRAFT;
                            database.updateElephant(elephantInfo);
                            confirmFinish(false);
                        }
                    })
                    .show();
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddElephantRegistrationFragment(), getString(R.string.registration));
        adapter.addFragment(new AddElephantDescriptionFragment(), getString(R.string.description));
        adapter.addFragment(new AddElephantOwnershipFragment(), getString(R.string.ownership));
        adapter.addFragment(new AddElephantParentageFragment(), getString(R.string.parentage));
        adapter.addFragment(new AddElephantConsultationFragment(), getString(R.string.consultations));
        adapter.addFragment(new AddElephantDocumentFragment(), getString(R.string.documents));
        adapter.addFragment(new AddElephantLocationFragment(), getString(R.string.location));
        viewPager.setAdapter(adapter);
    }

    public void showDialogFragment(DialogFragment dialog) {
        dialog.show(getSupportFragmentManager(), "Date");
    }

}
