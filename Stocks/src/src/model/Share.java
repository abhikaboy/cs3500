package src.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import src.helper.DateFormat;

/**
 * Class that represents a share in the Stock Portfolio Manager.
 */
public class Share {

  private String symbol;
  private int quantity;
  private Map<String, StockRow> data;
  private String date;
  private Map<String, Integer> history;

  /**
   * Constructor for a new stock.
   *
   * @param symbol   Symbol representing the stock.
   * @param quantity Quantity of the stock.
   * @param data     Stock data for the stock.
   */
  public Share(String symbol, int quantity, Map<String, StockRow> data) {
    this.symbol = symbol;
    this.quantity = quantity;
    this.data = data;
    Date today = new Date();
    this.date = DateFormat.toString(today); // todays date
    this.history = new HashMap<>();
    this.history.put(this.date, this.quantity);
    System.out.println("Created new share for " + symbol + " with quantity " + quantity + " without the date");
  }
  /**
   * Constructor for a new stock.
   *
   * @param symbol   Symbol representing the stock.
   * @param quantity Quantity of the stock.
   * @param data     Stock data for the stock.
   */
  public Share(String symbol, int quantity, HashMap<String, StockRow> data, String date) {
    this.symbol = symbol;
    this.quantity = quantity;
    this.data = data;
    this.date = date;
    this.history = new HashMap<>();
    this.history.put(this.date, this.quantity);
  }

  /**
   * Purchase a quantity of the stock.
   *
   * @param quantity Quantity to purchase.
   */

  public void purchase(int quantity) {
    this.quantity += quantity;
    this.history.put(this.date, this.quantity);
  }


  public void purchase(int amount, String date) {
    this.quantity += amount;
    history.put(date, this.quantity); // Record the new quantity on the specified date
  }

  /**
   * Sell a quantity of the stock.
   *
   * @param quantity Quantity to sell.
   */
  public void sell(int quantity) {
    if (this.quantity < quantity) {
      throw new IllegalArgumentException("Cannot sell more than you have");
    }
    this.quantity -= quantity;
    this.history.put(this.date, this.quantity);
  }
  /**
   * Sell a quantity of the stock.
   *
   * @param quantity Quantity to sell.
   */
  public void sell(int quantity, String date) {
    if (this.quantity < quantity) {
      throw new IllegalArgumentException("Cannot sell more than you have");
    }
    this.quantity -= quantity;
    this.history.put(date, this.quantity);
  }

  /**
   * Get the data copy for the stock.
   *
   * @return The data for the stock.
   */
  public HashMap<String, StockRow> getData() {
    // return a copy of the data
    return new HashMap<>(data);
  }

  /**
   * Get the quantity of the stock.
   *
   * @return The quantity of the stock.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Get the closing price of this share's stock on the specified date.
   * @param date the date you want to find the closing price of.
   * @return double, value of a single share at the end of the specified date.
   */
  public double getPriceOnDate(String date) {
    StockRow row = data.get(date);
    if (row != null) {
      return row.getClose();
    }

    // If exact date is not found, find the closest date
    TreeMap<String, StockRow> sortedData = new TreeMap<>(data);
    Map.Entry<String, StockRow> closestEntry = sortedData.floorEntry(date);
    if (closestEntry == null) {
      closestEntry = sortedData.ceilingEntry(date);
    }
    if (closestEntry != null) {
      return closestEntry.getValue().getClose();
    }

    System.out.println("No data found for " + date);
    return 0;
  }

  /**
   * Get the total value of the shares in this stock based on the inputted date.
   * @param date the date to observe the share values.
   * @return the total value of shares owned. Share closing price * share quantity.
   */
  public double getValueOnDate(String date) {
    double price = getPriceOnDate(date);
    if (price == 0) {
      return 0; // Indicate that no data is available
    }
    int quantityOnDate = getQuantityOnDate(date);
    return price * quantityOnDate;
  }

  /**
   * Updates the number of shares by adding the given quantity. Quantity could be negative.
   * @param quantity number of shares to add or remove from the current quantity. Action depends
   *                 on the sign of the argument.
   * @param date the date that which this update takes place.
   */
  public void updateQuantity(int quantity, String date) {
    this.quantity += quantity;
    this.date = date;
    this.history.put(this.date, this.quantity);
  }

  public int getQuantityOnDate(String date) {
    Date targetDate = DateFormat.toDate(date);
    int closestQuantity = 0;
    Date closestDate = null;

    System.out.println("Date history for " + this.symbol + " is " + history.size());

    printMap(history);

    // loop over all the keys in the map
    for (String key : history.keySet()) {
      Date historyDate = DateFormat.toDate(key);
      System.out.println("Comparing " + historyDate + " to " + targetDate);
      if (historyDate.before(targetDate)) {
        if (closestDate == null || historyDate.after(closestDate)) {
          System.out.println("Found closest date " + historyDate);
          closestDate = historyDate;
          closestQuantity = history.get(key);
        }
      }
    }

    System.out.println("Closest quantity for " + date + " is " + closestQuantity);
    return closestQuantity;
  }

  public HashMap<String, Integer> getHistory() {
    printMap(history);
    printMap(new HashMap<>(history));
    return new HashMap<>(history);
  }

  private void printMap(HashMap<String, Integer> map) {
    System.out.println("======================================================");
    System.out.println("Map has " + map.size() + " entries");
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }
        System.out.println("======================================================");

  }

  public void addToHistory(String date, int quantity) {
    history.put(date, quantity);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Share stock = (Share) o;
    return quantity == stock.quantity &&
            symbol.equals(stock.symbol) &&
            data.equals(stock.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(symbol, quantity, data);
  }

  public boolean boughtBefore(Date date){
    return DateFormat.toDate(this.date).before(date);
  }
}
