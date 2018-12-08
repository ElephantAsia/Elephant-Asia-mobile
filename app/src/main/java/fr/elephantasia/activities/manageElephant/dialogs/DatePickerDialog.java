package fr.elephantasia.activities.manageElephant.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import fr.elephantasia.R;

/**
 * Created by Stephane on 16/03/2017.
 */

public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

  private Date baseDate;
  private Listener listener;

  public DatePickerDialog setBaseDate(@Nullable Date baseDate) {
    this.baseDate = baseDate;
    return this;
  }

  @Override
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();

    if (baseDate != null) {
      c.setTime(baseDate);
    }
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    return new android.app.DatePickerDialog(getActivity(), R.style.DialogTheme, this, year, month, day);
  }

  public DatePickerDialog setListener(Listener listener) {
    this.listener = listener;
    return this;
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    listener.onDateSet(year, month, dayOfMonth);
  }

  public interface Listener {
    void onDateSet(int year, int month, int dayOfMonth);
  }
}