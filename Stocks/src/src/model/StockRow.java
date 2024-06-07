package src.model;

/**
 * Testing comment for push, replace it.
 */
public class StockRow {
  private double open;
  private double high;
  private double low;
  private double close;
  public StockRow(double open, double high, double low, double close){
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
  }

  public double getOpen() {
    return open;
  }

  public double getHigh() {
    return high;
  }

  public double getLow() {
    return low;
  }

  public double getClose() {
    return close;
  }
  
}
