package src.controller;

import src.helper.DateFormat;
import src.model.Portfolio;
import src.model.StockModel;
import src.view.StockView;

import java.util.Date;
import java.util.Scanner;
/**
 * Class representing the controller for the Stock Portfolio Manager.
 */
public class StockController implements StockControllerInterface {
  private final StockModel model;
  private final StockView view;
  private final Scanner scan;
  private Portfolio portfolio;
  private boolean exit;


  /**
   * Constructor representing the controller for the Stock Portfolio Manager.
   *
   * @param model the Model state the controller is utilizing.
   * @param view  the text-based display state.
   */
  public StockController(StockModel model, StockView view) {
    this.model = model;
    this.view = view;
    this.scan = new Scanner(System.in);
    this.portfolio = null;
    this.exit = false;
  }

  private String getUserInput() {
    return scan.next();
  }

  private int getUserChoice() {
    while (!scan.hasNextInt()) {
      scan.next();
      handleError("Invalid input. Please enter a number.");
    }
    return scan.nextInt();
  }

  private int getValidatedUserChoice(int lowerb, int upperb) {
    int choice;
    while (true) {
      choice = getUserChoice();
      if (choice >= lowerb && choice <= upperb) {
        break;
      } else {
        handleError("Invalid Input. Please Try Again.");
      }
    }
    return choice;
  }

  /**
   * This is the overall control method for the Stock Portfolio Software.
   * When the program starts, this is called.
   */
  public void control() {
    this.view.printWelcome();

    while (!exit) {
      if (model.countPortfolios() == 0) {
        view.printMenuNoPortfolios();
        int choice = getValidatedUserChoice(1, 2);

        switch (choice) {
          case 1:
            handleCreatePortfolio();
            break;
          case 2:
            exit = true;
            break;
          default:
            view.displayError("Invalid Input. Please Try Again.");
        }
      } else {
        handleChangePortfolio(true);
      }
    }
    handleExitProgram();
  }

  private void createMenu(Portfolio portfolio) {
    view.printMenu(portfolio);

    while (!exit) {
      int choice = getValidatedUserChoice(1, 6);

      switch (choice) {
        case 1:
          handleTransaction(null);
          break;
        case 2:
          handleViewPortfolioContents();
          break;
        case 3:
          handleAnalyzeStocks();
          break;
        case 4:
          handleViewPortfolioValue();
          break;
        case 5:
          handleChangePortfolio(true);
          break;
        case 6:
          exit = true;
          break;
        default:
          view.displayError("Invalid Input. Please Try Again.");
      }
    }
  }

  private void handleError(String msg) {
    view.displayError(msg);
  }

  private void handleCreatePortfolio() {
    view.printPortfolioMaker();
    String name = getUserInput();

    if (name.equalsIgnoreCase("Quit")) {
      createMenu(portfolio);
    } else {
      model.addPortfolio(name);
      portfolio = model.getPortfolio(name);

    }
  }

  private void handleViewPortfolioValue() {
    this.handleChangePortfolio(false);
    view.printPortfolioValuePrompt();
    String date = getUserInput();
    view.printPortfolioValueResult(portfolio.getPortfolioValue(date));
    createMenu(portfolio);
  }

  private void handleViewPortfolioContents() {
    view.printViewStocks(portfolio);
    createMenu(portfolio);
  }

  private void handleAnalyzeStocks(){
    view.printChooseStockOption();
    int choice = getValidatedUserChoice(1, 4);

    switch (choice) {
      case 1:
        handleViewStockPerformance();
        break;
      case 2:
        handleViewStockMovingAverage();
        break;
      case 3:
        handleViewStockCrossovers();
        break;
      case 4:
        createMenu(portfolio);
        break;
      default:
        view.displayError("Invalid Input. Please Try Again.");
        this.handleAnalyzeStocks();
    }
  }

  private void handleViewStockCrossovers() {
    view.printSpecifyStockToTransact();
    String ticker = getUserInput();

    if (!ticker.equalsIgnoreCase("Quit")) {
      model.getStock(ticker);

      String startDate;
      while (true) {
        view.printSpecifyStartDate();
        startDate = getUserInput();
        if (StockModel.isValidDate(startDate)) {
          break;
        } else {
          handleError("Invalid start date format. Please enter the date in YYYY-MM-DD format.");
        }
      }

      String endDate;
      while (true) {
        view.printSpecifyEndDate();
        endDate = getUserInput();
        if (StockModel.isValidDate(endDate)) {
          break;
        } else {
          handleError("Invalid end date format. Please enter the date in YYYY-MM-DD format.");
        }
      }

      view.printXValue();
      int x = getValidatedUserChoice(1, 100);
      view.printResultOfCrossOver(model.xDayCrossoverDays(ticker, x, startDate, endDate));
    }
    createMenu(portfolio);
  }

  private void handleViewStockMovingAverage() {
    view.printSpecifyStockToTransact();
    String ticker = getUserInput();

    if (!ticker.equalsIgnoreCase("Quit")) {
      model.getStock(ticker);
      view.printSpecifyStartDate();
      String date = getUserInput();
      Date dateObj = DateFormat.toDate(date);
      view.printXValue();
      int x = getValidatedUserChoice(1, 100);
      view.printResultOfAverage(model.getStockMovingAverage(ticker, dateObj, x));
    }
    createMenu(portfolio);
  }

  // Prompt user for a ticker symbol and a start and end date; use model to display the change/performance
  // of the stock over that period.
  private void handleViewStockPerformance() {
    view.printSpecifyStockToTransact();
    String ticker = getUserInput();

    if (!ticker.equalsIgnoreCase("Quit")) {
      model.getStock(ticker);
      view.printSpecifyStartDate();
      String startDate = getUserInput();

      view.printSpecifyEndDate();
      String endDate = getUserInput();

      view.printStockPerformance(model.getStockChange(ticker, startDate, endDate));
    }
    createMenu(portfolio);

  }

  private void handleTransaction(String ticker) {
    if (ticker == null) {
      handleSpecifyStock();
    } else {
      view.printAddOrSellStock();
      String transaction = getUserInput();
      // Split the input to get the action and quantity
      String[] parts = transaction.split(":");

      if (parts.length == 2) {
        String action = parts[0].trim();
        try {
          int quantity = Integer.parseInt(parts[1].trim());

          if (action.equalsIgnoreCase("Buy")) {
            model.addStockToPortfolio(ticker, portfolio, quantity);
            view.printSuccessfulTransaction();
          } else if (action.equalsIgnoreCase("Sell")) {
            model.sellStockFromPortfolio(ticker, portfolio, quantity);
            view.printSuccessfulTransaction();
          } else {
            handleError("Invalid action. Please enter 'Buy' or 'Sell'.");
            handleTransaction(ticker);
          }
        } catch (NumberFormatException e) {
          handleError("Invalid quantity. Please enter a valid number.");
          handleTransaction(ticker);
        }
      } else {
        handleError("Invalid input format. Please enter 'Buy:<quantity>' or 'Sell:<quantity>'.");
        handleTransaction(ticker);
      }
      createMenu(portfolio);
    }
  }

  private void handleChangePortfolio(boolean menu) {
    String[] portfolioNames = model.getPortfolioNames();
    view.printPortfolioChanger(model.getPortfolioNames());
    int choice = getValidatedUserChoice(1, portfolioNames.length + 1);

    portfolio = model.getPortfolio(portfolioNames[choice - 1]);
    if(menu){
      createMenu(portfolio);
    }
  }

  private void handleSpecifyStock() {
    view.printSpecifyStockToTransact();
    String ticker = getUserInput();

    if (ticker.equalsIgnoreCase("Quit")) {
      createMenu(portfolio);
    } else {
      try {
        if (model.isValidTicker(ticker)) {
          handleTransaction(ticker);
        } else {
          handleError("Invalid ticker symbol.");
          handleSpecifyStock();  // Prompt again for valid ticker
        }
      } catch (Exception e) {
        handleError("An error occurred while validating the ticker symbol.");
        createMenu(portfolio);
      }
    }
  }

  private void handleExitProgram() {
    view.displayFarewell();
  }
}