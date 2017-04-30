package fr.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import fr.elephantasia.R;
import fr.elephantasia.adapter.ContactAdapter;
import fr.elephantasia.interfaces.ContactListener;
import fr.elephantasia.utils.Contact;

public class SelectContactActivity extends AppCompatActivity {

  public static final String EXTRA_RESULT_USER_SELECTED = "user_selected";

  private ListView list;
  private ContactAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.select_contact_activity);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    list = (ListView) findViewById(R.id.list);
    adapter = new ContactAdapter(this, false, new ContactListener() {
      @Override
      public void onAddClick() {
        // nothing to do
      }

      @Override
      public void onItemClick(Contact contact) {
        Intent result = new Intent();
        result.putExtra(EXTRA_RESULT_USER_SELECTED, contact);
        setResult(RESULT_OK, result);
        finish();
      }
    });

    list.setAdapter(adapter);
    adapter.setData(Contact.generateTestUser());
  }

  @Override
  public boolean onSupportNavigateUp() {
    setResult(RESULT_CANCELED);
    finish();
    return true;
  }
}
