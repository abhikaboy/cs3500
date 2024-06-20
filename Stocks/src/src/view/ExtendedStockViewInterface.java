package src.view;

import src.controller.StockControllerInterface;
import src.controller.StockGUIController;

import java.awt.event.ActionListener;

public interface ExtendedStockViewInterface extends StockViewInterface {

  void showPortfolio(String portfolio);

  void displayMessage(String message);

  void printSpecifyPortfolioName();

  void setCreatePortfolioButtonActionListener(ActionListener listener);

  void setBuyButtonActionListener(ActionListener listener);

  void setSellButtonActionListener(ActionListener listener);

  void setQueryButtonActionListener(ActionListener listener);

  void setSaveButtonActionListener(ActionListener listener);

  void setRetrieveButtonActionListener(ActionListener listener);

  void setController(StockControllerInterface stockGUIController);
}