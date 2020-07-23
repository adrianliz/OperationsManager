package models.charts;

import controllers.Config;
import models.OperationsStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler;

import java.util.List;

public class SchengenStatisticChart implements IStatisticChart {
  private static final Logger LOG = LogManager.getLogger(MTOWStaticChart.class);

  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years, Config config) {
    PieChart chart =
      new PieChartBuilder()
        .title(config.getString(Config.SCHENGEN_CHART_TITLE))
        .build();

    LOG.info(config.getString(Config.GENERATING_CHART_LOG) + " " + chart.getTitle());

    int schengenOperations = operationsStatistics.getSchengenOperations(years.get(0));

    PieStyler styler = chart.getStyler();
    styler.setLegendVisible(true);
    styler.setDrawAllAnnotations(true);
    styler.setToolTipsEnabled(true);
    styler.setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
    styler.setAnnotationDistance(1.15);
    styler.setPlotContentSize(.8);
    styler.setStartAngleInDegrees(90);

    chart.addSeries(config.getString(Config.SCHENGEN_SERIES), schengenOperations);
    chart.addSeries(config.getString(Config.NO_SCHENGEN_SERIES),
              operationsStatistics.getOperationsCount(years.get(0)) - schengenOperations);

    return chart;
  }
}
