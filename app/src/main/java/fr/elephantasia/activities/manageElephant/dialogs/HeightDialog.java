package fr.elephantasia.activities.manageElephant.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;

/**
 * Created by seb on 30/04/2017.
 */

public class HeightDialog {

  // View binding
  @BindView(R.id.value) EditText value;
  @BindView(R.id.unit) Spinner unit;

  // Attr
  private Activity activity;
  private ArrayAdapter<String> spinnerUnits;
  private Elephant elephant;
  private EditText editTextTarget;

  public HeightDialog(Activity activity, Context ctx, final Elephant elephant, final EditText editText) {
    this.activity = activity;
    this.spinnerUnits = new ArrayAdapter<>(ctx, R.layout.ea_spinner, Arrays.asList("cm", "m"));
    this.elephant = elephant;
    this.editTextTarget = editText;
  }

  public void show() {
    final View view = activity.getLayoutInflater().inflate(R.layout.measurement_dialog_fragment, null);
    ButterKnife.bind(this, view);
    unit.setAdapter(spinnerUnits);
    value.setText(elephant.height);
    new MaterialDialog.Builder(activity)
        .title(R.string.set_height)
        .positiveText(R.string.OK)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            elephant.height = value.getText().toString();
            elephant.heightUnit = unit.getSelectedItem().toString();
          }
        })
        .dismissListener(new DialogInterface.OnDismissListener() {
          @Override
          public void onDismiss(DialogInterface dialog) {
            if (elephant.height != null) {
              elephant.heightUnit = TextUtils.isEmpty(elephant.height) ? "" : elephant.heightUnit;
              editTextTarget.setText(elephant.height + " " + elephant.heightUnit);
            }
          }
        })
        .negativeText(R.string.CANCEL)
        .customView(view, true)
        .show();
  }
}
