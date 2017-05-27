package fr.elephantasia.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.searchElephantResult.SearchElephantResultActivity;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.SearchElephantActivityBinding;
import fr.elephantasia.utils.KeyboardHelpers;

import static fr.elephantasia.activities.searchElephantResult.SearchElephantResultActivity.EXTRA_ELEPHANT_ID;

public class SearchElephantActivity extends AppCompatActivity {

  // Extras
  public static final String EXTRA_SEARCH_ELEPHANT = "extra_search_elephant";

  // Request code
  public static final int REQUEST_ELEPHANT_SELECTED = 1;

  // Views Binding
  @BindView(R.id.toolbar) Toolbar toolbar;

  // Attr
  private Elephant elephant = new Elephant();

  public SearchElephantActivity() {
    elephant.male = true;
    elephant.female = true;
  }

  // Listeners Binding
  @OnClick(R.id.search_button)
  public void searchElephant() {
    Intent intent = new Intent(this, SearchElephantResultActivity.class);
    intent.setAction(getIntent().getAction());
    intent.putExtra(EXTRA_SEARCH_ELEPHANT, Parcels.wrap(elephant));
    startActivityForResult(intent, REQUEST_ELEPHANT_SELECTED);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SearchElephantActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.search_elephant_activity);
    binding.setE(elephant);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    KeyboardHelpers.hideKeyboardListener(binding.getRoot(), this);
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
          resultIntent.putExtra(EXTRA_ELEPHANT_ID, data.getStringExtra(EXTRA_ELEPHANT_ID));
          setResult(RESULT_OK, resultIntent);
          finish();
          break;
      }
    }
  }
}
