package fr.elephantasia.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.databinding.SearchElephantActivityBinding;
import fr.elephantasia.fragment.SearchElephantFragment;
import fr.elephantasia.interfaces.SearchInterface;
import fr.elephantasia.realm.model.Elephant;
import fr.elephantasia.utils.ElephantInfo;
import fr.elephantasia.utils.KeyboardHelpers;

public class SearchElephantActivity extends AppCompatActivity {

  // Extras
  public static final String EXTRA_SEARCH_ELEPHANT = "extra_search_elephant";

  // Request code
  public static final int REQUEST_ELEPHANT_SELECTED = 1;

  // Attr
  private Elephant elephant = new Elephant();

  // Views Binding
  @BindView(R.id.toolbar) Toolbar toolbar;

  // Listeners Binding
  @OnClick(R.id.search_button)
  public void searchElephant() {
    Intent intent = new Intent(this, SearchElephantResultActivity.class);
    intent.putExtra(EXTRA_SEARCH_ELEPHANT, Parcels.wrap(elephant));
    startActivityForResult(intent, REQUEST_ELEPHANT_SELECTED);
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    if (requestCode == REQUEST_SEARCH_BROWSER) {
//      if (resultCode == RESULT_OK && mode == Mode.PICK_RESULT) {
//        ElephantInfo info = data.getParcelableExtra(SearchElephantResultActivity.EXTRA_ELEPHANT);
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_RESULT, info);
//        setResult(RESULT_OK, intent);
//        finish();
//      }
//    }
  }
}
