package models.graphs;

import controllers.Config;
import models.enums.AircraftType;
import models.OperationsStatistics;
import models.enums.Trimester;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.CategoryStyler;

import java.util.ArrayList;
import java.util.List;

public class AircraftTypeGraph implements IYearGraph, ITrimesterGraph {
  private static final Logger LOG = LogManager.getLogger(AircraftTypeGraph.class);

  private OperationsStatistics statistics;
  private Config config;

  public AircraftTypeGraph(OperationsStatistics statistics, Config config) {
    this.statistics = statistics;
    this.config = config;
  }

  @Override
  public Chart createGraph(List<Integer> years) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(config.getString(Config.AIRCRAFT_TYPE_GRAPH_TITLE))
        .xAxisTitle(config.getString(Config.X_YEAR_AIRCRAFT_TYPE_SERIES))
        .yAxisTitle(config.getString(Config.Y_AIRCRAFT_TYPE_SERIES))
        .build();

    LOG.info(config.getString(Config.GENERATING_GRAPH_LOG) + " " + chart.getTitle());

    CategoryStyler styler = chart.getStyler();
    styler.setChartTitleVisible(true);
    styler.setHasAnnotations(true);
    styler.setToolTipsEnabled(true);

    for (AircraftType aircraftType : AircraftType.values()) {
      chart.addSeries(aircraftType.toString(), years, statistics.getOperationsCount(years, aircraftType));
    }

    return chart;
  }

  @Override
  public Chart createGraph(Integer year, Trimester trimester) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(config.getString(Config.AIRCRAFT_TYPE_GRAPH_TITLE))
        .xAxisTitle(config.getString(Config.X_TRIMESTER_AIRCRAFT_TYPE_SERIES))
        .yAxisTitle(config.getString(Config.Y_AIRCRAFT_TYPE_SERIES))
        .build();

    LOG.info(config.getString(Config.GENERATING_GRAPH_LOG) + " " + chart.getTitle());

    CategoryStyler styler = chart.getStyler();
    styler.setChartTitleVisible(true);
    styler.setHasAnnotations(true);
    styler.setToolTipsEnabled(true);

    for (AircraftType aircraftType: AircraftType.values()) {
      List<Integer> yData = new ArrayList<>();
      List<String> xData = new ArrayList<>();
      xData.add(trimester.toString() + " " + year);
      yData.add(statistics.getOperationsCount(year, trimester, aircraftType));

      chart.addSeries(aircraftType.toString(), xData, yData);
    }

    return chart;
  }
}
