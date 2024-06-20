package src;

import src.controller.StockController;
import src.controller.StockGUIController;
import src.model.StockModel;
import src.model.StockModelInterface;
import src.view.StockView;
import src.view.StockSwingView;

import javax.swing.SwingUtilities;

/**
 * Main class to initiate the program.
 */
public class Main {

  /**
   * Main method to initiate the program.
   */
  public static void main(String[] args) {
    StockModelInterface model = new StockModel();

    if (args.length > 0) {
      String viewType = args[0];

      if (viewType.equalsIgnoreCase("text")) {

        StockView textView = new StockView(System.out);
        StockController textController = new StockController(model, textView);
        textController.control();
      } else if (viewType.equalsIgnoreCase("gui")) {

        SwingUtilities.invokeLater(() -> {
          StockSwingView guiView = new StockSwingView();
          StockGUIController guiController = new StockGUIController(model, guiView);
          guiView.setVisible(true);
          guiView.printWelcome();
        });
      } else {
        System.out.println("Invalid option." +
                " Use 'text' for text-based view or 'gui' for graphical user interface.");
      }
    } else {
      System.out.println("Please specify the view type as a command-line argument:" +
              " 'text' or 'gui'.");
    }
  }
}
