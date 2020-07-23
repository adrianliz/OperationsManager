package models;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;

import java.util.Calendar;
import java.util.Date;

public class Operation {
  private static final String SCHENGEN = "SCHENGEN";

  @ExcelCell(3)
  private Date dateRaw;
  private Calendar date;
  @ExcelCell(2)
  private String aircraftTypeRaw;
  private AircraftType aircraftType;
  @ExcelCell(4)
  private String aircraftModel;
  @ExcelCell(6)
  private double aircraftMTOW; //aircraft max weight at arrival
  @ExcelCell(7)
  private String CIA; //operation company
  @ExcelCell(8)
  private String airport;
  @ExcelCellName("SCHENGEN/NO-SCHENGEN")
  private String schengen;

  public Calendar getDate() {
    if (date == null) {
      date = Calendar.getInstance();
      date.setTime(dateRaw);
    }

    return date;
  }

  public AircraftType getAircraftType() {
    if (aircraftType == null) {
      aircraftType = AircraftType.getAircraftType(aircraftTypeRaw);
    }

    return aircraftType;
  }

  public String getAircraftModel() {
    return aircraftModel;
  }

  public double getAircraftMTOW() {
    return aircraftMTOW;
  }

  public String getCIA() {
    return CIA;
  }

  public String getAirport() {
    return airport;
  }

  public boolean isSchengen() {
    return schengen.equals(SCHENGEN);
  }
}
