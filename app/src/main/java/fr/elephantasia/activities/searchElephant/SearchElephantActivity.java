package fr.elephantasia.activities.searchElephant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.searchElephant.fragments.SearchElephantFragment;

public class SearchElephantActivity extends AppCompatActivity {

  /**
   * Classifier
   */

  static private final int REQUEST_SELECT_ELEPHANT = 1;

  static private final String EXTRA_ACTION = "SEA.EXTRA_ACTION";
  static private final String EXTRA_ELEPHANT_ID = "SEA.EXTRA_ELEPHANT_ID";

  static public void SetExtraAction(@NonNull Intent intent, String action) {
    intent.putExtra(EXTRA_ACTION, action);
  }

  static public void SetExtraElephantId(@NonNull Intent intent, Integer id) {
    intent.putExtra(EXTRA_ELEPHANT_ID, id);
  }

  @Nullable
  static public String GetExtraAction(@NonNull Intent intent) {
    return intent.getStringExtra(EXTRA_ACTION);
  }

  @NonNull
  static public Integer GetExtraElephantId(@NonNull Intent intent) {
    return intent.getIntExtra(EXTRA_ELEPHANT_ID, -1);
  }

  /**
   * Instance
   */

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.search_button) FloatingActionButton searchButton;

  private SearchElephantFragment fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_elephant_activity);
    ButterKnife.bind(this);

    setToolbar();
    setFragment();
    setSearchButton();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_activity_menu, menu);

    MenuItem resetFilter = menu.findItem(R.id.reset_filter);
    resetFilter.setIcon(
      new IconicsDrawable(this)
        .icon(CommunityMaterial.Icon.cmd_filter_remove_outline)
        .color(Color.WHITE)
        .sizeDp(22)
    );

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.reset_filter) {
      fragment.reset();
      return true;
    }
    return false;
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    return true;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK && data != null) {
      Intent resultIntent = new Intent();

      switch (requestCode) {
        case (REQUEST_SELECT_ELEPHANT):
          SetExtraElephantId(resultIntent, GetExtraElephantId(data));
          setResult(RESULT_OK, resultIntent);
          finish();
          break;
      }
    }
  }

  private void setToolbar() {
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  private void setFragment() {
    fragment = new SearchElephantFragment();
    getSupportFragmentManager().beginTransaction()
      .replace(R.id.search_fragment, fragment)
      .commit();
  }

  private void setSearchButton() {
    searchButton.setImageDrawable(
      new IconicsDrawable(this)
        .icon(MaterialDesignIconic.Icon.gmi_search)
        .color(Color.WHITE)
        .sizeDp(22)
    );
  }

  @OnClick(R.id.search_button)
  void searchElephant() {
    if (fragment.isFieldsValid()) {
      String action = SearchElephantActivity.GetExtraAction(getIntent());
      Intent intent = new Intent(this, SearchElephantResultActivity.class);
      SearchElephantResultActivity.SetExtraAction(intent, action);
      SearchElephantResultActivity.SetExtraElephant(intent, fragment.getElephant());
      startActivityForResult(intent, REQUEST_SELECT_ELEPHANT);
    } else {
      Toast.makeText(this, "You must fill at least one field", Toast.LENGTH_SHORT).show();
    }
  }

}
