package fr.elephantasia.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;

import fr.elephantasia.R;
import fr.elephantasia.activities.AddContactActivity;
import fr.elephantasia.activities.SearchContactActivity;
import fr.elephantasia.realm.model.Elephant;
import fr.elephantasia.realm.model.Location;

/**
 * Created by seb on 30/04/2017.
 */

public class AddContactDialog {

  private Activity activity;
  private Location loc;
  private String title;
  private int resultCode;

  public AddContactDialog(Activity activity, final Elephant elephant, final int resultCode) {
    this.activity = activity;
    this.loc = loc;
    this.title = title;
    this.resultCode = resultCode;
  }

  /**
   * Display dialog to allow user to set location either manually or from map
   */
  public void show() {
    new MaterialDialog.Builder(activity)
        .title(R.string.add_existing_contact)
        .positiveText(R.string.SEARCH)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            Intent intent = new Intent(activity, SearchContactActivity.class);
            activity.startActivity(intent);
          }
        })
        .negativeText(R.string.CREATE)
        .onNegative(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

//            Intent intent = new Intent(activity, AddContactActivity.class);
//            activity.startActivity(intent);
          }
        })
        .stackingBehavior(StackingBehavior.ALWAYS)
        .show();

  }


}
