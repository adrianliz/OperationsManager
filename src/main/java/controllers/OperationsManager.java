package controllers;

import com.poiji.exception.InvalidExcelFileExtension;
import controllers.graphfactory.GraphFactory;
import mdlaf.MaterialLookAndFeel;
import models.Operation;
import models.OperationsStatistics;
import models.Tuple;
import models.enums.StatisticType;
import models.graphs.GraphProperties;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.internal.chartpart.Chart;
import views.OperationsView;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OperationsManager implements IViewListener {
  private static final Logger LOG = LogManager.getLogger(OperationsManager.class);

  private final OperationsView view;
  private final Config config;
  private OperationsStatistics operationsStatistics;
  private Chart currentGraph;

  OperationsManager(Config config) {
    this.config = config;
    view = new OperationsView(this, config);

    readOperations();
  }

  private void readOperations() {
    String filePath = view.selectFilePath(OperationsView.FILE_CHOOSER_ACTION.OPEN_STATISTICS_FILE);

    if (filePath != null) {
      try {
        OperationsReader operationsReader = new OperationsReader(filePath, config);
        List<Operation> operations = operationsReader.readOperations();

        if (operations.isEmpty()) {
          view.showDialogMessage(config.getString(Config.OPERATIONS_NOT_FOUND), config.getIcon(Config.ALERT_ICON));
        } else {
          operationsStatistics = new OperationsStatistics(operations);
          GraphFactory.initFactory(operationsStatistics, config);

          view.disableChartPane();
          view.enableEvent(Event.SAVE_CHART, false);
          view.setStateStatus(config.getString(Config.OPERATIONS_READ_STATE) + " " + filePath);
          view.initStatisticSelection(operationsStatistics);

          LOG.info(config.getString(Config.OPERATIONS_READ_LOG) + " " + filePath);
        }
      } catch (InvalidExcelFileExtension e) {
        view.showDialogMessage(config.getString(Config.FILE_EXT_ERROR), config.getIcon(Config.ALERT_ICON));
      } catch (IOException e) {
        view.showDialogMessage(config.getString(Config.FILE_NOT_EXISTS_ERROR), config.getIcon(Config.ALERT_ICON));
      }
    }
  }

  private void createGraph(GraphFactory.GraphFamily graphFamily, StatisticType statisticType,
                           GraphProperties graphProperties) {

    currentGraph = GraphFactory.getFactory(graphFamily).createGraph(statisticType, graphProperties);

    view.initChartView(currentGraph);
    view.enableEvent(Event.SAVE_CHART, true);

    LOG.info(config.getString(Config.GRAPH_GENERATED_LOG) + " " + currentGraph.getTitle());
  }

  private void saveChart() {
    String filePath = view.selectFilePath(OperationsView.FILE_CHOOSER_ACTION.SAVE_GRAPH);

    try {
      BitmapEncoder.saveBitmapWithDPI(currentGraph, filePath,
                                      BitmapEncoder.BitmapFormat.PNG, config.getInt(Config.PNG_PDI));
      view.showDialogMessage(config.getString(Config.GRAPH_SAVED), config.getIcon(Config.SUCCESS_ICON));

      LOG.info(config.getString(Config.GRAPH_SAVED_LOG) + " " + filePath);
    } catch (IOException e) {
      view.showDialogMessage(config.getString(Config.SAVE_GRAPH_ERROR), config.getIcon(Config.ALERT_ICON));
      LOG.error(config.getString(Config.SAVE_GRAPH_ERROR));
    }
  }

  @Override
  public void eventFired(Event event, Object o) {
    switch (event) {
      case OPEN_FILE -> readOperations();
      case GENERATE_GRAPH -> {
          Tuple<GraphFactory.GraphFamily, Tuple<StatisticType, GraphProperties>> tuple =
            (Tuple<GraphFactory.GraphFamily, Tuple<StatisticType, GraphProperties>>) o;
          createGraph(tuple.a, tuple.b.a, tuple.b.b);
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
