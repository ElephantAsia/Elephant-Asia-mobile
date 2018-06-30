package fr.elephantasia.activities.searchElephant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.BaseApplication;
import fr.elephantasia.R;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.activities.searchElephant.adapters.ElephantsAdapter;
import fr.elephantasia.activities.searchElephant.fragments.SearchElephantResultFragment;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Elephant;

public class SearchElephantResultActivity extends AppCompatActivity {

  /**
   * Classifier
   */

  static public final String EXTRA_ACTION = "extra.action";
  static public final String EXTRA_ELEPHANT = "extra.elephant";

  static public final String SEARCH_ALL = "SEARCH_ALL";
  static public final String SEARCH_PENDING = "SEARCH_PENDING";
  static public final String SEARCH_DRAFT = "SEARCH_DRAFT";
  static public final String SEARCH_SAVED = "SEARCH_SAVED";

  static public void SetExtraAction(@NonNull Intent intent, String action) {
    intent.putExtra(EXTRA_ACTION, action);
  }

  static public void SetExtraElephant(@NonNull Intent intent, Elephant e) {
    intent.putExtra(EXTRA_ELEPHANT, Parcels.wrap(e));
  }

  @Nullable
  static public String GetExtraAction(@NonNull Intent intent) {
    return intent.getStringExtra(EXTRA_ACTION);
  }

  @Nullable
  static public Elephant GetExtraElephant(@NonNull Intent intent) {
    return Parcels.unwrap(intent.getParcelableExtra(EXTRA_ELEPHANT));
  }

  /**
   * Instance
   */

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.title) TextView toolbarTitle;

  private DatabaseController databaseController;
  private SearchElephantResultFragment fragment;

  private ElephantsAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_elephant_result_activity);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    databaseController = ((BaseApplication)getApplication()).getDatabaseController();
    setFragment();
    initAdapter();
  }

  @Override
  protected void onResume() {
    super.onResume();
    displaySearchResult();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    return true;
  }

  private void setFragment() {
    fragment = new SearchElephantResultFragment();
    getSupportFragmentManager().beginTransaction()
      .replace(R.id.search_elephant_result_fragment, fragment)
      .commit();
  }

  private void displaySearchResult() {
    List<Elephant> results;

    Elephant e = GetExtraElephant(getIntent());
    if (e != null) {
      results = databaseController.search(e);
    } else {
      DatabaseController.SearchMode sm = DatabaseController.SearchMode.All;
      String action = GetExtraAction(getIntent());
      if (action != null) {
        if (action.equals(SEARCH_DRAFT))
          sm = DatabaseController.SearchMode.Draft;
        else if (action.equals(SEARCH_PENDING))
          sm = DatabaseController.SearchMode.Pending;
        else if (action.equals(SEARCH_SAVED))
          sm = DatabaseController.SearchMode.Saved;
      }
      results = databaseController.searchElephantsByState(sm);
    }

    toolbarTitle.setText(String.format(getString(R.string.search_result), results.size()));

    adapter.setData(results);
    fragment.contentUpdated(results.isEmpty());
  }

  private void initAdapter() {
    String action = GetExtraAction(getIntent());
    ElephantsAdapter.ActionClickListener listener;

    if (action != null && action.equals(ElephantPreview.SELECT)) {
      listener = createElephantListener();
    } else {
      listener = createEditListener();
      action = getString(R.string.edit);
    }

    adapter = new ElephantsAdapter(listener, action);
    fragment.setAdapter(adapter);
  }

  private ElephantsAdapter.ActionClickListener createElephantListener() {
    return new ElephantsAdapter.ActionClickListener() {
      @Override
      public void onActionClick(Elephant elephant) {
        Intent resultIntent = getIntent();

        if (elephant != null) {
          SearchElephantActivity.SetExtraElephantId(resultIntent, elephant.id);
          setResult(RESULT_OK, resultIntent);
          finish();
        }
      }
    };
  }

  private ElephantsAdapter.ActionClickListener createEditListener() {
    return new ElephantsAdapter.ActionClickListener() {
      @Override
      public void onActionClick(Elephant elephant) {
        Intent intent = new Intent(SearchElephantResultActivity.this, ManageElephantActivity.class);
        Log.w("searchresult", "elephant: " + elephant);
        Log.w("searchresult", "id: " + elephant.id);
        if (elephant != null) {
          ManageElephantActivity.SetExtraElephantId(intent, elephant.id);
          startActivity(intent);
        }
      }
    };
  }
}
