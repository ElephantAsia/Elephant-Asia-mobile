package fr.elephantasia.activities.manageElephant.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Location;
import fr.elephantasia.utils.KeyboardHelpers;

/**
 * Created by seb on 29/04/2017.
 */

public class LocationInputDialog {

  // View binding
  @BindView(R.id.province) EditText provinceET;
  @BindView(R.id.district) EditText districtET;
  @BindView(R.id.city) EditText cityET;

  // Attr
  private Activity activity;
  private Location loc;
  private String title;
  private EditText editTextTarget;

  public LocationInputDialog(Activity activity, final Location loc, final String title, final EditText editText) {
    this.activity = activity;
    this.loc = loc;
    this.title = title;
    this.editTextTarget = editText;
  }

  public void show() {
    final View view = activity.getLayoutInflater().inflate(R.layout.add_elephant_location_dialog_fragment, null);
    ButterKnife.bind(this, view);

    if (!TextUtils.isEmpty(loc.provinceName)) {
      provinceET.setText(loc.provinceName);
    }
    if (!TextUtils.isEmpty(loc.districtName)) {
      districtET.setText(loc.districtName);
    }
    if (!TextUtils.isEmpty(loc.cityName)) {
      cityET.setText(loc.cityName);
    }

    KeyboardHelpers.hideKeyboardListener(view, activity);

    new MaterialDialog.Builder(activity)
        .title(title)
        .positiveText(R.string.OK)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            loc.cityName = cityET.getText().toString();
            loc.districtName = districtET.getText().toString();
            loc.provinceName = provinceET.getText().toString();

          }
        })
        .negativeText(R.string.CANCEL)
        .dismissListener(new DialogInterface.OnDismissListener() {
          @Override
          public void onDismiss(DialogInterface dialog) {
            editTextTarget.setText(loc.format());
          }
        })
        .customView(view, true)
        .show();
  }

}
