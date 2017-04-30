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
import fr.elephantasia.databinding.SearchContactActivityBinding;
import fr.elephantasia.realm.model.Contact;

public class SearchContactActivity extends AppCompatActivity {

  private Contact contact = new Contact();

  @OnClick(R.id.search_button)
  public void searchContact() {
    Intent myIntent = new Intent(this, ContactResultActivity.class);
    Contact t = new Contact();
//    t.setName("yolo");
    t.name = "yolo";
    myIntent.putExtra("test", Parcels.wrap(t));
    this.startActivity(myIntent);
    finish();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    contact.owner = true;
    contact.cornac = true;
    contact.vet = true;
    SearchContactActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.search_contact_activity);
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
}
