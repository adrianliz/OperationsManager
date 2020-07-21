package models;

public enum StatisticType {
  SCHENGEN("SCHENGEN"),
  TIPO_AERONAVE("TIPO AERONAVE"),
  NINGUNA("NINGUNA");

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

    return StatisticType.NINGUNA;
  }

  @Override
  public String toString() {
    return statisticType;
  }
}
