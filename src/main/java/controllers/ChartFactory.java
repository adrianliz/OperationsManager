package controllers;

import models.StatisticType;
import models.charts.AircraftTypeChart;
import models.charts.IChart;
import models.charts.SchengenChart;

public class ChartFactory {
  IChart newChart(StatisticType statisticType) {
    switch (statisticType) {
      case SCHENGEN -> {
        return new SchengenChart();
      }
      case TIPO_AERONAVE -> {
        return new AircraftTypeChart();
      }
      default -> {
        return null;
      }
    }
  }
}
