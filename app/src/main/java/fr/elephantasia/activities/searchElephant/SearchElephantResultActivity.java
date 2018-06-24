package fr.elephantasia.activities.searchElephant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.BaseApplication;
import fr.elephantasia.R;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.adapter.SearchElephantAdapter;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Elephant;
import io.realm.OrderedRealmCollection;

import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_ELEPHANT_ID;
import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_SEARCH_ELEPHANT;

public class SearchElephantResultActivity extends AppCompatActivity {

  public static String SEARCH_ALL = "SEARCH_ALL";
  public static String SEARCH_PENDING = "SEARCH_PENDING";
  public static String SEARCH_DRAFT = "SEARCH_DRAFT";
  public static String SEARCH_SAVED = "SEARCH_SAVED";

  // View Binding
  @BindView(R.id.result_view) RecyclerView resultList;
  @BindView(R.id.no_result) TextView noResult;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.title) TextView toolbarTitle;

  private DatabaseController databaseController;

  // Attr
  private SearchElephantAdapter adapter;
  // private Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_elephant_result_activity);
    ButterKnife.bind(this);

    databaseController = ((BaseApplication)getApplication()).getDatabaseController();
    // realm = Realm.getDefaultInstance();

    resultList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    displaySearchResult();

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
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

  private void displaySearchResult() {
    OrderedRealmCollection<Elephant> results;

    Elephant e = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_SEARCH_ELEPHANT));
    if (e != null) {
      results = databaseController.search(e);
    } else {
      DatabaseController.SearchMode sm = DatabaseController.SearchMode.All;
      String action = getIntent().getAction();
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

    if (!results.isEmpty()) {
      initAdapter(results);
      resultList.setAdapter(adapter);
    } else {
      resultList.setVisibility(View.GONE);
      noResult.setVisibility(View.VISIBLE);
    }
  }

  private void initAdapter(OrderedRealmCollection<Elephant> realmResults) {
    String action = getIntent().getAction();
    SearchElephantAdapter.OnActionClickListener listener;

    if (action != null && action.equals(ElephantPreview.SELECT)) {
      listener = createElephantListener();
    } else {
      listener = createEditListener();
      action = getString(R.string.edit);
    }
    adapter = new SearchElephantAdapter(realmResults, listener, action);
  }

  SearchElephantAdapter.OnActionClickListener createElephantListener() {
    return new SearchElephantAdapter.OnActionClickListener() {
      @Override
      public void onActionClick(Elephant elephant) {
        Intent resultIntent = getIntent();

        if (elephant != null) {
          resultIntent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
          setResult(RESULT_OK, resultIntent);
          finish();
        }
      }
    };
  }

  SearchElephantAdapter.OnActionClickListener createEditListener() {
    return new SearchElephantAdapter.OnActionClickListener() {
      @Override
      public void onActionClick(Elephant elephant) {
        Intent intent = new Intent(SearchElephantResultActivity.this, ManageElephantActivity.class);

        if (elephant != null) {
          intent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
          startActivity(intent);
        }
      }
    };
  }
}
