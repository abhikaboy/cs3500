package src.view;

/**
 * Interface representing the View component of this program following the MVC architecture.
 */
public interface StockViewInterface {

  void printWelcome();

  void printPortfolioMaker();

  void printPortfolioChanger(String[] portfolioNames);

  void printPortfolioValuePrompt();

  void printPortfolioValueResult(Double value);

  void printChooseStockOption();

  void printSpecifyStock();

  void printAddOrSellStock();

  void printSuccessfulTransaction();

  void printSpecifyStartDate();

  void printSpecifyEndDate();

  void printStockPerformance(Double change);

  void printXValue();

  void printResultOfAverage(double average);

  void displayError(String msg);

  void displayFarewell();

  void printSpecifyQuantity();

  void printSpecifyDate();

}
