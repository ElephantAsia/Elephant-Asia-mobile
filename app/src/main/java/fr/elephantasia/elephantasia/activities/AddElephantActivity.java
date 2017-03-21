package fr.elephantasia.elephantasia.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.ViewPagerAdapter;
import fr.elephantasia.elephantasia.database.Database;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantBottomSheet;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantDescriptionFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantDocumentFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantLocationFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantOwnershipFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantParentageFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantRegistrationFragment;
import fr.elephantasia.elephantasia.interfaces.AddElephantInterface;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.StaticTools;
import fr.elephantasia.elephantasia.utils.UserInfo;

public class AddElephantActivity extends AppCompatActivity implements AddElephantInterface {

    private static final int REQUEST_ADD_OWNER = 1;
    private static final int REQUEST_SET_FATHER = 2;
    private static final int REQUEST_SET_MOTHER = 3;
    private static final int REQUEST_ADD_CHILDREN = 4;

    private static final int FRAGMENT_OWNERSHIP_IDX = 2;
    private static final int FRAGMENT_PARENTAGE_IDX = 3;

    private Database database;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabNext;
    private BottomSheetDialogFragment bs;

    private ViewPagerAdapter adapter;

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
                FloatingActionButton button = (FloatingActionButton) findViewById(R.id.add_elephant_fab);

                if (button != null && keyboardIsUp) {
                    button.hide();
                } else if (button != null) {
                    button.show();
                }
            }
        });

        bs = AddElephantBottomSheet.newInstance();
        fabNext = (FloatingActionButton)findViewById(R.id.add_elephant_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
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

        database = new Database(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_OWNER) {
            if (resultCode == RESULT_OK && data != null) {
                UserInfo user = data.getParcelableExtra(SelectOwnerActivity.EXTRA_RESULT_USER_SELECTED);
                refreshOwner(user);
            }
        } else if (requestCode == REQUEST_SET_FATHER) {
            if (resultCode == RESULT_OK && data != null) {
                ElephantInfo info = data.getParcelableExtra(SearchActivity.EXTRA_RESULT);
                refreshFather(info);
            }
        } else if (requestCode == REQUEST_SET_MOTHER) {
            if (resultCode == RESULT_OK && data != null) {
                ElephantInfo info = data.getParcelableExtra(SearchActivity.EXTRA_RESULT);
                refreshMother(info);
            }
        } else if (requestCode == REQUEST_ADD_CHILDREN) {
            if (resultCode == RESULT_OK && data != null) {
                ElephantInfo info = data.getParcelableExtra(SearchActivity.EXTRA_RESULT);
                refreshChildren(info);
            }
        }
    }

    @Override
    public void nextPage() {
        /*if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }*/

        elephantInfo.displayAttr();
        database.insert(elephantInfo);
        setResult(RESULT_OK);
        finish();
    }

    public ElephantInfo getElephantInfo() {
        return this.elephantInfo;
    }

    private void confirmFinish(final boolean confirm) {
        if (confirm && elephantInfo != null && elephantInfo.isValid()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.put_drafts)
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            confirmFinish(false);
                        }
                    })
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            elephantInfo.state = ElephantInfo.State.DRAFT;
                            database.update(elephantInfo);
                            confirmFinish(false);
                        }
                    })
                    .show();
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void addOwner() {
        Intent intent = new Intent(this, SelectOwnerActivity.class);
        startActivityForResult(intent, REQUEST_ADD_OWNER);
    }

    @Override
    public void setFather() {
        Intent intent = new Intent(this, SearchActivity.class);
        SearchActivity.setMode(intent, SearchActivity.Mode.PICK_RESULT);
        startActivityForResult(intent, REQUEST_SET_FATHER);
    }

    @Override
    public void setMother() {
        Intent intent = new Intent(this, SearchActivity.class);
        SearchActivity.setMode(intent, SearchActivity.Mode.PICK_RESULT);
        startActivityForResult(intent, REQUEST_SET_MOTHER);
    }

    @Override
    public void addChildren() {
        Intent intent = new Intent(this, SearchActivity.class);
        SearchActivity.setMode(intent, SearchActivity.Mode.PICK_RESULT);
        startActivityForResult(intent, REQUEST_ADD_CHILDREN);
    }

    @Override
    public void onElephantClick(ElephantInfo elephant) {
        Intent intent = new Intent(this, ConsultationActivity.class);
        ConsultationActivity.setElephant(intent, elephant);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void refreshOwner(UserInfo user) {
        AddElephantOwnershipFragment fragment = (AddElephantOwnershipFragment)adapter.getItem(FRAGMENT_OWNERSHIP_IDX);

        elephantInfo.addOwner(user.id);
        fragment.refreshOwner(user);
    }

    private void refreshFather(ElephantInfo info) {
        AddElephantParentageFragment fragment = (AddElephantParentageFragment)adapter.getItem(FRAGMENT_PARENTAGE_IDX);

        elephantInfo.father = info.id.toString();
        fragment.refreshFather(info);
    }

    private void refreshMother(ElephantInfo info) {
        AddElephantParentageFragment fragment = (AddElephantParentageFragment)adapter.getItem(FRAGMENT_PARENTAGE_IDX);

        elephantInfo.mother = info.id.toString();
        fragment.refreshMother(info);
    }

    private void refreshChildren(ElephantInfo info) {
        AddElephantParentageFragment fragment = (AddElephantParentageFragment)adapter.getItem(FRAGMENT_PARENTAGE_IDX);

        elephantInfo.addChildren(info.id);
        fragment.refreshChildren(info);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddElephantRegistrationFragment(), getString(R.string.registration));
        adapter.addFragment(new AddElephantDescriptionFragment(), getString(R.string.description));
        adapter.addFragment(new AddElephantOwnershipFragment(), getString(R.string.ownership));
        adapter.addFragment(new AddElephantParentageFragment(), getString(R.string.parentage));
        adapter.addFragment(new AddElephantDocumentFragment(), getString(R.string.documents));
        adapter.addFragment(new AddElephantLocationFragment(), getString(R.string.location));
        viewPager.setAdapter(adapter);
    }

    public void showDialogFragment(DialogFragment dialog) {
        dialog.show(getSupportFragmentManager(), "Date");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_elephant_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_elephant_menu) {
            bs.show(getSupportFragmentManager(), bs.getTag());
            return true;
        }
        return false;
    }

}
