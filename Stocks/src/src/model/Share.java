package src.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import src.helper.DateFormat;

/**
 * Class that represents a share in the Stock Portfolio Manager.
 */
public class Share {

  private String symbol;
  private int quantity;
  private HashMap<String, StockRow> data;
  private String date;
  private HashMap<String, Integer> history;

  /**
   * Constructor for a new stock.
   *
   * @param symbol   Symbol representing the stock.
   * @param quantity Quantity of the stock.
   * @param data     Stock data for the stock.
   */
  public Share(String symbol, int quantity, HashMap<String, StockRow> data) {
    this.symbol = symbol;
    this.quantity = quantity;
    this.data = data;
    Date today = new Date();
    this.date = DateFormat.toString(today); // todays date
    this.history = new HashMap<>();
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


  public void purchase(int quantity, String date) {
    this.quantity += quantity;
    this.date = date; 
    this.history.put(this.date, this.quantity);
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
    this.history.put(this.date, this.quantity);
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
    return -1;
  }

  /**
   * Get the total value of the shares in this stock based on the inputted date.
   * @param date the date to observe the share values.
   * @return the total value of shares owned. Share closing price * share quantity.
   */
  public double getValueOnDate(String date) {
    return getPriceOnDate(date) * quantity;
  }

  /**
   * Updates the number of shares by adding the given quantity. Quantity could be negative.
   * @param quantity number of shares to add or remove from the current quantity. Action depends
   *                 on the sign of the argument.
   */
  public void updateQuantity(int quantity) {
    this.quantity += quantity;
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
