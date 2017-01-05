package fr.elefantasia.elefantasia.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.fragment.ElephantCivilStatus;
import fr.elefantasia.elefantasia.fragment.ElephantPhysical;
import fr.elefantasia.elefantasia.utils.ViewPagerAdapter;


public class AddElephant extends AppCompatActivity {

    private ElephantCivilStatus fragment;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_elephant);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ElephantCivilStatus(), "Civil Status");
        adapter.addFragment(new ElephantPhysical(), "Physical");
        viewPager.setAdapter(adapter);
    }

}
