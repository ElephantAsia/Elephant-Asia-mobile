package fr.elephantasia.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Cette classe a pour fonction de gérer les préférences de l'application.
 */
public class Preferences {

  private final static String LAST_DL_SYNC = "LAST_DL_SYNC";
  private final static String LAST_UP_SYNC = "LAST_UP_SYNC";

  private final static String DEFAULT_DATE = "1970-01-01T00:00:00.000Z";

  /**
   * Retourne l'instance par défaut des préférences partagées.
   *
   * @param context Le contexte
   * @return L'instance par défaut
   */
  @NonNull
  private static SharedPreferences GetSharedPreferences(
      @NonNull Context context
  ) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  public static String GetLastDownloadSync(Context context) {
    SharedPreferences prefs = GetSharedPreferences(context);

    try {
      return prefs.getString(LAST_DL_SYNC, DEFAULT_DATE);
    } catch (Exception e) {
      SetLastDownloadSync(context, DEFAULT_DATE);
      return null;
    }
  }

  public static void SetLastDownloadSync(Context context, String value) {
    SharedPreferences prefs = GetSharedPreferences(context);

    if (value == null) {
      prefs.edit().remove(LAST_DL_SYNC).apply();
    } else {
      prefs.edit().putString(LAST_DL_SYNC, value).apply();
    }
  }

  public static String GetLastUploadSync(Context context) {
    SharedPreferences prefs = GetSharedPreferences(context);

    try {
      return prefs.getString(LAST_UP_SYNC, DEFAULT_DATE);
    } catch (Exception e) {
      SetLastUploadSync(context, null);
      return null;
    }
  }

  public static void SetLastUploadSync(Context context, String value) {
    SharedPreferences prefs = GetSharedPreferences(context);

    if (value == null) {
      prefs.edit().remove(LAST_UP_SYNC).apply();
    } else {
      prefs.edit().putString(LAST_UP_SYNC, value).apply();
    }
  }

}
