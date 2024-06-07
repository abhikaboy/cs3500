package src.model;

import java.util.HashMap;

public class Portfolio {

  // Symbol, Stock
  HashMap<String, Stock> stocks;

  public Portfolio() {
    stocks = new HashMap<>();
  }

  public Portfolio(Portfolio portfolio) {
    stocks = new HashMap<>();
    for (String symbol : portfolio.stocks.keySet()) {
      stocks.put(symbol, 
      new Stock(symbol, 
      portfolio.stocks.get(symbol).getQuantity(), 
      portfolio.stocks.get(symbol).getData()));
    }
  }


  public int getStockQuantity(String symbol) {
    if(stocks.containsKey(symbol)) {
      return stocks.get(symbol).getQuantity();
    } else {
      throw new IllegalArgumentException("Stock not found in portfolio");
    }
  }

  public void buyStock(String symbol, HashMap<String, StockRow> stock, int quantity) {
    System.out.println("Buying " + quantity + " shares of " + symbol);
    System.out.println(this.portfolioAString());
    if(stocks.containsKey(symbol)) {
      System.out.println("Stock already in portfolio");
      stocks.get(symbol).purchase(quantity);
    } else {
      System.out.println("Adding stock to portfolio");
      stocks.put(symbol, new Stock(symbol, quantity, stock));
    }
  }

  public void sellStock(String symbol, int quantity) {
    if(stocks.containsKey(symbol)) {
      stocks.get(symbol).sell(quantity);
      if(stocks.get(symbol).getQuantity() == 0) {
        stocks.remove(symbol);
      }
    } else {
      throw new IllegalArgumentException("Stock not found in portfolio");
    }
  }

  public double getPortfolioValue() {
    double total = 0;
    for (String symbol : stocks.keySet()) {
      HashMap<String, StockRow> stock = stocks.get(symbol).getData();
      StockRow lastRow = null;
      // todays date: 
      String date = "2024-06-06";
      lastRow = stock.get(date);
      total += lastRow.getClose() * stocks.get(symbol).getQuantity();
    }
    return total;
  }
  public double getPortfolioValue(String date) {
    double total = 0;
    for (String symbol : stocks.keySet()) {
      HashMap<String, StockRow> stock = stocks.get(symbol).getData();
      StockRow lastRow = stock.get(date);
      // todays date: 
      total += lastRow.getClose() * stocks.get(symbol).getQuantity();
    }
    return total;
  }

  /**
   * Get the size of this portfolio by counting the size of the HashMap. Essentially, returns
   * the total amount of stocks present.
   *
   * @return  int. The total amount of stocks in this portfolio.
   */
  public int getPortfolioSize() {
    return stocks.size();
  }

  /**
   * Get the names of all the stocks.
   * @return the names of all the stocks
   */
  public String[] getStockNames() {
    return stocks.keySet().toArray(new String[0]);
  }

  public String portfolioAString() {
    String output = "";
    for (String symbol : stocks.keySet()) {
      output += symbol + " " + stocks.get(symbol).getQuantity() + "\n";
    }
    return output;
  }

  /**
   * Returns the stock given the symbol.
   * @param symbol  Symbol representing a stock.
   * @return Stock with the corresponding inputted symbol.
   */
  public Stock getStock(String symbol) {
    return stocks.get(symbol);
  }

}
