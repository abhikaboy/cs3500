package src;

import java.util.HashMap;

public class Stock {

  private String symbol;
  private int quantity;
  private HashMap<String, StockRow> data;

  public Stock(String symbol, int quantity, HashMap<String, StockRow> data) {
    this.symbol = symbol;
    this.quantity = quantity;
    this.data = data;
  }

  public void purchase(int quantity) {
    this.quantity += quantity;
  }

  public void sell(int quantity) {
    if(this.quantity < quantity) {
      throw new IllegalArgumentException("Cannot sell more than you have");
    }
    this.quantity -= quantity;
  }

  public HashMap<String, StockRow> getData() {
    return data;
  }

  public int getQuantity() {
    return quantity;
  }
  
}
