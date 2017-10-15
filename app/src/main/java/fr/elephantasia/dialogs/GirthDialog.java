package fr.elephantasia.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;

/**
 * Created by seb on 30/04/2017.
 */

public class GirthDialog {

  // View binding
  @BindView(R.id.value) EditText value;
  @BindView(R.id.unit) Spinner unit;

  // Attr
  private Activity activity;
  private Elephant elephant;
  private TextView weightView;
  private EditText girthView;

  public GirthDialog(Activity activity, Context ctx, final Elephant elephant, final TextView weightView, final EditText girthView) {
    this.activity = activity;
    this.elephant = elephant;
    this.weightView = weightView;
    this.girthView = girthView;
  }

  public void show() {
    final View view = activity.getLayoutInflater().inflate(R.layout.measurement_dialog_fragment, null);
    ButterKnife.bind(this, view);
    value.setText(elephant.girth);
    unit.setVisibility(View.GONE);
    new MaterialDialog.Builder(activity)
        .title(R.string.set_girth)
        .positiveText(R.string.OK)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            elephant.girth = value.getText().toString();
          }
        })
        .dismissListener(new DialogInterface.OnDismissListener() {
          @Override
          public void onDismiss(DialogInterface dialog) {
            if (elephant.girth != null) {
              String res = elephant.girth.isEmpty() ? "" : elephant.girth + " cm";
              elephant.setWeight(elephant.girth);
              weightView.setText(elephant.getWeightText());
              girthView.setText(res);
            }

          }
        })
        .negativeText(R.string.CANCEL)
        .customView(view, true)
        .show();
  }
}
