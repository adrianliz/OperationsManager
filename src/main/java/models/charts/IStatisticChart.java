package models.charts;

import models.OperationsStatistics;
import org.knowm.xchart.internal.chartpart.Chart;

import java.util.List;

public interface IStatisticChart {
 Chart createChart(OperationsStatistics operationsStatistics, List<Integer> years);
}
