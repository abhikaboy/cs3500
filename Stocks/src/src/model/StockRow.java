package src.model;

/**
 * Class that represents a stock row in the Stock Portfolio Manager.
 */
public class StockRow {
  private double open;
  private double high;
  private double low;
  private double close;

  /**
   * Constructor for a new stock row.
   *
   * @param open  Open price.
   * @param high  High price.
   * @param low   Low price.
   * @param close Close price.
   */
  public StockRow(double open, double high, double low, double close) {
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
  }

  /**
   * Get the open price.
   *
   * @return The open price.
   */
  public double getOpen() {
    return open;
  }

  /**
   * Get the high price.
   *
   * @return The high price.
   */
  public double getHigh() {
    return high;
  }

  /**
   * Get the low price.
   *
   * @return The low price.
   */
  public double getLow() {
    return low;
  }

  /**
   * Get the close price.
   *
   * @return The close price.
   */
  public double getClose() {
    return close;
  }

}
