package fr.elephantasia.activities;

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
import fr.elephantasia.adapter.ViewPagerAdapter;
import fr.elephantasia.fragment.addElephant.ContactFragment;
import fr.elephantasia.fragment.addElephant.DescriptionFragment;
import fr.elephantasia.fragment.addElephant.ProfilFragment;
import fr.elephantasia.fragment.addElephant.RegistrationFragment;
import fr.elephantasia.realm.RealmDB;
import fr.elephantasia.realm.model.Contact;
import fr.elephantasia.realm.model.Elephant;
import fr.elephantasia.utils.KeyboardHelpers;

import static fr.elephantasia.activities.SearchContactActivity.EXTRA_SEARCH_RESULT;

public class AddElephantActivity extends AppCompatActivity {

  // Result code
  public static final int RESULT_DRAFT = 2;
  public static final int RESULT_VALIDATE = 3;

  public static final int REQUEST_CURRENT_LOCATION = 1;
  public static final int REQUEST_BIRTH_LOCATION = 2;
  public static final int REQUEST_REGISTRATION_LOCATION = 3;
  public static final int REQUEST_CONTACT_SELECTED = 4;
  public static final int REQUEST_SET_FATHER = 5;
  public static final int REQUEST_SET_MOTHER = 6;
  public static final int REQUEST_ADD_CHILDREN = 7;
  public static final int REQUEST_CAPTURE_PHOTO = 8;
  public static final int REQUEST_IMPORT_PHOTO = 9;

  //TODO: remove useless index
  // Fragment index
//  private static final int FRAGMENT_PARENTAGE_IDX = 4;
//  private static final int FRAGMENT_DOCUMENTS_IDX = 5;
//  private PickImageDialog pickImageDialog;

  //Fragment
  private ProfilFragment profilFragment;
  private RegistrationFragment registrationFragment;
  private ContactFragment contactFragment;

  private ViewPagerAdapter adapter;
  private Elephant elephant = new Elephant();

  @BindView(R.id.toolbar) public Toolbar toolbar;
  @BindView(R.id.tabs) public TabLayout tabLayout;
  @BindView(R.id.viewpager) public ViewPager viewPager;
  @BindView(R.id.add_elephant_activity) public View rootView;
  @BindView(R.id.add_elephant_fab) public FloatingActionButton fab;

  @OnClick(R.id.add_elephant_fab)
  public void nextPage() {
    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
      viewPager.setCurrentItem(0);
    } else {
      viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
  }

  public AddElephantActivity() {
    profilFragment = new ProfilFragment();
    registrationFragment = new RegistrationFragment();
    contactFragment = new ContactFragment();
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
  }


  private void setupViewPager(ViewPager viewPager) {
    adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(profilFragment, getString(R.string.profil));
    adapter.addFragment(registrationFragment, getString(R.string.registration));
    adapter.addFragment(new DescriptionFragment(), getString(R.string.description));
    adapter.addFragment(contactFragment, getString(R.string.contact));
//    adapter.addFragment(new ParentageFragment(), getString(R.string.parentage));
//    adapter.addFragment(new DocumentFragment(), getString(R.string.documents));
    viewPager.setAdapter(adapter);
  }

  public Elephant getElephant() {
    return this.elephant;
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
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK && data != null) {
      Contact contact;
//      ElephantInfo info = data.getParcelableExtra(SearchElephantActivity.EXTRA_RESULT);
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
          contact = Parcels.unwrap(data.getParcelableExtra(EXTRA_SEARCH_RESULT));
          contactFragment.addContactTolist(contact);
          break;
//        case REQUEST_SET_FATHER:
//          refreshFather(info);
//          break;
//        case REQUEST_SET_MOTHER:
//          refreshMother(info);
//          break;
//        case REQUEST_ADD_CHILDREN:
//          refreshChildren(info);
//          break;
//        case REQUEST_CAPTURE_PHOTO:
//          addDocument(Uri.fromFile(ImageUtil.getCapturePhotoFile(this)));
//          break;
//        case REQUEST_IMPORT_PHOTO:
//          addDocument(data.getData());
//          break;
       }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_elephant_more_menu, menu);
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

//  @Override
//  public void onAddDocumentClick() {
//    buildPickDocumentDialog();
//  }
//
//  private void addDocument(Uri uri) {
//    DocumentFragment fragment = (DocumentFragment) adapter.getItem(FRAGMENT_DOCUMENTS_IDX);
//    String path = ImageUtil.createImageFileFromUri(this, uri);
//
//    if (path != null) {
//      Log.i("add_photo", "aucune erreur");
//      fragment.addDocument(path);
//    } else {
//      Toast.makeText(getApplicationContext(), "Error on adding document", Toast.LENGTH_SHORT).show();
//    }
//  }
//
//  private void buildPickDocumentDialog() {
//    pickImageDialog = new PickImageDialogBuilder(this)
//        .build()
//        .setListener(new PickImageDialog.Listener() {
//          @Override
//          public void execute(Intent intent, int requestCode) {
//            if (requestCode == REQUEST_CAPTURE_PHOTO) {
//              if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(AddElephantActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
//              } else {
//                startActivityForResult(intent, requestCode);
//              }
//            } else {
//              startActivityForResult(intent, requestCode);
//            }
//          }
//        })
//        .setCaptureCode(REQUEST_CAPTURE_PHOTO)
//        .setImportCode(REQUEST_IMPORT_PHOTO)
//        .load();
//    pickImageDialog.show();
//  }
//
//
//  @Override
//  public void setFather() {
//    Intent intent = new Intent(this, SearchElephantActivity.class);
//    SearchElephantActivity.setMode(intent, SearchElephantActivity.Mode.PICK_RESULT);
//    startActivityForResult(intent, REQUEST_SET_FATHER);
//  }
//
//  @Override
//  public void setMother() {
//    Intent intent = new Intent(this, SearchElephantActivity.class);
//    SearchElephantActivity.setMode(intent, SearchElephantActivity.Mode.PICK_RESULT);
//    startActivityForResult(intent, REQUEST_SET_MOTHER);
//  }
//
//  @Override
//  public void addChildren() {
//    Intent intent = new Intent(this, SearchElephantActivity.class);
//    SearchElephantActivity.setMode(intent, SearchElephantActivity.Mode.PICK_RESULT);
//    startActivityForResult(intent, REQUEST_ADD_CHILDREN);
//  }
//
//  @Override
//  public void onElephantClick(ElephantInfo elephant) {
//    Intent intent = new Intent(this, ConsultationActivity.class);
//    ConsultationActivity.setElephant(intent, elephant);
//    startActivity(intent);
//    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//  }
//
//  private void refreshFather(ElephantInfo info) {
//    ParentageFragment fragment = (ParentageFragment) adapter.getItem(FRAGMENT_PARENTAGE_IDX);
//
//    elephantInfo.father = info.id.toString();
//    fragment.refreshFather(info);
//  }
//
//  private void refreshMother(ElephantInfo info) {
//    ParentageFragment fragment = (ParentageFragment) adapter.getItem(FRAGMENT_PARENTAGE_IDX);
//
//    elephantInfo.mother = info.id.toString();
//    fragment.refreshMother(info);
//  }
//
//  private void refreshChildren(ElephantInfo info) {
//    ParentageFragment fragment = (ParentageFragment) adapter.getItem(FRAGMENT_PARENTAGE_IDX);
//
//    elephantInfo.addChildren(info.id);
//    fragment.refreshChildren(info);
//  }

}
