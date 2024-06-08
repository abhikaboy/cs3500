package src.model;

import java.util.Date;

/**
 * Interface for the Model component of this software that follows MVC architecture.
 */
public interface StockModelInterface {

  public void addStockToPortfolio(String ticker, Portfolio portfolio, int quantity);

  public void sellStockFromPortfolio(String ticker, Portfolio portfolio, int quantity);

  public double getStockMovingAverage(String ticker, Date startDate, int x);

  public double getStockChange(String ticker, String startDate, String endDate);

  public Portfolio getPortfolio(String name);

  public String[] getPortfolioNames();

  public void addPortfolio(String name);
}
