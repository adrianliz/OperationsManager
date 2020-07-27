package models;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;

import java.util.Calendar;
import java.util.Date;

public class Operation {
  public enum Schengen {SCHENGEN, NO_SCHENGEN}

  @ExcelCell(3)
  private Date dateRaw;
  private Calendar date;
  @ExcelCell(2)
  private String aircraftTypeRaw;
  private AircraftType aircraftType;
  @ExcelCell(6)
  private double aircraftMTOW; //aircraft max weight at arrival
  @ExcelCellName("SCHENGEN/NO-SCHENGEN")
  private String schengen;

  public Calendar getDate() {
    if (date == null) {
      if (dateRaw != null) {
        date = Calendar.getInstance();
        date.setTime(dateRaw);
      }
    }

    return date;
  }

  public AircraftType getAircraftType() {
    if (aircraftType == null) {
      aircraftType = AircraftType.getAircraftType(aircraftTypeRaw);
    }

    return aircraftType;
  }

  public double getAircraftMTOW() {
    return aircraftMTOW;
  }

  public boolean isSchengen() {
    if (schengen != null) {
      return schengen.equals(Schengen.SCHENGEN.toString());
    }
    return false;
  }
}
