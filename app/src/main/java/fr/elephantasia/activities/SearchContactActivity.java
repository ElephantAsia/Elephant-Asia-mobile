package fr.elephantasia.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.constants.ActivityResult;
import fr.elephantasia.databinding.AddContactActivityBinding;
import fr.elephantasia.realm.model.Contact;

import static fr.elephantasia.constants.ActivityResult.CONTACT_SELECTED;

public class SearchContactActivity extends AppCompatActivity {
  public static final String EXTRA_SEARCH = "extra_search";

  private Contact contact = new Contact();

  public SearchContactActivity() {
    contact.owner = true;
    contact.cornac = true;
    contact.vet = true;
  }

  @OnClick(R.id.search_button)
  public void searchContact() {
    Intent myIntent = new Intent(this, SearchContactResultActivity.class);
    myIntent.putExtra(EXTRA_SEARCH, Parcels.wrap(contact));
    startActivityForResult(myIntent, CONTACT_SELECTED);
  }

  @OnClick(R.id.create_button)
  public void createContact() {
    Intent myIntent = new Intent(this, SearchContactResultActivity.class);
//    myIntent.putExtra("test", Parcels.wrap(contact));
//    this.startActivity(myIntent);
    finish();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AddContactActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.add_contact_activity);
    binding.setC(contact);
    ButterKnife.bind(this);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
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

    // TODO: check the code return here
    if (resultCode == RESULT_OK) {
      switch(requestCode) {
        case (CONTACT_SELECTED) : {
          setResult(RESULT_OK, data);
          finish();
          break;
        }
      }
    }
  }
}
