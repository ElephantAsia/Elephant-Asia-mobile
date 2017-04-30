package fr.elephantasia.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import fr.elephantasia.R;
import fr.elephantasia.adapter.ViewPagerAdapter;
import fr.elephantasia.database.Database;
import fr.elephantasia.dialogs.PickImageDialog;
import fr.elephantasia.dialogs.PickImageDialogBuilder;
import fr.elephantasia.fragment.addElephant.DescriptionFragment;
import fr.elephantasia.fragment.addElephant.DocumentFragment;
import fr.elephantasia.fragment.addElephant.ContactFragment;
import fr.elephantasia.fragment.addElephant.ParentageFragment;
import fr.elephantasia.fragment.addElephant.ProfilFragment;
import fr.elephantasia.fragment.addElephant.RegistrationFragment;
import fr.elephantasia.interfaces.AddElephantInterface;
import fr.elephantasia.realm.model.Contact;
import fr.elephantasia.realm.model.Elephant;
import fr.elephantasia.realm.RealmDB;
import fr.elephantasia.utils.ElephantInfo;
import fr.elephantasia.utils.ImageUtil;
import fr.elephantasia.utils.KeyboardHelpers;
import io.realm.RealmResults;

public class AddElephantActivity extends AppCompatActivity implements AddElephantInterface {

  // Result code
  public static final int RESULT_DRAFT = 2;
  public static final int RESULT_VALIDATE = 3;
  public static final int REQUEST_SET_FATHER = 2;
  public static final int REQUEST_SET_MOTHER = 3;
  public static final int REQUEST_ADD_CHILDREN = 4;
  public static final int REQUEST_CAPTURE_PHOTO = 5;
  public static final int REQUEST_IMPORT_PHOTO = 6;
  public static final int REQUEST_CURRENT_LOCATION = 7;
  public static final int REQUEST_BIRTH_LOCATION = 8;
  public static final int REQUEST_REGISTRATION_LOCATION = 9;
  public static final int REQUEST_ADD_CONTACT = 1;
  // Fragment index
  private static final int FRAGMENT_OWNERSHIP_IDX = 3;
  private static final int FRAGMENT_PARENTAGE_IDX = 4;
  private static final int FRAGMENT_DOCUMENTS_IDX = 5;

  //Fragment
  private ProfilFragment profilFragment;
  private RegistrationFragment registrationFragment;
  private PickImageDialog pickImageDialog;
  private Database database;
  private TabLayout tabLayout;
  private ViewPager viewPager;
  private FloatingActionButton fabNext;
  private ViewPagerAdapter adapter;
  private ElephantInfo elephantInfo;
  private Elephant elephant = new Elephant();
  private RealmResults<Elephant> eleResult;

  public AddElephantActivity() {
    elephantInfo = new ElephantInfo();
    profilFragment = new ProfilFragment();
    registrationFragment = new RegistrationFragment();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_elephant_activity);

    final View activityRootView = findViewById(R.id.add_elephant_activity);

    activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        boolean keyboardIsUp = KeyboardHelpers.keyboardIsDisplay(activityRootView);
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.add_elephant_fab);

        if (button != null && keyboardIsUp) {
          button.hide();
        } else if (button != null) {
          button.show();
        }
      }
    });

    fabNext = (FloatingActionButton) findViewById(R.id.add_elephant_fab);
    fabNext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        nextPage();
      }
    });

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    viewPager = (ViewPager) findViewById(R.id.viewpager);
    setupViewPager(viewPager);

    tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);


    Contact one = new Contact();

    one.name = "sebastien saletes";
    one.phone = "+33782992683";
    one.email = "seb.saletes@gmail.com";
    one.address.cityName = "Montpellier";
    one.address.districtName = "herault";
    one.address.provinceName = "langudeoc";
    one.address.streetName = "35 bd jeu de paume";
    RealmDB.copyOrUpdate(one);
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
    if (resultCode == RESULT_OK && data != null) {
      ElephantInfo info = data.getParcelableExtra(SearchElephantActivity.EXTRA_RESULT);
      Place selectedPlace = PlacePicker.getPlace(this, data);

      switch (requestCode) {
        case REQUEST_SET_FATHER:
          refreshFather(info);
          break;
        case REQUEST_SET_MOTHER:
          refreshMother(info);
          break;
        case REQUEST_ADD_CHILDREN:
          refreshChildren(info);
          break;
        case REQUEST_CAPTURE_PHOTO:
          addDocument(Uri.fromFile(ImageUtil.getCapturePhotoFile(this)));
          break;
        case REQUEST_IMPORT_PHOTO:
          addDocument(data.getData());
          break;
        case REQUEST_CURRENT_LOCATION:
          profilFragment.setCurrentLocation(selectedPlace.getAddress().toString());
          break;
        case REQUEST_BIRTH_LOCATION:
          profilFragment.setBirthLocation(selectedPlace.getAddress().toString());
          break;
        case REQUEST_REGISTRATION_LOCATION:
          registrationFragment.setRegistrationLocation(selectedPlace.getAddress().toString());
          break;
      }
    }
  }

  @Override
  public void onAddDocumentClick() {
    buildPickDocumentDialog();
  }

  @Override
  public void nextPage() {
    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
      viewPager.setCurrentItem(0);
    } else {
      viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_elephant_more_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.add_elephant_menu_draft) {
      elephant.state.draft = true;
      saveElephant(RESULT_DRAFT);
      return true;
    } else if (item.getItemId() == R.id.add_elephant_menu_validate) {
      elephant.state.local = true;
      saveElephant(RESULT_VALIDATE);
      return true;
    }
    return false;
  }

  private boolean checkMandatoryFields() {
    if (elephant.name.isEmpty()) {
      profilFragment.setNameError();
      Toast.makeText(this, R.string.name_required, Toast.LENGTH_SHORT).show();
      return false;
    }
    if (elephant.sex.gender == null) {
      profilFragment.setSexError();
      Toast.makeText(this, R.string.sex_required, Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }

  private void addDocument(Uri uri) {
    DocumentFragment fragment = (DocumentFragment) adapter.getItem(FRAGMENT_DOCUMENTS_IDX);
    String path = ImageUtil.createImageFileFromUri(this, uri);

    if (path != null) {
      Log.i("add_photo", "aucune erreur");
      fragment.addDocument(path);
    } else {
      Toast.makeText(getApplicationContext(), "Error on adding document", Toast.LENGTH_SHORT).show();
    }
  }

  public void saveElephant(int result) {
    if (checkMandatoryFields()) {
      setResult(result);
      RealmDB.copyOrUpdate(elephant);
      finish();
    }
  }

  public ElephantInfo getElephantInfo() {
    return this.elephantInfo;
  }

  public Elephant getElephant() {
    return this.elephant;
  }

  /**
   * Prevent user to cancel activity without saving his current Elephant.
   * Happens only if some data have been set.
   */
  private void confirmFinish() {

    if (elephantInfo.isEmpty()) {
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
                elephantInfo.state = ElephantInfo.State.DRAFT;
                database.insert(elephantInfo);
                setResult(RESULT_DRAFT);
                finish();
              }
            }
          })
          .show();
    }
  }

  private void buildPickDocumentDialog() {
    pickImageDialog = new PickImageDialogBuilder(this)
        .build()
        .setListener(new PickImageDialog.Listener() {
          @Override
          public void execute(Intent intent, int requestCode) {
            if (requestCode == REQUEST_CAPTURE_PHOTO) {
              if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddElephantActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
              } else {
                startActivityForResult(intent, requestCode);
              }
            } else {
              startActivityForResult(intent, requestCode);
            }
          }
        })
        .setCaptureCode(REQUEST_CAPTURE_PHOTO)
        .setImportCode(REQUEST_IMPORT_PHOTO)
        .load();
    pickImageDialog.show();
  }


  @Override
  public void setFather() {
    Intent intent = new Intent(this, SearchElephantActivity.class);
    SearchElephantActivity.setMode(intent, SearchElephantActivity.Mode.PICK_RESULT);
    startActivityForResult(intent, REQUEST_SET_FATHER);
  }

  @Override
  public void setMother() {
    Intent intent = new Intent(this, SearchElephantActivity.class);
    SearchElephantActivity.setMode(intent, SearchElephantActivity.Mode.PICK_RESULT);
    startActivityForResult(intent, REQUEST_SET_MOTHER);
  }

  @Override
  public void addChildren() {
    Intent intent = new Intent(this, SearchElephantActivity.class);
    SearchElephantActivity.setMode(intent, SearchElephantActivity.Mode.PICK_RESULT);
    startActivityForResult(intent, REQUEST_ADD_CHILDREN);
  }

  @Override
  public void onElephantClick(ElephantInfo elephant) {
    Intent intent = new Intent(this, ConsultationActivity.class);
    ConsultationActivity.setElephant(intent, elephant);
    startActivity(intent);
    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
  }

  private void refreshFather(ElephantInfo info) {
    ParentageFragment fragment = (ParentageFragment) adapter.getItem(FRAGMENT_PARENTAGE_IDX);

    elephantInfo.father = info.id.toString();
    fragment.refreshFather(info);
  }

  private void refreshMother(ElephantInfo info) {
    ParentageFragment fragment = (ParentageFragment) adapter.getItem(FRAGMENT_PARENTAGE_IDX);

    elephantInfo.mother = info.id.toString();
    fragment.refreshMother(info);
  }

  private void refreshChildren(ElephantInfo info) {
    ParentageFragment fragment = (ParentageFragment) adapter.getItem(FRAGMENT_PARENTAGE_IDX);

    elephantInfo.addChildren(info.id);
    fragment.refreshChildren(info);
  }

  private void setupViewPager(ViewPager viewPager) {
    adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new ContactFragment(), getString(R.string.contact));
    adapter.addFragment(profilFragment, getString(R.string.profil));
    adapter.addFragment(registrationFragment, getString(R.string.registration));
    adapter.addFragment(new DescriptionFragment(), getString(R.string.description));
    adapter.addFragment(new ParentageFragment(), getString(R.string.parentage));
    adapter.addFragment(new DocumentFragment(), getString(R.string.documents));
    viewPager.setAdapter(adapter);
  }

  public void showDialogFragment(DialogFragment dialog) {
    dialog.show(getSupportFragmentManager(), "Date");
  }


}
