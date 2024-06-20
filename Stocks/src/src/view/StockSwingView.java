package src.view;

import src.controller.StockControllerInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The StockSwingView class implements the ExtendedStockViewInterface and provides a graphical
 * user interface for the Stock Portfolio Manager application.
 * It is responsible for displaying the user interface components and interacting with the user.
 */
public class StockSwingView extends JFrame implements ExtendedStockViewInterface {
  private StockControllerInterface controller;
  private JTextArea displayArea;
  private JButton createPortfolioButton, buyButton, sellButton, queryButton,
          saveButton, retrieveButton, saveAndQuitButton;

  /**
   * Constructor to initialize the GUI components of the StockSwingView.
   */
  public StockSwingView() {
    setTitle("Stock Portfolio Manager");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    displayArea = new JTextArea();
    displayArea.setEditable(false);
    add(new JScrollPane(displayArea), BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 4)); // Adjusted for additional button

    createPortfolioButton = new JButton("Create Portfolio");
    buyButton = new JButton("Buy Stock");
    sellButton = new JButton("Sell Stock");
    queryButton = new JButton("Query Portfolio");
    saveButton = new JButton("Save Portfolio");
    retrieveButton = new JButton("Retrieve Portfolio");
    saveAndQuitButton = new JButton("Save and Quit"); // New button

    buttonPanel.add(createPortfolioButton);
    buttonPanel.add(buyButton);
    buttonPanel.add(sellButton);
    buttonPanel.add(queryButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(retrieveButton);
    buttonPanel.add(saveAndQuitButton); // Add new button to the panel

    add(buttonPanel, BorderLayout.SOUTH);

    setVisible(true);
  }

  /**
   * Displays the specified portfolio details in the text area.
   *
   * @param portfolio the portfolio details to display.
   */
  @Override
  public void showPortfolio(String portfolio) {
    displayArea.setText(portfolio);
  }

  /**
   * Displays a message dialog with the specified message.
   *
   * @param message the message to display.
   */
  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Sets the controller for the view.
   *
   * @param controller the controller to set.
   */
  public void setController(StockControllerInterface controller) {
    this.controller = controller;
  }

  @Override
  public void printSpecifyQuantity() {
    displayMessage("Please specify the quantity of shares.");
  }

  @Override
  public void printSpecifyDate() {
    displayMessage("Please specify the date (YYYY-MM-DD).");
  }

  @Override
  public void printSpecifyPortfolioName() {
    displayMessage("Please enter the portfolio name.");
  }

  @Override
  public void printWelcome() {
    displayMessage("Welcome to the Stock Portfolio Manager!");
  }

  @Override
  public void printPortfolioMaker() {
    displayMessage("Please create a new portfolio.");
  }

  @Override
  public void printPortfolioChanger(String[] portfolioNames) {
    StringBuilder message = new StringBuilder("Select a portfolio to change:\n");
    for (int i = 0; i < portfolioNames.length; i++) {
      message.append((i + 1)).append(". ").append(portfolioNames[i]).append("\n");
    }
    displayMessage(message.toString());
  }

  @Override
  public void printPortfolioValuePrompt() {
    displayMessage("Enter the date to query portfolio value.");
  }

  @Override
  public void printPortfolioValueResult(Double value) {
    displayMessage("The value of the portfolio is: " + value);
  }

  @Override
  public void printChooseStockOption() {
    displayMessage("Choose an option for the stock.");
  }

  @Override
  public void printSpecifyStock() {
    displayMessage("Specify the stock details.");
  }

  @Override
  public void printAddOrSellStock() {
    displayMessage("Do you want to add or sell stock?");
  }

  @Override
  public void printSuccessfulTransaction() {
    displayMessage("Transaction completed successfully.");
  }

  @Override
  public void printSpecifyStartDate() {
    displayMessage("Specify the start date.");
  }

  @Override
  public void printSpecifyEndDate() {
    displayMessage("Specify the end date.");
  }

  @Override
  public void printStockPerformance(Double change) {
    displayMessage("The stock performance change is: " + change);
  }

  @Override
  public void printXValue() {
    displayMessage("Enter the X value.");
  }

  @Override
  public void printResultOfAverage(double average) {
    displayMessage("The result of the average is: " + average);
  }

  @Override
  public void displayError(String msg) {
    displayMessage("Error: " + msg);
  }

  @Override
  public void displayFarewell() {
    displayMessage("Goodbye!");
  }

  /**
   * Sets the action listener for the Create Portfolio button.
   *
   * @param listener the action listener to set.
   */
  public void setCreatePortfolioButtonActionListener(ActionListener listener) {
    createPortfolioButton.addActionListener(listener);
  }

  /**
   * Sets the action listener for the Buy Stock button.
   *
   * @param listener the action listener to set.
   */
  public void setBuyButtonActionListener(ActionListener listener) {
    buyButton.addActionListener(listener);
  }

  /**
   * Sets the action listener for the Sell Stock button.
   *
   * @param listener the action listener to set.
   */
  public void setSellButtonActionListener(ActionListener listener) {
    sellButton.addActionListener(listener);
  }

  /**
   * Sets the action listener for the Query Portfolio button.
   *
   * @param listener the action listener to set.
   */
  public void setQueryButtonActionListener(ActionListener listener) {
    queryButton.addActionListener(listener);
  }

  /**
   * Sets the action listener for the Save Portfolio button.
   *
   * @param listener the action listener to set.
   */
  public void setSaveButtonActionListener(ActionListener listener) {
    saveButton.addActionListener(listener);
  }

  /**
   * Sets the action listener for the Retrieve Portfolio button.
   *
   * @param listener the action listener to set.
   */
  public void setRetrieveButtonActionListener(ActionListener listener) {
    retrieveButton.addActionListener(listener);
  }

  /**
   * Sets the action listener for the Save and Quit button.
   *
   * @param listener the action listener to set.
   */
  public void setSaveAndQuitButtonActionListener(ActionListener listener) {
    saveAndQuitButton.addActionListener(listener);
  }
}
