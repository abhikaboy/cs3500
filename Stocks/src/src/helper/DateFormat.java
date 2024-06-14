package src.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
  Convert a date to a string in the format YYYY-MM-DD
  Convert a string in the format YYYY-MM-DD to a date
*/  
public class DateFormat {
  /**
   * Convert a string in the format YYYY-MM-DD to a date.
   * @param date
   * @return a convertef date object based on the string.
   */
  public static Date toDate(String date) {
    String[] dateParts = date.split("-");
    return new Date(Integer.parseInt(dateParts[0]) - 1900,
            Integer.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[2]));
  }

  /**
   * Convert a date to a string in the format YYYY-MM-DD
   * @param date
   * @return a string representation of the date. 
   */
  public static String toString(Date date) {
    String dateString = "";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    dateString = formatter.format(date);
    return dateString;
  } 
}
