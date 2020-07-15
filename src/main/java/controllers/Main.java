package controllers;

import models.Operation;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    try {
      Config config = new Config();
      OperationsReader operationsReader = new OperationsReader(config);
      List<Operation> operations = operationsReader.getOperations();

      for (Operation operation: operations) {
        System.out.println(operation);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
