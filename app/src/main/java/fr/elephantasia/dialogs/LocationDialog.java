package fr.elephantasia.dialogs;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import fr.elephantasia.R;
import fr.elephantasia.database.model.Location;

import static android.content.ContentValues.TAG;

/**
 * Created by seb on 29/04/2017.
 */

public class LocationDialog {

  private Activity activity;
  private Location loc;
  private String title;
  private int resultCode;
  private EditText editText;

  private LocationInputDialog inputDialog;

  public LocationDialog(Activity activity, final Location loc, final String title, final int resultCode, final EditText editText) {
    this.activity = activity;
    this.loc = loc;
    this.title = title;
    this.resultCode = resultCode;
    this.editText = editText;
  }

  /**
   * Display dialog to allow user to set location either manually or from map
   */
  public void show() {
    new MaterialDialog.Builder(activity)
        .title(R.string.set_location_from_map)
        .positiveText(R.string.from_map)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            showLocationPicker(resultCode);
          }
        })
        .negativeText(R.string.manually)
        .onNegative(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            inputDialog = new LocationInputDialog(activity, loc, title, editText);
            inputDialog.show();
          }
        })
        .stackingBehavior(StackingBehavior.ALWAYS)
        .show();

  }

  private void showLocationPicker(int resultCode) {
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    LatLng burmaNorth = new LatLng(16.193669, 95.229859);
    LatLng burmaSouth = new LatLng(28.279449, 97.576320);
    LatLngBounds bound = new LatLngBounds(burmaNorth, burmaSouth);

    builder.setLatLngBounds(bound);

    try {
      activity.startActivityForResult(builder.build(activity), resultCode);
    } catch (Exception e) {
      Log.e(TAG, e.getMessage());
    }
  }
}