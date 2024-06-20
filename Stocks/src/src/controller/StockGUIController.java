package src.controller;

import src.helper.DateFormat;
import src.model.Portfolio;
import src.model.Share;
import src.model.StockModel;
import src.model.StockModelInterface;
import src.view.ExtendedStockViewInterface;
import src.view.StockView;

import javax.swing.*;
import java.util.Date;

/**
 * The StockGUIController class extends the StockController class to provide
 * a graphical user interface (GUI) controller for the Stock Portfolio Manager application.
 * This class handles user interactions through a GUI and updates the view accordingly.
 */
public class StockGUIController extends StockController implements ExtendedStockControllerInterface {

  private final ExtendedStockViewInterface guiView;
  private Portfolio currentPortfolio;

  /**
   * Constructor for the StockGUIController.
   *
   * @param model   the Model state the controller is utilizing.
   * @param guiView the graphical user interface view.
   */
  public StockGUIController(StockModelInterface model, ExtendedStockViewInterface guiView) {
    super(model, new StockView(System.out));
    this.guiView = guiView;
    guiView.setController(this);
    registerListeners();
    displayAllPortfolios();
  }

  /**
   * Registers the action listeners for the GUI buttons.
   */
  private void registerListeners() {
    guiView.setCreatePortfolioButtonActionListener(e -> createPortfolio());
    guiView.setBuyButtonActionListener(e -> buyStock());
    guiView.setSellButtonActionListener(e -> sellStock());
    guiView.setQueryButtonActionListener(e -> queryPortfolio());
    guiView.setSaveButtonActionListener(e -> savePortfolio());
    guiView.setRetrieveButtonActionListener(e -> retrievePortfolio());
    guiView.setSaveAndQuitButtonActionListener(e -> saveAndQuit());
  }

  /**
   * Displays all available portfolios in the GUI.
   */
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

  /**
   * Creates a new portfolio.
   */
  @Override
  public void createPortfolio() {
    String name = getInput("Create Portfolio", "Enter portfolio name:");
    if (name != null && !name.trim().isEmpty()) {
      getModel().addPortfolio(name);
      currentPortfolio = getModel().getPortfolio(name); // Track the new portfolio
      guiView.showPortfolio("Portfolio '" + name + "' created successfully.");
      displayAllPortfolios(); // Update the portfolio list
    } else {
      guiView.showPortfolio("Portfolio name cannot be empty.");
    }
  }

  /**
   * Buys stock for the current portfolio.
   */
  @Override
  public void buyStock() {
    if (currentPortfolio == null) {
      guiView.showPortfolio("No portfolio selected. Please create or select a portfolio first.");
      return;
    }

    String stock = handleSpecifyStock();
    if (stock == null) return;

    String sharesStr = getInput("Buy Stock", "Enter number of shares:");
    if (sharesStr == null) return;

    String date = handleSpecifyDate();
    if (date == null) return;

    try {
      int shares = Integer.parseInt(sharesStr);
      getModel().addStockToPortfolio(stock, currentPortfolio, shares, date);
      guiView.printSuccessfulTransaction();
      guiView.showPortfolio(getPortfolioDetails(currentPortfolio));
    } catch (NumberFormatException e) {
      guiView.showPortfolio("Invalid number of shares.");
    } catch (Exception e) {
      guiView.showPortfolio(e.getMessage());
    }
  }

  /**
   * Sells stock from the current portfolio.
   */
  @Override
  public void sellStock() {
    if (currentPortfolio == null) {
      guiView.showPortfolio("No portfolio selected. Please create or select a portfolio first.");
      return;
    }

    String stock = handleSpecifyStock();
    if (stock == null) return;

    String sharesStr = getInput("Sell Stock", "Enter number of shares:");
    if (sharesStr == null) return;

    String date = handleSpecifyDate();
    if (date == null) return;

    try {
      int shares = Integer.parseInt(sharesStr);
      getModel().sellStockFromPortfolio(stock, currentPortfolio, shares, date);
      guiView.printSuccessfulTransaction();
      guiView.showPortfolio(getPortfolioDetails(currentPortfolio));
    } catch (NumberFormatException e) {
      guiView.showPortfolio("Invalid number of shares.");
    } catch (Exception e) {
      guiView.showPortfolio(e.getMessage());
    }
  }

  /**
   * Queries the portfolio value at a specified date.
   */
  @Override
  public void queryPortfolio() {
    if (currentPortfolio == null) {
      guiView.showPortfolio("No portfolio selected. Please create or select a portfolio first.");
      return;
    }

    String dateInput = handleSpecifyDate();
    if (dateInput == null) return;

    try {
      Date date = DateFormat.toDate(dateInput);
      Double value = getModel().getPortfolioValue(currentPortfolio, DateFormat.toString(date));
      guiView.printPortfolioValueResult(value);
      guiView.showPortfolio(getPortfolioDetailsOnDate(currentPortfolio, date));
    } catch (Exception e) {
      guiView.showPortfolio("Error: " + e.getMessage());
    }
  }

  /**
   * Gets the details of the portfolio at a specified date.
   *
   * @param portfolio the portfolio to get details from.
   * @param date the date to get the portfolio details at.
   * @return the string representation of the portfolio details at the specified date.
   */
  private String getPortfolioDetailsOnDate(Portfolio portfolio, Date date) {
    StringBuilder details = new StringBuilder("Portfolio: " + portfolio.getName() + "\n");

    for (String stock : portfolio.getStockNames()) {
      try {
        Share share = portfolio.getShare(stock);
        double quantity = share.getQuantityOnDate(DateFormat.toString(date));  // Get quantity on the specific date
        double price = share.getPriceOnDate(DateFormat.toString(date));  // Get price on the specific date
        double value = quantity * price;
        details.append("Stock: ").append(stock)
                .append(", Quantity: ").append(quantity)
                .append(", Price on ").append(date).append(": ").append(price)
                .append(", Value: ").append(value).append("\n");
      } catch (RuntimeException e) {
        details.append("Error retrieving details for stock: ").append(stock).append("\n");
      }
    }

    double totalValue = portfolio.getPortfolioValue(DateFormat.toString(date));
    details.append("Total Portfolio Value: ").append(totalValue).append("\n");

    return details.toString();
  }

  /**
   * Saves the current portfolio.
   */
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

  /**
   * Saves the current portfolio and exits the application.
   */
  private void saveAndQuit() {
    if (currentPortfolio == null) {
      guiView.showPortfolio("No portfolio selected. Please create or select a portfolio first.");
      return;
    }

    guiView.showPortfolio("Saving '" + currentPortfolio.getName() + "'...");

    try {
      getModel().writePortfolioToFile(currentPortfolio);
      guiView.showPortfolio("Portfolio '" + currentPortfolio.getName() + "' saved successfully.");
      System.exit(0); // Exit the program
    } catch (Exception e) {
      guiView.showPortfolio("Error saving portfolio: " + e.getMessage());
    }
  }

  /**
   * Retrieves a portfolio based on user selection.
   */
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
      guiView.showPortfolio(getPortfolioDetails(currentPortfolio));
    } catch (Exception e) {
      guiView.showPortfolio("Portfolio not found.");
    }
  }

  /**
   * Handles specifying the stock from user input.
   *
   * @return the stock ticker symbol specified by the user.
   */
  @Override
  protected String handleSpecifyStock() {
    String ticker = getInput("Specify Stock", "Enter stock ticker symbol:");

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

  /**
   * Handles specifying the date from user input.
   *
   * @return the date specified by the user.
   */
  @Override
  protected String handleSpecifyDate() {
    return handleDate();
  }

  /**
   * Handles getting the date from user input.
   *
   * @return the date specified by the user.
   */
  @Override
  protected String handleDate() {
    while (true) {
      String date = getInput("Specify Date", "Enter date (YYYY-MM-DD):");
      if (date == null || date.equalsIgnoreCase("Quit")) {
        return null;
      } else if (StockModel.isValidDate(date)) {
        return date;
      } else {
        guiView.showPortfolio("Invalid date format. Please enter the date in YYYY-MM-DD format.");
      }
    }
  }

  /**
   * Handles getting a user input as a double value.
   *
   * @return the double value inputted by the user.
   */
  @Override
  protected double getUserInputAsDouble() {
    while (true) {
      String input = getInput("Specify Number", "Enter a number:");
      try {
        return Double.parseDouble(input);
      } catch (NumberFormatException e) {
        guiView.showPortfolio("Invalid input. Please enter a number.");
      }
    }
  }

  /**
   * Gets the details of the current portfolio.
   *
   * @param portfolio the portfolio to get details from.
   * @return the string representation of the portfolio details.
   */
  private String getPortfolioDetails(Portfolio portfolio) {
    StringBuilder details = new StringBuilder("Portfolio: " + portfolio.getName() + "\n");

    for (String stock : portfolio.getStockNames()) {
      try {
        Share share = portfolio.getShare(stock);
        double quantity = share.getQuantity();
        double latestPrice = share.getLatestPrice();
        details.append("Stock: ").append(stock)
                .append(", Quantity: ").append(quantity)
                .append(", Latest Price Per Share: ").append(latestPrice).append("\n");
      } catch (RuntimeException e) {
        details.append("Error retrieving details for stock: ").append(stock).append("\n");
      }
    }

    double totalValue = portfolio.getPortfolioValue();
    details.append("Total Portfolio Value: ").append(totalValue).append("\n");

    return details.toString();
  }

  /**
   * Set the portfolio
   * @param portfolio portfolio to change to.
   */
  public void setCurrentPortfolio(Portfolio portfolio) {
    this.currentPortfolio = portfolio;
  }

  /**
   * Get the portfolio the controller is currently examining.
   * @return Portfolio currently in the controller field.
   */
  public Portfolio getCurrentPortfolio() {
    return this.currentPortfolio;
  }

  /**
   * Helper method to get user input with a message.
   *
   * @param title   the title of the input dialog.
   * @param message the message to display in the input dialog.
   * @return the user input as a string.
   */
  private String getInput(String title, String message) {
    return JOptionPane.showInputDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
  }
}
