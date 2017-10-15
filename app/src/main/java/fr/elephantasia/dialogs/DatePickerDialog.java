package fr.elephantasia.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Stephane on 16/03/2017.
 */

public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

  private Listener listener;

  @Override
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    android.app.DatePickerDialog d = new android.app.DatePickerDialog(getActivity(), this, year, month, day);
    return d;
  }

  public void setListener(Listener listener) {
    this.listener = listener;
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    listener.onDateSet(year, month + 1, dayOfMonth);
  }

  public interface Listener {
    void onDateSet(int year, int month, int dayOfMonth);
  }
}