package src.controller;

import java.util.ArrayList;
import java.util.HashMap;

public class TransactionOption {
  private Runnable runnable;
  private String name;
  private HashMap<String, String> parametersLabel;

  public TransactionOption(Runnable runnable, String name, HashMap<String, String> parametersLabel) {
    this.runnable = runnable;
    this.name = name;
    this.runnable = runnable;
    this.parametersLabel = parametersLabel;
  }

  public void execute() {
    // execute the runnable with the parameters passed in
    runnable.run();
  }

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
