package src.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

/**
 * Class to test both the Stock and Portfolio classes.
 */
public class StockPortfolioTest {
  private Stock stock;
  private Portfolio portfolio;
  private StockRow stockRow;
  private HashMap stockData;


  @Before
  public void setUp() {
    // Mock Stock
    stockRow = new StockRow(100, 120, 80, 90);
    StockRow stockRow2 = new StockRow(90, 120, 80, 100);
    StockRow stockRow3 = new StockRow(95, 120, 80, 90);
    stockData = new HashMap<>();
    stockData.put("2024-06-03", stockRow);
    stockData.put("2024-06-04", stockRow2);
    stockData.put("2024-06-03", stockRow3);
    stock = new Stock("AAPL", 100, stockData);
    portfolio = new Portfolio();
  }

  // Testing the StockRow class
  @Test
  public void testGetOpen() {
    assertEquals(100, stockRow.getOpen(), 0.01);
  }

  @Test
  public void testGetHigh() {
    assertEquals(120, stockRow.getHigh(), 0.01);
  }

  @Test
  public void testGetLow() {
    assertEquals(80, stockRow.getLow(), 0.01);
  }

  @Test
  public void testGetClose() {
    assertEquals(90, stockRow.getClose(), 0.01);
  }

  // Testing the Stock class
  @Test
  public void testPurchase() {
    stock.purchase(100);
    assertEquals(200, stock.getQuantity());
  }

  @Test
  public void testSell() {
    stock.purchase(100);
    stock.sell(50);
    assertEquals(150, stock.getQuantity());
  }

  @Test
  public void testGetData() {
    assertEquals(stockData, stock.getData());
  }

  @Test
  public void testGetQuantity() {
    assertEquals(100, stock.getQuantity());
  }
 
  // Testing the Portfolio class
  @Test
  public void testBuyStock() {
    portfolio.buyStock("AAPL", stockData, 100);
    assertEquals(100, portfolio.getStockQuantity("AAPL"));
  }

  @Test
  public void testSellStock() {
    portfolio.buyStock("AAPL", stockData, 100);
    portfolio.sellStock("AAPL", 50);
    assertEquals(50, portfolio.getStockQuantity("AAPL"));
  }

  @Test
  public void testGetStock() {
    portfolio.buyStock("AAPL", stockData, 100);
    assertEquals(stock, portfolio.getStock("AAPL"));
  }


}
