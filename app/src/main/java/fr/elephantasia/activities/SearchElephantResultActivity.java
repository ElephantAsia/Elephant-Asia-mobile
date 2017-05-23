package fr.elephantasia.activities;

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
import fr.elephantasia.adapter.ListElephantPreviewAdapter;
import fr.elephantasia.database.model.Elephant;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static fr.elephantasia.activities.SearchElephantActivity.EXTRA_SEARCH_ELEPHANT;
import static fr.elephantasia.database.model.Elephant.CHIPS1;
import static fr.elephantasia.database.model.Elephant.FEMALE;
import static fr.elephantasia.database.model.Elephant.MALE;
import static fr.elephantasia.database.model.Elephant.NAME;

public class SearchElephantResultActivity extends AppCompatActivity {

  public static final String EXTRA_ELEPHANT_SELECTED_ID = "extra_elephant_selected";

  // Attr
  private ListElephantPreviewAdapter adapter;
  private Realm realm;

  // View Binding
  @BindView(R.id.list_view) ListView listView;
  @BindView(R.id.no_result) TextView noResult;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_elephant_result_activity);
    ButterKnife.bind(this);

    RealmList<Elephant> elephants = new RealmList<>();
    elephants.addAll(searchElephants());

    if (!elephants.isEmpty()) {
      adapter = new ListElephantPreviewAdapter(this, elephants);
      listView.setAdapter(adapter);
    } else {
      listView.setVisibility(View.GONE);
      noResult.setVisibility(View.VISIBLE);
    }

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(SearchElephantResultActivity.this, ShowElephantActivity.class);
        String elephantId = adapter.getItem(i).id;
        intent.putExtra(EXTRA_ELEPHANT_SELECTED_ID, elephantId);
        startActivity(intent);
      }
    });

    TextView title = (TextView) toolbar.findViewById(R.id.title);
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

  private RealmResults<Elephant> searchElephants() {
    Elephant e = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_SEARCH_ELEPHANT));
    realm = Realm.getDefaultInstance();
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
}
