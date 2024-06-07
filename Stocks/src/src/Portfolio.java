package src;

import java.util.HashMap;

public class Portfolio {
  HashMap<String, HashMap<String, StockRow>> stocks;

  public Portfolio() {
    stocks = new HashMap<>();
  }

  public void addStock(String symbol, HashMap<String, StockRow> stock) {
    if(stocks.containsKey(symbol)) {
      return;
    }
    stocks.put(symbol, stock);
  }

  public void removeStock(String symbol, HashMap<String, StockRow> stock) {
    if(stocks.containsKey(symbol)) {
      stocks.remove(symbol);
    }
  }

  public double getPortfolioValue() {
    double total = 0;
    for (String symbol : stocks.keySet()) {
      HashMap<String, StockRow> stock = stocks.get(symbol);
      StockRow lastRow = null;
      // todays date: 
      String date = "2024-06-06";
      lastRow = stock.get(date);
      total += lastRow.getClose();
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
