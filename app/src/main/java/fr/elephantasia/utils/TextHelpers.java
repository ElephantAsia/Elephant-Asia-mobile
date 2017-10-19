package fr.elephantasia.utils;

import java.util.Date;

public class TextHelpers {

  /**
   *
   * @param str
   * @return a string with every word capitalize
   */
  static public String capitalize(String str) {
    String[] strArray = str.split(" ");

    StringBuilder builder = new StringBuilder();
    for (String s : strArray) {
      if (s.length() > 0) {
        String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
        builder.append(cap).append(" ");
      }
    }
    return builder.toString();
  }

}
