package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import static controllers.OperationsReader.TIME_FORMAT;

public class Landing extends Operation {
  enum ParkingType {HA, P, C, HE}

  private String departureAirport;
  private int landingHead;
  private ParkingType parking;
  private LocalTime STA;
  private LocalTime LDT;
  private LocalTime IBT;

  public Landing(Scanner operation) throws ParseException {
    super(operation);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_FORMAT);

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

