/*============================================================================/*
| Assignment1.java                                                             |
|                                                                              |
| CSCI 330 - Winter 2017                                                       |
|                                                                              |
| Given a tsv input file containing stock market data, this program identifies |
| * crazy days (when there are high price fluctuations in a single day)        |
| * craziest days (the day for which the price fluctuation was highest)        |
| * stock splits of 2:1, 3:1, and 3:2 ratios                                   |
|                                                                              |
| by Stephanie Mason                                                           |
/*============================================================================*/

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

public class Assignment1 {
  public static void main(String[] args) {
    String inputFileName = "Stockmarket-1990-2015.txt";
    BufferedReader buffRead = null;
    FileReader fileRead = null;

    // Read input file
    try {
      fileRead = new FileReader(inputFileName);
      buffRead = new BufferedReader(fileRead);

      ArrayList<Split> splits = new ArrayList<Split>();
      ArrayList<StockDay> crazyDays = new ArrayList<StockDay>();
      String currLine;
      String currTicker = "none";
      String prevTicker = "none";
      StockDay prevDay = null;
      StockDay currDay = null;
      StockDay craziestDay = null;

      // Iterate through the lines in the file
      while ((currLine = buffRead.readLine()) != null) {
        String[] splitString = currLine.split("\t");

        // Create a StockDay Object for the current line
        String ticker = splitString[0];
        String date = splitString[1];
        float openingPrice = Float.parseFloat(splitString[2]);
        float highPrice = Float.parseFloat(splitString[3]);
        float lowPrice = Float.parseFloat(splitString[4]);
        float closingPrice = Float.parseFloat(splitString[5]);
        int volumeOfShares = Integer.parseInt(splitString[6]);
        float adjustedClosingPrice = Float.parseFloat(splitString[7]);

        // Update holder variables
        currDay = new StockDay(ticker, date, openingPrice, highPrice,
        lowPrice, closingPrice, volumeOfShares, adjustedClosingPrice);
        currTicker = currDay.getTicker();
        if (prevDay != null) {
          prevTicker = prevDay.getTicker();
        }

        // Check for new company ticker
        // If the company ticker has changed, print the output and reset vars
        if (!prevTicker.equals(currTicker) && !prevTicker.equals("none")) {
          System.out.println("Processing " + prevTicker);

          printOutput(splits, crazyDays, craziestDay);
          splits = new ArrayList<Split>();
          crazyDays = new ArrayList<StockDay>();
          craziestDay = null;

          System.out.println();
        }

        // Check for crazy days
        if (currDay.isCrazyDay()){
          crazyDays.add(currDay);

          // Keep track of craziest day
          if (craziestDay == null)
          craziestDay = currDay;
          else if (currDay.getPriceDif() > craziestDay.getPriceDif())
          craziestDay = currDay;
        }

        // Check for splits
        // Keep in mind that in this case, prevDay is the day on the previous
        // line, but because the days are listed in revers chronological order
        // prevDay is actually the following day
        if (prevDay != null) {
          float currClosePrice = currDay.getClosingPrice();
          float prevOpenPricePrice = prevDay.getOpeningPrice();
          boolean didSplit = false;
          String splitType = "none";

          // 2:1 split
          if (Math.abs((currClosePrice/prevOpenPricePrice) - 2.0) < 0.05) {
            didSplit = true;
            splitType = "2:1";
          }
          // 3:1 split
          if (Math.abs((currClosePrice/prevOpenPricePrice) - 3.0) < 0.05) {
            didSplit = true;
            splitType = "3:1";
          }
          // 3:2 split
          if (Math.abs((currClosePrice/prevOpenPricePrice) - 1.5) < 0.05) {
            didSplit = true;
            splitType = "3:2";
          }

          if (didSplit == true) {
            Split currSplit = new Split(
              splitType, currDay.getDate(), currClosePrice, prevOpenPricePrice
            );
            splits.add(currSplit);
          }
        }

        prevDay = currDay;
      }

      // Print output for final company
      System.out.println("Processing " + prevTicker);
      printOutput(splits, crazyDays, craziestDay);
      System.out.println();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {

      try {
        if (buffRead !=null)
        buffRead.close();

        if (fileRead != null)
        fileRead.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  // This function prints the output for a given stock ticker to the screen
  private static void printOutput(ArrayList<Split> splits,
  ArrayList<StockDay> crazyDays, StockDay craziestDay) {
    int numCrazyDays = crazyDays.size();
    int numSplits = splits.size();

    System.out.println("===================================\n");

    for (int i = 0; i < numCrazyDays; i++) {
      StockDay thisDay = crazyDays.get(i);
      float fluctuation = thisDay.getPriceDif()*100;

      System.out.println("Crazy day: "
        + thisDay.getDate() + " "
        + String.format("%.2f", fluctuation)
        + "%"
      );
    }
    System.out.println("Total crazy days: " + numCrazyDays);
    if (numCrazyDays != 0)
    System.out.println("The craziest day: "
      + craziestDay.getDate() + " "
      + String.format("%.2f", craziestDay.getPriceDif()*100) + "%"
    );

    System.out.println();

    for (int i = 0; i < numSplits; i++) {
      Split thisSplit = splits.get(i);
      System.out.println(thisSplit.getType() + " split on "
        + thisSplit.getDate() + "\t"
        + String.format("%.2f", thisSplit.getClosingPrice()) + " --> "
        + String.format("%.2f", thisSplit.getOpeningPrice())
      );
    }
    System.out.println("Total number of splits: " + numSplits);
  }

}
