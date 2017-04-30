package fr.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.elephantasia.R;
import fr.elephantasia.adapter.SearchElephantAdapter;
import fr.elephantasia.database.Database;
import fr.elephantasia.utils.ElephantInfo;

public class SearchBrowserActivity extends AppCompatActivity implements SearchElephantAdapter.Listener {

  public final static String EXTRA_ELEPHANT = "elephant";
  public static final String EXTRA_MODE = "extra_mode";

  private final static int REQUEST_CONSULTATION = 1;
  private SearchElephantActivity.Mode mode;
  private ListView mListView;
  private Database database;
  private TextView noItem;
  private ElephantInfo toSearch;
  private SearchElephantAdapter adapter;

  public static SearchElephantActivity.Mode getMode(Intent intent) {
    return (SearchElephantActivity.Mode) intent.getSerializableExtra(EXTRA_MODE);
  }

  public static void setMode(Intent intent, SearchElephantActivity.Mode mode) {
    intent.putExtra(EXTRA_MODE, mode);
  }

  public static ElephantInfo getElephant(Intent intent) {
    return intent.getParcelableExtra(EXTRA_ELEPHANT);
  }

  public static void setElephant(Intent intent, ElephantInfo info) {
    intent.putExtra(EXTRA_ELEPHANT, info);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_browser_activity);

    database = new Database(getApplicationContext());
    database.open();

    toSearch = getElephant(getIntent());
    mode = getMode(getIntent());

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    TextView title = (TextView) toolbar.findViewById(R.id.title);
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
    if (mode == SearchElephantActivity.Mode.CONSULTATION) {
      Intent intent = new Intent(this, ConsultationActivity.class);
      ConsultationActivity.setElephant(intent, info);
      startActivityForResult(intent, REQUEST_CONSULTATION);
      overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    } else if (mode == SearchElephantActivity.Mode.PICK_RESULT) {
      Intent intent = new Intent();
      intent.putExtra(EXTRA_ELEPHANT, info);
      setResult(RESULT_OK, intent);
      finish();
    }
  }

  private void refreshList() {
    List<ElephantInfo> elephants = database.search(toSearch);
    if (elephants.size() == 0) {
      mListView.setVisibility(View.GONE);
      noItem.setVisibility(View.VISIBLE);
    }

    adapter = new SearchElephantAdapter(getApplicationContext(), this);

    adapter.addList(elephants);
    mListView.setAdapter(adapter);
  }
}
