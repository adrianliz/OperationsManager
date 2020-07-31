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

public class MTOWGraph extends Graph implements IYearGraph {
  private static final Logger LOG = LogManager.getLogger(MTOWGraph.class);

  public MTOWGraph(OperationsStatistics statistics, Config config) {
    super(statistics, config);
  }

  @Override
  public Chart createGraph(List<Integer> years) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(super.config.getString(Config.MTOW_GRAPH_TITLE))
        .xAxisTitle(super.config.getString(Config.X_MTOW_SERIES))
        .yAxisTitle(super.config.getString(Config.Y_MTOW_SERIES))
        .build();

    LOG.info(super.config.getString(Config.GENERATING_GRAPH_LOG) + " " + chart.getTitle());

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
        yData.add(super.statistics.getMTOWAverage(year));
      }

      chart.addSeries(super.config.getString(Config.MTOW_GRAPH_TITLE), xData, yData);
    } catch (ParseException e) {
      chart.addSeries(super.config.getString(Config.MTOW_GRAPH_TITLE), years, yData);
    }

    return chart;
  }
}
