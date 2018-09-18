package fr.elephantasia.utils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerBindingUtil {

  @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
  public static void bindSpinnerData(Spinner pSpinner, String newSelectedValue, final InverseBindingListener newTextAttrChanged) {
    pSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        newTextAttrChanged.onChange();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
    if (newSelectedValue != null) {
      int pos = ((ArrayAdapter<String>) pSpinner.getAdapter()).getPosition(newSelectedValue);
      pSpinner.setSelection(pos, true);
    }
  }

  @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
  public static String captureSelectedValue(Spinner pSpinner) {
    return (String) pSpinner.getSelectedItem();
  }

}
