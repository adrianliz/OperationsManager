package models.graphs;

import models.enums.Trimester;
import org.knowm.xchart.internal.chartpart.Chart;

public interface ITrimesterGraph {
  Chart createGraph(Integer year, Trimester trimester);
}
