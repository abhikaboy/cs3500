package src;

import java.util.Date;

public interface StockModelInterface {
    // Implement the method signature and the JDoc comment for all the public methods in the StockModel class.
    public void addStockToPortfolio(String ticker, Portfolio portfolio, int quantity);
    public void sellStockFromPortfolio(String ticker, Portfolio portfolio, int quantity);
    public double getStockMovingAverage(String ticker, Date startDate, int x);
    public double getStockChange(String ticker, String startDate, String endDate);
    public Portfolio getPortfolio(String name);
    public String[] getPortfolioNames();
    public void addPortfolio(String name);
}
