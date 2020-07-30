package models.graphs;

import controllers.Config;
import models.Operation;
import models.OperationsStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler;

import java.util.List;

public class SchengenGraph implements IYearGraph {
  private static final Logger LOG = LogManager.getLogger(MTOWGraph.class);

  private final OperationsStatistics statistics;
  private final Config config;

  public SchengenGraph(OperationsStatistics statistics, Config config) {
    this.statistics = statistics;
    this.config = config;
  }

  @Override
  public Chart createGraph(List<Integer> years) {
    PieChart chart =
      new PieChartBuilder()
        .title(config.getString(Config.SCHENGEN_GRAPH_TITLE))
        .build();

    LOG.info(config.getString(Config.GENERATING_GRAPH_LOG) + " " + chart.getTitle());

    PieStyler styler = chart.getStyler();
    styler.setLegendVisible(true);
    styler.setDrawAllAnnotations(true);
    styler.setToolTipsEnabled(true);
    styler.setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
    styler.setAnnotationDistance(1.15);
    styler.setPlotContentSize(.8);
    styler.setStartAngleInDegrees(90);

    chart.addSeries(config.getString(Config.SCHENGEN_SERIES),
                    statistics.getOperationsCount(years.get(0), Operation.Schengen.SCHENGEN));
    chart.addSeries(config.getString(Config.NO_SCHENGEN_SERIES),
                    statistics.getOperationsCount(years.get(0), Operation.Schengen.NO_SCHENGEN));

    return chart;
  }
}
