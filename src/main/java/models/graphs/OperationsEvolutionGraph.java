package models.graphs;

import controllers.Config;
import models.OperationsStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.XYStyler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperationsEvolutionGraph extends Graph implements IYearGraph {
  private static final Logger LOG = LogManager.getLogger(OperationsEvolutionGraph.class);

  public OperationsEvolutionGraph(OperationsStatistics statistics, Config config) {
    super(statistics, config);
  }

  @Override
  public Chart createGraph(List<Integer> years) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    XYChart chart = new XYChartBuilder()
      .title(super.config.getString(Config.OPERATIONS_EVOLUTION_GRAPH_TITLE))
      .xAxisTitle(super.config.getString(Config.X_OPERATIONS_EVOLUTION_SERIES))
      .yAxisTitle(super.config.getString(Config.Y_OPERATIONS_EVOLUTION_SERIES))
      .build();

    LOG.info(super.config.getString(Config.GENERATING_GRAPH_LOG) + " " + chart.getTitle());

    XYStyler styler = chart.getStyler();
    styler.setChartTitleVisible(true);
    styler.setHasAnnotations(true);
    styler.setToolTipsEnabled(true);
    styler.setDatePattern("yyyy");

    List<Date> xData = new ArrayList<>();
    List<Integer> yData = new ArrayList<>();
    String seriesName = super.config.getString(Config.OPERATIONS_EVOLUTION_SERIES_NAME);

    try {
      for (int year : years) {
        xData.add(sdf.parse(year + ""));
        yData.add(super.statistics.getOperationsCount(year));
      }

      chart.addSeries(seriesName, xData, yData);
    } catch (ParseException e) {
      chart.addSeries(seriesName, years, yData);
    }

    return chart;
  }
}
