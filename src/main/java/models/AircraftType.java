package models;

public enum AircraftType {
  GRAN_TAMANIO("AERONAVES GRAN TAMAÑO"),
  JET("JET"),
  HELICOPTEROS("HELICÓPTEROS"),
  ESTADO("AERONAVES DE ESTADO"),
  GENERAL("AVIACIÓN GENERAL");

  private final String aircraftType;

  AircraftType(final String aircraftType) {
    this.aircraftType = aircraftType;
  }

  public static AircraftType getAircraftType(String s) {
    for (AircraftType aircraftType: AircraftType.values()) {
      if (aircraftType.toString().equals(s)) {
        return aircraftType;
      }
    }

    return null;
  }

  @Override
  public String toString() {
    return aircraftType;
  }
}
