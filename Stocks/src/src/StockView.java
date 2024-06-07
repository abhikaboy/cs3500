package src;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Class that represents the visual representation of the Stock Portfolio Manager.
 * Contains all methods related to creating visual output.
 */
public class StockView implements StockViewInterface {
  private final PrintStream out;

  /**
   * Constructor for the view of the stock portfolio manager.
   * @param out the output that will be printed.
   */
  public StockView(PrintStream out) {
    this.out = out;
  }
  /**
   *
   * Allow a user to examine the gain or loss of a stock over a specified period.
   *
   * Allow a user to examine the x-day moving average of a stock for a specified date
   * and a specified value of x.
   *
   * Allow a user to determine which days are x-day crossovers for a specified stock
   * over a specified date range and a specified value of x.
   *
   * Allow a user to create one or more portfolios with shares of one or more stock,
   * and find the value of that portfolio on a specific date. You can assume that all the
   * stocks and their quantities will exist on that date.
   *
   */

  /**
   * If no portfolio currently, allow a user to
   *  [1. Build Portfolio] ***
   *  [2. Quit]
   * Else Give users options [1-5]
   * 1) View gain or loss of stock over a window of time
   *  [Start Date DD-MM-YYYY]
   *  [End Date DD-MM-YYYY]
   * 2) Crossover ???
   *  Over a specified period of time, what days are "x-day crossovers"?
   *  An x-day crossover happens when the closing price for a day is greater
   *  than the x-day moving average for that day. A crossover signals a "buy"
   *  opportunity. A crossover can be defined over any period of time, although
   *  the 30-day period (i.e. the 30-day crossover) is usually popular.
   * 3) View Portfolio Value on specific date
   * 4) Add Stock to portfolio ***
   * 5) Quit
   */

  /**
   * Portfolios should be stored locally as a CSV or JSON
   */


  /**
   * Prints out prompt, welcoming the user :).
   */
  public void printWelcome() {
     out.print("Welcome to the Stock Portfolio Manager! " + System.lineSeparator());
   }

  /**
   * Prints out prompt, telling user to either create a portfolio or quit due to not having a
   * portfolio in store.
   */
  public void printMenuNoPortfolios() {
    out.print("1) Create A Portfolio" + System.lineSeparator());
    out.print("2) Quit" + System.lineSeparator());
   }

  /**
   * Prints out prompt, telling user to choose one of the 6 menu options regarding their
   * current portfolio selection.
   * @param portfolio  The current portfolio that is being managed through the menu.
   */
  public void printMenu(Portfolio portfolio) {
    out.println("====================== Menu ======================");
      out.println("Please Select An Option:");
      out.println("You Have " + portfolio.getPortfolioSize() + " Items In Your Portfolio");
      out.println("1) Add/Remove Item to Portfolio");
      out.println("2) View Stocks");
      out.println("3) View Portfolio Value");
      out.println("4) Change/Create Portfolio");
      out.println("5) Exit");
    out.println("======================================================");
   }

  /**
   * Prints out prompt, telling user to select their desired portfolio.
   * @param portfolios  List of portfolios stored in the model to be chosen from.
   */
  public void printPortfolioChanger(String[] portfolios) {
    int start = 1;
    if(portfolios.length == 0) {
      out.println("No Portfolios Found");
      return;
    }
    out.println("============== Portfolio Selector =================");
    out.println("Please Select A Portfolio: [1-" + portfolios.length + "]");
    for (int x = 0; x < portfolios.length; x++) {
      out.println((x + 1) + ") " + portfolios[x]);
    }
    out.println("======================================================");
  }

  /**
   * Prints out prompt, telling user to name a new portfolio.
   */
  public void printPortfolioMaker() {
    out.println("============== Portfolio Maker =================");
    out.println("Please Name Your New Portfolio");
    out.println("Write 'Quit' To Cancel");
    out.println("======================================================");
  }

  /**
   * Prints out prompt telling user to inpuit Stock Ticker.
   */
  public void printSpecifyStockToTransact() {
    out.println("============== Choose A Stock Option =================");
    out.println("Type The Stock Ticker Symbol To Add/Remove");
    out.println("Write 'Quit' To Cancel");
    out.println("======================================================");

  }

  /**
   * Prints out prompt telling user to input Sell/Buy action with the specific quantity of
   * shares.
   */
  public void printAddOrSellStock() {
    out.println("Type The Action And Then Quantity");
    out.println("Ex: Sell:30 OR Buy:102");
    out.println("Write 'Quit' To Cancel");
  }

  /**
   * Prints out prompt informing the user that their transaction was successful.
   */
  public void printSuccessfulTransaction() {
    out.println("Transaction Successful!");
  }

  /**
   * Prints out all stocks inside a portfolio in a numbered list, prompting the user to select one.
   * @param portfolio  Portfolio whose stocks will be examined.
   */
  public void printViewStocks(Portfolio portfolio) {
    String[] names = portfolio.getStockNames();
    out.println("Select Which Stock To Examine");
    for (int x = 0; x < portfolio.getPortfolioSize(); x++) {
      out.println((x + 1) + ") " + names[x]);
    }
    out.println("Write 'Quit' To Cancel");
  }

  /**
   * Prints out options to choose while observing a specific stock.
   */
  public void printChooseStockOption() {
    out.println("============== Choose A Stock Option =================");
    out.println("1) View Stock Performance Over Specified Period");
    out.println("2) View Stock X-Day Moving Average");
    out.println("3) View Stock X-Day Crossovers");
    out.println("4) Back");
    out.println("======================================================");
  }

  public void printStockPerformance(Double change) {
    out.println("The Stock Change Was: " + change);
  }

  /**
   * Prints out message, telling user to input a start date.
   */
  public void printSpecifyStartDate() {
    out.println("Please Specify Start Date YYYY-MM-DD");
  }

  /**
   * Prints out message, telling user to input an end date.
   */
  public void printSpecifyEndDate() {
    out.println("Please Specify End Date YYYY-MM-DD");
  }

  /**
   * Prints out message, telling user to input an x-value to observe X-Day information.
   */
  public void printPortfolioValueResult(Double value) {
    out.println("The Portfolio Value Was " + value);
  }

  public void printPortfolioValuePrompt() {
    out.println("Please Specify the date you wish to view the value of the portfolio YYYY-MM-DD");
  }

  /**
   * Prints out message, telling user to input an x-value to observe X-Day information.
   */
  public void printXValue() {
    out.println("Please Input the X Value of days you wish to observe");
  }

  /**
   * Prints out a message showing the result of the X-Day Moving Average.
   * @param num The result.
   */
  public void printResultOfAverage(double num) {
    out.println("The Moving Average Is: " + num);
  }

  /**
   * Prints out a message showing the result of the X-Day crossovers.
   * @param days The resulting days.
   */
  public void printResultOfCrossOver(ArrayList<String> days) {
    out.println("The Crossover Days Are The Following: ");
    for (String day : days) {
      out.println(day);
    }
  }

  /**
   * Displays an error message.
   * @param error The error message to display.
   */
  public void displayError(String error) {
    out.println("Error: " + error);
  }

  /**
   * Displays a farewell message.
   */
  public void displayFarewell() {
    out.println("Thank You For Using The Stock Portfolio Manager!");
  }


}
