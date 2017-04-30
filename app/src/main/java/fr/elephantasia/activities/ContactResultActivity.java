package fr.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.adapter.ContactResultAdapter;
import fr.elephantasia.adapter.SearchElephantAdapter;
import fr.elephantasia.database.Database;
import fr.elephantasia.realm.Yolo;
import fr.elephantasia.realm.model.Contact;
import fr.elephantasia.utils.ElephantInfo;
import io.realm.Realm;
import io.realm.RealmResults;

public class ContactResultActivity extends AppCompatActivity {

  private ContactResultAdapter adapter;
  private Realm realm;

  @BindView(R.id.list_view) ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.result_contacts_activity);
    ButterKnife.bind(this);
    Contact contactFilters = Parcels.unwrap(getIntent().getParcelableExtra("test"));

    //
    realm = Realm.getDefaultInstance();

    RealmResults<Contact> contactsList = realm.where(Contact.class).findAllSorted("name");
    adapter = new ContactResultAdapter(contactsList);
    listView.setAdapter(adapter);

    //

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    if (requestCode == REQUEST_CONSULTATION) {
//      if (resultCode == RESULT_OK) {
////        refreshList();
//      }
//    }
  }

//  @Override
//  public void onItemClick(ElephantInfo info) {
//    if (mode == SearchElephantActivity.Mode.CONSULTATION) {
//      Intent intent = new Intent(this, ConsultationActivity.class);
//      ConsultationActivity.setElephant(intent, info);
//      startActivityForResult(intent, REQUEST_CONSULTATION);
//      overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//    } else if (mode == SearchElephantActivity.Mode.PICK_RESULT) {
//      Intent intent = new Intent();
//      intent.putExtra(EXTRA_ELEPHANT, info);
//      setResult(RESULT_OK, intent);
//      finish();
//    }
//  }

//  private void refreshList() {
//    List<ElephantInfo> elephants = database.search(toSearch);
//    if (elephants.size() == 0) {
//      mListView.setVisibility(View.GONE);
//      noItem.setVisibility(View.VISIBLE);
//    }
//
//    adapter = new SearchElephantAdapter(getApplicationContext(), this);
//
//    adapter.addList(elephants);
//    mListView.setAdapter(adapter);
//  }
}
