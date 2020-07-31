package controllers.graphfactory;

import models.enums.StatisticType;
import models.graphs.GraphProperties;
import org.knowm.xchart.internal.chartpart.Chart;

class YearGraphFactory extends GraphFactory {
  @Override
  public Chart createGraph(StatisticType statisticType, GraphProperties graphProperties) {
    switch (statisticType) {
      case SCHENGEN_OP -> {
        return schengenGraph.createGraph(graphProperties.getYears());
      }
      case AIRCRAFT_TYPE -> {
        return aircraftTypeGraph.createGraph(graphProperties.getYears());
      }
      case MTOW_AVERAGE -> {
        return MTOWGraph.createGraph(graphProperties.getYears());
      }
      case OP_EVOLUTION -> {
        return operationsEvolutionGraph.createGraph(graphProperties.getYears());
      }
      case HEADER_RUNWAY_OP -> {
        return headerRunwayGraph.createGraph(graphProperties.getYears());
      }
      default -> throw new IllegalStateException("Unexpected value: " + statisticType);
    }
  }
}
