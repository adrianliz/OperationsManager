package models.charts;

import controllers.Config;
import models.Operation;
import models.OperationsStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler;

import java.util.List;

public class SchengenStatistic implements IStatisticModel {
  private static final Logger LOG = LogManager.getLogger(MTOWStatistic.class);

  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years, Config config) {
    PieChart chart =
      new PieChartBuilder()
        .title(config.getString(Config.SCHENGEN_CHART_TITLE))
        .build();

    LOG.info(config.getString(Config.GENERATING_CHART_LOG) + " " + chart.getTitle());

    PieStyler styler = chart.getStyler();
    styler.setLegendVisible(true);
    styler.setDrawAllAnnotations(true);
    styler.setToolTipsEnabled(true);
    styler.setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
    styler.setAnnotationDistance(1.15);
    styler.setPlotContentSize(.8);
    styler.setStartAngleInDegrees(90);

    chart.addSeries(config.getString(Config.SCHENGEN_SERIES),
                    operationsStatistics.getOperationsCount(years.get(0), Operation.Schengen.SCHENGEN));
    chart.addSeries(config.getString(Config.NO_SCHENGEN_SERIES),
                    operationsStatistics.getOperationsCount(years.get(0), Operation.Schengen.NO_SCHENGEN));

    return chart;
  }
}
