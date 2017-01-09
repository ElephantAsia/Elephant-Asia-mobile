package fr.elefantasia.elefantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.adapter.SearchElephantAdapter;
import fr.elefantasia.elefantasia.database.ElefantDatabase;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

public class SearchBrowserActivity extends AppCompatActivity implements SearchElephantAdapter.Listener {

    public static String EXTRA_ELEPHANT = "extra.elephant";

    private ListView mListView;
    private ElefantDatabase database;
    private TextView noItem;
    private ElephantInfo toSearch;

    private static ElephantInfo getElephantInfo(Intent intent) {
        return (ElephantInfo)intent.getParcelableExtra(EXTRA_ELEPHANT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_browser_activity);

        database = new ElefantDatabase(getApplicationContext());
        database.open();

        toSearch = getElephantInfo(getIntent());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView title = (TextView)toolbar.findViewById(R.id.title);
        title.setText(String.format(getString(R.string.search_result), toSearch.name));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mListView = (ListView) findViewById(R.id.listViewSearch);
        noItem = (TextView) findViewById(R.id.search_browser_no_item);

        List<ElephantInfo> elephants = database.getElephant(toSearch.name, toSearch.sex);
        if (elephants.size() == 0) {
            mListView.setVisibility(View.GONE);
            noItem.setVisibility(View.VISIBLE);
        }

        SearchElephantAdapter adapter = new SearchElephantAdapter(getApplicationContext(), this);

        adapter.addList(elephants);
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemClick(ElephantInfo info) {
        Intent intent = new Intent(this, ConsultationActivity.class);
        intent.putExtra(EXTRA_ELEPHANT, info);
        startActivity(intent);
    }
}
