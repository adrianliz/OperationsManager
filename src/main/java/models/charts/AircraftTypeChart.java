package models.charts;

import controllers.Config;
import models.AircraftType;
import models.OperationsStatistics;
import models.Tuple;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AircraftTypeChart implements IChart {
  private List<Integer> getYearsBetween(int firstYear, int lastYear) {
    List<Integer> years = new ArrayList<>();

    for (int year = firstYear; year <= lastYear; year++) {
      years.add(year);
    }

    return years;
  }

  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, Tuple<Integer, Integer> years) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .width(Config.screenDimension.width - 50)
        .title("Aircraft types")
        .xAxisTitle("Aircraft type")
        .yAxisTitle("Count")
        .build();

    for (AircraftType aircraftType: AircraftType.values()) {
      List<Integer> yearsBt = getYearsBetween(years.a, years.b);

      chart.addSeries(aircraftType.toString(), yearsBt,
                      operationsStatistics.getOperationsCount(yearsBt, aircraftType));
    }

    return chart;
  }
}
