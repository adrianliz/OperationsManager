package controllers.graphfactory;

import controllers.Config;
import models.OperationsStatistics;
import models.enums.StatisticType;
import models.graphs.AircraftTypeGraph;
import models.graphs.GraphProperties;
import models.graphs.MTOWGraph;
import models.graphs.SchengenGraph;
import org.knowm.xchart.internal.chartpart.Chart;

public abstract class GraphFactory {
  public enum GraphFamily {YEAR, TRIMESTER}

  private static final GraphFactory YEAR_GRAPH_FACTORY = new YearGraphFactory();
  private static final GraphFactory TRIMESTER_GRAPH_FACTORY = new TrimesterGraphFactory();

  protected static SchengenGraph schengenGraph;
  protected static AircraftTypeGraph aircraftTypeGraph;
  protected static MTOWGraph MTOWGraph;

  public static void initFactory(OperationsStatistics statistics, Config config) {
    schengenGraph = new SchengenGraph(statistics, config);
    aircraftTypeGraph = new AircraftTypeGraph(statistics, config);
    MTOWGraph = new MTOWGraph(statistics, config);
  }

  public static GraphFactory getFactory(GraphFamily graphFamily) {
    GraphFactory graphFactory = null;

    switch (graphFamily) {
      case YEAR -> graphFactory = YEAR_GRAPH_FACTORY;
      case TRIMESTER -> graphFactory = TRIMESTER_GRAPH_FACTORY;
    }

    return graphFactory;
  }

  public abstract Chart createGraph(StatisticType statisticType, GraphProperties graphProperties);
}
