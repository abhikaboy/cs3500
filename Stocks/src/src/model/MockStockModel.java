package src.model;

import java.util.Date;
import java.util.HashMap;

/**
 * Mock class for testing use of the Model class.
 */
public class MockStockModel extends StockModel {
  private HashMap<String, Boolean> tickerValidity;

  public MockStockModel() {
    tickerValidity = new HashMap<>();
  }

  public void setTickerValidity(String ticker, boolean isValid) {
    tickerValidity.put(ticker, isValid);
  }

  @Override
  public boolean isValidTicker(String ticker) {
    return tickerValidity.getOrDefault(ticker, false);
  }

  @Override
  public void addPortfolio(String name) {
    // Mock implementation
  }

  @Override
  public Portfolio getPortfolio(String name) {
    return new Portfolio(); // Return a new portfolio for simplicity
  }

  @Override
  public void addStockToPortfolio(String ticker, Portfolio portfolio, int quantity) {
    portfolio.buyStock(ticker, new HashMap<>(), quantity);
  }

  @Override
  public void sellStockFromPortfolio(String ticker, Portfolio portfolio, int quantity) {
    portfolio.sellStock(ticker, quantity);
  }

  @Override
  public int countPortfolios() {
    return 1; // Assume there's always one portfolio for simplicity
  }

  @Override
  public String[] getPortfolioNames() {
    return new String[]{"Default"};
  }

  @Override
  public double getStockMovingAverage(String ticker, Date date, int days) {
    return 0.0; // Mock implementation
  }

  @Override
  public double getStockChange(String ticker, String startDate, String endDate) {
    return 0.0; // Mock implementation
  }
}
