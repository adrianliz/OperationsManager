package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.Departure;
import models.Landing;
import models.Operation;

public class OperationsReader {
  private static final String DELIMITER = ";";

  private Workbook workbook;
  private Config config;

  OperationsReader(Config config) throws IOException, InvalidFormatException {
    this.config = config;
    workbook = new XSSFWorkbook(config.getFile(Config.OPERATIONS_FILE));
  }

  private List<String> readSheet(int sheetNumber) throws IOException {
    Sheet sheet = workbook.getSheetAt(sheetNumber);
    DataFormatter df = new DataFormatter();
    List<String> operations = new ArrayList<>();

    for (int i = config.getInt(Config.START_ROW) - 1; i < sheet.getPhysicalNumberOfRows(); i++) {
      Row row;
      if ((row = sheet.getRow(i)) == null) break;

      String operation = "";

      loop:
      for (Cell cell : row) {
        switch (cell.getCellType()) {
          case STRING:
            operation += cell.getStringCellValue() + DELIMITER;
            break;

          case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
              operation += new SimpleDateFormat(config.getString(Config.DATE_FORMAT)).format(cell.getDateCellValue()) +
                DELIMITER;
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

    for (String operation : readSheet(config.getInt(Config.LANDINGS_SHEET))) {
      operations.add(new Landing(new Scanner(operation).useDelimiter(DELIMITER), config));
    }

    for (String operation : readSheet(config.getInt(Config.DEPARTURES_SHEET))) {
      operations.add(new Departure(new Scanner(operation).useDelimiter(DELIMITER), config));
    }

    return operations;
  }
}
