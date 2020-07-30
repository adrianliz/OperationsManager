package models.enums;

public enum StatisticType {
  AIRCRAFT_TYPE("Estadística Tipo Aeronave"),
  MTOW_AVERAGE("Estadística Media MTOW"),
  SCHENGEN_OP("Estadística Schengen");

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
