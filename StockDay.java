/*============================================================================/*
| StockDay.java                                                                |
|                                                                              |
| Used by Assignment1.java                                                     |
|                                                                              |
| Creates a stock object with:                                                 |
| * ticker symbol                                                              |
| * date                                                                       |
| * opening price                                                              |
| * high price                                                                 |
| * low price                                                                  |
| * closing price                                                              |
| * volume of shares traded                                                    |
| * adjusted closing price                                                     |
|                                                                              |
| by Stephanie Mason                                                           |
/*============================================================================*/

public class StockDay {
  String ticker;
  String date;
  float openingPrice;
  float highPrice;
  float lowPrice;
  float closingPrice;
  int volumeOfShares;
  float adjustedClosingPrice;

  public StockDay( String ticker, String date, float openingPrice,
  float highPrice, float lowPrice, float closingPrice, int volumeOfShares,
  float adjustedClosingPrice ) {
    this.ticker = ticker;
    this.date = date;
    this.openingPrice = openingPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.closingPrice = closingPrice;
    this.volumeOfShares = volumeOfShares;
    this.adjustedClosingPrice = adjustedClosingPrice;
  }

  // Check for Crazy Days
  // a crazy day is defined as a day in which the price fluctiation is greater
  // than 15%
  public boolean isCrazyDay() {
    float priceDif = (highPrice-lowPrice)/highPrice;
    if (priceDif >= 0.15){
      return true;
    }
    return false;
  }

}
