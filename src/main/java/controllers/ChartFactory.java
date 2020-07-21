package controllers;

import models.OperationsStatistics;
import models.StatisticType;
import models.charts.AircraftTypeChart;
import models.charts.IChart;
import models.charts.SchengenChart;

public class ChartFactory {

  IChart newChart(StatisticType statisticType, OperationsStatistics operationsStatistics) {
    switch (statisticType) {
      case SCHENGEN -> {
        return new SchengenChart(operationsStatistics);
      }
      case TIPO_AERONAVE -> {
        return new AircraftTypeChart(operationsStatistics);
      }
      default -> {
        return null;
      }
    }
  }
}
