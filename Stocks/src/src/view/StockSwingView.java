package src.view;

import src.controller.StockControllerInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StockSwingView extends JFrame implements ExtendedStockViewInterface {
  private StockControllerInterface controller;
  private JTextArea displayArea;
  private JButton createPortfolioButton, buyButton, sellButton, queryButton, saveButton, retrieveButton, saveAndQuitButton;

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

  @Override
  public void showPortfolio(String portfolio) {
    displayArea.setText(portfolio);
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
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

  public void setCreatePortfolioButtonActionListener(ActionListener listener) {
    createPortfolioButton.addActionListener(listener);
  }

  public void setBuyButtonActionListener(ActionListener listener) {
    buyButton.addActionListener(listener);
  }

  public void setSellButtonActionListener(ActionListener listener) {
    sellButton.addActionListener(listener);
  }

  public void setQueryButtonActionListener(ActionListener listener) {
    queryButton.addActionListener(listener);
  }

  public void setSaveButtonActionListener(ActionListener listener) {
    saveButton.addActionListener(listener);
  }

  public void setRetrieveButtonActionListener(ActionListener listener) {
    retrieveButton.addActionListener(listener);
  }

  public void setSaveAndQuitButtonActionListener(ActionListener listener) {
    saveAndQuitButton.addActionListener(listener);
  }
}
