package fr.elephantasia.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import fr.elephantasia.R;
import fr.elephantasia.realm.Location;
import fr.elephantasia.utils.LocationHelpers;
import fr.elephantasia.utils.StaticTools;

/**
 * Created by seb on 29/04/2017.
 */

public class LocationInputDialog {

  private Activity activity;
  private Location loc;
  private String title;
  private EditText editText;

  public LocationInputDialog(Activity activity, final Location loc, final String title, final EditText editText) {
    this.activity = activity;
    this.loc = loc;
    this.title = title;
    this.editText = editText;

  }

  public void show() {
    final View view = activity.getLayoutInflater().inflate(R.layout.add_elephant_location_dialog_fragment, null);

    final EditText provinceET = ((EditText) view.findViewById(R.id.province));
    final EditText districtET = ((EditText) view.findViewById(R.id.district));
    final EditText cityET = ((EditText) view.findViewById(R.id.city));

    provinceET.setText(loc.provinceName);
    districtET.setText(loc.districtName);
    cityET.setText(loc.cityName);

    StaticTools.setupHideKeyboardListener(view, activity);
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
        .onNegative(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
          }
        })
        .dismissListener(new DialogInterface.OnDismissListener() {
          @Override
          public void onDismiss(DialogInterface dialog) {
            editText.setText(LocationHelpers.concat(loc));
          }
        })
        .customView(view, true)
        .show();
  }

}
