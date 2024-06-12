package src.view;

/**
 * Interface representing the View component of this program following the MVC architecture.
 */
public interface StockViewInterface {

  public void printWelcome();

  public void printPortfolioMaker();

  public void printPortfolioChanger(String[] portfolioNames);

  public void printPortfolioValuePrompt();

  public void printPortfolioValueResult(Double value);

  public void printChooseStockOption();

  public void printSpecifyStock();

  public void printAddOrSellStock();

  public void printSuccessfulTransaction();

  public void printSpecifyStartDate();

  public void printSpecifyEndDate();

  public void printStockPerformance(Double change);

  public void printXValue();

  public void printResultOfAverage(double average);

  public void displayError(String msg);

  public void displayFarewell();

}
