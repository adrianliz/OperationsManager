package models.charts;

import models.OperationsStatistics;
import models.Tuple;
import org.knowm.xchart.internal.chartpart.Chart;

public interface IChart {
 Chart createChart(OperationsStatistics operationsStatistics, Tuple<Integer, Integer> years);
}
