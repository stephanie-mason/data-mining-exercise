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

public class Assignment1 {
  public static void main(String[] args) {
    //String inputFileName = "Stockmarket-1990-2015.txt";
    String inputFileName = "test.txt";
    BufferedReader buffRead = null;
    FileReader fileRead = null;

    //Create an ArrayList to hold crazy days
    ArrayList<StockDay> crazyDays = new ArrayList<StockDay>();

    // Read input file
    try {
      fileRead = new FileReader(inputFileName);
      buffRead = new BufferedReader(fileRead);

      String currLine;
      String currTicker = "none";
      String prevTicker = "none";
      StockDay prevDay = null;
      StockDay currDay = null;
      StockDay craziestDay = null;

      // Iterate through the lines in the file
      while ((currLine = buffRead.readLine()) != null) {

          String[] splitString = currLine.split("\t");

          // Create a StockDay Object
          String ticker = splitString[0];
          String date = splitString[1];
          float openingPrice = Float.parseFloat(splitString[2]);
          float highPrice = Float.parseFloat(splitString[3]);
          float lowPrice = Float.parseFloat(splitString[4]);
          float closingPrice = Float.parseFloat(splitString[5]);
          int volumeOfShares = Integer.parseInt(splitString[6]);
          float adjustedClosingPrice = Float.parseFloat(splitString[7]);

          currDay = new StockDay(ticker, date, openingPrice, highPrice,
          lowPrice, closingPrice, volumeOfShares, adjustedClosingPrice);
          currTicker = currDay.getTicker();
          if (prevDay != null) {
            prevDay = currDay;
            prevTicker = prevDay.getTicker();
          }



          System.out.println("prevTicker: " + prevTicker);
          System.out.println("currTicker: " + currTicker);

          if (currDay.isCrazyDay()){
            crazyDays.add(currDay);

            // Keep track of craziest day
            if (craziestDay == null)
              craziestDay = currDay;
            else if (currDay.getPriceDif() > craziestDay.getPriceDif())
              craziestDay = currDay;
          }

          prevDay = currDay;
      }

      printOutput(crazyDays, craziestDay);

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

  private static void printOutput(ArrayList<StockDay> crazyDays, StockDay craziestDay) {
    int numCrazyDays = crazyDays.size();
    for (int i = 0; i < numCrazyDays; i++) {
      StockDay thisDay = crazyDays.get(i);
      float fluctuation = thisDay.getPriceDif()*100;
      System.out.println("Crazy day: " + thisDay.getDate() + " " + String.format("%.2f", fluctuation) + "%");
    }
    System.out.println("Total crazy days: " + crazyDays.size());
    System.out.println("The craziest day: " + craziestDay.getDate());
  }

}
