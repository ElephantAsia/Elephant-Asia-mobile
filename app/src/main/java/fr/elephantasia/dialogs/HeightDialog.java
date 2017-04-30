package fr.elephantasia.dialogs;

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
import fr.elephantasia.realm.model.Elephant;

/**
 * Created by seb on 30/04/2017.
 */

public class HeightDialog {
  private Activity activity;
  private ArrayAdapter<String> spinnerUnits;
  private Elephant elephant;
  private EditText editTextTarget;

  @BindView(R.id.editTextDialog) EditText editTextDialog;
  @BindView(R.id.spinnerDialog) Spinner spinnerDialog;

  public HeightDialog(Activity activity, Context ctx, final Elephant elephant, final EditText editText) {
    this.activity = activity;
    this.spinnerUnits = new ArrayAdapter<>(ctx, R.layout.ea_spinner, Arrays.asList("cm", "m"));
    this.elephant = elephant;
    this.editTextTarget = editText;
  }

  public void show() {
    final View view = activity.getLayoutInflater().inflate(R.layout.add_elephant_description_dialog_fragment, null);
    ButterKnife.bind(this, view);
    spinnerDialog.setAdapter(spinnerUnits);
    editTextDialog.setText(elephant.height);
    new MaterialDialog.Builder(activity)
        .title(R.string.set_weight)
        .positiveText(R.string.OK)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            elephant.height = editTextDialog.getText().toString();
            elephant.heightUnit = spinnerDialog.getSelectedItem().toString();
          }
        })
        .dismissListener(new DialogInterface.OnDismissListener() {
          @Override
          public void onDismiss(DialogInterface dialog) {
            elephant.heightUnit = TextUtils.isEmpty(elephant.height) ? "" : elephant.heightUnit;
            editTextTarget.setText(elephant.height + " " + elephant.heightUnit);
          }
        })
        .negativeText(R.string.CANCEL)
        .customView(view, true)
        .show();
  }
}
