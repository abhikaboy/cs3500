package src.controller;

import java.awt.event.ActionListener;

public interface ExtendedStockControllerInterface extends StockControllerInterface {
  void createPortfolio();
  void buyStock();
  void sellStock();
  void queryPortfolio();
  void savePortfolio();
  void retrievePortfolio();
}