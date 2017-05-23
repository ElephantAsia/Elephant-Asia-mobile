package fr.elephantasia.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.databinding.ShowElephantActivityBinding;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.KeyboardHelpers;
import io.realm.Realm;

import static fr.elephantasia.activities.SearchElephantResultActivity.EXTRA_ELEPHANT_SELECTED_ID;
import static fr.elephantasia.database.model.Elephant.ID;

public class ShowElephantActivity extends AppCompatActivity {

  // Attr
  private Elephant elephant;
  private Realm realm;

  // View Binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.toolbar_title) TextView toolbarTitle;
  @BindView(R.id.profil) TextView profil;
  @BindView(R.id.chip1) TextView chip1;
  @BindView(R.id.location) TextView location;
  @BindView(R.id.state_local) TextView stateLocal;

  @OnClick(R.id.delete_button)
  public void deleteElephant() {
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
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ShowElephantActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.show_elephant_activity);
    elephant = getExtraElephant();
    binding.setE(elephant);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    toolbarTitle.setText(String.format(getString(R.string.elephant_show_title), elephant.name));
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    profil.setText(formatProfil());
    chip1.setText(formatChip());
    location.setText(elephant.currentLoc.format());

    if (elephant.state.local || elephant.state.draft) {
      stateLocal.setVisibility(View.VISIBLE);
    }

    KeyboardHelpers.hideKeyboardListener(binding.getRoot(), this);
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

  private Elephant getExtraElephant() {
    Intent intent = getIntent();
    String id = intent.getStringExtra(EXTRA_ELEPHANT_SELECTED_ID);
    realm = Realm.getDefaultInstance();
    Elephant el = realm.where(Elephant.class).equalTo(ID, id).findFirst();

    return el;
  }

  private String formatProfil() {
    String res = elephant.name + ", " + elephant.getSex() + ", ";

    if (!TextUtils.isEmpty(elephant.birthDate)) {
      res += elephant.getAge() + " y/o";
    } else {
      res += "Age N/A";
    }
    return res;
  }

  private String formatChip() {
    String res;

    if (!TextUtils.isEmpty(elephant.chips1)) {
      res = "#" + elephant.chips1;
    } else {
      res = "N/A";
    }
    return res;
  }

}
