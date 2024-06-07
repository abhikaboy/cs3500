package src;

import src.controller.StockController;
import src.model.StockModel;
import src.view.StockView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    StockModel poop = new StockModel();
    StockView piss = new StockView(System.out);
    // poop.getStock("GOOG");
    for(String s : poop.getPortfolioNames()){
      System.out.println(s);
    }
    StockController controller = new StockController(poop, piss);
    controller.control();
  }
}