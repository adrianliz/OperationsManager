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
import java.util.List;

public class OperationsManager implements IViewListener {
  private static final Logger LOG = LogManager.getLogger(OperationsManager.class);

  private OperationsView view;
  private Config config;
  private OperationsStatistics operationsStatistics;
  private ChartFactory chartFactory;
  private Chart chart;

  OperationsManager(Config config) {
    this.config = config;
    view = new OperationsView(this, config);
    chartFactory = new ChartFactory();

    openFile();
  }

  private void openFile() {
    String filePath = view.selectFilePath(OperationsView.FILE_ACTION.OPEN);

    if (filePath != null) {
      try {
        OperationsReader operationsReader = new OperationsReader(filePath, config);
        List<Operation> operations = operationsReader.getOperations();

        if (operations.isEmpty()) {
          view.showDialogMessage(config.getString(Config.OPERATIONS_NOT_FOUND), config.getIcon(Config.ALERT_ICON));
        } else {
          operationsStatistics = new OperationsStatistics(operations);

          view.disableChartPane();
          view.enableEvent(Event.SAVE_CHART, false);
          view.setStateStatus(config.getString(Config.FILE_SELECTED_STATE) + " " + filePath);
          view.initStatisticSelection(operationsStatistics);

          LOG.info(config.getString(Config.FILE_OPEN_LOG) + " " + filePath);
        }
      } catch (InvalidExcelFileExtension e) {
        view.showDialogMessage(config.getString(Config.FILE_EXT_ERROR), config.getIcon(Config.ALERT_ICON));
      }
    }
  }

  private void generateChart(StatisticType statisticType, List<Integer> years) {
    IStatisticChart statisticChart = chartFactory.newChart(statisticType);

    chart = statisticChart.createChart(operationsStatistics, years, config);
    view.initChartView(chart);
    view.enableEvent(Event.SAVE_CHART, true);

    LOG.info(config.getString(Config.CHART_GENERATED_LOG) + " " + chart.getTitle());
  }

  private void saveChart() {
    String filePath = view.selectFilePath(OperationsView.FILE_ACTION.SAVE);

    try {
      BitmapEncoder.saveBitmapWithDPI(chart, filePath, BitmapEncoder.BitmapFormat.PNG, config.getInt(Config.PNG_PDI));
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
      case OPEN -> openFile();
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
