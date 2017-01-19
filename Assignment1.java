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

public class Assignment1 {
  public static void main(String[] args) {
    //String inputFileName = "Stockmarket-1990-2015.txt";
    String inputFileName = "test.txt";
    BufferedReader buffRead = null;
    FileReader fileRead = null;

    // Read input file
    try {
      fileRead = new FileReader(inputFileName);
      buffRead = new BufferedReader(fileRead);

      String currLine;
      StockDay prevDay = null;
      StockDay currDay = null;

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

          if (prevDay != null) {
            prevDay = currDay;
          }
          currDay = new StockDay(ticker, date, openingPrice, highPrice,
          lowPrice, closingPrice, volumeOfShares, adjustedClosingPrice);

          if (prevDay != null) {}
          System.out.println("Result: " + currDay.checkCrazyDay(prevDay, currDay));
      }









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

}
