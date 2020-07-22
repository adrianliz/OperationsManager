package models.charts;

import controllers.Config;
import models.OperationsStatistics;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.XYStyler;

import java.util.ArrayList;
import java.util.List;

public class MTOWStaticChart implements IStatisticChart {

  @Override
  public Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years) {
    CategoryChart chart =
      new CategoryChartBuilder()
        .title(Config.MTOW_CHART)
        .xAxisTitle(Config.X_MTOW)
        .yAxisTitle(Config.Y_MTOW)
        .build();

    CategoryStyler styler = chart.getStyler();
    styler.setChartTitleVisible(true);
    chart.getStyler().setAvailableSpaceFill(.20);
    styler.setLegendPosition(Styler.LegendPosition.InsideNW);
    styler.setHasAnnotations(true);

    List<Integer> yData = new ArrayList<>();
    for (int year: years) {
      yData.add(operationsStatistics.getMTOWAverage(year));
    }

    chart.addSeries(Config.MTOW_CHART, years, yData);
    return chart;
  }
}
