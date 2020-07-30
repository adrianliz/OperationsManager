package models.enums;

import java.util.regex.Pattern;

public enum AircraftType {
  BIG("AERONAVES GRAN TAMAÑO"),
  JET("AVIACIÓN PRIVADA TIPO JET"),
  HELICOPTER("HELICÓPTEROS"),
  STATE("AERONAVES DE ESTADO"),
  MILITARY("MILITAR"),
  GENERAL("AVIACIÓN GENERAL"),
  OTHER("OTRO");

  private final String aircraftType;
  private final static Pattern BIG_REGEXP = Pattern.compile(".*GRAN.*");
  private final static Pattern JET_REGEXP = Pattern.compile(".*JET.*");
  private final static Pattern HELICOPTER_REGEXP = Pattern.compile(".*HELI.*");
  private final static Pattern STATE_REGEXP = Pattern.compile(".*ESTADO.*");
  private final static Pattern MILITARY_REGEXP = Pattern.compile(".*MILITAR.*");
  private final static Pattern GENERAL_REGEXP = Pattern.compile(".*GENERAL.*");

  AircraftType(final String aircraftType) {
    this.aircraftType = aircraftType;
  }

  public static AircraftType getAircraftType(String s) {
    if (s != null) {
      if (BIG_REGEXP.matcher(s).matches()) {
        return BIG;
      } else if (JET_REGEXP.matcher(s).matches()) {
        return JET;
      } else if (HELICOPTER_REGEXP.matcher(s).matches()) {
        return HELICOPTER;
      } else if (STATE_REGEXP.matcher(s).matches()) {
        return STATE;
      } else if (MILITARY_REGEXP.matcher(s).matches()) {
        return MILITARY;
      } else if (GENERAL_REGEXP.matcher(s).matches()) {
        return GENERAL;
      }
    }

    return OTHER;
  }

  @Override
  public String toString() {
    return aircraftType;
  }
}
