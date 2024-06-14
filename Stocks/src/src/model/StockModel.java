package src.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.sound.sampled.Port;

import src.helper.DateFormat;

/**
 * Class representing the Model aspect of the Stock Portfolio Manager's MVC architecture.
 * This class manages stock portfolios and handles stock data queries, loading, and caching.
 * It provides functionality to add and remove stocks from portfolios, calculate portfolio
 * values, track portfolio history, and perform various stock data calculations such as
 * moving averages and crossover days.
 *
 * The StockModel class works closely with the Portfolio and Share classes to maintain the
 * integrity of stock data and portfolio transactions. It also uses a cache to store stock
 * data locally for performance optimization.
 */
public class StockModel implements StockModelInterface {
  private Map<String, Portfolio> portfolios;
  private Map<String, HashMap<String, StockRow>> stocksCache;

  /**
   * Writes the stock data to a file in the stocks directory.
   *
   * @param output      all the stock data
   * @param stockSymbol the stock symbol
   */
  public void writeStockToFile(String output, String stockSymbol) {
    String[] rows = output.split("\n");
    File file;
    FileWriter writer = null;
    try {
      file = new File("./stocks/" + stockSymbol + ".csv");
      if (!file.exists()) {
        file.createNewFile();
      }
      writer = new FileWriter(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    stocksCache.put(stockSymbol, new HashMap<>());
    for (String row : rows) {
      String[] items = row.split(",");
      if (!items[1].equals("open")) {

        if (writer != null) {
          try {
            writer.write(row + "\n");
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        StockRow stockRow = new StockRow(Double.parseDouble(items[1]),
                Double.parseDouble(items[2]),
                Double.parseDouble(items[3]),
                Double.parseDouble(items[4]));
        stocksCache.get(stockSymbol).put(items[0], stockRow);
      }
    }

  }

  /**
   * Count the number of portfolios.
   *
   * @return the number of portfolios
   */
  public int countPortfolios() {
    return portfolios.size();
  }

  /**
   * Get the names of all the portfolios.
   *
   * @return the names of all the portfolios
   */
  public String[] getPortfolioNames() {
    return portfolios.keySet().toArray(new String[0]);
  }


  /**
   * Query the stock data from the API.
   *
   * @param symbol the stock symbol
   */
  public void queryStock(String symbol) {
    String apiKey = "W0M1JOKC82EZEQA8";
    String stockSymbol = symbol; //ticker symbol for Google
    // replace 
    URL url = null;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    System.out.println("Queried Stock : " + stockSymbol);
    writeStockToFile(output.toString(), stockSymbol);
  }

  /**
   * Load a given stock from the local directory.
   *
   * @param stockSymbol the stock symbol
   * @return the stock data
   */
  public HashMap<String, StockRow> loadLocalStock(String stockSymbol) {
    // checks the local directory for the stock csv 
    // file and loads it into the cache
    File file = new File("./stocks/" + stockSymbol + ".csv");
    HashMap<String, StockRow> stock = new HashMap<>();
    if (!file.exists()) {
      return null;
    }
    // read the file and load it into the cache
    else {
      try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          String[] items = line.split(",");
          if (items.length < 5) {
            continue;
          }
          StockRow stockRow = new StockRow(Double.parseDouble(items[1]),
                  Double.parseDouble(items[2]),
                  Double.parseDouble(items[3]),
                  Double.parseDouble(items[4]));
          stock.put(items[0], stockRow);
        }
        return stock;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    return stock;
  }

  /**
   * Get the stock data for a given symbol.
   *
   * @param symbol The ticker/symbol of the stock.
   * @return the stock data.
   */
  public HashMap<String, StockRow> getStock(String symbol) {
    if (stocksCache.containsKey(symbol)) {
      return stocksCache.get(symbol);
    } else {
      queryStock(symbol);
      return stocksCache.get(symbol);
    }
  }

  /**
   * Add a stock to a portfolio.
   *
   * @param symbol    the stock symbol
   * @param portfolio the portfolio
   */
  public void addStockToPortfolio(String symbol, Portfolio portfolio, int shares) {
    portfolio.buyStock(symbol, getStock(symbol), shares);
  }
  /**
   * Add a stock to a portfolio.
   *
   * @param symbol    the stock symbol
   * @param portfolio the portfolio
   * @param shares    the number of shares
   * @param date      the date of the transaction
   */
  public void addStockToPortfolio(String symbol, Portfolio portfolio, int shares, String date) {
    portfolio.buyStock(symbol, getStock(symbol), shares, date);
  }

  /**
   * Remove a stock from a portfolio.
   *
   * @param symbol    the stock symbol
   * @param portfolio the portfolio
   */
  public void sellStockFromPortfolio(String symbol, Portfolio portfolio, int quantity) {
    portfolio.sellStock(symbol, quantity);
  }

  /**
   * Remove a stock from a portfolio.
   *
   * @param symbol    the stock symbol
   * @param portfolio the portfolio
   */
  public void sellStockFromPortfolio(String symbol, Portfolio portfolio, int quantity, String date) {
    portfolio.sellStock(symbol, quantity, date);
  }


  /**
   * Get the value of a portfolio.
   *
   * @return the value of the portfolio
   */
  public double getPortfolioValue(String date, Portfolio portfolio) {
    return portfolio.getPortfolioValue(date);
  }

  /**
   * Constructor for the StockModel.
   */
  public StockModel() {
    portfolios = new HashMap<>();
    stocksCache = new HashMap<>();
    File file = new File("./stocks");
    File portfolios = new File("./portfolios");
    if (!file.exists()) {
      file.mkdir();
    } else {
      // go through each file in the stocks directory and load it in
      for (File f : file.listFiles()) {
        System.out.println("Loading local stock data: " + f.getName());
        String stockSymbol = f.getName().replace(".csv", "");
        stocksCache.put(stockSymbol, loadLocalStock(stockSymbol));
      }
    }
    if(!portfolios.exists()){
      portfolios.mkdir();
    }
    for(File f : portfolios.listFiles()){
      String name = f.getName().replace(".txt", "");
      try (Scanner scanner = new Scanner(f)) {
        String fileContent = scanner.useDelimiter("\\Z").next();
        readPortfolioFromFile(name, fileContent);
        scanner.close();
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    }

  }

  /**
   * Get a portfolio by name.
   *
   * @param name the portfolio name
   * @return the portfolio
   */
  public Portfolio getPortfolio(String name) {
    if (portfolios.containsKey(name)) {
      // return a hard copy of the portfolio
      return new Portfolio(name, portfolios.get(name));
    } else {
      throw new IllegalArgumentException("Portfolio does not exist");
    }
  }

  /**
   * Creates a portfolio.
   *
   * @param name the portfolio name
   */
  public void addPortfolio(String name) {
    if (portfolios.containsKey(name)) {
      return;
    }
    portfolios.put(name, new Portfolio(name));
  }

  /**
   * Writes a portfolio to a file.
   *
   * @param portfolio the portfolio
   */
  public void writePortfolioToFile(Portfolio portfolio) {
    portfolio.writeHistoryToFile("./portfolios/" + portfolio.getName() + ".txt");
  }

  public void readPortfolioFromFile(String name, String fileContent) {
    Portfolio portfolio = new Portfolio(name);
    String[] lines = fileContent.split("\n");
    for (String line : lines) {
      String[] items = line.split(";");
      switch(items[0]){
        case "BUY":
          portfolio.buyStock(items[1], getStock(items[1]), Integer.parseInt(items[2]), items[3]);
          break;
        case "SELL":
          portfolio.sellStock(items[1], Integer.parseInt(items[2]), items[3]);
          break;
    }
    portfolios.put(name, portfolio);
  }
  }


  public String graphPortfolio(Portfolio portfolio, String startDate, String endDate) {
    GraphModel graphModel = new GraphModel(DateFormat.toDate(startDate), DateFormat.toDate(endDate), portfolio);
    String graph = graphModel.getGraph();
    return graph;
  }
  /**
   * Get the change in stock price over a given period.
   *
   * @param symbol    the stock symbol
   * @param startDate the start date
   * @param endDate   the end date
   * @return the change in stock price
   */
  public double getStockChange(String symbol, String startDate, String endDate) {
    HashMap<String, StockRow> stock = getStock(symbol);
    StockRow startRow = findClosestRecordedDate(stock, startDate);
    StockRow endRow = findClosestRecordedDate(stock, endDate);
    System.out.println("Start Row: " + startRow.getClose());
    System.out.println("End Row: " + endRow.getClose());
    return endRow.getClose() - startRow.getClose();
  }

  /**
   * Find the closest stock row to a given date.
   *
   * @param stock the stock data
   * @param date  the date
   * @return the closest recorded date
   */
  public StockRow findClosestRecordedDate(HashMap<String, StockRow> stock, String date) {
    StockRow stockRow = stock.get(date);
    if (stockRow != null) {
      return stockRow;
    }
    Date dateObj = DateFormat.toDate(date);
    String dateString = DateFormat.toString(dateObj);

    while (stockRow == null) {
      dateObj = new Date(dateObj.getTime() - 86400000); // subtract a day
      dateString = DateFormat.toString(dateObj);
      stockRow = stock.get(dateString);
    }
    System.out.println(date + " Closest Recorded Date: " + dateString);
    return stockRow;
  }

  /**
   * Get the moving average of a stock.
   *
   * @param symbol the stock symbol
   * @param date   the date
   * @param x      the number of days
   * @return the moving average
   */
  public double getStockMovingAverage(String symbol, Date date, int x) {
    HashMap<String, StockRow> stock = getStock(symbol);
    // get the date in the format yyyy-mm-dd
    String dateString = date.toString();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    dateString = formatter.format(date);
    double sum = 0;
    StockRow currentRow = findClosestRecordedDate(stock, dateString);
    for (int i = 0; i < x; i++) {
      currentRow = findClosestRecordedDate(stock, dateString);
      if (currentRow != null) {
        sum += currentRow.getClose();
      }
      date = new Date(date.getTime() - 86400000); // subtract a day
      dateString = formatter.format(date);
    }
    return sum / x;
  }

  /**
   * Gets a list of dates that are considered crossover days.
   *
   * @param symbol    the stock symbol
   * @param x         the number of days
   * @param startDate the start date
   * @param endDate   the end date
   * @return the list of crossover days
   * @throws IllegalArgumentException if the stock symbol is not found
   */
  public ArrayList<String> xDayCrossoverDays(String symbol, int x, String startDate,
                                             String endDate) {
    HashMap<String, StockRow> stock = getStock(symbol);
    double movingAverageX = getStockMovingAverage(symbol, DateFormat.toDate(endDate), x);
    ArrayList<String> crossoverDays = new ArrayList<>();
    String currentDate = startDate;
    while (!currentDate.equals(endDate)) {
      StockRow currentRow = findClosestRecordedDate(stock, currentDate);
      if (currentRow.getClose() > movingAverageX) {
        crossoverDays.add(currentDate);
      }
      Date date = DateFormat.toDate(currentDate);
      date = new Date(date.getTime() + 86400000); // add a day
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      currentDate = formatter.format(date);
    }
    return crossoverDays;
  }

  /**
   * Get the portfolio as a string.
   *
   * @param portfolioName the portfolio name
   * @return the portfolio as a string
   */
  public String getPortfolioAsString(String portfolioName) {
    Portfolio portfolio = getPortfolio(portfolioName);
    return portfolioName + ": \n" + portfolio.portfolioAString();
  }

  /**
   * Checks if the given ticker is valid.
   *
   * @param ticker the ticker symbol to check
   * @return true if the ticker is valid, false otherwise
   */
  public boolean isValidTicker(String ticker) {
    return stocksCache.containsKey(ticker);
  }

  /**
   * Is this date string valid.
   *
   * @param dateStr The inputted date in YYYY-MM-DD Format
   * @return boolean, is this day real?
   */
  public static boolean isValidDate(String dateStr) {
    try {
      String[] dateParts = dateStr.split("-");
      if (dateParts.length != 3) {
        return false;
      }
      int year = Integer.parseInt(dateParts[0]);
      int month = Integer.parseInt(dateParts[1]);
      int day = Integer.parseInt(dateParts[2]);
      if (month < 1 || month > 12) {
        return false;
      }
      if (day < 1 || day > 31) {
        return false;
      }

      if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
        return false;
      }
      if (month == 2) {
        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        if ((isLeapYear && day > 29) || (!isLeapYear && day > 28)) {
          return false;
        }
      }

      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
