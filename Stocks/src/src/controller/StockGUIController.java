package src.controller;

import src.model.Portfolio;
import src.model.StockModelInterface;
import src.view.ExtendedStockViewInterface;
import src.view.StockView;

import javax.swing.JOptionPane;

public class StockGUIController extends StockController implements ExtendedStockControllerInterface {

  private final ExtendedStockViewInterface guiView;
  private Portfolio currentPortfolio;

  public StockGUIController(StockModelInterface model, ExtendedStockViewInterface guiView) {
    super(model, new StockView(System.out));
    this.guiView = guiView;
    guiView.setController(this);
    registerListeners();
    displayAllPortfolios(); // Display all portfolios at the start
  }

  private void registerListeners() {
    guiView.setCreatePortfolioButtonActionListener(e -> createPortfolio());
    guiView.setBuyButtonActionListener(e -> buyStock());
    guiView.setSellButtonActionListener(e -> sellStock());
    guiView.setQueryButtonActionListener(e -> queryPortfolio());
    guiView.setSaveButtonActionListener(e -> savePortfolio());
    guiView.setRetrieveButtonActionListener(e -> retrievePortfolio());
  }

  private void displayAllPortfolios() {
    String[] portfolioNames = getModel().getPortfolioNames();
    if (portfolioNames.length == 0) {
      guiView.showPortfolio("No portfolios available. Please create a new portfolio.");
    } else {
      StringBuilder portfoliosList = new StringBuilder("Available Portfolios:\n");
      for (String name : portfolioNames) {
        portfoliosList.append(name).append("\n");
      }
      guiView.showPortfolio(portfoliosList.toString());
    }
  }

  @Override
  public void createPortfolio() {
    guiView.printPortfolioMaker();
    String name = JOptionPane.showInputDialog("Enter portfolio name:");
    if (name != null && !name.trim().isEmpty()) {
      getModel().addPortfolio(name);
      currentPortfolio = getModel().getPortfolio(name); // Track the new portfolio
      guiView.showPortfolio("Portfolio '" + name + "' created successfully.");
      displayAllPortfolios(); // Update the portfolio list
    } else {
      guiView.showPortfolio("Portfolio name cannot be empty.");
    }
  }

  @Override
  public void buyStock() {
    if (currentPortfolio == null) {
      guiView.showPortfolio("No portfolio selected. Please create or select a portfolio first.");
      return;
    }

    String stock = handleSpecifyStock();
    if (stock == null) return;

    guiView.printSpecifyQuantity();
    String sharesStr = JOptionPane.showInputDialog("Enter number of shares:");
    if (sharesStr == null) return;

    guiView.printSpecifyDate();
    String date = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
    if (date == null) return;

    try {
      int shares = Integer.parseInt(sharesStr);
      getModel().addStockToPortfolio(stock, currentPortfolio, shares, date);
      guiView.printSuccessfulTransaction();
      showPortfolio(currentPortfolio.getName());
    } catch (NumberFormatException e) {
      guiView.showPortfolio("Invalid number of shares.");
    } catch (Exception e) {
      guiView.showPortfolio(e.getMessage());
    }
  }

  @Override
  public void sellStock() {
    if (currentPortfolio == null) {
      guiView.showPortfolio("No portfolio selected. Please create or select a portfolio first.");
      return;
    }

    String stock = handleSpecifyStock();
    if (stock == null) return;

    guiView.printSpecifyQuantity();
    String sharesStr = JOptionPane.showInputDialog("Enter number of shares:");
    if (sharesStr == null) return;

    guiView.printSpecifyDate();
    String date = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
    if (date == null) return;

    try {
      int shares = Integer.parseInt(sharesStr);
      getModel().sellStockFromPortfolio(stock, currentPortfolio, shares, date);
      guiView.printSuccessfulTransaction();
      showPortfolio(currentPortfolio.getName());
    } catch (NumberFormatException e) {
      guiView.showPortfolio("Invalid number of shares.");
    } catch (Exception e) {
      guiView.showPortfolio(e.getMessage());
    }
  }

  @Override
  public void queryPortfolio() {
    if (currentPortfolio == null) {
      guiView.showPortfolio("No portfolio selected. Please create or select a portfolio first.");
      return;
    }

    guiView.printPortfolioValuePrompt();
    String date = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
    if (date == null) return;

    try {
      Double value = getModel().getPortfolioValue(currentPortfolio, date);
      guiView.printPortfolioValueResult(value);
      showPortfolio(currentPortfolio.getName());
    } catch (Exception e) {
      guiView.showPortfolio(e.getMessage());
    }
  }

  @Override
  public void savePortfolio() {
    if (currentPortfolio == null) {
      guiView.showPortfolio("No portfolio selected. Please create or select a portfolio first.");
      return;
    }

    guiView.showPortfolio("Saving '" + currentPortfolio.getName() + "'...");

    try {
      getModel().writePortfolioToFile(currentPortfolio);
      guiView.showPortfolio("Portfolio '" + currentPortfolio.getName() + "' saved successfully.");
    } catch (Exception e) {
      guiView.showPortfolio("Error saving portfolio: " + e.getMessage());
    }
  }


  @Override
  public void retrievePortfolio() {
    String[] portfolioNames = getModel().getPortfolioNames();
    if (portfolioNames.length == 0) {
      guiView.showPortfolio("No portfolios available. Please create a new portfolio.");
      return;
    }
    String name = (String) JOptionPane.showInputDialog(
            null,
            "Select portfolio to retrieve:",
            "Retrieve Portfolio",
            JOptionPane.QUESTION_MESSAGE,
            null,
            portfolioNames,
            portfolioNames[0]
    );

    if (name == null || name.trim().isEmpty()) {
      guiView.showPortfolio("Portfolio name cannot be empty.");
      return;
    }

    try {
      currentPortfolio = getModel().getPortfolio(name);
      guiView.showPortfolio("Portfolio '" + name + "' retrieved successfully.");
      showPortfolio(currentPortfolio.getName());
    } catch (Exception e) {
      guiView.showPortfolio("Portfolio not found.");
    }
  }

  @Override
  protected String handleSpecifyStock() {
    guiView.printSpecifyStock();
    String ticker = JOptionPane.showInputDialog("Enter stock ticker symbol:");

    if (ticker == null || ticker.equalsIgnoreCase("Quit")) {
      return null;
    } else {
      try {
        if (getModel().isValidTicker(ticker)) {
          return ticker;
        } else {
          guiView.showPortfolio("Invalid ticker symbol. " + ticker);
          return handleSpecifyStock();
        }
      } catch (Exception e) {
        guiView.showPortfolio("An error occurred while validating the ticker symbol.");
        return null;
      }
    }
  }

  @Override
  protected String handleSpecifyDate() {
    guiView.printSpecifyDate();
    return JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
  }

  @Override
  protected String handleDate() {
    return JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
  }

  @Override
  protected double getUserInputAsDouble() {
    while (true) {
      String input = JOptionPane.showInputDialog("Enter a number:");
      try {
        return Double.parseDouble(input);
      } catch (NumberFormatException e) {
        guiView.showPortfolio("Invalid input. Please enter a number.");
      }
    }
  }

  public void showPortfolio(String portfolio) {
    if (currentPortfolio == null) {
      guiView.showPortfolio("No portfolio selected. Please create or select a portfolio first.");
      return;
    }

    StringBuilder portfolioDetails = new StringBuilder("Portfolio: " + portfolio + "\n");
    portfolioDetails.append("Stocks:\n");

    for (String stockName : currentPortfolio.getStockNames()) {
      portfolioDetails.append(stockName).append(": ")
              .append(currentPortfolio.getShare(stockName).getQuantity())
              .append(" shares\n");
    }

    guiView.showPortfolio(portfolioDetails.toString());
  }
}
