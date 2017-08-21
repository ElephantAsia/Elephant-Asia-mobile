package fr.elephantasia.activities.addDocument;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;

public class AddDocumentActivity extends AppCompatActivity {

    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_TYPE = "rtype";

    public static String photo;

    public static void setExtraTitle(Intent intent, String title) {
        intent.putExtra(EXTRA_TITLE, title);
    }

    public static void setExtraType(Intent intent, String type) {
        intent.putExtra(EXTRA_TYPE, type);
    }

    public static String getExtraTitle(Intent intent) {
        return intent.getStringExtra(EXTRA_TITLE);
    }

    public static String getExtraType(Intent intent) {
        return intent.getStringExtra(EXTRA_TYPE);
    }

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.title) EditText title;
    @BindView(R.id.type) EditText type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_document_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_document_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.validate && checkMandatoryFields()) {
            validate();
            return true;
        }
        return false;
    }

    private void validate() {
        Intent data = new Intent();
        setExtraTitle(data, title.getText().toString());
        setExtraType(data, type.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }

    private boolean checkMandatoryFields() {
        return title.getText().toString().trim().length() > 0
                && type.getText().toString().trim().length() > 0;
    }
}
