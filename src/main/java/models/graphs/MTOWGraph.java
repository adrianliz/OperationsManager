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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MTOWGraph implements IYearGraph {
  private static final Logger LOG = LogManager.getLogger(MTOWGraph.class);

  private final OperationsStatistics statistics;
  private final Config config;

  public MTOWGraph(OperationsStatistics statistics, Config config) {
    this.statistics = statistics;
    this.config = config;
  }

  @Override
  public Chart createGraph(List<Integer> years) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
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
    styler.setDatePattern("yyyy");
    styler.setAvailableSpaceFill(.20);

    List<Date> xData = new ArrayList<>();
    List<Integer> yData = new ArrayList<>();

    try {
      for (int year : years) {
        xData.add(sdf.parse(year + ""));
        yData.add(statistics.getMTOWAverage(year));
      }

      chart.addSeries(config.getString(Config.MTOW_GRAPH_TITLE), xData, yData);
    } catch (ParseException e) {
      chart.addSeries(config.getString(Config.MTOW_GRAPH_TITLE), years, yData);
    }

    return chart;
  }
}
