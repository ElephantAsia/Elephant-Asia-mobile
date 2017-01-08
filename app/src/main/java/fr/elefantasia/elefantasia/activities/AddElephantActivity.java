package fr.elefantasia.elefantasia.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.adapter.ViewPagerAdapter;
import fr.elefantasia.elefantasia.database.ElefantDatabase;
import fr.elefantasia.elefantasia.fragment.AddElephantCivilStatusFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantPhysicalFragment;
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
        adapter.addFragment(new AddElephantCivilStatusFragment(), getString(R.string.civil_status));
        adapter.addFragment(new AddElephantPhysicalFragment(), getString(R.string.physical));
        adapter.addFragment(new AddElephantPhysicalFragment(), getString(R.string.heal));
        adapter.addFragment(new AddElephantPhysicalFragment(), getString(R.string.genealogy));
        viewPager.setAdapter(adapter);
    }

}
