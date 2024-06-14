package src.controller;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that represents a transaction command on the menu
 * 
 */
public class TransactionOption {
  private Runnable runnable;
  private String name;
  private HashMap<String, String> parametersLabel;

  /**
   * Constructor for a new transaction option.
   *
   * @param runnable     The runnable to execute when the option is selected.
   * @param name         The name of the option.
   * @param parametersLabel A map of the parameters for the option.
   */
  public TransactionOption(Runnable runnable, String name, HashMap<String, String> parametersLabel) {
    this.runnable = runnable;
    this.name = name;
    this.runnable = runnable;
    this.parametersLabel = parametersLabel;
  }

  /**
   * Execute the runnable with the parameters passed in.
   */
  public void execute() {
    // execute the runnable with the parameters passed in
    runnable.run();
  }

  /**
   * A user friendly string representation of the option.
   *
   * @return friendly string representation of the option.
   */
  
  public String toString() {
    String ret = '[' + name + ']';
    for (String key : parametersLabel.keySet()) {
      ret += " <";
      ret += key + ": " + parametersLabel.get(key);
      ret += ">;";
    }
    ret += System.lineSeparator();
    return ret;
  }
}
