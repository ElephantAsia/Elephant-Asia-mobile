package fr.elefantasia.elefantasia.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.adapter.ViewPagerAdapter;
import fr.elefantasia.elefantasia.database.ElefantDatabase;
import fr.elefantasia.elefantasia.fragment.AddElephantConsultationFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantDescriptionFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantDocumentFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantLocationFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantOwnershipFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantParentageFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantRegistrationFragment;
import fr.elefantasia.elefantasia.interfaces.AddElephantInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;
import fr.elefantasia.elefantasia.utils.StaticTools;

public class AddElephantActivity extends AppCompatActivity implements AddElephantInterface {

    private ElefantDatabase database;
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

        //TODO: check avec galibe si c est pas trop degeu
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                FloatingActionButton button = (FloatingActionButton) findViewById(R.id.elephant_registration_fab);

                if (button != null) {
                    if (StaticTools.keyboardIsDisplay(activityRootView)) {
                        button.hide();
                    } else {
                        button.show();
                    }
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

        database = new ElefantDatabase(this);
        database.open();
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void nextPage() {
        /*if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }*/

        database.insertElephant(elephantInfo);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setName(String name) {
        elephantInfo.name = name;
    }

    @Override
    public void setNickname(String nickname) {
        elephantInfo.nickName = nickname;
    }

    @Override
    public void setSex(ElephantInfo.Gender sex) {
        elephantInfo.sex = sex;
    }

    @Override
    public void hasEarTag(boolean value) {
        elephantInfo.earTag = value;
    }

    @Override
    public void hasEyeD(boolean value) {
        elephantInfo.eyeD = value;
    }

    @Override
    public void setBirthdate(String date) {
        elephantInfo.birthDate = date;
    }

    @Override
    public void setBirthVillage(String village) {
        elephantInfo.birthVillage = village;
    }

    @Override
    public void setBirthDistrict(String district) {
        elephantInfo.birthDistrict = district;
    }

    @Override
    public void setBirthProvince(String province) {
        elephantInfo.birthProvince = province;
    }

    @Override
    public void setChipNumber(String value) {
        elephantInfo.chips1 = value;
    }

    @Override
    public void setRegistrationID(String id) {
        elephantInfo.registrationID = id;
    }

    @Override
    public void setRegistrationVillage(String village) {
        elephantInfo.registrationVillage = village;
    }

    @Override
    public void setRegistrationDistrict(String district) {
        elephantInfo.registrationDistrict = district;
    }

    @Override
    public void setRegistrationProvince(String province) {
        elephantInfo.registrationProvince = province;
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

}
