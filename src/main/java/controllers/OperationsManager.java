package controllers;

import com.poiji.exception.InvalidExcelFileExtension;
import mdlaf.MaterialLookAndFeel;
import models.Operation;
import models.OperationsStatistics;
import models.StatisticType;
import models.Tuple;
import models.charts.IStatisticChart;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.internal.chartpart.Chart;
import views.OperationsView;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationsManager implements IViewListener {
  private static final Logger LOG = LogManager.getLogger(OperationsManager.class);

  private OperationsView view;
  private Config config;
  private OperationsStatistics operationsStatistics;
  private ChartFactory chartFactory;
  private Map<Tuple<StatisticType, List<Integer>>, Chart> statisticsCharts;
  private Chart currentChart;

  OperationsManager(Config config) {
    this.config = config;
    view = new OperationsView(this, config);
    chartFactory = new ChartFactory();

    readOperations();
  }

  private void readOperations() {
    String filePath = view.selectFilePath(OperationsView.FILE_ACTION.OPEN);

    if (filePath != null) {
      try {
        OperationsReader operationsReader = new OperationsReader(filePath, config);
        List<Operation> operations = operationsReader.readOperations();

        if (operations.isEmpty()) {
          view.showDialogMessage(config.getString(Config.OPERATIONS_NOT_FOUND), config.getIcon(Config.ALERT_ICON));
        } else {
          statisticsCharts = new HashMap<>();
          operationsStatistics = new OperationsStatistics(operations);

          view.disableChartPane();
          view.enableEvent(Event.SAVE_CHART, false);
          view.setStateStatus(config.getString(Config.OPERATIONS_READ_STATE) + " " + filePath);
          view.initStatisticSelection(operationsStatistics);

          LOG.info(config.getString(Config.OPERATIONS_READ_LOG) + " " + filePath);
        }
      } catch (InvalidExcelFileExtension e) {
        view.showDialogMessage(config.getString(Config.FILE_EXT_ERROR), config.getIcon(Config.ALERT_ICON));
      }
    }
  }

  private void generateChart(StatisticType statisticType, List<Integer> years) {
    IStatisticChart statisticModel = chartFactory.newChart(statisticType);

    currentChart = statisticsCharts.get(new Tuple(statisticType, years));

    if (currentChart == null) {
      currentChart = statisticModel.createChart(operationsStatistics, years, config);
      statisticsCharts.put(new Tuple(statisticType, years), currentChart);
    }

    view.initChartView(currentChart);
    view.enableEvent(Event.SAVE_CHART, true);

    LOG.info(config.getString(Config.CHART_GENERATED_LOG) + " " + currentChart.getTitle());
  }

  private void saveChart() {
    String filePath = view.selectFilePath(OperationsView.FILE_ACTION.SAVE);

    try {
      BitmapEncoder.saveBitmapWithDPI(currentChart, filePath,
                                      BitmapEncoder.BitmapFormat.PNG, config.getInt(Config.PNG_PDI));
      view.showDialogMessage(config.getString(Config.CHART_SAVED), config.getIcon(Config.SUCCESS_ICON));

      LOG.info(config.getString(Config.CHART_SAVED_LOG) + " " + filePath);
    } catch (IOException e) {
      view.showDialogMessage(config.getString(Config.SAVE_CHART_ERROR), config.getIcon(Config.ALERT_ICON));
      LOG.error(config.getString(Config.SAVE_CHART_ERROR));
    }
  }

  @Override
  public void eventFired(Event event, Object o) {
    switch (event) {
      case OPEN -> readOperations();
      case GENERATE_CHART -> {
        Tuple tuple = (Tuple) o;
        generateChart((StatisticType) tuple.a, (List<Integer>) tuple.b);
      }
      case SAVE_CHART -> saveChart();
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      Config config = null;

      try {
        config = new Config();
      } catch (ConfigurationException e) {
        LOG.fatal(Config.READ_CONFIG_ERROR);
        System.exit(1);
      }

      try {
        UIManager.setLookAndFeel(new MaterialLookAndFeel());
        new OperationsManager(config);
      } catch (UnsupportedLookAndFeelException e) {
        LOG.error(config.getString(Config.LAF_ERROR));
      }
    });
  }
}
