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
    StockModel model = new StockModel();
    StockView view = new StockView(System.out);
    StockController controller = new StockController(model, view);
    controller.control();
  }
}