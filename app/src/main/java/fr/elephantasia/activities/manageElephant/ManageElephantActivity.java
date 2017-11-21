package fr.elephantasia.activities.manageElephant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.manageElephant.fragment.ChildrenFragment;
import fr.elephantasia.activities.manageElephant.fragment.ContactFragment;
import fr.elephantasia.activities.manageElephant.fragment.DescriptionFragment;
import fr.elephantasia.activities.manageElephant.fragment.ParentageFragment;
import fr.elephantasia.activities.manageElephant.fragment.ProfilFragment;
import fr.elephantasia.activities.manageElephant.fragment.RegistrationFragment;
import fr.elephantasia.adapter.ViewPagerAdapter;
import fr.elephantasia.database.RealmDB;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.database.model.Elephant.StateValue;
import fr.elephantasia.utils.KeyboardHelpers;
import io.realm.Realm;

import static fr.elephantasia.activities.SearchContactActivity.EXTRA_SEARCH_CONTACT;
import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_ELEPHANT_ID;
import static fr.elephantasia.database.model.Elephant.ID;

public class ManageElephantActivity extends AppCompatActivity {

  // Result code
  public static final int RESULT_DRAFT = 2;
  public static final int RESULT_VALIDATE = 3;

  // Request codes
  public static final int REQUEST_CONTACT_SELECTED = 4;
  public static final int REQUEST_FATHER_SELECTED = 5;
  public static final int REQUEST_MOTHER_SELECTED = 6;
  public static final int REQUEST_CHILD_SELECTED = 7;
  // public static final int REQUEST_CAPTURE_PHOTO = 8;
  // public static final int REQUEST_IMPORT_PHOTO = 9;
  public static final int REQUEST_ADD_DOCUMENT = 10;

  // View binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.title) TextView toolbarTitle;
  @BindView(R.id.tabs) TabLayout tabLayout;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.add_elephant_activity) View rootView;
  @BindView(R.id.add_elephant_fab) FloatingActionButton fab;

  // Fragment
  private ProfilFragment profilFragment = new ProfilFragment();
  private RegistrationFragment registrationFragment = new RegistrationFragment();

  private ContactFragment contactFragment = new ContactFragment();
  private ParentageFragment parentageFragment = new ParentageFragment();
  private ChildrenFragment childrenFragment = new ChildrenFragment();

  // Attr
  private Elephant elephant;
  // private PickImageDialog pickImageDialog;
  private Realm realm;
  private List<Document> documents = new ArrayList<>();

  // Icons
  Drawable draftIcon;
  Drawable validateIcon;
  Drawable nextStepIcon;

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
    setContentView(R.layout.manage_elephant_activity);
    ButterKnife.bind(this);
    initIcon();

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

    int id = getIntent().getIntExtra(EXTRA_ELEPHANT_ID, -1);
    if (id != -1) {
      elephant = realm.copyFromRealm(realm.where(Elephant.class).equalTo(ID, id).findFirst());
      documents = realm.copyFromRealm(realm.where(Document.class).equalTo(Document.ELEPHANT_ID, id).findAll());

      toolbarTitle.setText(String.format(getString(R.string.edit_elephant_title), elephant.name));
      toolbarTitle.setMaxLines(1);
      toolbarTitle.setHorizontallyScrolling(true);
      toolbarTitle.setEllipsize(TextUtils.TruncateAt.END);
    } else {
      elephant = new Elephant();
    }
  }

  private void initIcon() {
    draftIcon = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_archive)
        .color(Color.WHITE).sizeDp(24);
    validateIcon = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_check)
        .color(Color.WHITE).sizeDp(24);
    nextStepIcon = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_chevron_right)
        .color(Color.WHITE).sizeDp(24);
    fab.setImageDrawable(nextStepIcon);
  }

  private void setupViewPager(ViewPager viewPager) {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(profilFragment, getString(R.string.profil));
    adapter.addFragment(registrationFragment, getString(R.string.registration));
    adapter.addFragment(contactFragment, getString(R.string.contact));
    adapter.addFragment(new DescriptionFragment(), getString(R.string.description));
    adapter.addFragment(parentageFragment, getString(R.string.parentage));
    adapter.addFragment(childrenFragment, getString(R.string.children));
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

    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case REQUEST_CONTACT_SELECTED:
          Contact contact = Parcels.unwrap(data.getParcelableExtra(EXTRA_SEARCH_CONTACT));
          contactFragment.addContactTolist(contact);
          break;
        case REQUEST_MOTHER_SELECTED:
          parentageFragment.setMother(data.getIntExtra(EXTRA_ELEPHANT_ID, -1));
          break;
        case REQUEST_FATHER_SELECTED:
          parentageFragment.setFather(data.getIntExtra(EXTRA_ELEPHANT_ID, -1));
          break;
        case REQUEST_CHILD_SELECTED:
          childrenFragment.setChild(data.getIntExtra(EXTRA_ELEPHANT_ID, -1));
          break;
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_elephant_options_menu, menu);
    menu.findItem(R.id.add_elephant_menu_draft).setIcon(draftIcon);
    menu.findItem(R.id.add_elephant_menu_validate).setIcon(validateIcon);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.add_elephant_menu_draft && checkMandatoryFields()) {
      elephant.state = StateValue.draft.name();
      saveToDb();
      setResult(RESULT_DRAFT);
      finish();
      return true;
    } else if (item.getItemId() == R.id.add_elephant_menu_validate && checkMandatoryFields()) {
      elephant.state = StateValue.saved.name();
      saveToDb();
      setResult(RESULT_VALIDATE);
      finish();
      return true;
    }
    return false;
  }

  /* @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      startActivityForResult(pickImageDialog.getIntent(0), pickImageDialog.getRequestCode(0));
    }
  } */

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
                elephant.state = StateValue.draft.name();
                saveToDb();
                setResult(RESULT_DRAFT);
                finish();
              }
            }
          })
          .show();
    }
  }

  /* public void onAddDocumentClick() {
    pickImageDialog = new PickImageDialogBuilder(this)
        .build()
        .setListener(new PickImageDialog.Listener() {
          @Override
          public void execute(Intent intent, int requestCode) {
            if (requestCode == REQUEST_CAPTURE_PHOTO) {
              if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ManageElephantActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
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
  } */

  private void saveToDb() {
    RealmDB.insertOrUpdateElephant(elephant, documents);
    // TODO: add popup 'saving ...'
  }

}
