package src.model;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

public class GraphModel {
  private Date startDate;
  private Date endDate;
  private Portfolio portfolio;
  private String portfolioName;

  public GraphModel(Date startDate, Date endDate, Portfolio portfolio, String portfolioName) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.portfolio = portfolio;
    this.portfolioName = portfolioName;

    // Determine number of lines to draw in the graph [between 5 and 30] based on start and end date
    int numLines = (int) Math.round(Math.abs(startDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24));
    if (numLines < 5) {
      numLines = 5;
    } else if (numLines > 30) {
      numLines = 30;
    }
    
    // TODO 
    // Determine how many number of lines to draw 
    // Determine the scale of the graph 
    // Draw the graph
  }

  // given a maximum and minimum value, print how many starts are between the two
}
