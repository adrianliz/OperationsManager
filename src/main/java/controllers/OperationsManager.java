package controllers;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import models.Operation;
import models.OperationsStatistics;
import models.StatisticType;
import models.Tuple;
import models.charts.IChart;
import org.apache.commons.configuration2.ex.ConfigurationException;
import views.OperationsView;

import javax.swing.*;

public class OperationsManager implements IViewListener {
  private OperationsView view;
  private Config config;
  private OperationsStatistics operationsStatistics;
  private ChartFactory chartFactory;

  private OperationsManager() throws ConfigurationException {
    config = new Config();
    view = new OperationsView(this, config);
    chartFactory = new ChartFactory();
  }

  private void openFile() {
    String filePath = view.selectFile();

    if (filePath != null) {
      OperationsReader operationsReader = new OperationsReader(filePath, config);
      operationsStatistics = new OperationsStatistics(operationsReader.getOperations());

      view.initStatisticSelection(operationsStatistics);
    }
  }

  private void generateChart(int year, StatisticType statisticType) {
    IChart chart = chartFactory.newChart(statisticType, operationsStatistics);
    view.initChartView(chart.createChart(year));
  }

  @Override
  public void eventFired(Event event, Object o) {
    switch (event) {
      case OPEN -> openFile();
      case ACCEPT_STATISTIC -> {
        Tuple tuple = (Tuple) o;
        generateChart((int) tuple.a, (StatisticType) tuple.b);
      }
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater (() -> {
        try {
          UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));
          new OperationsManager();
        } catch (UnsupportedLookAndFeelException | ConfigurationException e) {
          e.printStackTrace();
        }
      }
    );
  }
}
