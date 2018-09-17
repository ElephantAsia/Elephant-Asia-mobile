package fr.elephantasia.activities.contact;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.BaseApplication;
import fr.elephantasia.R;
import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.databinding.AddContactActivityBinding;
import fr.elephantasia.utils.KeyboardHelpers;

public class AddContactActivity extends AppCompatActivity {

  // Extra code
  public static final String EXTRA_CONTACT_CREATED = "extra_contact_created";

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;

  private DatabaseController databaseController;

  // Attr
  private Contact contact = new Contact();

  // Listener binding
  @OnClick(R.id.validate_button)
  public void addNewContact() {
    contact.setCuid(UUID.randomUUID().toString());
    contact.setDbState(Contact.DbState.Created);

    databaseController.beginTransaction();
    databaseController.insertOrUpdate(contact);
    databaseController.commitTransaction();

    Intent resultIntent = getIntent();
    resultIntent.putExtra(EXTRA_CONTACT_CREATED, contact.getCuid());
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

    databaseController = ((BaseApplication)getApplication()).getDatabaseController();
    // realm = Realm.getDefaultInstance();

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
    // realm.close();
  }
}
