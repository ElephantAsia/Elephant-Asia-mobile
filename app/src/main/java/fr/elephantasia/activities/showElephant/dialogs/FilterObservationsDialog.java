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
import fr.elephantasia.database.model.ElephantNote.Category;
import fr.elephantasia.database.model.ElephantNote.Priority;

public class FilterObservationsDialog {

  private Activity activity;
  private Category category;
  private Priority priority;
  private Listener listener;

  @BindView(R.id.category) Spinner categorySpinner;
  @BindView(R.id.priority) Spinner prioritySpinner;

  public FilterObservationsDialog(Activity activity, Category category, Priority prioriy, Listener listener) {
    this.activity = activity;
    this.category = category;
    this.priority = prioriy;
    this.listener = listener;
  }

  public void show() {
    View customView = activity.getLayoutInflater().inflate(R.layout.elephant_observation_filter, null);
    ButterKnife.bind(this, customView);

    categorySpinner.setSelection(category.getValue());
    prioritySpinner.setSelection(priority.getValue());

    new MaterialDialog.Builder(activity)
      .title("Filter observations")
      .negativeText("Cancel")
      .positiveText("Ok")
      .customView(customView, false)
      .onPositive(new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
          listener.onValidate(
            Category.valueOf(String.valueOf(categorySpinner.getSelectedItem())),
            Priority.valueOf(String.valueOf(prioritySpinner.getSelectedItem()))
          );
        }
      })
      .build()
      .show();
  }

  public interface Listener {
    void onValidate(Category categoryFilter, Priority priorityFilter);
  }

}
