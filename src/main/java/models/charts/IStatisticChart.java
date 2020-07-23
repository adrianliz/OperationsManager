package models.charts;

import controllers.Config;
import models.OperationsStatistics;
import org.knowm.xchart.internal.chartpart.Chart;

import java.util.List;

public interface IStatisticChart {
 Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years, Config config);
}
