package src.view;

import org.junit.Before;
import org.junit.Test;

import src.model.Portfolio;
import src.model.Share;
import src.model.StockRow;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for the StockView class.
 */
public class StockViewTest {

  private ByteArrayOutputStream outContent;
  private StockView view;

  @Before
  public void setUp() {
    outContent = new ByteArrayOutputStream();
    PrintStream testOut = new PrintStream(outContent);
    PrintStream originalOut = System.out;
    System.setOut(testOut);

    view = new StockView(testOut);
  }

  @Test
  public void testPrintWelcome() {
    view.printWelcome();
    assertEquals("Welcome to the Stock Portfolio Manager!\r\n", outContent.toString());
  }

  @Test
  public void testPrintMenuNoPortfolios() {
    view.printMenuNoPortfolios();
    assertEquals("1) Create A Portfolio\r\n2) Quit\r\n", outContent.toString());
  }

  @Test
  public void testPrintMenu() {
    // Mock Portfolio
    Portfolio mockPortfolio = new Portfolio() {
      @Override
      public int getPortfolioSize() {
        return 5;
      }
    };

    view.printMenu(mockPortfolio);
    assertEquals("====================== Menu ======================\r\n" +
            "Please Select An Option:\r\n" +
            "You Have 5 Items In Your Portfolio\r\n" +
            "1) Add/Remove Item to Portfolio\r\n" +
            "2) View Portfolio Contents\r\n" +
            "3) Analyze Current Stocks\r\n" +
            "4) View Portfolio Value\r\n" +
            "5) Change/Create Portfolio\r\n" +
            "6) Exit\r\n" +
            "======================================================\r\n", outContent.toString());
  }

  @Test
  public void testPrintPortfolioChanger() {
    String[] portfolios = {"Portfolio1", "Portfolio2"};
    view.printPortfolioChanger(portfolios);
    assertEquals("============== Portfolio Selector =================\r\n" +
            "Please Select A Portfolio: [1-2]\r\n" +
            "1) Portfolio1\r\n" +
            "2) Portfolio2\r\n" +
            "3) Create New Portfolio\r\n" +
            "======================================================\r\n", outContent.toString());
  }

  @Test
  public void testPrintPortfolioMaker() {
    view.printPortfolioMaker();
    assertEquals("============== Portfolio Maker =================\r\n" +
            "Please Name Your New Portfolio\r\n" +
            "Write 'Quit' To Cancel\r\n" +
            "======================================================\r\n", outContent.toString());
  }

  @Test
  public void testPrintSpecifyStockToTransact() {
    view.printSpecifyStockToTransact();
    assertEquals("============== Input Desired Stock =================\r\n" +
            "Type The Desired Stock Ticker Symbol\r\n" +
            "Write 'Quit' To Cancel\r\n" +
            "======================================================\r\n", outContent.toString());
  }

  @Test
  public void testPrintAddOrSellStock() {
    view.printAddOrSellStock();
    assertEquals("Type The Action And Then Quantity\r\n" +
            "Ex: Sell:30 OR Buy:102\r\n" +
            "Write 'Quit' To Cancel\r\n", outContent.toString());
  }

  @Test
  public void testPrintSuccessfulTransaction() {
    view.printSuccessfulTransaction();
    assertEquals("Transaction Successful!\r\n", outContent.toString());
  }

  @Test
  public void testPrintViewStocks() {
    // Creating mock data for stocks
    HashMap<String, StockRow> stockData1 = new HashMap<>();
    HashMap<String, StockRow> stockData2 = new HashMap<>();

    Share stock1 = new Share("AAPL", 10, stockData1);
    Share stock2 = new Share("GOOG", 5, stockData2);

    Portfolio portfolio = new Portfolio();
    portfolio.buyStock("AAPL", stockData1, 10);
    portfolio.buyStock("GOOG", stockData2, 5);

    // Printing the portfolio contents
    view.printViewStocks(portfolio);

    String actualOutput = outContent.toString();
    // Extract only the relevant part of the output
    String portfolioContents = actualOutput.substring(actualOutput.indexOf(
            "============== Portfolio Contents ================="));

    String expectedOutput = "============== Portfolio Contents =================\r\n" +
            "AAPL: 10\r\n" +
            "GOOG: 5\r\n" +
            "======================================================\r\n";

    assertEquals(expectedOutput, portfolioContents);
  }

  @Test
  public void testPrintChooseStockOption() {
    // Create a Stock instance
    HashMap<String, StockRow> data = new HashMap<>();
    Share mockStock = new Share("AAPL", 10, data);

    view.printChooseStockOption();
    assertEquals("============== Choose A Stock Analysis Option =================\r\n" +
            "1) View Stock Performance Over Specified Period\r\n" +
            "2) View Stock X-Day Moving Average\r\n" +
            "3) View Stock X-Day Crossovers\r\n" +
            "4) Back\r\n" +
            "======================================================\r\n", outContent.toString());
  }

  @Test
  public void testPrintSpecifyEndDate() {
    view.printSpecifyEndDate();
    assertEquals("Please Specify End Date YYYY-MM-DD\r\n", outContent.toString());
  }

  @Test
  public void testPrintSpecifyStartDate() {
    view.printSpecifyStartDate();
    assertEquals("Please Specify Start Date YYYY-MM-DD\r\n", outContent.toString());
  }

  @Test
  public void testDisplayError() {
    view.displayError("An error occurred");
    assertEquals("Error: An error occurred\r\n", outContent.toString());
  }

  @Test
  public void testDisplayFarewell() {
    view.displayFarewell();
    assertEquals("Thank You For Using The Stock Portfolio Manager!\r\n", outContent.toString());
  }
}