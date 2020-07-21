package models.charts;

import controllers.Config;
import models.OperationsStatistics;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler;

public class SchengenChart implements IChart {
  private OperationsStatistics operationsStatistics;

  public SchengenChart(OperationsStatistics operationsStatistics) {
    this.operationsStatistics = operationsStatistics;
  }
  
  @Override
  public Chart createChart(int year) {

    PieChart chart =
      new PieChartBuilder()
        .width(Config.screenDimension.width - 50)
        .title(Config.SCHENGEN_CHART_TITLE)
        .build();

    int schengenOperations = operationsStatistics.getSchengenOperations(year);

    chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndValue);
    chart.getStyler().setAnnotationDistance(.82);
    chart.getStyler().setPlotContentSize(.9);
    chart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
    chart.getStyler().setDecimalPattern("#");
    chart.getStyler().setSumVisible(true);
    chart.getStyler().setSumFontSize(20f);
    chart.addSeries("SCHENGEN", schengenOperations);
    chart.addSeries("NO SCHENGEN", operationsStatistics.getOperationsCount(year) - schengenOperations);

    return chart;
  }
}
