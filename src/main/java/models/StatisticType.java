package models;

public enum StatisticType {
  SCHENGEN("Estadística Schengen"),
  TIPO_AERONAVE("Estadística Tipo Aeronave"),
  MEDIA_MTOW("Estadística Media MTOW");

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
