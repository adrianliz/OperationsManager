package controllers;


import models.Operation;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    try {
      OperationsReader operationsReader = new OperationsReader("/operaciones.xlsx");
      List<Operation> operations = operationsReader.getOperations();

      for (Operation operation: operations) {
        System.out.println(operation);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
