package fr.elephantasia.activities.contact;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.databinding.SearchContactActivityBinding;
import fr.elephantasia.utils.KeyboardHelpers;

import static fr.elephantasia.activities.contact.AddContactActivity.EXTRA_CONTACT_CREATED;
import static fr.elephantasia.activities.contact.SearchContactResultActivity.EXTRA_CONTACT_SELECTED;

public class SearchContactActivity extends AppCompatActivity {

  // Extra
  public static final String EXTRA_SEARCH_FILTERS = "extra_search_filters";
  public static final String EXTRA_SEARCH_CONTACT = "extra_search_contact";

  // Request code
  public static final int REQUEST_CONTACT_SELECTED = 1;
  public static final int REQUEST_CONTACT_CREATED = 2;

  // Views Binding
  @BindView(R.id.toolbar) Toolbar toolbar;

  // Attr
  private Contact contact = new Contact();

  public SearchContactActivity() {
    contact.owner = true;
    contact.cornac = true;
    contact.vet = true;
  }

  // Listeners Binding
  @OnClick(R.id.search_button)
  public void searchContact() {
    Intent intent = new Intent(this, SearchContactResultActivity.class);
    intent.putExtra(EXTRA_SEARCH_FILTERS, Parcels.wrap(contact));
    startActivityForResult(intent, REQUEST_CONTACT_SELECTED);
  }

  @OnClick(R.id.create_button)
  public void createContact() {
    Intent intent = new Intent(this, AddContactActivity.class);
    startActivityForResult(intent, REQUEST_CONTACT_CREATED);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SearchContactActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.search_contact_activity);
    binding.setC(contact);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    KeyboardHelpers.hideKeyboardListener(binding.getRoot(), this);
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    return true;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK && data != null) {
      Intent resultIntent = new Intent();

      switch (requestCode) {
        case (REQUEST_CONTACT_SELECTED):
          resultIntent.putExtra(EXTRA_SEARCH_CONTACT, data.getParcelableExtra(EXTRA_CONTACT_SELECTED));
          setResult(RESULT_OK, resultIntent);
          finish();
          break;
        case (REQUEST_CONTACT_CREATED):
          resultIntent.putExtra(EXTRA_SEARCH_CONTACT, data.getParcelableExtra(EXTRA_CONTACT_CREATED));
          setResult(RESULT_OK, resultIntent);
          finish();
          break;
      }
    }
  }
}
