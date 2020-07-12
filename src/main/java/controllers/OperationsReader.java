package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.Departure;
import models.Landing;
import models.Operation;

public class OperationsReader {
  /* TODO: Config file */
  private static final String DELIMITER = ";";

  private static final int START_ROW = 5;
  private static final int LANDING_SHEET = 0;
  private static final int DEPARTURE_SHEET = 1;

  public static final String DATE_FORMAT = "dd/MM/yyyy";
  public static final String TIME_FORMAT = "HH:mm'H'";

  private final Workbook workbook;

  OperationsReader(String fileName) throws IOException {
    workbook = new XSSFWorkbook(this.getClass().getResource(fileName).getPath());
  }

  private List<String> readSheet(int sheetNumber) throws IOException {
    Sheet sheet = workbook.getSheetAt(sheetNumber);
    DataFormatter df = new DataFormatter();
    List<String> operations = new ArrayList<>();

    for (int i = START_ROW - 1; i < sheet.getPhysicalNumberOfRows(); i++) {
      Row row;
      if ((row = sheet.getRow(i)) == null) break;

      String operation = "";

      loop: for (Cell cell : row) {
        switch (cell.getCellType()) {
          case STRING:
            operation += cell.getStringCellValue() + DELIMITER;
            break;

          case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
              operation += new SimpleDateFormat(DATE_FORMAT).format(cell.getDateCellValue()) + DELIMITER;
            } else {
              operation += df.formatCellValue(cell) + DELIMITER;
            }
            break;

          case BLANK:
            break loop;
        }
      }

      operations.add(operation);
    }

    workbook.close();
    return operations;
  }

  public List<Operation> getOperations() throws IOException, ParseException {
    List<Operation> operations = new ArrayList<>();

    for (String operation: readSheet(LANDING_SHEET)) {
      operations.add(new Landing(new Scanner(operation).useDelimiter(DELIMITER)));
    }

    for (String operation: readSheet(DEPARTURE_SHEET)) {
      operations.add(new Departure(new Scanner(operation).useDelimiter(DELIMITER)));
    }

    return operations;
  }
}
