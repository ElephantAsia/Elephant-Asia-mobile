package fr.elephantasia.activities.searchElephant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.adapter.RealmElephantAdapter;
import fr.elephantasia.database.model.Elephant;
import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_SEARCH_ELEPHANT;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.SELECT_ELEPHANT;
import static fr.elephantasia.database.model.Elephant.CHIPS1;
import static fr.elephantasia.database.model.Elephant.FEMALE;
import static fr.elephantasia.database.model.Elephant.MALE;
import static fr.elephantasia.database.model.Elephant.NAME;

public class SearchElephantResultActivity extends AppCompatActivity {

  // Extra code
  public static final String EXTRA_ELEPHANT_ID = "EXTRA_ELEPHANT_ID";

  // View Binding
  @BindView(R.id.list_view) ListView listView;
  @BindView(R.id.no_result) TextView noResult;
  @BindView(R.id.toolbar) Toolbar toolbar;

  // Attr
  private RealmElephantAdapter adapter;
  private Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_elephant_result_activity);
    ButterKnife.bind(this);
    realm = Realm.getDefaultInstance();

    displaySearchResult();
    setListItemClickListener();

    TextView title = toolbar.findViewById(R.id.title);
    title.setText(getString(R.string.search_result));

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    realm.close();
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    return true;
  }

  private void displaySearchResult() {
    OrderedRealmCollection<Elephant> realmResults = searchElephants();
    if (!realmResults.isEmpty()) {
      adapter = new RealmElephantAdapter(realmResults, this, false, true);
      listView.setAdapter(adapter);
    } else {
      listView.setVisibility(View.GONE);
      noResult.setVisibility(View.VISIBLE);
    }
  }

  private RealmResults<Elephant> searchElephants() {
    Elephant e = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_SEARCH_ELEPHANT));
    RealmQuery<Elephant> query = realm.where(Elephant.class);

    query.contains(NAME, e.name, Case.INSENSITIVE);

    if (e.chips1 != null) {
      query.contains(CHIPS1, e.chips1, Case.INSENSITIVE);
    }

    if (!e.male) {
      query.equalTo(MALE, false);
    }

    if (!e.female) {
      query.equalTo(FEMALE, false);
    }
    return query.findAll();
  }

  private void setListItemClickListener() {
    String action = getIntent().getAction();

    if (action != null && action.equals(SELECT_ELEPHANT)) {
      listView.setOnItemClickListener(selectElephantListener());
    } else {
      listView.setOnItemClickListener(showElephantListener());
    }
  }

  AdapterView.OnItemClickListener selectElephantListener() {
    return new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent resultIntent = getIntent();
        Elephant elephant = adapter.getItem(i);

        if (elephant != null) {
          resultIntent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
          setResult(RESULT_OK, resultIntent);
          finish();
        }
      }
    };
  }

  AdapterView.OnItemClickListener showElephantListener() {
    return new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(SearchElephantResultActivity.this, ShowElephantActivity.class);
        Elephant elephant = adapter.getItem(i);

        if (elephant != null) {
          intent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
          startActivity(intent);
        }
      }
    };
  }

}
