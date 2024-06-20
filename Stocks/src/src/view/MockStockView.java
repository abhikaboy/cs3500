package src.view;

import src.model.Portfolio;

import java.io.PrintStream;

/**
 * Mock class for testing, represents the View class.
 */
public class MockStockView extends StockView {
  private final StringBuilder log;


  public MockStockView(PrintStream out) {
    super(out);
    log = new StringBuilder();
  }

  @Override
  public void printWelcome() {
    log.append("Welcome to the Stock Portfolio Manager\r\n");
    super.printWelcome();
  }

  @Override
  public void printMenuNoPortfolios() {
    log.append("1) Create A Portfolio\r\n");
    log.append("2) Quit\r\n");
    super.printMenuNoPortfolios();
  }

  @Override
  public void printMenu(Portfolio portfolio) {
    log.append("Please Select An Option:\r\n");
    log.append("You Have " + portfolio.getPortfolioSize() + " Items In Your Portfolio\r\n");
    log.append("1) Add/Remove Item to Portfolio\r\n");
    log.append("2) View Portfolio Contents\r\n");
    log.append("3) Analyze Stocks\r\n");
    log.append("4) View Portfolio Value\r\n");
    log.append("5) Change/Create Portfolio\r\n");
    log.append("6) Exit\r\n");
    super.printMenu(portfolio);
  }

  @Override
  public void printPortfolioMaker() {
    super.printPortfolioMaker();
  }

  @Override
  public void printPortfolioChanger(String[] portfolios) {
    super.printPortfolioChanger(portfolios);
  }

  @Override
  public void printSpecifyStock() {
    super.printSpecifyStock();
  }

  @Override
  public void printAddOrSellStock() {
    super.printAddOrSellStock();
  }

  @Override
  public void printSuccessfulTransaction() {
    super.printSuccessfulTransaction();
  }

  @Override
  public void printViewStocks(Portfolio portfolio) {
    super.printViewStocks(portfolio);
  }

  @Override
  public void printChooseStockOption() {
    super.printChooseStockOption();
  }

  @Override
  public void printSpecifyStartDate() {
    super.printSpecifyStartDate();
  }

  @Override
  public void printSpecifyEndDate() {
    super.printSpecifyEndDate();
  }

  @Override
  public void displayError(String error) {
    super.displayError(error);
  }

  @Override
  public void displayFarewell() {
    super.displayFarewell();
  }

  @Override
  public void printPortfolioValuePrompt() {
    super.printPortfolioValuePrompt();
  }

  @Override
  public void printXValue() {
    super.printXValue();
  }

  @Override
  public void printResultOfAverage(double num) {
    super.printResultOfAverage(num);
  }

  public String getOutput() {
    return log.toString();
  }

}