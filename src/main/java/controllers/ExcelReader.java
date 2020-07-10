package controllers;

import java.io.IOException;
import java.util.*;

import models.LandingOperation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
  private String fileName;

  ExcelReader(String fileName) {
    this.fileName = fileName;
  }

  List<LandingOperation> readSheetFrom(int sheetNumber, int startRow) throws IOException {
    Workbook workbook = new XSSFWorkbook(this.getClass().getResource(fileName).getPath());
    Sheet sheet = workbook.getSheetAt(sheetNumber);
    List<LandingOperation> landingOperations = new ArrayList<>();

    for (int i = startRow - 1; i < sheet.getLastRowNum(); i++) {
      Row row = null;
      if ((row = sheet.getRow(i)) == null) break;

      String landingOperation = "";
      Iterator<Cell> iCells = row.iterator();

      while (iCells.hasNext()) {
        Cell cell = iCells.next();

        switch (cell.getCellType()) {
          case STRING:
            landingOperation += cell.getStringCellValue() + " ";
            break;

          case NUMERIC:
            landingOperation += cell.getNumericCellValue() + " ";
            break;
        }
      }

      landingOperations.add(new LandingOperation(new Scanner(landingOperation)));
    }

    workbook.close();
    return landingOperations;
  }
}
