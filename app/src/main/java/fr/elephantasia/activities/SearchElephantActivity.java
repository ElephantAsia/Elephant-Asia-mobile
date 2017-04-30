package fr.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import fr.elephantasia.R;
import fr.elephantasia.fragment.SearchElephantFragment;
import fr.elephantasia.interfaces.SearchInterface;
import fr.elephantasia.utils.ElephantInfo;

public class SearchElephantActivity extends AppCompatActivity implements SearchInterface {

  public static final String EXTRA_MODE = "mode";
  public static final String EXTRA_RESULT = "result";

  private static final int REQUEST_SEARCH_BROWSER = 1;
  private Mode mode;

  static public Mode getMode(Intent intent) {
    int mode = intent.getIntExtra(EXTRA_MODE, Mode.CONSULTATION.ordinal());
    return Mode.values()[mode];
  }

  static public void setMode(Intent intent, Mode mode) {
    intent.putExtra(EXTRA_MODE, mode.ordinal());
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_elephant_activity);

    mode = getMode(getIntent());
    Log.i("mode", mode + "");

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
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_SEARCH_BROWSER) {
      if (resultCode == RESULT_OK && mode == Mode.PICK_RESULT) {
        ElephantInfo info = data.getParcelableExtra(SearchBrowserActivity.EXTRA_ELEPHANT);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, info);
        setResult(RESULT_OK, intent);
        finish();
      }
    }
  }

  @Override
  public void onClickSearch(ElephantInfo info) {
    Intent intent = new Intent(this, SearchBrowserActivity.class);

    SearchBrowserActivity.setMode(intent, mode);
    SearchBrowserActivity.setElephant(intent, info);

    startActivityForResult(intent, REQUEST_SEARCH_BROWSER);
    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
  }

  private void setSearchFragment() {
    SearchElephantFragment searchElephantFragment = new SearchElephantFragment();
    Bundle args = new Bundle();
    searchElephantFragment.setArguments(args);
    getSupportFragmentManager().beginTransaction().replace(R.id.search_elephant_fragment , searchElephantFragment).commit();
  }

  enum Mode {
    PICK_RESULT,
    CONSULTATION
  }

}