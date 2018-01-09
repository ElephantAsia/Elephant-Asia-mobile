package fr.elephantasia.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

  static public String UrlEncoder(Map<String, String> values) throws UnsupportedEncodingException {
    StringBuilder builder = new StringBuilder();

    for (HashMap.Entry<String, String> entry : values.entrySet()) {
      if (builder.length() > 0) builder.append('&');
      builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      builder.append('=');
      builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    }
    return builder.toString();
  }

  static public boolean IsEmpty(String s) {
  	return s == null || s.trim().length() == 0 || s.length() == 0;
	}

}
