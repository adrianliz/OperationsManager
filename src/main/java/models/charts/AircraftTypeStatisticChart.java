package models.charts;

import controllers.Config;
import controllers.OperationsManager;
import models.AircraftType;
import models.OperationsStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.CategoryStyler;

import java.util.List;

public class AircraftTypeStatisticChart implements IStatisticChart {
  private static final Logger LOG = LogManager.getLogger(AircraftTypeStatisticChart.class);

  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years, Config config) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(config.getString(Config.AIRCRAFT_TYPE_CHART_TITLE))
        .xAxisTitle(config.getString(Config.X_AIRCRAFT_TYPE_SERIES))
        .yAxisTitle(config.getString(Config.Y_AIRCRAFT_TYPE_SERIES))
        .build();

    LOG.info(config.getString(Config.GENERATING_CHART_LOG) + " " + chart.getTitle());

    CategoryStyler styler = chart.getStyler();
    styler.setChartTitleVisible(true);
    styler.setHasAnnotations(true);
    styler.setToolTipsEnabled(true);

    for (AircraftType aircraftType: AircraftType.values()) {
      chart.addSeries(aircraftType.toString(), years, operationsStatistics.getOperationsCount(years, aircraftType));
    }

    return chart;
  }
}
