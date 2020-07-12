package models;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static controllers.OperationsReader.TIME_FORMAT;

public class Departure extends Operation {
  private String arrivalAirport;
  private int departureHead;
  private LocalTime STD;
  private LocalTime OBT;
  private LocalTime TOT;

  public Departure(Scanner operation) throws ParseException {
    super(operation);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_FORMAT);

    arrivalAirport = operation.next();
    STD = LocalTime.parse(operation.next(), dtf);
    OBT = LocalTime.parse(operation.next(), dtf);
    TOT = LocalTime.parse(operation.next(), dtf);
    departureHead = operation.nextInt();
    schengen = operation.next();
  }

  @Override
  public String toString() {
    return "Departure{" +
      "arrivalAirport='" + arrivalAirport + '\'' +
      ", departureHead=" + departureHead +
      ", STD=" + STD +
      ", OBT=" + OBT +
      ", TOT=" + TOT +
      "} " + super.toString();
  }
}
