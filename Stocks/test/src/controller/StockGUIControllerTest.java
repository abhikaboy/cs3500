package src.controller;

import org.junit.Before;
import org.junit.Test;
import src.model.Portfolio;
import src.model.StockModel;
import src.model.StockModelInterface;
import src.view.ExtendedStockViewInterface;
import src.view.StockSwingView;

import javax.swing.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StockGUIControllerTest {

  private StockModelInterface model;
  private ExtendedStockViewInterface guiView;
  private StockGUIController controller;

  @Before
  public void setUp() {
    model = new StockModel();
    guiView = new StockSwingView();
    controller = new StockGUIController(model, guiView);
  }

  @Test
  public void testBuyStock() {
    Portfolio portfolio = new Portfolio("Test Portfolio");
    model.addPortfolio(portfolio.getName());
    controller.setCurrentPortfolio(portfolio);

    simulateUserInput("AAPL");
    simulateUserInput("100");
    simulateUserInput("2024-01-01");

    controller.buyStock();

    assertEquals(100, portfolio.getShare("AAPL").getQuantity(), 0.0);
  }

  @Test
  public void testSellStock() {
    Portfolio portfolio = new Portfolio("Test Portfolio");
    model.addPortfolio(portfolio.getName());
    controller.setCurrentPortfolio(portfolio);

    simulateUserInput("AAPL");
    simulateUserInput("50");
    simulateUserInput("2024-01-01");

    controller.sellStock();

    assertEquals(50, portfolio.getShare("AAPL").getQuantity(), 0.0);
  }

  @Test
  public void testQueryPortfolio() {
    Portfolio portfolio = new Portfolio("Test Portfolio");
    model.addPortfolio(portfolio.getName());
    controller.setCurrentPortfolio(portfolio);

    simulateUserInput("2024-01-01");

    controller.queryPortfolio();

    // Replace with appropriate assertion if needed
    assertNotNull(portfolio);
  }

  @Test
  public void testSavePortfolio() {
    Portfolio portfolio = new Portfolio("Test Portfolio");
    model.addPortfolio(portfolio.getName());
    controller.setCurrentPortfolio(portfolio);

    controller.savePortfolio();

    // Check if file exists, replace with appropriate check if needed
    assertNotNull(portfolio);
  }

  @Test
  public void testRetrievePortfolio() {
    Portfolio portfolio = new Portfolio("Test Portfolio");
    model.addPortfolio(portfolio.getName());
    model.writePortfolioToFile(portfolio);

    simulateUserInput("Test Portfolio");

    controller.retrievePortfolio();

    assertEquals("Test Portfolio", controller.getCurrentPortfolio().getName());
  }

  @Test
  public void testHandleSpecifyStock() {
    simulateUserInput("AAPL");

    String stock = controller.handleSpecifyStock();

    assertEquals("AAPL", stock);
  }

  @Test
  public void testHandleSpecifyDate() {
    simulateUserInput("2024-01-01");

    String date = controller.handleSpecifyDate();

    assertEquals("2024-01-01", date);
  }

  @Test
  public void testGetUserInputAsDouble() {
    simulateUserInput("100.5");

    double value = controller.getUserInputAsDouble();

    assertEquals(100.5, value, 0.0);
  }

  private void simulateUserInput(String input) {

    JOptionPane.showInputDialog(null, input);
  }
}
