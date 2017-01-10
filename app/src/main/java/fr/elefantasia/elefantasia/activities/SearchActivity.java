package fr.elefantasia.elefantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.fragment.SearchFragment;
import fr.elefantasia.elefantasia.interfaces.SearchInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

public class SearchActivity extends AppCompatActivity implements SearchInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setSearchFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        return true;
    }

    @Override
    public void onClickSearch(ElephantInfo info) {
        Intent intent = new Intent(this, SearchBrowserActivity.class);
        intent.putExtra(SearchBrowserActivity.EXTRA_ELEPHANT, info);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void setSearchFragment() {
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        searchFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.search_fragment, searchFragment).commit();
    }

}
