package models;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import controllers.Config;

public class Landing extends Operation {
  enum ParkingType {HA, P, C, HE}

  private String departureAirport;
  private int landingHead;
  private ParkingType parking;
  private LocalTime STA;
  private LocalTime LDT;
  private LocalTime IBT;

  public Landing(Scanner operation, Config config) throws ParseException {
    super(operation, config);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(config.getString(Config.TIME_FORMAT));

    departureAirport = operation.next();
    STA = LocalTime.parse(operation.next(), dtf);
    LDT = LocalTime.parse(operation.next(), dtf);
    IBT = LocalTime.parse(operation.next(), dtf);
    landingHead = operation.nextInt();
    parking = ParkingType.valueOf(operation.next());
    schengen = operation.next();
  }

  @Override
  public String toString() {
    return "Landing{" +
      "departureAirport='" + departureAirport + '\'' +
      ", landingHead=" + landingHead +
      ", parking=" + parking +
      ", STA=" + STA +
      ", LDT=" + LDT +
      ", IBT=" + IBT +
      "} " + super.toString();
  }
}

