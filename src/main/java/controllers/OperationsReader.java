package controllers;

import com.poiji.bind.Poiji;
import com.poiji.bind.mapping.PoijiNumberFormat;
import com.poiji.config.Casting;
import com.poiji.option.PoijiOptions;
import models.Operation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OperationsReader {
  private String filePath;
  private Config config;

  OperationsReader(String filePath, Config config) {
    this.filePath = filePath;
    this.config = config;
  }

  private List<Operation> readSheet(int sheetIndex) {
    PoijiNumberFormat numberFormat = new PoijiNumberFormat();
    numberFormat.putNumberFormat((short) Config.DATE_FORMAT_INDEX, config.getString(Config.DATE_FORMAT));
    PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().poijiNumberFormat(numberFormat)
      .headerStart(config.getInt(Config.HEADER_START)).sheetIndex(sheetIndex).build();

    return Poiji.fromExcel(new File(filePath), Operation.class, options);
  }

  List<Operation> getOperations() {
    List<Operation> operations = new ArrayList<>();

    operations.addAll(readSheet(config.getInt(Config.LANDINGS_SHEET)));
    operations.addAll(readSheet(config.getInt(Config.DEPARTURES_SHEET)));

    return operations;
  }
}
