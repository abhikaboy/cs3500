package src.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for facilitating and enforcing the formatting of dates, either in string format or
 * in the Java.util "Date" format.
 * Convert a date to a string in the format YYYY-MM-DD.
 * Convert a string in the format YYYY-MM-DD to a date.
 */
public class DateFormat {
  /**
   * Convert a string in the format YYYY-MM-DD to a date.
   *
   * @param date the string version of a date to turn into a Date.
   * @return a converted date object based on the string.
   */
  public static Date toDate(String date) {
    String[] dateParts = date.split("-");
    return new Date(Integer.parseInt(dateParts[0]) - 1900,
            Integer.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[2]));
  }

  /**
   * Convert a date to a string in the format YYYY-MM-DD.
   *
   * @param date Date representation to transform into a string.
   * @return a string representation of the date.
   */
  public static String toString(Date date) {
    String dateString = "";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    dateString = formatter.format(date);
    return dateString;
  }
}
