package models.enums;

import java.util.regex.Pattern;

public enum AircraftType {
  BIG("AERONAVES GRAN TAMAÑO", ".*GRAN.*"),
  JET("AVIACIÓN PRIVADA TIPO JET", ".*JET.*"),
  HELICOPTER("HELICÓPTEROS", ".*HELI.*"),
  STATE("AERONAVES DE ESTADO", ".*ESTADO.*"),
  MILITARY("MILITAR", ".*MILITAR.*"),
  GENERAL("AVIACIÓN GENERAL", ".*GENERAL.*"),
  OTHER("OTRO", "");

  private final String aircraftType;
  private final String regexp;

  AircraftType(String aircraftType, String regexp) {
    this.aircraftType = aircraftType;
    this.regexp = regexp;
  }

  public static AircraftType getAircraftType(String s) {
    for (AircraftType aircraftType : AircraftType.values()) {
      if (Pattern.compile(aircraftType.regexp()).matcher(s).matches()) {
        return aircraftType;
      }
    }

    return OTHER;
  }

  private String regexp() {
    return regexp;
  }

  @Override
  public String toString() { return aircraftType; }
}
