package models;

import java.util.Date;
import java.util.Scanner;

public class LandingOperation {
  enum ParkingType {HA, P, C, HE};

  private String airportUse;
  private String operationType;
  private String aircraftClasification;
  private Date date;
  private String aircraftType;
  private String numberPlate;
  private int MTOW; //kg
  private String CIA;
  private String departureAirport;
  private Date STA;
  private Date LDT;
  private Date IBT;
  private Double landingHead;
  private ParkingType parkingType;
  private boolean schengen;

  public LandingOperation(Scanner landingOperation) {

  }

  @Override
  public String toString() {
    return "LandingOperation{" +
      "airportUse='" + airportUse + '\'' +
      ", operationType='" + operationType + '\'' +
      ", aircraftClasification='" + aircraftClasification + '\'' +
      ", date=" + date +
      ", aircraftType='" + aircraftType + '\'' +
      ", numberPlate='" + numberPlate + '\'' +
      ", MTOW=" + MTOW +
      ", CIA='" + CIA + '\'' +
      ", departureAirport='" + departureAirport + '\'' +
      ", STA=" + STA +
      ", LDT=" + LDT +
      ", IBT=" + IBT +
      ", landingHead=" + landingHead +
      ", parkingType=" + parkingType +
      ", schengen=" + schengen +
      '}';
  }
}
