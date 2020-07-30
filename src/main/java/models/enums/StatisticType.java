package models.enums;

public enum StatisticType {
  AIRCRAFT_TYPE("Estadística Tipo Aeronave"),
  MTOW_AVERAGE("Estadística Media MTOW"),
  SCHENGEN_OP("Estadística Operaciones Schengen"),
  OP_EVOLUTION("Estadística Evolución Operaciones"),
  HEADER_RUNWAY_OP("Estadística Operaciones Cabecera");

  private final String statisticType;

  StatisticType(final String statisticType) {
    this.statisticType = statisticType;
  }

  @Override
  public String toString() {
    return statisticType;
  }
}
