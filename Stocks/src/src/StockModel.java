package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class StockModel {
  HashMap<String, HashMap<String, StockRow>> portfolio;
  HashMap<String, HashMap<String, StockRow>> stocksCache;

  public void writeStockToFile(String output, String stockSymbol){
    String[] rows = output.toString().split("\n");
    File file;
    FileWriter writer = null;
    try {
      file = new File("./stocks/" + stockSymbol + ".csv");
      writer = new FileWriter(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    stocksCache.put(stockSymbol, new HashMap<>());
    for (String row : rows) {
      String[] items = row.split(",");
      if (writer != null) {
        try {
          writer.write(row + "\n");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      // items[0] = date
      StockRow stockRow = new StockRow(Double.parseDouble(items[1]),
              Double.parseDouble(items[2]),
              Double.parseDouble(items[3]),
              Double.parseDouble(items[4]));
      stocksCache.get(stockSymbol).put(items[0], stockRow);

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
    System.out.println("Return value: ");
    System.out.println(output.toString());
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
  public void addStockToPortfolio(String symbol){
    if (portfolio.containsKey(symbol)) {
      return;
    }
    HashMap<String, StockRow> stock = getStock(symbol);
    if (stock != null) {
      portfolio.put(symbol, stock);
    }
  }
  public void removeStockFromPortfolio(String symbol){
    if (portfolio.containsKey(symbol)) {
      portfolio.remove(symbol);
    }
  }
  public void getPortfolioValue(){
    double total = 0;
    for (String symbol : portfolio.keySet()) {
      HashMap<String, StockRow> stock = portfolio.get(symbol);
      StockRow lastRow = null;
      // todays date: 
      String date = "2024-06-06";
      lastRow = stock.get(date);
      total += lastRow.getClose();
    }
    System.out.println("Total portfolio value: " + total);
  }
}
