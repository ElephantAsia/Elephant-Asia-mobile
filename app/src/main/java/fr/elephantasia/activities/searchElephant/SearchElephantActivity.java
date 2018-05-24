package fr.elephantasia.activities.searchElephant;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.SearchElephantActivityBinding;
import fr.elephantasia.utils.KeyboardHelpers;

public class SearchElephantActivity extends AppCompatActivity {

  // Extras
  public static final String EXTRA_SEARCH_ELEPHANT = "extra_search_elephant";
  public static final String EXTRA_ELEPHANT_ID = "EXTRA_ELEPHANT_ID";

  // Request code
  public static final int REQUEST_ELEPHANT_SELECTED = 1;

  // Views Binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.search_button) FloatingActionButton searchButton;
  @BindView(R.id.name) EditText nameEditText;
  @BindView(R.id.male_only) CheckBox maleCb;
  @BindView(R.id.female_only) CheckBox femaleCb;
  @BindView(R.id.microchip) EditText microchipEditText;
  @BindView(R.id.mte) EditText mteEditText;
  @BindView(R.id.registration_province) EditText provinceEditText;
  @BindView(R.id.registration_district) EditText districtEditText;
  @BindView(R.id.registration_city) EditText cityEditText;

  // Attr
  private SearchElephantActivityBinding binding;
  private Elephant elephant = new Elephant();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.search_elephant_activity);
    binding.setE(elephant);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    KeyboardHelpers.hideKeyboardListener(binding.getRoot(), this);

    searchButton.setImageDrawable(
      new IconicsDrawable(this)
        .icon(MaterialDesignIconic.Icon.gmi_search)
        .color(Color.WHITE)
        .sizeDp(22)
    );
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
      resetFilter();
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
        case (REQUEST_ELEPHANT_SELECTED):
          resultIntent.putExtra(EXTRA_ELEPHANT_ID, data.getIntExtra(EXTRA_ELEPHANT_ID, -1));
          setResult(RESULT_OK, resultIntent);
          finish();
          break;
      }
    }
  }

  @OnClick(R.id.search_button)
  void searchElephant() {
    if (isFieldsValid()) {
      Log.w("search", "sex: " + ((elephant.sex != null) ? elephant.sex : "null"));
      Intent intent = new Intent(this, SearchElephantResultActivity.class);
      intent.setAction(getIntent().getAction());
      intent.putExtra(EXTRA_SEARCH_ELEPHANT, Parcels.wrap(elephant));
      startActivityForResult(intent, REQUEST_ELEPHANT_SELECTED);
    } else {
      Toast.makeText(this, "You must fill at least one field", Toast.LENGTH_SHORT).show();
    }
  }

  @OnClick(R.id.male_only)
  void setMaleOnly() {
    elephant.sex = maleCb.isChecked() ? "M" : null;

    if (femaleCb.isChecked()) {
      femaleCb.toggle();
    }
  }

  @OnClick(R.id.female_only)
  void setFemaleOnly() {
    elephant.sex = femaleCb.isChecked() ? "F" : null;

    if (maleCb.isChecked()) {
      maleCb.toggle();
    }
  }

  private void resetFilter() {
    femaleCb.setChecked(false);
    maleCb.setChecked(false);

    elephant = new Elephant();
    binding.setE(elephant);
  }

  private boolean isFieldsValid() {
    return nameEditText.length() + microchipEditText.length() + mteEditText.length()
      + provinceEditText.length() + districtEditText.length() + cityEditText.length()
      + ((femaleCb.isChecked())?1:0) + ((maleCb.isChecked())?1:0) > 0;
  }

}
