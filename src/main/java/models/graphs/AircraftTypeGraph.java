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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AircraftTypeGraph implements IYearGraph, ITrimesterGraph {
  private static final Logger LOG = LogManager.getLogger(AircraftTypeGraph.class);

  private final OperationsStatistics statistics;
  private final Config config;

  public AircraftTypeGraph(OperationsStatistics statistics, Config config) {
    this.statistics = statistics;
    this.config = config;
  }

  @Override
  public Chart createGraph(List<Integer> years) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
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
    styler.setDatePattern("yyyy");

    for (AircraftType aircraftType : AircraftType.values()) {
      List<Date> xData = new ArrayList<>();
      List<Integer> yData = new ArrayList<>();

      try {
        for (int year : years) {
          xData.add(sdf.parse(year + ""));
          yData.add(statistics.getOperationsCount(year, aircraftType));
        }

        chart.addSeries(aircraftType.toString(), xData, yData);
      } catch (ParseException e) {
        chart.addSeries(aircraftType.toString(), years, yData);
      }
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
