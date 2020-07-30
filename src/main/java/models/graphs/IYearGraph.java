package models.graphs;

import org.knowm.xchart.internal.chartpart.Chart;

import java.util.List;

public interface IYearGraph {
  Chart createGraph(List<Integer> years);
}
