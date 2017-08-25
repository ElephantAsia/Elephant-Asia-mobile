package fr.elephantasia.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by seb on 30/04/2017.
 */

public class KeyboardHelpers {

  /**
   * Hide keyboard
   */
  public static void hideSoftKeyboard(Activity activity) {
    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

    View currentFocus = activity.getCurrentFocus();
    if (currentFocus != null) {
      inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }
  }

  public static void showSoftKeyboard(Context context, View view) {
    InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
  }

  /**
   * Attach a listener that hide keyboard to all View and nested View except EditText
   * It allows the soft keyboard to get hidden automatically when EditText is not focus
   *
   * @param view     Current view
   * @param activity Current activity
   */
  public static void hideKeyboardListener(View view, final Activity activity) {
    // Set up touch listener for non-text box views to hide keyboard.
    if (!(view instanceof EditText)) {
      view.setOnTouchListener(new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
          hideSoftKeyboard(activity);
          return false;
        }
      });
    }

    //If a layout container, iterate over children and seed recursion.
    if (view instanceof ViewGroup) {
      for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
        View innerView = ((ViewGroup) view).getChildAt(i);
        hideKeyboardListener(innerView, activity);
      }
    }
  }

  /**
   * Check if keyboard is display.
   * We compare the root view height with the current height.
   * If the difference is higher than 200 we assume the keyboard is up.
   *
   * @param v Current view
   */
  public static boolean keyboardIsDisplay(View v) {
    int heightDiff = v.getRootView().getHeight() - v.getHeight();
    return heightDiff > ConvertHelpers.dpToPx(v.getContext(), 200); // if more than 200 dp, keyboard is up
  }
}
