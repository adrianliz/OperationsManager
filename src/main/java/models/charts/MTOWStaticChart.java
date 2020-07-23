package models.charts;

import controllers.Config;
import models.OperationsStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;

public class MTOWStaticChart implements IStatisticChart {
  private static final Logger LOG = LogManager.getLogger(MTOWStaticChart.class);

  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years, Config config) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(config.getString(Config.MTOW_CHART_TITLE))
        .xAxisTitle(config.getString(Config.X_MTOW_SERIES))
        .yAxisTitle(config.getString(Config.Y_MTOW_SERIES))
        .build();

    LOG.info(config.getString(Config.GENERATING_CHART_LOG) + " " + chart.getTitle());

    CategoryStyler styler = chart.getStyler();
    styler.setChartTitleVisible(true);
    styler.setHasAnnotations(true);
    styler.setToolTipsEnabled(true);
    styler.setLegendPosition(Styler.LegendPosition.InsideNW);
    styler.setAvailableSpaceFill(.20);

    List<Integer> yData = new ArrayList<>();
    for (int year: years) {
      yData.add(operationsStatistics.getMTOWAverage(year));
    }

    chart.addSeries(config.getString(Config.MTOW_CHART_TITLE), years, yData);
    return chart;
  }
}
