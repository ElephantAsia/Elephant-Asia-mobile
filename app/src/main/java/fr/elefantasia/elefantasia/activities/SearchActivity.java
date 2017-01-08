package fr.elefantasia.elefantasia.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.adapter.ElephantAdapter;
import fr.elefantasia.elefantasia.database.ElefantDatabase;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

/**
 * Created by care_j on 15/11/16.
 */

public class SearchActivity extends AppCompatActivity {

    private ListView mListView;
    private ElefantDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        database = new ElefantDatabase(getApplicationContext());
        database.open();

        mListView = (ListView) findViewById(R.id.listViewSearch);

        List<ElephantInfo> elephants = database.getElephantByName("test");

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
