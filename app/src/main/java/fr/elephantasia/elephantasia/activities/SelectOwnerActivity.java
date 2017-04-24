package fr.elephantasia.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.OwnershipAdapter;
import fr.elephantasia.elephantasia.interfaces.OwnershipListener;
import fr.elephantasia.elephantasia.utils.UserInfo;

public class SelectOwnerActivity extends AppCompatActivity {

    public static final String EXTRA_RESULT_USER_SELECTED = "user_selected";

    private ListView list;
    private OwnershipAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_owner_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        list = (ListView)findViewById(R.id.list);
        adapter = new OwnershipAdapter(this, false, new OwnershipListener() {
            @Override
            public void onAddClick() {
                // nothing to do
            }

            @Override
            public void onItemClick(UserInfo userInfo) {
                Intent result = new Intent();
                result.putExtra(EXTRA_RESULT_USER_SELECTED, userInfo);
                setResult(RESULT_OK, result);
                finish();
            }
        });

        list.setAdapter(adapter);
        adapter.setData(UserInfo.generateTestUser());
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }
}
