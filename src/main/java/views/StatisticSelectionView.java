package views;

import controllers.Config;
import controllers.IViewListener;
import controllers.graphfactory.GraphFactory;
import models.*;
import models.enums.StatisticType;
import models.enums.Trimester;
import models.graphs.GraphProperties;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class StatisticSelectionView extends JPanel {
  private final OperationsView mainView;
  private JComboBox<String> statisticsOptions;
  private JComboBox<Integer> firstYearOptions;
  private JComboBox<Integer> lastYearOptions;
  private JComboBox<String> trimesterOptions;

  private final Config config;
  private List<Integer> statisticsYears;
  private GraphFactory.GraphFamily graphFamily;

  StatisticSelectionView(OperationsView mainView, JPanel parentPanel,
                         GraphFactory.GraphFamily graphFamily, Config config) {

    super(new FlowLayout(FlowLayout.CENTER, config.getInt(Config.HORIZONTAL_GAP_STATISTIC_SELECTION), 0));
    this.config = config;
    this.mainView = mainView;
    this.graphFamily = graphFamily;

    createView();
    parentPanel.add(this);
  }

  private void createView() {
    statisticsOptions = new JComboBox<>();
    firstYearOptions = new JComboBox<>();
    lastYearOptions = new JComboBox<>();
    trimesterOptions = new JComboBox<>();
    JButton generateGraphButton = new JButton(config.getString(Config.GENERATE_GRAPH_BUTTON));

    //TODO Clean listeners
    firstYearOptions.addItemListener(e -> {
      lastYearOptions.removeAllItems();

      for (int year: statisticsYears) {
        if (year >= (int) e.getItem()) {
          lastYearOptions.addItem(year);
        }
      }
    });

    for (StatisticType statisticType: StatisticType.values()) {
      statisticsOptions.addItem(statisticType.toString());

      statisticsOptions.addItemListener(e -> {
        lastYearOptions.setEnabled(StatisticType.getStatisticType((String) e.getItem()) != StatisticType.SCHENGEN_OP);
      });
    }

    for (Trimester trimester: Trimester.values()) {
      trimesterOptions.addItem(trimester.toString());
    }

    generateGraphButton.addActionListener(e -> StatisticSelectionView.this.readSelection());

    add(statisticsOptions);
    add(firstYearOptions);
    add(lastYearOptions);
    add(trimesterOptions);
    add(generateGraphButton);

    trimesterOptions.setVisible(false);
    setVisible(false);
  }

  private void readSelection() {
    GraphProperties graphProperties = null;
    StatisticType statisticType = StatisticType.getStatisticType((String) statisticsOptions.getSelectedItem());
    int firstYear = (int) firstYearOptions.getSelectedItem();

    switch (graphFamily) {
      case YEAR -> graphProperties = new GraphProperties(getYearsAfter(firstYear, (int) lastYearOptions.getSelectedItem()));
      case TRIMESTER -> graphProperties =
        new GraphProperties(firstYear, Trimester.getTrimesterType((String) trimesterOptions.getSelectedItem()));

    }

    mainView.notify(IViewListener.Event.GENERATE_GRAPH, new Tuple<>(graphFamily, new Tuple<>(statisticType, graphProperties)));
  }

  private List<Integer> getYearsAfter(int firstYear, int lastYear) {
    List <Integer> years = new ArrayList<>();

    while (firstYear <= lastYear) {
      years.add(firstYear++);
    }

    return years;
  }

  void initStatisticSelection(OperationsStatistics statistics) {
    statisticsYears = statistics.getDifferentYears();

    firstYearOptions.removeAllItems();
    lastYearOptions.removeAllItems();

    for (int year: statisticsYears) {
      firstYearOptions.addItem(year);
      lastYearOptions.addItem(year);
    }

    setVisible(true);
  }

  void changeStatisticSelection(GraphFactory.GraphFamily graphFamily) {
    switch (graphFamily) {
      case YEAR -> {
        lastYearOptions.setVisible(true);
        trimesterOptions.setVisible(false);
        statisticsOptions.addItem(StatisticType.MTOW_AVERAGE.toString());
        statisticsOptions.addItem(StatisticType.SCHENGEN_OP.toString());
      }

      case TRIMESTER -> {
        lastYearOptions.setVisible(false);
        trimesterOptions.setVisible(true);
        statisticsOptions.removeItem(StatisticType.MTOW_AVERAGE.toString());
        statisticsOptions.removeItem(StatisticType.SCHENGEN_OP.toString());
      }
    }

    this.graphFamily = graphFamily;
  }
}
