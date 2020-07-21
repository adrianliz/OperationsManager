package models;

public enum AircraftType {
  GRAN_TAMANIO("GRAN TAMAÑO"),
  JET("JET"),
  HELICOPTEROS("HELICÓPTEROS"),
  ESTADO("AERONAVES DE ESTADO"),
  GENERAL("AVIACIÓN GENERAL"),
  OTRO("OTRO");

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

    return AircraftType.OTRO;
  }

  @Override
  public String toString() {
    return aircraftType;
  }
}
