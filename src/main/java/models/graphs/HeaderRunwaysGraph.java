package models.graphs;

import controllers.Config;
import models.OperationsStatistics;
import org.apache.commons.collections.ArrayStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HeaderRunwaysGraph implements IYearGraph {
  private static final Logger LOG = LogManager.getLogger(HeaderRunwaysGraph.class);

  private final OperationsStatistics statistics;
  private final Config config;

  public HeaderRunwaysGraph(OperationsStatistics statistics, Config config) {
    this.statistics = statistics;
    this.config = config;
  }

  @Override
  public Chart createGraph(List<Integer> years) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(config.getString(Config.HEADER_RUNWAY_GRAPH_TITLE))
        .xAxisTitle(config.getString(Config.X_HEADER_RUNWAY_SERIES))
        .yAxisTitle(config.getString(Config.Y_HEADER_RUNWAY_SERIES))
        .build();

    LOG.info(config.getString(Config.GENERATING_GRAPH_LOG) + " " + chart.getTitle());

    CategoryStyler styler = chart.getStyler();
    styler.setHasAnnotations(true);
    styler.setToolTipsEnabled(true);
    styler.setLegendPosition(Styler.LegendPosition.InsideNW);
    styler.setDatePattern("yyyy");
    styler.setAvailableSpaceFill(.20);

    List<Integer> headerRunways = statistics.getDifferentHeaderRunways();
    for (int headerRunway: headerRunways) {
      List<Date> xData = new ArrayList<>();
      List<Integer> yData = new ArrayList<>();

      try {
        for (int year : years) {
          xData.add(sdf.parse(year + ""));
          yData.add(statistics.getOperationsCount(year, headerRunway));
        }

        chart.addSeries(headerRunway + "", xData, yData);
      } catch (ParseException e) {
        chart.addSeries(headerRunway + "", years, yData);
      }
    }

    return chart;
  }
}
