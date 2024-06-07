package src.controller;

import src.model.Portfolio;
import src.model.StockModel;
import src.view.StockView;

import java.util.Date;
import java.util.Scanner;
/**
 * Class representing the controller for the Stock Portfolio Manager.
 */
public class StockController implements StockControllerInterface {
  private StockModel model;
  private StockView view;
  private Scanner scan;
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
    exitProgram();
  }

  private void createMenu(Portfolio portfolio) {
    view.printMenu(portfolio);

    while (!exit) {
      int choice = getValidatedUserChoice(1, 5);

      switch (choice) {
        case 1:
          handleTransaction();
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
          createMenu(portfolio);
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
        // handleViewStockCrossovers();
        break;
      case 4:
        createMenu(portfolio);
        break;
      default:
        view.displayError("Invalid Input. Please Try Again.");
        this.handleAnalyzeStocks();
    }
  }

  private void handleViewStockMovingAverage() {
    view.printSpecifyStockToTransact();
    String ticker = getUserInput();

    if (!ticker.equalsIgnoreCase("Quit")) {
      model.getStock(ticker);
      view.printSpecifyStartDate();
      String date = getUserInput();

    String[] dateParts = date.split("-");
    Date dateObj = new Date(Integer.parseInt(dateParts[0]) - 1900, Integer.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[2]));
   

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

  private void handleTransaction() {
    view.printSpecifyStockToTransact();
    String ticker = getUserInput();

    //If we still don't quit, show the add or sell prompt, and split the answer into twos
    //based on a space.
    if (!ticker.equalsIgnoreCase("Quit")) {
      view.printAddOrSellStock();
      String transaction = getUserInput();
      String[] parts = transaction.split(":");

      //if the split has two parts we keep going
      if (parts.length == 2) {
        String action = parts[0];

        //If the first split is either Buy or Sell, we do the appropriate actions.
        try {
          int quantity = Integer.parseInt(parts[1]);

          //if input equals buy, add it to the portfolio.
          if (action.equalsIgnoreCase("Buy")) {
            model.addStockToPortfolio(ticker, portfolio, quantity);
            view.printSuccessfulTransaction();
            createMenu(portfolio);

            //if input equals sell, remove it to the portfolio.
          } else if (action.equalsIgnoreCase("Sell")) {
            model.sellStockFromPortfolio(ticker, portfolio, quantity);
            view.printSuccessfulTransaction();
            createMenu(portfolio);

            //Didn't say sell or buy.
          } else {
            handleError("Invalid action. Please enter 'Buy' or 'Sell'.");
          }
          //invalid number
        } catch (NumberFormatException e) {
          handleError("Invalid quantity. Please enter a valid number.");
        }
        //Overall ass formatting
      } else {
        System.out.println("transaction: " + transaction);
        System.out.println(parts);
        handleError("Invalid input format. Please enter 'Buy:<quantity>' or 'Sell:<quantity>'.");
      }
    } else {
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

  private void exitProgram() {
    view.displayFarewell();
  }
}