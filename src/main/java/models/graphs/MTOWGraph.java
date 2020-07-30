package models.graphs;

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

public class MTOWGraph implements IYearGraph {
  private static final Logger LOG = LogManager.getLogger(MTOWGraph.class);

  private OperationsStatistics statistics;
  private Config config;

  public MTOWGraph(OperationsStatistics statistics, Config config) {
    this.statistics = statistics;
    this.config = config;
  }

  @Override
  public Chart createGraph(List<Integer> years) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(config.getString(Config.MTOW_GRAPH_TITLE))
        .xAxisTitle(config.getString(Config.X_MTOW_SERIES))
        .yAxisTitle(config.getString(Config.Y_MTOW_SERIES))
        .build();

    LOG.info(config.getString(Config.GENERATING_GRAPH_LOG) + " " + chart.getTitle());

    CategoryStyler styler = chart.getStyler();
    styler.setChartTitleVisible(true);
    styler.setHasAnnotations(true);
    styler.setToolTipsEnabled(true);
    styler.setLegendPosition(Styler.LegendPosition.InsideNW);
    styler.setAvailableSpaceFill(.20);

    List<Integer> yData = new ArrayList<>();
    for (int year: years) {
      yData.add(statistics.getMTOWAverage(year));
    }

    chart.addSeries(config.getString(Config.MTOW_GRAPH_TITLE), years, yData);
    return chart;
  }
}
