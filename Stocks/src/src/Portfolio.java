package src;

import java.util.HashMap;

public class Portfolio {

  // Symbol, Stock
  HashMap<String, Stock> stocks;

  public Portfolio() {
    stocks = new HashMap<>();
  }

  public void buyStock(String symbol, HashMap<String, StockRow> stock, int quantity) {
    if(stocks.containsKey(symbol)) {
      stocks.get(symbol).purchase(quantity);
    } else {
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

  /**
   * Get the size of this portfolio by counting the size of the HashMap. Essentially, returns
   * the total amount of stocks present.
   *
   * @return  int. The total amount of stocks in this portfolio.
   */
  public int getPortfolioSize() {
    return stocks.size();
  }

}
