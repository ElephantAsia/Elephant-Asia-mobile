package fr.elephantasia.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Cette classe a pour fonction de gérer les préférences de l'application.
 */
public class Preferences {

  private final static String LAST_SYNC = "LAST_SYNC";

  /**
   * Retourne l'instance par défaut des préférences partagées.
   *
   * @param context Le contexte
   * @return L'instance par défaut
   */
  @NonNull
  private static SharedPreferences getSharedPreferences(
      @NonNull Context context
  ) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  public static String GetLastSync(Context context) {
    SharedPreferences prefs = getSharedPreferences(context);
    try {
      return prefs.getString(LAST_SYNC, "1970-01-01T00:00:00.000Z");
    } catch (Exception e) {
      SetLastSync(context, "1970-01-01T00:00:00.000Z");
      return null;
    }
  }

  public static void SetLastSync(Context context, String value) {
    SharedPreferences prefs = getSharedPreferences(context);

    if (value == null) {
      prefs.edit().remove(LAST_SYNC).apply();
    } else {
      prefs.edit().putString(LAST_SYNC, value).apply();
    }
  }

}
