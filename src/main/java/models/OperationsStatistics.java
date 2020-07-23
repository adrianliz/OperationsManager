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

    Collections.sort(years);
    return years;
  }

  public int getOperationsCount(int year) {
    return getOperationsIn(year).size();
  }

  public int getOperationsCount(int year, AircraftType aircraftType) {
    int operationsCount = 0;

    for (Operation operation: getOperationsIn(year)) {
      if (operation.getAircraftType().equals(aircraftType)) {
        operationsCount++;
      }
    }

    return operationsCount;
  }

  public List<Integer> getOperationsCount(List<Integer> years, AircraftType aircraftType) {
    List<Integer> operationsCount = new ArrayList<>();

    for (int year: years) {
      operationsCount.add(getOperationsCount(year, aircraftType));
    }

    return operationsCount;
  }

  public int getSchengenOperations(int year) {
    int operationsCount = 0;

    for (Operation operation: getOperationsIn(year)) {
      if (operation.isSchengen()) {
        operationsCount++;
      }
    }

    return operationsCount;
  }

  public int getMTOWAverage(int year) {
    int sum = 0;

    List<Operation> operations = getOperationsIn(year);

    if (operations.isEmpty()) {
      return 0;
    }

    for (Operation operation: operations) {
      sum += operation.getAircraftMTOW();
    }
    return sum / operations.size();
  }
}
