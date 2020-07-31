package controllers;

import com.poiji.bind.Poiji;
import com.poiji.bind.mapping.PoijiNumberFormat;
import com.poiji.option.PoijiOptions;
import models.Operation;
import models.enums.AircraftType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class OperationsReader {
  private enum MONTH {ENERO, FEBRERO, MARZO, ABRIL, MAYO, JUNIO, JULIO, AGOSTO, SEPTIEMBRE, OCTUBRE, NOVIEMBRE, DICIEMBRE}
  static Map<MONTH, Integer> MONTHS = new EnumMap<>(MONTH.class);
  static {
    MONTHS.put(MONTH.ENERO, 0); MONTHS.put(MONTH.FEBRERO, 1); MONTHS.put(MONTH.MARZO, 2); MONTHS.put(MONTH.ABRIL, 3);
    MONTHS.put(MONTH.MAYO, 4); MONTHS.put(MONTH.JUNIO, 5); MONTHS.put(MONTH.JULIO, 6); MONTHS.put(MONTH.AGOSTO, 7);
    MONTHS.put(MONTH.SEPTIEMBRE, 8); MONTHS.put(MONTH.OCTUBRE, 9); MONTHS.put(MONTH.NOVIEMBRE, 10);
    MONTHS.put(MONTH.DICIEMBRE, 11);
  }

  private final String filePath;
  private final Config config;
  private final Workbook workbook;

  OperationsReader(String filePath, Config config) throws IOException {
    this.filePath = filePath;
    this.config = config;

    workbook = new XSSFWorkbook(filePath);
  }

  private List<Operation> readTotalOperations(int sheetIndex, AircraftType aircraftType) {
    List<Operation> operations = new ArrayList<>();
    Sheet sheet = workbook.getSheetAt(sheetIndex);

    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);

      int year = (int) row.getCell(0).getNumericCellValue();
      String month = row.getCell(1).getStringCellValue();
      int count = (int) row.getCell(2).getNumericCellValue();

      Calendar cal = new GregorianCalendar(year, MONTHS.get(MONTH.valueOf(month)), 1);

      for (int j = 0; j < count; j++) {
        Operation operation = new Operation();
        operation.setAircraftType(aircraftType);
        operation.setDate(cal);

        operations.add(operation);
      }
    }

    return operations;
  }

  private List<Operation> readSheet(int sheetIndex) {
    PoijiNumberFormat numberFormat = new PoijiNumberFormat();
    numberFormat.putNumberFormat((short) config.getInt(Config.EXCEL_DATE_FORMAT_INDEX),
                                         config.getString(Config.EXCEL_DATE_FORMAT));

    PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().poijiNumberFormat(numberFormat)
      .headerStart(config.getInt(Config.EXCEL_HEADER_ROW)).sheetIndex(sheetIndex).build();

    return Poiji.fromExcel(new File(filePath), Operation.class, options);
  }

  List<Operation> readOperations() {
    List<Operation> operations = new ArrayList<>();

    operations.addAll(readSheet(config.getInt(Config.LANDINGS_SHEET)));
    operations.addAll(readSheet(config.getInt(Config.DEPARTURES_SHEET)));
    operations.addAll(readTotalOperations(config.getInt(Config.SHEET_112), AircraftType.HELICOPTER));

    return operations;
  }
}
