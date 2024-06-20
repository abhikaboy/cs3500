package src.view;

import org.junit.Before;
import org.junit.Test;

import src.controller.StockControllerInterface;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing class for the StockSwingView class.
 */
public class StockSwingViewTest {

  private StockSwingView view;

  @Before
  public void setUp() {
    view = new StockSwingView();
  }

  @Test
  public void testInitialSetup() {
    assertEquals("Stock Portfolio Manager", view.getTitle());
    assertEquals(800, view.getSize().width);
    assertEquals(600, view.getSize().height);
    assertTrue(view.getDefaultCloseOperation() == JFrame.EXIT_ON_CLOSE);

    Container contentPane = view.getContentPane();
    assertEquals(BorderLayout.class, contentPane.getLayout().getClass());

    JScrollPane scrollPane = (JScrollPane) contentPane.getComponent(0);
    JTextArea displayArea = (JTextArea) scrollPane.getViewport().getView();
    assertEquals(false, displayArea.isEditable());

    JPanel buttonPanel = (JPanel) contentPane.getComponent(1);
    assertEquals(GridLayout.class, buttonPanel.getLayout().getClass());
    assertEquals(7, buttonPanel.getComponentCount());
  }

  @Test
  public void testShowPortfolio() {
    String portfolioDetails = "Portfolio: Test Portfolio\nStock: AAPL, Shares: 100\nStock: GOOG, Shares: 50\n";
    view.showPortfolio(portfolioDetails);

    JTextArea displayArea = (JTextArea) ((JScrollPane) view.getContentPane().getComponent(0)).getViewport().getView();
    assertEquals(portfolioDetails, displayArea.getText());
  }

  @Test
  public void testDisplayMessage() {
    String message = "Test Message";
    displayDialogMessage(() -> view.displayMessage(message), message);
  }

  @Test
  public void testDisplayError() {
    String errorMessage = "Test Error Message";
    displayDialogMessage(() -> view.displayError(errorMessage), "Error: " + errorMessage);
  }

  @Test
  public void testDisplayFarewell() {
    String farewellMessage = "Goodbye!";
    displayDialogMessage(() -> view.displayFarewell(), farewellMessage);
  }

  @Test
  public void testSetController() {
    StockControllerInterface mockController = new StockControllerInterface() {
      @Override
      public void control() {}
    };

    view.setController(mockController);
    assertEquals(mockController, view.controller);
  }


  private void displayDialogMessage(Runnable action, String expectedMessage) {
    SwingUtilities.invokeLater(() -> {
      action.run();
      JOptionPane pane = getOptionPane(view);
      assertEquals(expectedMessage, pane.getMessage());
      closeJOptionPane(pane);
    });
  }

  private JOptionPane getOptionPane(Component parent) {
    if (parent instanceof JOptionPane) {
      return (JOptionPane) parent;
    } else if (parent instanceof Container) {
      Component[] children = ((Container) parent).getComponents();
      for (Component child : children) {
        JOptionPane pane = getOptionPane(child);
        if (pane != null) {
          return pane;
        }
      }
    }
    return null;
  }

  private void closeJOptionPane(JOptionPane pane) {
    Window window = SwingUtilities.getWindowAncestor(pane);
    if (window != null) {
      window.dispose();
    }
  }
}
