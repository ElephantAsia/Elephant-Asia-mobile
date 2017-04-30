package fr.elephantasia.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by seb on 30/04/2017.
 */

public class ConvertHelpers {

  /**
   * Convert dp to pixel
   *
   * @param ctx       Current context
   * @param valueInDp the dp value in float
   */
  public static float dpToPx(Context ctx, float valueInDp) {
    DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
  }
}
