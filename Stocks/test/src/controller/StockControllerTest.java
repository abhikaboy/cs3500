package src.controller;

import org.junit.Before;
import org.junit.Test;

import src.model.MockStockModel;
import src.view.MockStockView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

/**
 * Testing class for the Controller.
 */
public class StockControllerTest {
  private MockStockModel model;
  private MockStockView view;
  private StockController controller;

  @Before
  public void setUp() {
    model = new MockStockModel();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    view = new MockStockView(new PrintStream(outContent));
    controller = new StockController(model, view); // Initialize the controller here
  }

  @Test
  public void testPrintMenuNoPortfolios() {
    // Simulate user input to create a portfolio and then quit
    String input = "";
    InputStream inContent = new ByteArrayInputStream(input.getBytes());
    System.setIn(inContent);

    // Call the control method to display the initial menu
    controller.control();

    // Capture the output from the view
    String output = view.getOutput();
    System.out.println("Captured output:\n" + output); // Debugging line to see the captured output

    // Verify that the initial menu is displayed correctly
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