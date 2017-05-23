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
import fr.elephantasia.databinding.SearchElephantActivityBinding;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.KeyboardHelpers;

public class SearchElephantActivity extends AppCompatActivity {

  // Extras
  public static final String EXTRA_SEARCH_ELEPHANT = "extra_search_elephant";

  // Attr
  private Elephant elephant = new Elephant();

  // Views Binding
  @BindView(R.id.toolbar) Toolbar toolbar;

  // Listeners Binding
  @OnClick(R.id.search_button)
  public void searchElephant() {
    Intent intent = new Intent(this, SearchElephantResultActivity.class);
    intent.putExtra(EXTRA_SEARCH_ELEPHANT, Parcels.wrap(elephant));
    startActivity(intent);
  }

  public SearchElephantActivity() {
    elephant.male = true;
    elephant.female = true;
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
}
