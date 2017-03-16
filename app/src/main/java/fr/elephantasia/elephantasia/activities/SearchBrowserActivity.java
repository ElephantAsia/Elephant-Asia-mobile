package fr.elephantasia.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.SearchElephantAdapter;
import fr.elephantasia.elephantasia.database.ElephantDatabase;
import fr.elephantasia.elephantasia.utils.ElephantInfo;

public class SearchBrowserActivity extends AppCompatActivity implements SearchElephantAdapter.Listener {

    public final static String EXTRA_ELEPHANT = "extra.elephant";

    private final static int REQUEST_CONSULTATION = 1;

    private ListView mListView;
    private ElephantDatabase database;
    private TextView noItem;
    private ElephantInfo toSearch;
    private SearchElephantAdapter adapter;

    private static ElephantInfo getElephantInfo(Intent intent) {
        return (ElephantInfo)intent.getParcelableExtra(EXTRA_ELEPHANT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_browser_activity);

        database = new ElephantDatabase(getApplicationContext());
        database.open();

        toSearch = getElephantInfo(getIntent());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView title = (TextView)toolbar.findViewById(R.id.title);
        title.setText(getString(R.string.search_result));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mListView = (ListView) findViewById(R.id.listViewSearch);
        noItem = (TextView) findViewById(R.id.search_browser_no_item);
        refreshList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONSULTATION) {
            if (resultCode == RESULT_OK) {
                refreshList();
            }
        }
    }

    @Override
    public void onItemClick(ElephantInfo info) {
        Intent intent = new Intent(this, ConsultationActivity.class);
        intent.putExtra(EXTRA_ELEPHANT, info);
        startActivityForResult(intent, REQUEST_CONSULTATION);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void refreshList() {
        List<ElephantInfo> elephants = database.getElephantFromSearch(toSearch);
        if (elephants.size() == 0) {
            mListView.setVisibility(View.GONE);
            noItem.setVisibility(View.VISIBLE);
        }

        adapter = new SearchElephantAdapter(getApplicationContext(), this);

        adapter.addList(elephants);
        mListView.setAdapter(adapter);
    }
}
