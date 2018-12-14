package fr.elephantasia.activities.contact;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.BaseApplication;
import fr.elephantasia.R;
import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Elephant;
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

  private Drawable validateIcon;


    // Listener binding
  // @OnClick(R.id.validate_button)
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

    initIcon();
  }

  @Override
  public boolean onSupportNavigateUp() {
    setResult(RESULT_OK);
    finish();
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    return true;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.contact_add_menu, menu);
    menu.findItem(R.id.menu_validate).setIcon(validateIcon);
    return true;
  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_validate) {
            if (contact.isEmpty()) {
                Toast.makeText(this, "You must fill at least one field", Toast.LENGTH_SHORT).show();
            } else {
                addNewContact();
            }
            return true;
        }
        return false;
    }

  private void initIcon() {
    validateIcon = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_check)
            .color(Color.WHITE).sizeDp(24);
  }

}
