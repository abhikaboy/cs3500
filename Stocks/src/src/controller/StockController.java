package src.controller;

import src.helper.DateFormat;
import src.model.Portfolio;
import src.model.StockModel;
import src.view.StockView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
  private HashMap<String, TransactionOption> transactionOptions;


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

    this.transactionOptions = new HashMap<String, TransactionOption>();
    // buy option parameters hashmap
    HashMap<String, String> buyParameters = new HashMap<>();
    buyParameters.put("Quantity", "Number of shares to Buy");

    transactionOptions.put("Buy",
     new TransactionOption( () -> model.addStockToPortfolio(
      handleSpecifyStock(), portfolio, getNumberShares())  ,
     "Buy",  buyParameters));

    // sell option parameters hashmap
    HashMap<String, String> sellParameters = new HashMap<>();
    sellParameters.put("Quantity", "Number of shares to Sell");

    transactionOptions.put("Sell",
     new TransactionOption( () -> model.sellStockFromPortfolio(
      handleSpecifyStock(), portfolio, getNumberShares())  ,
     "Sell",  sellParameters));

     // buy from specific date option parameters hashmap
    HashMap<String, String> buyFromDateParameters = new HashMap<>();
    buyFromDateParameters.put("Quantity", "Number of shares to Buy");
    buyFromDateParameters.put("Date", "Date to Buy Stock");

    transactionOptions.put("BuyOnDate",
     new TransactionOption( () -> model.addStockToPortfolio(
      handleSpecifyStock(), portfolio, getNumberShares(), handleSpecifyDate())  ,
     "BuyOnDate",  buyFromDateParameters));

    // sell from specific date option parameters hashmap
    HashMap<String, String> sellFromDateParameters = new HashMap<>();    
    buyFromDateParameters.put("Quantity", "Number of shares to Sell");
    sellFromDateParameters.put("Date", "Date to Sell Stock");

    transactionOptions.put("SellOnDate",
     new TransactionOption( () -> model.sellStockFromPortfolio(
      handleSpecifyStock(), portfolio, getNumberShares(), handleSpecifyDate())  ,
     "SellOnDate",  sellFromDateParameters));


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
    HashMap<Integer, Runnable> menuOptions = new HashMap<>();
    menuOptions.put(1, () -> handleTransaction());
    menuOptions.put(2, () -> handleViewPortfolioContents());
    menuOptions.put(3, () -> handleAnalyzeStocks());
    menuOptions.put(4, () -> handleViewPortfolioValue());
    menuOptions.put(5, () -> handleChangePortfolio(true));
    menuOptions.put(6, () -> handleViewPortfolioOnSpecificDate());
    menuOptions.put(7, () -> savePortfolio());
    menuOptions.put(8, () -> view.exitProgram());

    while (!exit) {
      int choice = getValidatedUserChoice(1, menuOptions.size());
      if(menuOptions.containsKey(choice)){
        menuOptions.get(choice).run();
        if(choice == menuOptions.size()){
          exit = true;
        }
      } else {
          view.displayError("Invalid Input. Please Try Again.");
      }
    }
  }

  private void savePortfolio() {
    view.savePortfolio(portfolio.getName());
    model.writePortfolioToFile(portfolio);
    createMenu(portfolio);
  }

  private void handleError(String msg) {
    view.displayError(msg);
  }

  private void handleCreatePortfolio() {
    view.printPortfolioMaker();
    String name = getUserInput();

    if (name.equalsIgnoreCase("Quit")) {

      //If you try quitting at the menu without even making a portfolio
      if (portfolio == null) {
        return;
      } else {
        createMenu(portfolio);
      }

    } else {
      model.addPortfolio(name);
      portfolio = model.getPortfolio(name);
    }
  }

  private void handleViewPortfolioValue() {
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
    String ticker = handleSpecifyStock();

    if (!ticker.equalsIgnoreCase("Quit")) {
      model.getStock(ticker);

      view.printSpecifyStartDate();
      String startDate = handleDate();

      view.printSpecifyEndDate();
      String endDate = handleDate();

      view.printXValue();
      int x = getValidatedUserChoice(1, 100);

      view.printResultOfCrossOver(model.xDayCrossoverDays(ticker, x, startDate, endDate));
    }
    createMenu(portfolio);
  }

  private void handleViewStockMovingAverage() {
    String ticker = handleSpecifyStock();

    if (!ticker.equalsIgnoreCase("Quit")) {
      model.getStock(ticker);
      view.printSpecifyStartDate();
      String date = handleDate();
      Date dateObj = DateFormat.toDate(date);
      view.printXValue();
      int x = getValidatedUserChoice(1, 100);
      view.printResultOfAverage(model.getStockMovingAverage(ticker, dateObj, x));
    }
    createMenu(portfolio);
  }

  // Prompt user for a ticker symbol and a start and end date;
  // use model to display the change/performance of the stock over that period.
  private void handleViewStockPerformance() {
    String ticker = handleSpecifyStock();

    if (!ticker.equalsIgnoreCase("Quit")) {
      model.getStock(ticker);

      view.printSpecifyStartDate();
      String startDate = handleDate();

      view.printSpecifyEndDate();
      String endDate = handleDate();

      view.printStockPerformance(model.getStockChange(ticker, startDate, endDate));
    }
    createMenu(portfolio);

  }


  private int getNumberShares(){
    int shares = 0;
    view.printSpecifyQuantity();
    while (true) {
      String sharesString = getUserInput();
      try {
        shares = Integer.parseInt(sharesString);
        if (shares < 0) {
          throw new NumberFormatException();
        }
        break;
      } catch (NumberFormatException e) {
        view.displayError("Invalid input. Please enter a valid number.");
      }
    }
    return shares;
  }

  private void handleTransaction(){
    view.printTransactionOptions(this.transactionOptions);
    String option = getUserInput();

    transactionOptions.get(option).execute();
    view.printSuccessfulTransaction();
    createMenu(portfolio);
  }
  // Abstract this method 

  
  private void handleChangePortfolio(boolean menu) {
    while (true) {
      String[] portfolioNames = model.getPortfolioNames();
      view.printPortfolioChanger(portfolioNames);

      int choice = getValidatedUserChoice(1, portfolioNames.length + 1);

      if (choice == portfolioNames.length + 1) {
        handleCreatePortfolio();
        // After creating a new portfolio, continue the loop to update the list and re-prompt

      } else {
        portfolio = model.getPortfolio(portfolioNames[choice - 1]);
        break;
      }
    }

    if (menu) {
      createMenu(portfolio);
    }
  }

  private String handleSpecifyStock() {
    view.printSpecifyStock();
    String ticker = getUserInput();

    if (ticker.equalsIgnoreCase("Quit")) {
      createMenu(portfolio);
      return null;
    } else {
      try {
        if (model.isValidTicker(ticker)) {
          return ticker;
        } else {
          handleError("Invalid ticker symbol. " + ticker);
          return handleSpecifyStock();  // Prompt again for valid ticker
        }
      } catch (Exception e) {
        handleError("An error occurred while validating the ticker symbol.");
        createMenu(portfolio);
        return null;
      }
    }
  }

  private String handleSpecifyDate() {
    view.printSpecifyDate();
    return handleDate();
  }

  private String handleDate() {
    String date = getUserInput();
    if (date.equalsIgnoreCase("Quit")) {
      createMenu(portfolio);
      return null;
    } else {
      try {
        if (StockModel.isValidDate(date)) {
          return date;
        } else {
          handleError("Invalid date format. Please enter the date in YYYY-MM-DD format.");
          return handleDate();  // Prompt again
        }
      } catch (Exception e) {
        handleError("An error occurred while validating the date.");
        createMenu(portfolio);
        return null;
      }
    }
  }

  private void handleExitProgram() {
    view.displayFarewell();
  }

  private void handleViewPortfolioOnSpecificDate() {
  }


}