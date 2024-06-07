package src;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Class that represents the visual representation of the Stock Portfolio Manager.
 * Contains all methods related to creating visual output.
 */
public class StockView {
  private final PrintStream out;
  private final Scanner scan;

  /**
   *
   * @param out
   */
  public StockView(PrintStream out) {
    this.out = out;
    this.scan = new Scanner(System.in);
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
   * TODO:Once a method is able to count total portfolios, direct controller methods to this
   */
  protected void printWelcome() {
     out.print("Welcome to the Stock Portfolio Manager");
   }

  /**
   * TODO:Once a method is able to count total portfolios, direct controller methods to this
   */

   private void printMenuNoPortfolios() {
    out.print("1) Create A Portfolio");
    out.print("2) Quit");
   }

  /**
   * TODO:Once a method is able to count total portfolios, direct controller methods to this
   *      specifically, replace the [x] with the amount of items in portfolio
   */
  private void printMenu() {
      out.println("Please Select An Option:");
      out.println("You Have [x] Items In Your Portfolio");
      out.println("1) Add Item to Portfolio");
      out.println("2) View Stocks");
      out.println("3) View Portfolio Value");
      out.println("4) View Crossover");
      out.println("5) Change/Create Portfolio");
      out.println("6) Quit");
   }

  /**
   * TODO: Once a method is able to count total portfolios, use a getter to identify the number
   *       of portfolios and complete the forLoop of creating the menu indices.
   */
  private void printPortfolioChanger() {
    out.println("Please Select A Portfolio:");
    for (int x = 0; x < 5; x++) {
      out.println((x + 1) + ") " + "Portfolio Name");
    }
  }

  private void printPortfolioMaker() {

  }
}
