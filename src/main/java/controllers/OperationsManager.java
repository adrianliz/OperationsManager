package controllers;

import com.poiji.exception.InvalidExcelFileExtension;
import mdlaf.MaterialLookAndFeel;
import models.Operation;
import models.OperationsStatistics;
import models.StatisticType;
import models.Tuple;
import models.charts.IStatisticChart;
import org.apache.commons.configuration2.ex.ConfigurationException;
import views.OperationsView;

import javax.swing.*;
import java.util.List;

public class OperationsManager implements IViewListener {
  private OperationsView view;
  private Config config;
  private OperationsStatistics operationsStatistics;
  private ChartFactory chartFactory;

  OperationsManager() throws ConfigurationException {
    config = new Config();
    view = new OperationsView(this, config);
    chartFactory = new ChartFactory();

    openFile();
  }

  private void openFile() {
    String filePath = view.selectFile();

    if (filePath != null) {
      try {
        OperationsReader operationsReader = new OperationsReader(filePath, config);
        List<Operation> operations = operationsReader.getOperations();

        if (operations.isEmpty()) {
          view.showDialogMessage(Config.OP_NOT_FOUND);
        } else {
          operationsStatistics = new OperationsStatistics(operations);

          view.disableChartPane();
          view.setStateStatus(Config.FILE_STATE + " " + filePath);
          view.initStatisticSelection(operationsStatistics);
        }
      } catch (InvalidExcelFileExtension e) {
        view.showDialogMessage(Config.FILE_EXT_ERROR);
      }
    }
  }

  private void generateChart(StatisticType statisticType, List<Integer> years) {
    IStatisticChart chart = chartFactory.newChart(statisticType);
    view.initChartView(chart.createChart(operationsStatistics, years));
  }

  @Override
  public void eventFired(Event event, Object o) {
    switch (event) {
      case OPEN -> openFile();
      case GENERATE_CHART -> {
        Tuple tuple = (Tuple) o;
        generateChart((StatisticType) tuple.a, (List<Integer>) tuple.b);
      }
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater (() -> {
        try {
          UIManager.setLookAndFeel(new MaterialLookAndFeel());
          new OperationsManager();
        } catch (UnsupportedLookAndFeelException e) {
          System.err.println(Config.LAF_ERROR); //TODO logger
        } catch (ConfigurationException e) {
          System.err.println(Config.CONFIG_ERROR);
          System.exit(1);
        }
    });
  }
}
