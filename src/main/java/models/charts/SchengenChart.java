package models.charts;

import controllers.Config;
import models.OperationsStatistics;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler;

import java.util.List;

public class SchengenChart implements IChart {
  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years) {
    PieChart chart =
      new PieChartBuilder()
        .width(Config.screenDimension.width - 100)
        .title(Config.SCHENGEN_CHART)
        .build();

    int schengenOperations = operationsStatistics.getSchengenOperations(years.get(0));

    PieStyler pieStyler = chart.getStyler();
    pieStyler.setLegendVisible(true);
    pieStyler.setDrawAllAnnotations(true);
    pieStyler.setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
    pieStyler.setAnnotationDistance(1.15);
    pieStyler.setPlotContentSize(.8);
    pieStyler.setStartAngleInDegrees(90);

    chart.addSeries(Config.SCHENGEN_SERIES, schengenOperations);
    chart.addSeries(Config.NO_SCHENGEN_SERIES,
              operationsStatistics.getOperationsCount(years.get(0)) - schengenOperations);

    return chart;
  }
}
