package controllers;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    ExcelReader excelReader = new ExcelReader("/operaciones.xlsx");

    try {
      excelReader.readSheetFrom(0, 5);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
