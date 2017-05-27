package fr.elephantasia.activities.addElephant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.addElephant.fragment.ContactFragment;
import fr.elephantasia.activities.addElephant.fragment.DescriptionFragment;
import fr.elephantasia.activities.addElephant.fragment.ParentageFragment;
import fr.elephantasia.activities.addElephant.fragment.ProfilFragment;
import fr.elephantasia.activities.addElephant.fragment.RegistrationFragment;
import fr.elephantasia.adapter.ViewPagerAdapter;
import fr.elephantasia.database.RealmDB;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.KeyboardHelpers;
import io.realm.Realm;

import static fr.elephantasia.activities.SearchContactActivity.EXTRA_SEARCH_CONTACT;
import static fr.elephantasia.activities.searchElephantResult.SearchElephantResultActivity.EXTRA_ELEPHANT_ID;

public class AddElephantActivity extends AppCompatActivity {

  // Result code
  public static final int RESULT_DRAFT = 2;
  public static final int RESULT_VALIDATE = 3;

  // Request codes
  public static final int REQUEST_CURRENT_LOCATION = 1;
  public static final int REQUEST_BIRTH_LOCATION = 2;
  public static final int REQUEST_REGISTRATION_LOCATION = 3;
  public static final int REQUEST_CONTACT_SELECTED = 4;
  public static final int REQUEST_FATHER_SELECTED = 5;
  public static final int REQUEST_MOTHER_SELECTED = 6;
  public static final int REQUEST_CHILD_SELECTED = 7;

  // Action code
  public static final String SELECT_ELEPHANT = "select_elephant";

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.tabs) TabLayout tabLayout;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.add_elephant_activity) View rootView;
  @BindView(R.id.add_elephant_fab) FloatingActionButton fab;

  //Fragment
  private ProfilFragment profilFragment;
  private RegistrationFragment registrationFragment;
  private ContactFragment contactFragment;
  private ParentageFragment parentageFragment;
  private ViewPagerAdapter adapter;

  //Attr
  private Elephant elephant = new Elephant();
  private Realm realm;

  public AddElephantActivity() {
    profilFragment = new ProfilFragment();
    registrationFragment = new RegistrationFragment();
    contactFragment = new ContactFragment();
    parentageFragment = new ParentageFragment();
  }

  // Listener binding
  @OnClick(R.id.add_elephant_fab)
  public void nextPage() {
    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
      viewPager.setCurrentItem(0);
    } else {
      viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_elephant_activity);
    ButterKnife.bind(this);

    rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        boolean keyboardIsUp = KeyboardHelpers.keyboardIsDisplay(rootView);

        if (keyboardIsUp) {
          fab.hide();
        } else {
          fab.show();
        }
      }
    });

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    setupViewPager(viewPager);
    tabLayout.setupWithViewPager(viewPager);
    realm = Realm.getDefaultInstance();
  }


  private void setupViewPager(ViewPager viewPager) {
    adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(profilFragment, getString(R.string.profil));
    adapter.addFragment(registrationFragment, getString(R.string.registration));
    adapter.addFragment(new DescriptionFragment(), getString(R.string.description));
    adapter.addFragment(contactFragment, getString(R.string.contact));
    adapter.addFragment(parentageFragment, getString(R.string.parentage));
//    adapter.addFragment(new DocumentFragment(), getString(R.string.documents));
    viewPager.setAdapter(adapter);
  }

  public Elephant getElephant() {
    return this.elephant;
  }

  public Realm getRealm() {
    return this.realm;
  }

  @Override
  public boolean onSupportNavigateUp() {
    confirmFinish();
    return true;
  }

  @Override
  public void onBackPressed() {
    confirmFinish();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    realm.close();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK && data != null) {
      Contact contact;
      Place selectedPlace = PlacePicker.getPlace(this, data);

      switch (requestCode) {
        case REQUEST_CURRENT_LOCATION:
          profilFragment.setCurrentLocation(selectedPlace.getAddress().toString());
          break;
        case REQUEST_BIRTH_LOCATION:
          profilFragment.setBirthLocation(selectedPlace.getAddress().toString());
          break;
        case REQUEST_REGISTRATION_LOCATION:
          registrationFragment.setRegistrationLocation(selectedPlace.getAddress().toString());
          break;
        case REQUEST_CONTACT_SELECTED:
          contact = Parcels.unwrap(data.getParcelableExtra(EXTRA_SEARCH_CONTACT));
          contactFragment.addContactTolist(contact);
          break;
        case REQUEST_MOTHER_SELECTED:
          parentageFragment.setMother(data.getStringExtra(EXTRA_ELEPHANT_ID));
          break;
        case REQUEST_FATHER_SELECTED:
          parentageFragment.setFather(data.getStringExtra(EXTRA_ELEPHANT_ID));
          break;
        case REQUEST_CHILD_SELECTED:
          parentageFragment.setChild(data.getStringExtra(EXTRA_ELEPHANT_ID));
          break;
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_elephant_options_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.add_elephant_menu_draft && checkMandatoryFields()) {
      elephant.state.draft = true;
      setResult(RESULT_DRAFT);
      RealmDB.copyOrUpdate(elephant);
      finish();
      return true;
    } else if (item.getItemId() == R.id.add_elephant_menu_validate && checkMandatoryFields()) {
      elephant.state.local = true;
      setResult(RESULT_VALIDATE);
      RealmDB.copyOrUpdate(elephant);
      finish();
      return true;
    }
    return false;
  }

  /**
   * @return true if at least the name and sex are set
   */
  private boolean checkMandatoryFields() {
    if (TextUtils.isEmpty(elephant.name)) {
      profilFragment.setNameError();
      Toast.makeText(this, R.string.name_required, Toast.LENGTH_SHORT).show();
      return false;
    }
    if (!elephant.male && !elephant.female) {
      profilFragment.setSexError();
      Toast.makeText(this, R.string.sex_required, Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }

  /**
   * Prevent user to cancel activity without saving his current Elephant.
   * Happens only if some data have been set.
   */
  private void confirmFinish() {

    if (elephant.isEmpty()) {
      setResult(RESULT_CANCELED);
      finish();
    } else {
      new AlertDialog.Builder(this)
          .setMessage(R.string.put_drafts)
          .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              setResult(RESULT_CANCELED);
              finish();
            }
          })
          .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              if (checkMandatoryFields()) {
                elephant.state.draft = true;
                RealmDB.copyOrUpdate(elephant);
                setResult(RESULT_DRAFT);
                finish();
              }
            }
          })
          .show();
    }
  }
}
