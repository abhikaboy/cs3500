package src.controller;

/**
 * Interface representing the controller aspect of the MVC (Model-View-Controller) architecture
 * for the Stock Portfolio Manager application.
 *
 * Responsibilities:
 * - Define the primary control method to start and manage the application flow.
 * - Serve as a blueprint for implementing controllers in the Stock Portfolio Manager application.
 *
 * Key Method:
 * - control(): The main method to initiate the application logic and user interaction loop.
 *
 * Implementing classes should provide the logic for initializing the application,
 * handling user input, navigating menus, and coordinating actions between the model and the view.
 */
public interface StockControllerInterface {

  public void control();
  
} 
