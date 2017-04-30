package fr.elephantasia.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import fr.elephantasia.R;


/**
 * Created by Stephane on 31/10/2016.
 */

public class StaticTools {

  /**
   * Copie un flux de données entrant vers un flux de données sortant.
   *
   * @param input  Flux entrant
   * @param output Flux sortant
   * @return TRUE si la copie a réussi
   */
  public static boolean copyStreamToStream(
      @NonNull InputStream input,
      @NonNull OutputStream output
  ) {
    boolean result = false;

    try {
      byte[] buffer = new byte[1024];
      int bytesRead;

      while ((bytesRead = input.read(buffer)) > 0) {
        output.write(buffer, 0, bytesRead);
      }
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      input.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      output.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (result);
  }

  /**
   * Convertit un flux de données entrant en un tableau d'octet.
   *
   * @param input Flux entrant
   * @return Un tableau de byte
   */
  @NonNull
  public static byte[] getByteArrayFromStream(
      @NonNull InputStream input
  ) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    copyStreamToStream(input, output);
    return (output.toByteArray());
  }

  /**
   * Converti un flux de données entrant en un texte encodé avec charset.
   *
   * @param input   Flux entrant
   * @param charset Le charset du texte
   * @return La chaîne de caractères ou NULL si échec
   */
  @Nullable
  public static String getStringFromStream(
      @NonNull InputStream input,
      @NonNull String charset
  ) {
    try {
      return (new String(getByteArrayFromStream(input), charset));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return (null);
    }
  }

  public static boolean copyStreamToFile(InputStream input, File target) {
    try {
      target.getParentFile().mkdirs();
      FileOutputStream output = new FileOutputStream(target);
      return copyStreamToStream(input, output);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Check if the the textview is empty
   *
   * @param ctx  Current context
   * @param view Le Current TextView
   */
  public static void checkEmptyField(Context ctx, TextView view) {
    if (view.getText().toString().trim().length() == 0) {
      view.setError(view.getResources().getString(R.string.empty_field));
    } else {
      view.setError(null);
    }
  }

}
