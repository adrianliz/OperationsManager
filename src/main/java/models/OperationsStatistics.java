package models;

import java.util.*;

public class OperationsStatistics {
  private List<Operation> operations;
  private Map<Integer, List<Operation>>  yearOperations;

  public OperationsStatistics(List<Operation> operations) {
    this.operations = operations;
    yearOperations = new HashMap<>();
  }

  private List<Operation> getOperationsIn(int year) {
    List<Operation> operations;
    if ((operations = yearOperations.get(year)) != null) {
      return operations;
    }

    operations = new ArrayList<>();
    for (Operation operation: this.operations) {
      if (operation.getDate().get(Calendar.YEAR) == year) {
        operations.add(operation);
      }
    }

    yearOperations.put(year, operations);
    return operations;
  }

  public List<Integer> getDifferentYears() {
    List<Integer> years = new ArrayList<>();

    for (Operation operation: operations) {
      int year = operation.getDate().get(Calendar.YEAR);

      if (! years.contains(year)) {
        years.add(year);
      }
    }

    return years;
  }

  public int getOperationsCount(int year) {
    return getOperationsIn(year).size();
  }

  public int getOperationsCount(int year, AircraftType aircraftType) {
    int numOperations = 0;

    for (Operation operation: getOperationsIn(year)) {
      if (operation.getAircraftType().equals(aircraftType)) {
        numOperations++;
      }
    }

    return numOperations;
  }

  public List<Integer> getOperationsCount(List<Integer> years, AircraftType aircraftType) {
    List<Integer> numOperations = new ArrayList<>();

    for (int year: years) {
      numOperations.add(getOperationsCount(year, aircraftType));
    }

    return numOperations;
  }

  public int getSchengenOperations(int year) {
    int numOperations = 0;

    for (Operation operation: getOperationsIn(year)) {
      if (operation.isSchengen()) {
        numOperations++;
      }
    }

    return numOperations;
  }
}
