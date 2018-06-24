package fr.elephantasia.activities.contact;

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
import fr.elephantasia.BaseApplication;
import fr.elephantasia.R;
import fr.elephantasia.adapter.ContactPreviewAdapter;
import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Contact;
import io.realm.RealmList;
import io.realm.RealmResults;

import static fr.elephantasia.activities.contact.SearchContactActivity.EXTRA_SEARCH_FILTERS;

public class SearchContactResultActivity extends AppCompatActivity {

  // Extra code
  public static final String EXTRA_CONTACT_SELECTED = "extra_contact_selected";

  // View binding
  @BindView(R.id.list_view) ListView listView;
  @BindView(R.id.toolbar) Toolbar toolbar;

  // Attr
  private DatabaseController databaseController;
  private ContactPreviewAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_contact_result_activity);
    ButterKnife.bind(this);

    databaseController = ((BaseApplication) getApplication()).getDatabaseController();

    RealmList<Contact> contacts = new RealmList<>();
    contacts.addAll(searchContacts());

    adapter = new ContactPreviewAdapter(this, contacts, false);
    listView.setAdapter(adapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent resultIntent = getIntent();
        Contact contact = adapter.getItem(i);
        resultIntent.putExtra(EXTRA_CONTACT_SELECTED, Parcels.wrap(contact));
        setResult(RESULT_OK, resultIntent);
        finish();
      }
    });

    TextView title = toolbar.findViewById(R.id.title);
    title.setText(getString(R.string.search_result, contacts.size()));

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // realm.close();
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    return true;
  }

  private RealmResults<Contact> searchContacts() {
    Contact c = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_SEARCH_FILTERS));
    return databaseController.search(c);
  }
}
