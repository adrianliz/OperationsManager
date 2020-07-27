package models;

import java.util.regex.Pattern;

public enum AircraftType {
  GRAN_TAMANIO("AERONAVES GRAN TAMAÑO"),
  JET("AVIACIÓN PRIVADA TIPO JET"),
  HELICOPTEROS("HELICÓPTEROS"),
  ESTADO("AERONAVES DE ESTADO"),
  MILITAR("MILITAR"),
  GENERAL("AVIACIÓN GENERAL"),
  OTRO("OTRO");

  private final String aircraftType;
  private final static Pattern granTamanioExp = Pattern.compile(".*GRAN.*");
  private final static Pattern jetExp = Pattern.compile(".*JET.*");
  private final static Pattern helicopteroExp = Pattern.compile(".*HELI.*");
  private final static Pattern estadoExp = Pattern.compile(".*ESTADO.*");
  private final static Pattern militarExp = Pattern.compile(".*MILITAR.*");
  private final static Pattern generalExp = Pattern.compile(".*GENERAL.*");

  AircraftType(final String aircraftType) {
    this.aircraftType = aircraftType;
  }

  public static AircraftType getAircraftType(String s) {
    if (s != null) {
      if (granTamanioExp.matcher(s).matches()) {
        return GRAN_TAMANIO;
      } else if (jetExp.matcher(s).matches()) {
        return JET;
      } else if (helicopteroExp.matcher(s).matches()) {
        return HELICOPTEROS;
      } else if (estadoExp.matcher(s).matches()) {
        return ESTADO;
      } else if (militarExp.matcher(s).matches()) {
        return MILITAR;
      } else if (generalExp.matcher(s).matches()) {
        return GENERAL;
      }
    }

    return OTRO;
  }

  @Override
  public String toString() {
    return aircraftType;
  }
}
