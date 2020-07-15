package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import controllers.Config;

public abstract class Operation {
  protected UUID idOperation;
  protected String use;
  protected String type;
  protected Date date;
  protected String schengen;
  protected Aircraft aircraft;

  private Config config;

  public Operation(Scanner operation, Config config) throws ParseException {
    idOperation = UUID.randomUUID();
    use = operation.next();
    type = operation.next();
    String aircraftType = operation.next();
    date = new SimpleDateFormat(config.getString(Config.DATE_FORMAT)).parse(operation.next());

    aircraft = new Aircraft(aircraftType, operation.next(), operation.next(),
                            Double.parseDouble(operation.next()), operation.next());
    this.config = config;
  }

  @Override
  public String toString() {
    return "Operation{" +
      "idOperation=" + idOperation +
      ", use='" + use + '\'' +
      ", type='" + type + '\'' +
      ", date=" + new SimpleDateFormat(config.getString(Config.DATE_FORMAT)).format(date) +
      ", schengen='" + schengen + '\'' +
      ", aircraft=" + aircraft +
      '}';
  }
}
