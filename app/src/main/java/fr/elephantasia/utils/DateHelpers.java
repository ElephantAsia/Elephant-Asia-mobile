package fr.elephantasia.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;

public class DateHelpers {

  /**
   *
   * @return the timestamp value one week ago
   */
  static public Date getLastWeek() {
    Date now = new Date();
    return new Date(now.getTime() - 604800000L); // 7 * 24 * 60 * 60 * 1000
  }

  static public long getNbYearsDiff(long x, long y) {
    return (y - x) / (1000L * 60L * 60L * 24L * 365L);
  }

  static public String GetCurrentStringDate() {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    return df.format(Calendar.getInstance().getTime());
  }

  @Nullable
  static public String FriendlyUserStringDate(String stringDate) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      Date date = format.parse(stringDate);

      SimpleDateFormat displayedFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      return displayedFormat.format(date);
    } catch ( Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
