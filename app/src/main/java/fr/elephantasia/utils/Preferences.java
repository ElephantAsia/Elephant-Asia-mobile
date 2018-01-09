package fr.elephantasia.utils;

/**
 * Created by Stephane on 05/01/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Cette classe a pour fonction de gérer les préférences de l'application.
 */
public class Preferences {


  /**
   * Identifiant pour le nom d'utilisateur
   */
  // private final static String USERNAME = "USERNAME";

  /**
   * Identifiant pour le mot de passe
   */
  // private final static String PASSWORD = "PASSWORD";


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


  /**
   * Retourne le nom d'utilisateur enregistré dans les préférences.
   *
   * @param context Le contexte
   * @return La valeur ou NULL
   */
  /* @Nullable
  public static String getUsername(
      @NonNull Context context
  ) {
    SharedPreferences prefs = getSharedPreferences(context);
    try {
      return prefs.getString(USERNAME, null);
    } catch (Exception e) {
      setUsername(context, null);
      return null;
    }
  } */

  /**
   * Définit le nom d'utilisateur enregistré dans les préférences.
   *
   * @param context Le contexte
   * @param value   La valeur ou NULL
   */
  /* public static void setUsername(
      @NonNull Context context,
      @Nullable String value
  ) {
    SharedPreferences prefs = getSharedPreferences(context);
    if (value == null) {
      prefs.edit().remove(USERNAME).apply();
    } else {
      prefs.edit().putString(USERNAME, value).apply();
    }
  } */

  /**
   * Retourne le mot de passe enregistré dans les préférences.
   *
   * @param context Le contexte
   * @return La valeur ou NULL
   */
  /* @Nullable
  public static String getPassword(
      @NonNull Context context
  ) {
    SharedPreferences prefs = getSharedPreferences(context);
    try {
      return prefs.getString(PASSWORD, null);
    } catch (Exception e) {
      setPassword(context, null);
      return null;
    }
  } */

  /**
   * Définit le mot de passe enregistré dans les préférences.
   *
   * @param context Le contexte
   * @param value   La valeur ou NULL
   */
  /* public static void setPassword(
      @NonNull Context context,
      @Nullable String value
  ) {
    SharedPreferences prefs = getSharedPreferences(context);
    if (value == null) {
      prefs.edit().remove(PASSWORD).apply();
    } else {
      prefs.edit().putString(PASSWORD, value).apply();
    }
  } */

  /**
   * Vérifie que l'utilisateur se soit enregistré.
   *
   * @param context Le context
   * @return TRUE si l'utilisateur est enregistré
   */
  /* public static boolean hasSignin(
      @NonNull Context context
  ) {
    return getUsername(context) != null && getPassword(context) != null;
  } */

}
