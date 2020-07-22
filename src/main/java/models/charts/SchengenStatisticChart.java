package models.charts;

import controllers.Config;
import models.OperationsStatistics;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler;

import java.util.List;

public class SchengenStatisticChart implements IStatisticChart {
  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years) {
    PieChart chart =
      new PieChartBuilder()
        .title(Config.SCHENGEN_CHART)
        .build();

    int schengenOperations = operationsStatistics.getSchengenOperations(years.get(0));

    PieStyler styler = chart.getStyler();
    styler.setLegendVisible(true);
    styler.setDrawAllAnnotations(true);
    styler.setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
    styler.setAnnotationDistance(1.15);
    styler.setPlotContentSize(.8);
    styler.setStartAngleInDegrees(90);

    chart.addSeries(Config.SCHENGEN_SERIES, schengenOperations);
    chart.addSeries(Config.NO_SCHENGEN_SERIES,
              operationsStatistics.getOperationsCount(years.get(0)) - schengenOperations);

    return chart;
  }
}
