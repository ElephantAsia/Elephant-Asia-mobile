package fr.elephantasia.activities.manageElephant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
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
import fr.elephantasia.BaseApplication;
import fr.elephantasia.R;
import fr.elephantasia.activities.contact.SearchContactActivity;
import fr.elephantasia.activities.manageElephant.adapters.ViewPagerAdapter;
import fr.elephantasia.activities.manageElephant.fragments.ChildrenFragment;
import fr.elephantasia.activities.manageElephant.fragments.ContactFragment;
import fr.elephantasia.activities.manageElephant.fragments.DescriptionFragment;
import fr.elephantasia.activities.manageElephant.fragments.ParentageFragment;
import fr.elephantasia.activities.manageElephant.fragments.ProfilFragment;
import fr.elephantasia.activities.manageElephant.fragments.RegistrationFragment;
import fr.elephantasia.activities.searchElephant.SearchElephantActivity;
import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.KeyboardHelpers;
import fr.elephantasia.view.ElephantPreview;

import static fr.elephantasia.activities.contact.SearchContactActivity.EXTRA_SEARCH_CONTACT;

public class ManageElephantActivity extends AppCompatActivity {

  /**
   * Classifier
   */

  // Result code
  static public final int RESULT_DRAFT = 2;
  static public final int RESULT_VALIDATE = 3;

  // Request codes
  static private final int REQUEST_SELECT_CONTACT = 4;
  static private final int REQUEST_SELECT_FATHER = 5;
  static private final int REQUEST_SELECT_MOTHER = 6;
  static private final int REQUEST_SELECT_CHILD = 7;

  // Extra
  static private final String EXTRA_ELEPHANT_ID = "extra.eid";

  static public void SetExtraElephantId(Intent intent, Integer id) {
    intent.putExtra(EXTRA_ELEPHANT_ID, id);
  }

  static public Integer GetExtraElephantId(Intent intent) {
    return intent.getIntExtra(EXTRA_ELEPHANT_ID, -1);
  }

  /**
   * Instance
   */

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.title) TextView toolbarTitle;
  @BindView(R.id.tabs) TabLayout tabLayout;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.add_elephant_activity) View rootView;
  @BindView(R.id.add_elephant_fab) FloatingActionButton fab;

  // Controllers
  private DatabaseController databaseController;

  // Fragments
  private ProfilFragment profilFragment = new ProfilFragment();
  private RegistrationFragment registrationFragment = new RegistrationFragment();
  private ContactFragment contactFragment = new ContactFragment();
  private ParentageFragment parentageFragment = new ParentageFragment();
  private ChildrenFragment childrenFragment = new ChildrenFragment();

  // Icons
  private Drawable draftIcon;
  private Drawable validateIcon;
  private Drawable nextStepIcon;

  // Misc
  private Elephant elephant;
  private boolean editing = false;
  private List<Document> documents = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.manage_elephant_activity);
    ButterKnife.bind(this);

    databaseController = ((BaseApplication)getApplication()).getDatabaseController();

    initGlobalListener();
    initToolbar();
    initIcon();
    initViewPager();
    initElephant();
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

    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case REQUEST_SELECT_CONTACT:
          Contact contact = Parcels.unwrap(data.getParcelableExtra(EXTRA_SEARCH_CONTACT));
          contactFragment.addContactTolist(contact);
          break;
        case REQUEST_SELECT_MOTHER:
          setMother(SearchElephantActivity.GetExtraElephantId(data));
          break;
        case REQUEST_SELECT_FATHER:
          setFather(SearchElephantActivity.GetExtraElephantId(data));
          break;
        case REQUEST_SELECT_CHILD:
          addChild(SearchElephantActivity.GetExtraElephantId(data));
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
      // TODO: rework drafts
      elephant.dbState = Elephant.DbState.Edited.name();
      elephant.draft = true;
      saveToDb();
      setResult(RESULT_DRAFT);
      finish();
      return true;
    } else if (item.getItemId() == R.id.add_elephant_menu_validate && checkMandatoryFields()) {
      if (editing) {
        if (elephant.isPresentInServerDb()) {
          elephant.dbState = Elephant.DbState.Edited.name();
        } else {
          elephant.dbState = Elephant.DbState.Created.name();
        }
      } else {
        elephant.dbState = Elephant.DbState.Created.name();
      }
      elephant.syncState = null;
      saveToDb();
      setResult(RESULT_VALIDATE);
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
    if (elephant.sex == null) {
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
                elephant.draft = true;
                saveToDb();
                setResult(RESULT_DRAFT);
                finish();
              }
            }
          })
          .show();
    }
  }
  private void initGlobalListener() {
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
  }

  private void initToolbar() {
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

  private void initViewPager() {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(profilFragment, getString(R.string.profil));
    adapter.addFragment(registrationFragment, getString(R.string.registration));
    adapter.addFragment(contactFragment, getString(R.string.contact));
    adapter.addFragment(new DescriptionFragment(), getString(R.string.description));
    adapter.addFragment(parentageFragment, getString(R.string.parentage));
    adapter.addFragment(childrenFragment, getString(R.string.children));
    viewPager.setAdapter(adapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  private void initElephant() {
    Integer id = GetExtraElephantId(getIntent());
    if (id != -1) {
      editing = true;
      elephant = databaseController.getElephantById(id);
      documents = databaseController.getDocumentsByElephantId(id);

      toolbarTitle.setText(String.format(getString(R.string.edit_elephant_title), elephant.name));
      toolbarTitle.setMaxLines(1);
      toolbarTitle.setHorizontallyScrolling(true);
      toolbarTitle.setEllipsize(TextUtils.TruncateAt.END);
    } else {
      elephant = new Elephant();
    }
  }

  public Elephant getElephant() {
    return this.elephant;
  }

  private void setMother(Integer id) {
    Elephant mother = databaseController.getElephantById(id);
    if (mother != null) {
      parentageFragment.setMother(mother);
    }
  }

  private void setFather(Integer id) {
    Elephant father = databaseController.getElephantById(id);
    if (father != null) {
      parentageFragment.setFather(father);
    }
  }

  private void addChild(Integer id) {
    Elephant child = databaseController.getElephantById(id);
    if (child != null) {
      childrenFragment.addChild(child);
    }
  }

  private void saveToDb() {
    databaseController.insertOrUpdate(elephant, documents);
  }

  public void searchMother() {
    Intent intent = new Intent(this, SearchElephantActivity.class);
    SearchElephantActivity.SetExtraAction(intent, ElephantPreview.SELECT);
    startActivityForResult(intent, REQUEST_SELECT_MOTHER);
  }

  public void searchFather() {
    Intent intent = new Intent(this, SearchElephantActivity.class);
    SearchElephantActivity.SetExtraAction(intent, ElephantPreview.SELECT);
    startActivityForResult(intent, REQUEST_SELECT_FATHER);
  }

  public void searchChild() {
    Intent intent = new Intent(this, SearchElephantActivity.class);
    SearchElephantActivity.SetExtraAction(intent, ElephantPreview.SELECT);
    startActivityForResult(intent, REQUEST_SELECT_CHILD);
  }

  public void searchContact() {
    Intent intent = new Intent(this, SearchContactActivity.class);
    startActivityForResult(intent, REQUEST_SELECT_CONTACT);
  }

  @OnClick(R.id.add_elephant_fab)
  public void nextPage() {
    PagerAdapter adapter = viewPager.getAdapter();
    if (adapter != null) {
      if (viewPager.getCurrentItem() == adapter.getCount() - 1) {
        viewPager.setCurrentItem(0);
      } else {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
      }
    }
  }

}
