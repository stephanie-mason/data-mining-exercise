/*============================================================================/*
| Split.java                                                                   |
|                                                                              |
| Used by Assignment1.java                                                     |
|                                                                              |
| Creates a split object which contains:                                       |
| * type of split (2:1, 3:1, or 3:2)                                           |
| * date                                                                       |
| * closing price of day x                                                     |
| * opening price of day x+1                                                   |                                                                 |
|                                                                              |
| by Stephanie Mason                                                           |
/*============================================================================*/

public class Split {
  String type;
  String date;
  float closingPrice;
  float openingPrice;

  public Split(String type, String date, float closingPrice,
  float openingPrice) {
    this.type = type;
    this.date = date;
    this.closingPrice = closingPrice;
    this.openingPrice = openingPrice;
  }

  // Methods to return attributes
  public String getType() {
    return this.type;
  }
  public String getDate() {
    return this.date;
  }
  public float getClosingPrice() {
    return this.closingPrice;
  }
  public float getOpeningPrice() {
    return this.openingPrice;
  }
}
