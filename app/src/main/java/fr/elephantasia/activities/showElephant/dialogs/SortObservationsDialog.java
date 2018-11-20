package fr.elephantasia.activities.showElephant.dialogs;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.database.DatabaseController.SortOrder;

public class SortObservationsDialog {

  private Activity activity;
  private SortOrder dateOrder;
  private SortOrder priorityOrder;
  private Listener listener;

  @BindView(R.id.date) Spinner dateSpinner;
  @BindView(R.id.priority) Spinner prioritySpinner;

  public SortObservationsDialog(Activity activity, SortOrder dateOrder, SortOrder priorityOrder, Listener listener) {
    this.activity = activity;
    this.dateOrder = dateOrder;
    this.priorityOrder = priorityOrder;
    this.listener = listener;
  }

  public void show() {
    View customView = activity.getLayoutInflater().inflate(R.layout.elephant_observation_sort, null);
    ButterKnife.bind(this, customView);

    dateSpinner.setSelection(dateOrder.getValue());
    prioritySpinner.setSelection(priorityOrder.getValue());

    new MaterialDialog.Builder(activity)
      .title("Sort observations")
      .negativeText("Cancel")
      .positiveText("Ok")
      .customView(customView, false)
      .onPositive(new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
          listener.onValidate(
            SortOrder.valueOf(String.valueOf(dateSpinner.getSelectedItem())),
            SortOrder.valueOf(String.valueOf(prioritySpinner.getSelectedItem()))
          );
        }
      })
      .build()
      .show();
  }

  public interface Listener {
    void onValidate(SortOrder dateOrder, SortOrder priorityOrder);
  }

}
