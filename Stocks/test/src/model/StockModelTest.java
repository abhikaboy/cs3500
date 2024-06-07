package src.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;

public class StockModelTest {
  StockModel model;

  @Before
  public void setUp() throws Exception {
    // create a model instance 
    model = new StockModel();
  }

  @Test
  public void testCountPortfolios() {
    assertEquals(0, model.countPortfolios());
    model.addPortfolio("ABC");
    assertEquals(1, model.countPortfolios());
  }

  @Test
  public void addPortfolio() {
    model.addPortfolio("ABC");
    assertEquals(1, model.countPortfolios());

  }

  @Test
  public void testGetPortfolioNames() {
    model.addPortfolio("ABC");
    model.addPortfolio("DEF");
    assertEquals(2, model.getPortfolioNames().length);
  }

  @Test
  public void testGetPortfolio() {
    model.addPortfolio("ABC");
    Portfolio portfolio = model.getPortfolio("ABC");
    assertEquals(new Portfolio().portfolioAString(), portfolio.portfolioAString());
  }

  @Test
  public void testAddStockToPortfolio() {
    model.addPortfolio("ABC");
    model.addStockToPortfolio("AAPL", model.getPortfolio("ABC"), 100);
    assertEquals(100, model.getPortfolio("ABC").getStockQuantity("AAPL"));
  }

  @Test
  public void testGetStockChange() {
    model.addPortfolio("ABC");
    model.addStockToPortfolio("AAPL", model.getPortfolio("ABC"), 100);
    assertEquals(0.449, model.getStockChange("AAPL", "2024-06-03", "2024-06-06"),0.01);
  }

  @Test
  public void testXDayCrossoverDays() {
    model.addPortfolio("ABC");
    model.addStockToPortfolio("AAPL", model.getPortfolio("ABC"), 100);
    assertEquals(1, model.xDayCrossoverDays("AAPL", 1, "2024-06-03", "2024-06-06").size());
  }

  @Test
  public void testGetStockMovingAverage() {
    model.addPortfolio("ABC");
    model.addStockToPortfolio("AAPL", model.getPortfolio("ABC"), 100);
    assertEquals(191.62, model.getStockMovingAverage("AAPL", new Date(), 20),0.01);
  }

  @Test
  public void testGetStock() {
    model.addPortfolio("ABC");
    model.addStockToPortfolio("AAPL", model.getPortfolio("ABC"), 100);
    assertEquals(194.03, model.getStock("AAPL").get("2024-06-03").getClose(),0.01);
  }

  @Test
  public void testGetPortfolioValue() {
    model.addPortfolio("ABC");
    model.getStock("AAPL");
    model.addStockToPortfolio("AAPL", model.getPortfolio("ABC"), 100);
    assertEquals(0, model.getPortfolioValue("2024-06-03", model.getPortfolio("ABC")),0.01);
  }

  @Test
  public void testLoadLocalStock() {
    model.addPortfolio("ABC");
    model.addStockToPortfolio("AAPL", model.getPortfolio("ABC"), 100);
    assertEquals(194.03, model.getStock("AAPL").get("2024-06-03").getClose(),0.01);
  }
}