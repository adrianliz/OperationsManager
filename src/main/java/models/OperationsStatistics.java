package models;

import models.enums.AircraftType;
import models.enums.Trimester;

import java.util.*;

public class OperationsStatistics {
  private final List<Operation> operations;
  private final Map<Integer, List<Operation>> cachedYearOperations;

  public OperationsStatistics(List<Operation> operations) {
    this.operations = operations;
    cachedYearOperations = new HashMap<>();
  }

  private List<Operation> getOperationsIn(int year) {
    List<Operation> operations;
    if ((operations = cachedYearOperations.get(year)) != null) {
      return operations;
    }

    operations = new ArrayList<>();
    for (Operation operation : this.operations) {
      Calendar date = operation.getDate();

      if (date != null) {
        if (date.get(Calendar.YEAR) == year) {
          operations.add(operation);
        }
      }
    }

    cachedYearOperations.put(year, operations);
    return operations;
  }

  private List<Operation> filterOperationsBetween(List<Operation> operations, int firstMonth, int lastMonth) {
    List<Operation> betweenOperations = new ArrayList<>();

    for (Operation operation : operations) {
      Calendar date = operation.getDate();

      if (date != null) {
        int month = date.get(Calendar.MONTH);
        if ((month >= firstMonth) && (month <= lastMonth)) {
          betweenOperations.add(operation);
        }
      }
    }

    return betweenOperations;
  }

  private List<Operation> getOperationsIn(int year, Trimester trimester) {
    List<Operation> yearOperations = getOperationsIn(year);
    List<Operation> trimesterOperations = new ArrayList<>();

    switch (trimester) {
      case FIRST -> trimesterOperations = filterOperationsBetween(yearOperations, Calendar.JANUARY, Calendar.MARCH);
      case SECOND -> trimesterOperations = filterOperationsBetween(yearOperations, Calendar.APRIL, Calendar.JUNE);
      case THIRD -> trimesterOperations = filterOperationsBetween(yearOperations, Calendar.JULY, Calendar.SEPTEMBER);
      case QUARTER -> trimesterOperations = filterOperationsBetween(yearOperations, Calendar.OCTOBER, Calendar.DECEMBER);
    }

    return trimesterOperations;
  }

  public List<Integer> getDifferentYears() {
    List<Integer> years = new ArrayList<>();

    for (Operation operation : operations) {
      Calendar date = operation.getDate();

      if (date != null) {
        int year = date.get(Calendar.YEAR);

        if (! years.contains(year)) {
          years.add(year);
        }
      }
    }

    Collections.sort(years);
    return years;
  }

  public List<Integer> getDifferentHeaderRunways() {
    List<Integer> headerRunways = new ArrayList<>();

    for (Operation operation: operations) {
      int headerRunway = operation.getHeaderRunway();

      if (headerRunway != 0) {
        if (!headerRunways.contains(headerRunway)) {
          headerRunways.add(headerRunway);
        }
      }
    }

    return headerRunways;
  }

  public int getOperationsCount(int year) {
    return getOperationsIn(year).size();
  }


  public int getOperationsCount(int year, AircraftType aircraftType) {
    int operationsCount = 0;

    for (Operation operation : getOperationsIn(year)) {
      AircraftType operationAircraft = operation.getAircraftType();

      if (operationAircraft != null) {
        if (operationAircraft.equals(aircraftType)) {
          operationsCount++;
        }
      }
    }

    return operationsCount;
  }

  public int getOperationsCount(int year, Trimester trimester, AircraftType aircraftType) {
    int operationsCount = 0;

    for (Operation operation : getOperationsIn(year, trimester)) {
      if (operation.getAircraftType().equals(aircraftType)) {
        operationsCount++;
      }
    }

    return operationsCount;
  }

  public int getOperationsCount(int year, Operation.Schengen schengen) {
    int operationsCount = 0;

    for (Operation operation : getOperationsIn(year)) {
      switch (schengen) {
        case SCHENGEN -> {
          if (operation.isSchengen()) {
            operationsCount++;
          }
        }
        case NO_SCHENGEN -> {
          if (! operation.isSchengen()) {
            operationsCount++;
          }
        }
      }
    }

    return operationsCount;
  }

  public int getOperationsCount(int year, int headerRunway) {
    int operationsCount = 0;

    for (Operation operation: getOperationsIn(year)) {
      if (operation.getHeaderRunway() == headerRunway) {
        operationsCount++;
      }
    }

    return operationsCount;
  }

  public int getMTOWAverage(int year) {
    List<Operation> operations = getOperationsIn(year);
    if (operations.isEmpty()) {
      return 0;
    }

    int sum = 0;
    for (Operation operation : operations) {
      double aircraftMTOW = operation.getAircraftMTOW();

      if (aircraftMTOW > 0) {
        sum += aircraftMTOW;
      }
    }
    return sum / operations.size();
  }
}
