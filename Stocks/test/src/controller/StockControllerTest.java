package src.controller;

import org.junit.Before;
import org.junit.Test;

import src.controller.StockController;
import src.model.MockStockModel;
import src.model.Portfolio;
import src.view.MockStockView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StockControllerTest {
  private MockStockModel model;
  private MockStockView view;
  private StockController controller;
  private ByteArrayOutputStream outContent;

  @Before
  public void setUp() {
    model = new MockStockModel();
    outContent = new ByteArrayOutputStream();
    view = new MockStockView(outContent);
  }

  @Test
  public void testPrintMenuNoPortfolios() {
    // Simulate user input to quit
    String input = "Quit\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Scanner scanner = new Scanner(in);

    controller = new StockController(model, view);
    controller.control();

    String output = view.getOutput();
    assertTrue(output.contains("1) Create A Portfolio"));
    assertTrue(output.contains("2) Quit"));
  }

  @Test
  public void testExitProgram() {
    // Simulate user input to quit
    String input = "2\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Scanner scanner = new Scanner(in);

    controller = new StockController(model, view);
    controller.control();

    String output = view.getOutput();
    assertTrue(output.contains("Thank You For Using The Stock Portfolio Manager!"));
  }

  @Test
  public void testHandleTransactionInvalidQuantity() {
    // Set up the mock model to return true for a valid ticker
    model.setTickerValidity("AAPL", true);

    // Simulate user input for an invalid quantity
    String input = "AAPL\nBuy:InvalidQuantity\nQuit\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Scanner scanner = new Scanner(in);

    controller = new StockController(model, view);

    // Check that the error message is printed
    String output = view.getOutput();
    assertTrue(output.contains("Invalid quantity. Please enter a valid number."));
  }

}