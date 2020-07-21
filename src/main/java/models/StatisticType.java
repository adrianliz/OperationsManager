package models;

public enum StatisticType {
  SCHENGEN("CLASIFICAR POR SCHENGEN"),
  TIPO_AERONAVE("CLASIFICAR POR TIPO AERONAVE");

  private final String statisticType;

  StatisticType(final String statisticType) {
    this.statisticType = statisticType;
  }

  public static StatisticType getStatisticType(String s) {
    for (StatisticType statisticType: StatisticType.values()) {
      if (statisticType.toString().equals(s)) {
        return statisticType;
      }
    }

    return null;
  }

  @Override
  public String toString() {
    return statisticType;
  }
}
