package src.model;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class that represents a stock in the Stock Portfolio Manager.
 */
public class Stock {

  private String symbol;
  private int quantity;
  private HashMap<String, StockRow> data;

  /**
   * Constructor for a new stock.
   *
   * @param symbol   Symbol representing the stock.
   * @param quantity Quantity of the stock.
   * @param data     Stock data for the stock.
   */
  public Stock(String symbol, int quantity, HashMap<String, StockRow> data) {
    this.symbol = symbol;
    this.quantity = quantity;
    this.data = data;
  }

  /**
   * Purchase a quantity of the stock.
   *
   * @param quantity Quantity to purchase.
   */

  public void purchase(int quantity) {
    this.quantity += quantity;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Stock stock = (Stock) o;
    return quantity == stock.quantity &&
            symbol.equals(stock.symbol) &&
            data.equals(stock.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(symbol, quantity, data);
  }

}
