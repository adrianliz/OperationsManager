package models.graphs;

import controllers.Config;
import models.OperationsStatistics;

abstract class Graph {
  protected final OperationsStatistics statistics;
  protected final Config config;

  Graph(OperationsStatistics statistics, Config config) {
    this.statistics = statistics;
    this.config = config;
  }
}
