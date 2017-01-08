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
import fr.elefantasia.elefantasia.adapter.ElephantAdapter;
import fr.elefantasia.elefantasia.database.ElefantDatabase;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

public class SearchBrowserActivity extends AppCompatActivity {

    public static String EXTRA_NAME = "search.browser.name";

    private String name;
    private ListView mListView;
    private ElefantDatabase database;
    private TextView noItem;

    public static void setName(Intent intent, String value) {
        intent.putExtra(EXTRA_NAME, value);
    }

    public static String getName(Intent intent) {
        return intent.getStringExtra(EXTRA_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_browser_activity);

        database = new ElefantDatabase(getApplicationContext());
        database.open();

        name = getName(getIntent());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView title = (TextView)toolbar.findViewById(R.id.title);
        title.setText(String.format(getString(R.string.search_result), name));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mListView = (ListView) findViewById(R.id.listViewSearch);
        noItem = (TextView) findViewById(R.id.search_browser_no_item);

        List<ElephantInfo> elephants = database.getElephantByName(name);
        if (elephants.size() == 0) {
            mListView.setVisibility(View.GONE);
            noItem.setVisibility(View.VISIBLE);
        }

        ElephantAdapter adapter = new ElephantAdapter(getApplicationContext());
        adapter.addList(elephants);
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
