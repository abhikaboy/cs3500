package src.model;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import src.helper.DateFormat;

/**
 * A class that represents a portfolio of stocks.
 */
public class Portfolio {

  // Symbol, Share
  private HashMap<String, Share> shares;
  private String name;
  private ArrayList<String> history;

  /**
   * Constructor for a new portfolio.
   */
  public Portfolio(String name) {
    shares = new HashMap<>();
    this.name = name;
    history = new ArrayList<>();
  }

  /**
   * Constructor for a new portfolio.
   *
   * @param portfolio Portfolio to copy.
   */
  public Portfolio(String name, Portfolio portfolio) {
    this.name = name;
    shares = new HashMap<>();
    for (String symbol : portfolio.shares.keySet()) {
      shares.put(symbol,
              new Share(symbol,
                      portfolio.shares.get(symbol).getQuantity(),
                      portfolio.shares.get(symbol).getData()));
    }
    history = new ArrayList<>(portfolio.history);
  }

  public String getName() {
    return name;
  }

  /**
   * Get the quantity of a stock in the portfolio.
   *
   * @param symbol Symbol representing a stock.
   * @return The quantity of the stock in the portfolio.
   */
  public int getStockQuantity(String symbol) {
    if (shares.containsKey(symbol)) {
      return shares.get(symbol).getQuantity();
    } else {
      throw new IllegalArgumentException("Stock not found in portfolio");
    }
  }

  /**
   * Buy a stock in the portfolio.
   *
   * @param symbol   Symbol representing a stock.
   * @param stock    Stock data for the stock.
   * @param quantity Quantity of the stock to buy.
   */
  public void buyStock(String symbol, HashMap<String, StockRow> stock, int quantity) {
    if (shares.containsKey(symbol)) {
      shares.get(symbol).purchase(quantity);
    } else {
      shares.put(symbol, new Share(symbol, quantity, stock));
    }
    history.add("BUY;" + symbol + ";" + quantity + ";" + DateFormat.toString(new Date()));
  }

  public void buyStock(String symbol, HashMap<String, StockRow> stock, int quantity, String date) {
    if (shares.containsKey(symbol)) {
      shares.get(symbol).purchase(quantity, date);
    } else {
      shares.put(symbol, new Share(symbol, quantity, stock, date));
    } 
    history.add("BUY;" + symbol + ";" + quantity + ";" + date);
  }

  /**
   * Sell a stock in the portfolio.
   *
   * @param symbol   Symbol representing a stock.
   * @param quantity Quantity of the stock to sell.
   */
  public void sellStock(String symbol, int quantity) {
    if (shares.containsKey(symbol)) {
      shares.get(symbol).sell(quantity);
      if (shares.get(symbol).getQuantity() == 0) {
        shares.remove(symbol);
      }
    } else {
      throw new IllegalArgumentException("Stock not found in portfolio");
    }
    history.add("SELL;" + symbol + ";" + quantity + ";" + DateFormat.toString(new Date()));
  }
  /**
   * Sell a stock in the portfolio on a specific date.
   *
   * @param symbol   Symbol representing a stock.
   * @param quantity Quantity of the stock to sell.
   */
  public void sellStock(String symbol, int quantity, String date) {
    if (shares.containsKey(symbol)) {
      shares.get(symbol).sell(quantity, date);
      if (shares.get(symbol).getQuantity() == 0) {
        shares.remove(symbol);
      }
    } else {
      throw new IllegalArgumentException("Stock not found in portfolio");
    }
    history.add("SELL;" + symbol + ";" + quantity + ";" + date);
  }

  /**
   * Get the value of the portfolio.
   *
   * @return The value of the portfolio.
   */

  public double getPortfolioValue() {
    double total = 0;
    for (String symbol : shares.keySet()) {
      HashMap<String, StockRow> stock = shares.get(symbol).getData();
      StockRow lastRow = null;
      // todays date: 
      String date = "2024-06-06";
      lastRow = stock.get(date);
      total += lastRow.getClose() * shares.get(symbol).getQuantity();
    }
    return total;
  }

  /**
   * Get the value of the portfolio for a given date.
   *
   * @param date Date to get the value for.
   * @return The value of the portfolio for the given date.
   */
  public double getPortfolioValue(String date) {
    double total = 0;
    for (String symbol : shares.keySet()) {
      HashMap<String, StockRow> stock = shares.get(symbol).getData();
      StockRow lastRow = stock.get(date);
      // todays date: 
      total += lastRow.getClose() * shares.get(symbol).getQuantity();
    }
    return total;
  }

  /**
   * Get the size of this portfolio by counting the size of the HashMap. Essentially, returns
   * the total amount of shares present.
   *
   * @return int. The total amount of shares in this portfolio.
   */
  public int getPortfolioSize() {
    return shares.size();
  }

  /**
   * Get the names of all the shares.
   *
   * @return the names of all the shares
   */
  public String[] getStockNames() {
    return shares.keySet().toArray(new String[0]);
  }

  /**
   * The string representation of the portfolio.
   *
   * @return A string representation of the portfolio.
   * @see Portfolio#portfolioAString()
   */
  public String portfolioAString() {
    String output = "";
    for (String symbol : shares.keySet()) {
      output += symbol + " " + shares.get(symbol).getQuantity() + "\n";
    }
    return output;
  }

  /**
   * Returns the Shares given the symbol.
   *
   * @param symbol Symbol representing a stock.
   * @return Stock with the corresponding inputted symbol.
   */
  public Share getShare(String symbol) {
    return shares.get(symbol);
  }

  public void writeHistoryToFile(String filename) {
    try {
      FileWriter fw = new FileWriter(filename);
      for (String line : history) {
        fw.write(line + "\n");
      }
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
