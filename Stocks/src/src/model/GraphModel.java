package src.model;

import java.util.Date;

import src.helper.DateFormat;

import java.util.ArrayList;

/**
 * A class that represents a graph model for a portfolio.
 * Is able to generate a graph of the portfolio values over time.
 */

public class GraphModel {
  private Portfolio portfolio;
  private double valueRange;
  private ArrayList<Date> dates;
  private ArrayList<Double> values;
  private double minValue;
  private int maxStars;

  /**
   * Constructor for a new graph model.
   *
   * @param startDate The start date of the graph.
   * @param endDate   The end date of the graph.
   * @param portfolio The portfolio to graph.
   */
  public GraphModel(Date startDate, Date endDate, Portfolio portfolio) {
    this.portfolio = portfolio;
    this.dates = new ArrayList<>();
    this.values = new ArrayList<>();
    this.minValue = 0;
    this.maxStars = 50;


    // Determine number of lines to draw in the graph [between 5 and 30] based on start and end date
    // compute the range in days between start and end date
    long range = endDate.getTime() / (24 * 60 * 60 * 1000)
            - startDate.getTime() / (24 * 60 * 60 * 1000);
    // determine if scale should be in days, weeks, months, years, or decades

    int scale = determineScale(range);
    // ms in a day
    long msPerDay = 24 * 60 * 60 * 1000;
    Date currentDate = new Date(startDate.getTime());
    while (currentDate.before(endDate)) {
      this.dates.add(new Date(currentDate.getTime()));
      values.add(portfolio.getPortfolioValue(DateFormat.toString(currentDate)));
      currentDate.setTime(currentDate.getTime() + msPerDay * scale);
    }

    // find the maximum and minimum value in the values arraylist
    double minValue = Double.MAX_VALUE;
    double maxValue = Double.MIN_VALUE;
    for (int i = 0; i < values.size(); i++) {
      if (values.get(i) < minValue) {
        minValue = values.get(i);
      }
      if (values.get(i) > maxValue) {
        maxValue = values.get(i);
      }
    }
    this.valueRange = maxValue - minValue;
    this.minValue = minValue;

  }

  private String convertToStars(double value, double minValue, double starValue) {
    String stars = "*";
    for (int i = 0; i < (value - minValue) / starValue; i++) {
      stars += "*";
    }
    return stars;
  }

  /**
   * Get the bar graph as a string.
   * Complete with title, scale, and values
   *
   * @return The graph as a string.
   */
  public String getGraph() {
    // the max value shuold be 20 *
    // the min value should be 1 * 
    // the value of each * should be range / 20
    double starValue = this.valueRange / this.maxStars;
    StringBuilder sb = new StringBuilder();
    sb.append(portfolio.getName() + " Portfolio Values over time.\n");

    for (int i = 0; i < this.dates.size(); i++) {
      sb.append(this.dates.get(i) + "| " + convertToStars(this.values.get(i),
              this.minValue, starValue) + "\n");
    }
    sb.append("Each * represents " + starValue + " with the minimum value being " + this.minValue);
    return sb.toString();
  }

  private int determineScale(long range) {
    // maximize the length of the graph 
    // 1 day increments = maximum of 30 days 
    // 2 days increments = maximum of 60 days
    return (int) range / 30 + 1;
  }

  // given a maximum and minimum value, print how many starts are between the two
}
