package src;

import java.util.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    StockModel poop = new StockModel();
    // poop.getStock("GOOG");
    poop.addPortfolio("poop");
    poop.addPortfolio("two");
    poop.addPortfolio("three");
    poop.addStockToPortfolio("GOOG", "poop");
    poop.addStockToPortfolio("AAPL", "poop");
    System.out.println(poop.getPortfolio("poop"));
    System.out.println(poop.getPortfolioValue("poop"));
    System.out.println(poop.getStockChange("GOOG", "2024-05-06", "2024-06-06"));
    System.out.println("moving average: " + poop.getStockMovingAverage("GOOG", new Date(), 100));
    System.out.println(poop.countPortfolios());
    for(String s : poop.getPortfolioNames()){
      System.out.println(s);
    }
  }
}