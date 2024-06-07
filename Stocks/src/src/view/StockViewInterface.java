package src.view;

public interface StockViewInterface {
    // Implement the method signature and the JDoc comment for all the public methods in the StockView class.
    public void printWelcome();
    public void printPortfolioMaker();
    public void printPortfolioChanger(String[] portfolioNames);
    public void printPortfolioValuePrompt();
    public void printPortfolioValueResult(Double value);
    public void printChooseStockOption();
    public void printSpecifyStockToTransact();
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
