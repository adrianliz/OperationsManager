package models.charts;

import models.OperationsStatistics;
import models.Tuple;
import org.knowm.xchart.internal.chartpart.Chart;

import java.util.List;

public interface IChart {
 Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years);
}
