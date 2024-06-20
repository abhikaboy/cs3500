package src.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Interface for the Model component of the Stock Portfolio Manager, adhering to the
 * MVC architecture.
 * This interface defines the methods that must be implemented by any class that serves as the
 * model in the Stock Portfolio Manager application. The methods provide functionality for
 * managing stock portfolios, including adding and selling stocks, calculating stock metrics,
 * and retrieving portfolio information.
 */
public interface StockModelInterface {

  void addStockToPortfolio(String ticker, Portfolio portfolio, int quantity);

  void addStockToPortfolio(String symbol, Portfolio portfolio, int shares, String date);

  void sellStockFromPortfolio(String ticker, Portfolio portfolio, int quantity);

  void sellStockFromPortfolio(String ticker, Portfolio portfolio, int quantity, String date);

  double getStockMovingAverage(String ticker, Date startDate, int x);

  double getStockChange(String ticker, String startDate, String endDate);

  Portfolio getPortfolio(String name);

  String[] getPortfolioNames();

  void addPortfolio(String name);

  void queryStock(String symbol);

  Map<String, StockRow> loadLocalStock(String stockSymbol);

  Map<String, StockRow> getStock(String symbol);

  void writePortfolioToFile(Portfolio portfolio);

  void readPortfolioFromFile(String name, String fileContent);

  String graphPortfolio(Portfolio portfolio, String startDate, String endDate);

  StockRow findClosestRecordedDate(Map<String, StockRow> stock, String date);

  ArrayList<String> xDayCrossoverDays(String symbol, int x, String startDate,
                                      String endDate);

  boolean isValidTicker(String ticker);

  double getPortfolioValue(Portfolio portfolio, String date);

  int countPortfolios();
}
