package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class StockModel {
  HashMap<String, Portfolio> portfolios;
  HashMap<String, HashMap<String, StockRow>> stocksCache;

  public void writeStockToFile(String output, String stockSymbol) {
    String[] rows = output.toString().split("\n");
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

  public void queryStock(String symbol) {
    String apiKey = "W0M1JOKC82EZEQA8";
    String stockSymbol = "GOOG"; //ticker symbol for Google
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

  public HashMap<String, StockRow> getStock(String symbol) {
    if (stocksCache.containsKey(symbol)) {
      return stocksCache.get(symbol);
    } else {
      queryStock(symbol);
      return stocksCache.get(symbol);
    }
  }

  public void addStockToPortfolio(String symbol, String portfolioName) {
    Portfolio portfolio = getPortfolio(portfolioName);
    portfolio.addStock(symbol, getStock(symbol));
  }

  public void removeStockFromPortfolio(String symbol, String portfolioName) {
    Portfolio portfolio = getPortfolio(portfolioName);
    portfolio.removeStock(symbol, getStock(symbol));
  }

  public double getPortfolioValue(String name) {
    return getPortfolio(name).getPortfolioValue();
  }

  public StockModel() {
    portfolios = new HashMap<>();
    stocksCache = new HashMap<>();
    File file = new File("./stocks");
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
  }

  public Portfolio getPortfolio(String name) {
    if (portfolios.containsKey(name)) {
      return portfolios.get(name);
    } else {
      throw new IllegalArgumentException("Portfolio does not exist");
    }
  }

  public void addPortfolio(String name) {
    if (portfolios.containsKey(name)) {
      return;
    }
    portfolios.put(name, new Portfolio());
  }

  public double getStockChange(String symbol, String startDate, String endDate) {
    HashMap<String, StockRow> stock = getStock(symbol);
    StockRow startRow = stock.get(startDate);
    StockRow endRow = stock.get(endDate);
    return endRow.getClose() - startRow.getClose();
  }

  public double getStockMovingAverage(String symbol, Date date, int x) {
    HashMap<String, StockRow> stock = getStock(symbol);
    // get the date in the format yyyy-mm-dd
    String dateString = date.toString();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    dateString = formatter.format(date);
    double sum = 0;
    StockRow currentRow = stock.get(dateString);
    for (int i = 0; i < x; i++) {
      System.out.println(dateString);
      if (stock.containsKey(dateString)) {
        currentRow = stock.get(dateString);
      }
      if (currentRow != null) {
        sum += currentRow.getClose();
      }
      date = new Date(date.getTime() - 86400000); // subtract a day
      dateString = formatter.format(date);
      System.out.println(sum);
    }
    return sum / x;
  }
}
