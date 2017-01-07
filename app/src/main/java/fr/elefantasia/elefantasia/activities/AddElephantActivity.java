package fr.elefantasia.elefantasia.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.adapter.ViewPagerAdapter;
import fr.elefantasia.elefantasia.fragment.AddElephantCivilStatusFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantGenealogyFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantHealFragment;
import fr.elefantasia.elefantasia.fragment.AddElephantPhysicalFragment;
import fr.elefantasia.elefantasia.interfaces.AddElephantInterface;

public class AddElephantActivity extends AppCompatActivity implements AddElephantInterface {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //private FloatingActionButton fabNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_elephant_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*fabNext = (FloatingActionButton)findViewById(R.id.add_elephant_activity_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void next() {
        if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddElephantCivilStatusFragment(), getString(R.string.civil_status));
        adapter.addFragment(new AddElephantPhysicalFragment(), getString(R.string.physical));
        adapter.addFragment(new AddElephantHealFragment(), getString(R.string.heal));
        adapter.addFragment(new AddElephantGenealogyFragment(), getString(R.string.genealogy));
        viewPager.setAdapter(adapter);
    }

}
