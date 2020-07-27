package models.charts;

import controllers.Config;
import models.AircraftType;
import models.OperationsStatistics;
import models.Trimester;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.CategoryStyler;

import java.util.ArrayList;
import java.util.List;

public class AircraftTypeStatistic implements IStatisticModel {
  private static final Logger LOG = LogManager.getLogger(AircraftTypeStatistic.class);

  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years, Config config) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(config.getString(Config.AIRCRAFT_TYPE_CHART_TITLE))
        .yAxisTitle(config.getString(Config.Y_AIRCRAFT_TYPE_SERIES))
        .build();

    LOG.info(config.getString(Config.GENERATING_CHART_LOG) + " " + chart.getTitle());

    CategoryStyler styler = chart.getStyler();
    styler.setChartTitleVisible(true);
    styler.setHasAnnotations(true);
    styler.setToolTipsEnabled(true);

    //TODO esto se debe poder mejorar
    if (years.size() == 1) {
      chart.setXAxisTitle(config.getString(Config.X_TRIMESTER_AIRCRAFT_TYPE_SERIES));
      List<String> xData = new ArrayList<>();
      for (Trimester trimester: Trimester.values()) {
        xData.add(trimester.toString());
      }

      for (AircraftType aircraftType : AircraftType.values()) {
        chart.addSeries(aircraftType.toString(), xData,
                        operationsStatistics.getOperationsCount(years.get(0), Trimester.values(), aircraftType));
      }
    } else {
      chart.setXAxisTitle(config.getString(Config.X_YEAR_AIRCRAFT_TYPE_SERIES));
      for (AircraftType aircraftType : AircraftType.values()) {
        chart.addSeries(aircraftType.toString(), years, operationsStatistics.getOperationsCount(years, aircraftType));
      }
    }

    return chart;
  }
}
