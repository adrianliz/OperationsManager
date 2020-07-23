package models.charts;

import controllers.Config;
import models.AircraftType;
import models.OperationsStatistics;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;

import java.util.List;

public class AircraftTypeStatisticChart implements IStatisticChart {
  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years, Config config) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(config.getString(Config.AIRCRAFT_TYPE_CHART_TITLE))
        .xAxisTitle(config.getString(Config.X_AIRCRAFT_TYPE_SERIES))
        .yAxisTitle(config.getString(Config.Y_AIRCRAFT_TYPE_SERIES))
        .build();

    for (AircraftType aircraftType: AircraftType.values()) {
      chart.addSeries(aircraftType.toString(), years, operationsStatistics.getOperationsCount(years, aircraftType));
    }

    return chart;
  }
}
