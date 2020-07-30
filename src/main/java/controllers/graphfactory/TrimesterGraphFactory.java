package controllers.graphfactory;

import models.enums.StatisticType;
import models.graphs.GraphProperties;
import org.knowm.xchart.internal.chartpart.Chart;

class TrimesterGraphFactory extends GraphFactory {
  @Override
  public Chart createGraph(StatisticType statisticType, GraphProperties graphProperties) {
    switch (statisticType) {
      case AIRCRAFT_TYPE -> {
        return aircraftTypeGraph.createGraph(graphProperties.getYear(), graphProperties.getTrimester());
      }
      default -> throw new IllegalStateException("Unexpected value: " + statisticType);
    }
  }
}
