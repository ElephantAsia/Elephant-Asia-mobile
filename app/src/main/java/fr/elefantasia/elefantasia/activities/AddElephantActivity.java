package fr.elefantasia.elefantasia.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.adapter.ViewPagerAdapter;
import fr.elefantasia.elefantasia.database.ElefantDatabase;
import fr.elefantasia.elefantasia.fragment.AddElephantConsultationFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantDescriptionFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantDocumentFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantLocationFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantParentageFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantRegistrationFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantOwnershipFragment;
import fr.elefantasia.elefantasia.interfaces.AddElephantInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

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
    public void next() {
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
    public void setElephantName(String name) {
        elephantInfo.setName(name);
    }

    @Override
    public void setElephantNickname(String nickname) {
        elephantInfo.setNickname(nickname);
    }

    @Override
    public void setElephantIDNumber(String number) {
        elephantInfo.setRegistrationName(number);
    }

    @Override
    public void setElephantChipNumber(String value) {
        elephantInfo.addChips(value);
    }

    @Override
    public void setElephantSex(ElephantInfo.Gender sex) {
        elephantInfo.setSex(sex);
    }

    @Override
    public void setElephantBirthdate(String birthdate) {
        elephantInfo.setBirthdate(birthdate);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddElephantRegistrationFragment(), getString(R.string.registration));
        adapter.addFragment(new AddElephantOwnershipFragment(), getString(R.string.ownership));
        adapter.addFragment(new AddElephantDescriptionFragment(), getString(R.string.description));
        adapter.addFragment(new AddElephantParentageFragment(), getString(R.string.parentage));
        adapter.addFragment(new AddElephantConsultationFragment(), getString(R.string.consultations));
        adapter.addFragment(new AddElephantDocumentFragment(), getString(R.string.documents));
        adapter.addFragment(new AddElephantLocationFragment(), getString(R.string.location));
        viewPager.setAdapter(adapter);
    }

}
