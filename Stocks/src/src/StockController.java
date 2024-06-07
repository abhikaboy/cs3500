package src;

import java.util.Scanner;
// TODO: I Kinda did my own thing for the controller, but I realised I kind of messed it up
//  I did not Implement choices 2 and 3 yet:
//  - View Stocks
//  - View Portfolio Value
//  Also, there is a problem with my method of setting up control, because whenever you change
//  the portfolio, it increases the amount of times you are told farewell when quitting, the base
//  times is 2 for a reason I cant find out.
//  Choice 1: To purchase/sell stocks also just doesn't work. It'll always say its wrong. I prob
//  Just messed up in separating the String and number but I'm unable to see in my current state.
//  Finally: There's a lot of public methods with no comments and style errors:
//  and we got no tests... :(
/**
 * Class representing the controller for the Stock Portfolio Manager.
 */
public class StockController {
  StockModel model;
  StockView view;
  Scanner scan;
  Portfolio portfolio;
  boolean exit;


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
          handleViewStocks();
          break;
        case 3:
          handleViewPortfolioValue();
          break;
        case 4:
          handleChangePortfolio(true);
          break;
        case 5:
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

  private void handleViewStocks(){
    view.printChooseStockOption();
    int choice = getValidatedUserChoice(1, 4);

    switch (choice) {
      case 1:
        handleViewStockPerformance();
        break;
      case 2:
        // handleViewStockMovingAverage();
        break;
      case 3:
        // handleViewStockCrossovers();
        break;
      case 4:
        createMenu(portfolio);
        break;
      default:
        view.displayError("Invalid Input. Please Try Again.");
        this.handleViewStocks();
    }
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
      createMenu(portfolio);
    } else {
      createMenu(portfolio);
    }
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