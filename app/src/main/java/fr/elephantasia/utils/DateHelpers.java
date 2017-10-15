package fr.elephantasia.utils;

import java.util.Date;

public class DateHelpers {

  /**
   *
   * @return the timestamp value one week ago
   */
  static public Date getLastWeek() {
    Date now = new Date();
    return new Date(now.getTime() - 604800000L); // 7 * 24 * 60 * 60 * 1000
  }

}
