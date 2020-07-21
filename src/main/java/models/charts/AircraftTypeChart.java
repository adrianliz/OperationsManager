package models.charts;

import controllers.Config;
import models.AircraftType;
import models.OperationsStatistics;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;

import java.util.Arrays;

public class AircraftTypeChart implements IChart {
  private OperationsStatistics operationsStatistics;

  public AircraftTypeChart(OperationsStatistics operationsStatistics) {
    this.operationsStatistics = operationsStatistics;
  }

  @Override
  public Chart createChart(int year) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .width(Config.screenDimension.width - 50)
        .title("Aircraft types")
        .xAxisTitle("Aircraft type")
        .yAxisTitle("Count")
        .build();

    int i = 0;
    for (AircraftType aircraftType: AircraftType.values()) {
      chart.addSeries(aircraftType.toString(), Arrays.asList(new Integer[] {i}),
                      Arrays.asList(new Integer[] {operationsStatistics.getOperationsCount(year, aircraftType)}));
      i++;
    }
    return chart;
  }
}
