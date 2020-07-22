package models.charts;

import controllers.Config;
import models.AircraftType;
import models.OperationsStatistics;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;

import java.util.List;

public class AircraftTypeChart implements IChart {
  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .width(Config.screenDimension.width - 100)
        .title(Config.AIRCRAFT_TYPE_CHART)
        .xAxisTitle(Config.X_AIRCRAFT_TYPE)
        .yAxisTitle(Config.Y_AIRCRAFT_TYPE)
        .build();

    for (AircraftType aircraftType: AircraftType.values()) {
      chart.addSeries(aircraftType.toString(), years, operationsStatistics.getOperationsCount(years, aircraftType));
    }

    return chart;
  }
}
