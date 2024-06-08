package src.view;

import src.model.Portfolio;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Mock class for testing, represents the View class.
 */
public class MockStockView extends StockView {
  private ByteArrayOutputStream outContent;

  public MockStockView(ByteArrayOutputStream outContent) {
    super(new PrintStream(outContent));
    this.outContent = outContent;
  }

  public String getOutput() {
    return outContent.toString();
  }

  @Override
  public void printWelcome() {
    super.printWelcome();
  }

  @Override
  public void printMenuNoPortfolios() {
    super.printMenuNoPortfolios();
  }

  @Override
  public void printMenu(Portfolio portfolio) {
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
  public void printSpecifyStockToTransact() {
    super.printSpecifyStockToTransact();
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

}