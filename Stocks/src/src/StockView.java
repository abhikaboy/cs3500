package src;

public class StockView {
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

   public void printWelcome(){
     System.out.println("Welcome to the Stock Portfolio Manager");
     printMenu();
     String option = System.console().readLine();

   }

   public void printMenu(){
      System.out.println("Please select an option:");
      System.out.println("You have [x] items in your portfolio");
      System.out.println("1) Add Item to Portfolio");
      System.out.println("2) View Stocks");
      System.out.println("3) View Portfolio Value");
      System.out.println("4) View Crossover");
      System.out.println("5) Quit");
   }
}
