package models.charts;

import controllers.Config;
import models.OperationsStatistics;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;

public class MTOWStaticChart implements IStatisticChart {

  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years, Config config) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(config.getString(Config.MTOW_CHART_TITLE))
        .xAxisTitle(config.getString(Config.X_MTOW_SERIES))
        .yAxisTitle(config.getString(Config.Y_MTOW_SERIES))
        .build();

    CategoryStyler styler = chart.getStyler();
    styler.setChartTitleVisible(true);
    chart.getStyler().setAvailableSpaceFill(.20);
    styler.setLegendPosition(Styler.LegendPosition.InsideNW);
    styler.setHasAnnotations(true);

    List<Integer> yData = new ArrayList<>();
    for (int year: years) {
      yData.add(operationsStatistics.getMTOWAverage(year));
    }

    chart.addSeries(config.getString(Config.MTOW_CHART_TITLE), years, yData);
    return chart;
  }
}
