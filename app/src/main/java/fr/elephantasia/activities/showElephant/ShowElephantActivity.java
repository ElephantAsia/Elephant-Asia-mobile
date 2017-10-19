package fr.elephantasia.activities.showElephant;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.activities.showDocument.ShowDocumentActivity;
import fr.elephantasia.activities.showElephant.fragment.ShowChildrenFragment;
import fr.elephantasia.activities.showElephant.fragment.ShowDocumentFragment;
import fr.elephantasia.activities.showElephant.fragment.ShowOverviewFragment;
import fr.elephantasia.activities.showElephant.fragment.ShowParentageFragment;
import fr.elephantasia.adapter.DocumentAdapter;
import fr.elephantasia.adapter.ViewPagerAdapter;
import fr.elephantasia.database.RealmDB;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ShowElephantActivityBinding;
import io.realm.Realm;

import static fr.elephantasia.activities.manageElephant.ManageElephantActivity.RESULT_DRAFT;
import static fr.elephantasia.activities.manageElephant.ManageElephantActivity.RESULT_VALIDATE;
import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_ELEPHANT_ID;
import static fr.elephantasia.database.model.Elephant.ID;

public class ShowElephantActivity extends AppCompatActivity implements DocumentAdapter.Listener {

  public static final String EXTRA_EDIT_ELEPHANT_ID = "EXTRA_EDIT_ELEPHANT_ID";
  private static final int REQUEST_ELEPHANT_EDITED = 0;

  // View Binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.parent_layout) LinearLayout parentLayout;
  @BindView(R.id.tabs) TabLayout tabLayout;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.toolbar_title) TextView toolbarTitle;

  // Attr
  private Elephant elephant;
  private Realm realm;
  ViewPagerAdapter adapter;


  // Icons
  Drawable deleteIcon;
  Drawable editIcon;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ShowElephantActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.show_elephant_activity);

    realm = Realm.getDefaultInstance();
    elephant = getExtraElephant();

    RealmDB.updateLastVisitedDate(elephant.id);

    binding.setE(elephant);
    ButterKnife.bind(this);

    initIcon();
    setSupportActionBar(toolbar);
    toolbarTitle.setMaxLines(1);
    toolbarTitle.setHorizontallyScrolling(true);
    toolbarTitle.setEllipsize(TextUtils.TruncateAt.END);
    toolbarTitle.setText(String.format(getString(R.string.elephant_show_title), elephant.name));
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    setupViewPager(viewPager);
    tabLayout.setupWithViewPager(viewPager);
  }

  private void initIcon() {
    deleteIcon = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_delete)
        .color(Color.WHITE).sizeDp(24);
    editIcon = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_edit)
        .color(Color.WHITE).sizeDp(24);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    realm.close();
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
    getMenuInflater().inflate(R.menu.show_elephant_options_menu, menu);
    menu.findItem(R.id.edit_elephant).setIcon(editIcon);
    menu.findItem(R.id.delete_elephant).setIcon(deleteIcon);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.edit_elephant) {
      Intent intent = new Intent(this, ManageElephantActivity.class);
      intent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
      startActivityForResult(intent, REQUEST_ELEPHANT_EDITED);
      return true;
    } else if (item.getItemId() == R.id.delete_elephant) {
      new MaterialDialog.Builder(this)
          .title(R.string.delete_this_elephant_from_local_db)
          .positiveText(R.string.yes)
          .onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
              realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                  elephant.deleteFromRealm();
                }
              });
              finish();
            }
          })
          .negativeText(R.string.no)
          .stackingBehavior(StackingBehavior.ALWAYS)
          .show();
      return true;
    }
    return false;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_DRAFT || resultCode == RESULT_VALIDATE) {
      switch (requestCode) {
        case REQUEST_ELEPHANT_EDITED:
          Intent intent = getIntent();
          intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
          finish();
          startActivity(intent);
          break;
      }
    }
  }

  public Elephant getElephant() {
    return elephant;
  }

  private Elephant getExtraElephant() {
    Intent intent = getIntent();
    Integer id = intent.getIntExtra(EXTRA_ELEPHANT_ID, -1);
    if (id != -1) {
      return realm.where(Elephant.class).equalTo(ID, id).findFirst();
    }
    throw new RuntimeException("ShowElephantActivity:148: ID incorrect");
  }

  private void setupViewPager(ViewPager viewPager) {
    adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new ShowOverviewFragment(), getString(R.string.overview));
    adapter.addFragment(new ShowParentageFragment(), getString(R.string.parentage));
    adapter.addFragment(new ShowChildrenFragment(), getString(R.string.children));
    adapter.addFragment(new ShowDocumentFragment(), getString(R.string.documents));
    viewPager.setAdapter(adapter);
  }

  @Override
  public void onDocumentClick(Document document) {
    Intent intent = new Intent(this, ShowDocumentActivity.class);
    ShowDocumentActivity.setExtraTitle(intent, document.title);
    ShowDocumentActivity.setExtraPath(intent, document.path);
    startActivity(intent);
    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
  }

}
