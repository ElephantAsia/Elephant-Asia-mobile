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
import fr.elephantasia.database.RealmDB;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.databinding.AddContactActivityBinding;
import fr.elephantasia.utils.KeyboardHelpers;
import io.realm.Realm;

public class AddContactActivity extends AppCompatActivity {

  // Extra code
  public static final String EXTRA_CONTACT_CREATED = "extra_contact_created";

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;

  // Attr
  private Realm realm;
  private Contact contact = new Contact();

  // Listener binding
  @OnClick(R.id.validate_button)
  public void addNewContact() {
    RealmDB.copyOrUpdate(contact);
    Intent resultIntent = getIntent();
    resultIntent.putExtra(EXTRA_CONTACT_CREATED, Parcels.wrap(contact));
    setResult(RESULT_OK, resultIntent);
    finish();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AddContactActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.add_contact_activity);
    binding.setC(contact);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    realm = Realm.getDefaultInstance();

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    KeyboardHelpers.hideKeyboardListener(binding.getRoot(), this);
  }

  @Override
  public boolean onSupportNavigateUp() {
    setResult(RESULT_OK);
    finish();
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    return true;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    realm.close();
  }
}
