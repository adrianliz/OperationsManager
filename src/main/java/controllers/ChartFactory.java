package controllers;

import models.StatisticType;
import models.charts.AircraftTypeStatisticChart;
import models.charts.IStatisticChart;
import models.charts.MTOWStaticChart;
import models.charts.SchengenStatisticChart;

public class ChartFactory {
  IStatisticChart newChart(StatisticType statisticType) {
    switch (statisticType) {
      case SCHENGEN -> {
        return new SchengenStatisticChart();
      }
      case TIPO_AERONAVE -> {
        return new AircraftTypeStatisticChart();
      }
      case MEDIA_MTOW -> {
        return new MTOWStaticChart();
      }
      default -> {
        return null;
      }
    }
  }
}
