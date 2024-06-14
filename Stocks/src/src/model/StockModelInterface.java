package src.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Interface for the Model component of the Stock Portfolio Manager, adhering to the
 * MVC architecture.
 * This interface defines the methods that must be implemented by any class that serves as the
 * model in the Stock Portfolio Manager application. The methods provide functionality for
 * managing stock portfolios, including adding and selling stocks, calculating stock metrics,
 * and retrieving portfolio information.
 */
public interface StockModelInterface {

  public void addStockToPortfolio(String ticker, Portfolio portfolio, int quantity);

  public void addStockToPortfolio(String symbol, Portfolio portfolio, int shares, String date);

  public void sellStockFromPortfolio(String ticker, Portfolio portfolio, int quantity);

  public void sellStockFromPortfolio(String ticker, Portfolio portfolio, int quantity, String date);

  public double getStockMovingAverage(String ticker, Date startDate, int x);

  public double getStockChange(String ticker, String startDate, String endDate);

  public Portfolio getPortfolio(String name);

  public String[] getPortfolioNames();

  public void addPortfolio(String name);

  public void queryStock(String symbol);

  public HashMap<String, StockRow> loadLocalStock(String stockSymbol);

  public HashMap<String, StockRow> getStock(String symbol);

  public void writePortfolioToFile(Portfolio portfolio);

  public void readPortfolioFromFile(String name, String fileContent);

  public String graphPortfolio(Portfolio portfolio, String startDate, String endDate);

  public StockRow findClosestRecordedDate(HashMap<String, StockRow> stock, String date);

  public ArrayList<String> xDayCrossoverDays(String symbol, int x, String startDate,
                                             String endDate);

  public boolean isValidTicker(String ticker);
}
