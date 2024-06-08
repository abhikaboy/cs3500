package src;

import src.controller.StockController;
import src.model.StockModel;
import src.view.StockView;

/**
 * Main class to initiate the program.
 */
public class Main {

  /**
   * Main method to initiate the program.
   */
  public static void main(String[] args) {
    StockModel poop = new StockModel();
    StockView piss = new StockView(System.out);
    // poop.getStock("GOOG");
    for (String s : poop.getPortfolioNames()) {
      System.out.println(s);
    }
    StockController controller = new StockController(poop, piss);
    controller.control();
  }
}