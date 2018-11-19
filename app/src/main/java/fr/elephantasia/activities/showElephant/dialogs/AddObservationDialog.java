package fr.elephantasia.activities.showElephant.dialogs;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;

public class AddObservationDialog {

  private Activity activity;
  private Listener listener;

  @BindView(R.id.priority_spinner) Spinner priority;
  @BindView(R.id.category_spinner) Spinner category;
  @BindView(R.id.description) TextView desc;

  public AddObservationDialog(Activity activity, Listener listener) {
    this.activity = activity;
    this.listener = listener;
  }

  public void show() {
    View customView = activity.getLayoutInflater().inflate(R.layout.elephant_observation_add, null);

    ButterKnife.bind(this, customView);

    new MaterialDialog.Builder(activity)
      .title("Add observation")
      .negativeText("Cancel")
      .positiveText("Ok")
      .customView(customView, true)
      .onPositive(new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
          View view = dialog.getCustomView();

          if (view != null) {
            if (desc.getText().toString().trim().length() > 0) {
              listener.onValidate(
                String.valueOf(priority.getSelectedItem()),
                String.valueOf(category.getSelectedItem()),
                desc.getText().toString()
              );
            } else {
              listener.onError("A note must have a description");
            }
          }
        }
      }).build().show();
  }

  public interface Listener {
    void onValidate(String p, String c, String d);
    void onError(String why);
  }

}
