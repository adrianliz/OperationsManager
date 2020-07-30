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
  private final static StatisticType[] TRIMESTER_STATISTICS = {StatisticType.AIRCRAFT_TYPE};

  private final OperationsView mainView;
  private final Config config;

  private ComboBoxModel<StatisticType> yearStatisticsOptions;
  private ComboBoxModel<StatisticType> trimesterStatisticsOptions;
  private JComboBox<StatisticType> statisticOptions;
  private JComboBox<Integer> firstYearOptions;
  private JComboBox<Integer> lastYearOptions;
  private JComboBox<Trimester> trimesterOptions;

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
    yearStatisticsOptions = new DefaultComboBoxModel<>(StatisticType.values());
    trimesterStatisticsOptions = new DefaultComboBoxModel<>(TRIMESTER_STATISTICS);

    statisticOptions = new JComboBox<>(yearStatisticsOptions);
    firstYearOptions = new JComboBox<>();
    lastYearOptions = new JComboBox<>();
    trimesterOptions = new JComboBox<>(Trimester.values());
    JButton generateGraphButton = new JButton(config.getString(Config.GENERATE_GRAPH_BUTTON));

    statisticOptions.addItemListener(e -> lastYearOptions.setEnabled(!e.getItem().equals(StatisticType.SCHENGEN_OP)));
    generateGraphButton.addActionListener(e -> readSelection());

    add(statisticOptions);
    add(firstYearOptions);
    add(lastYearOptions);
    add(trimesterOptions);
    add(generateGraphButton);

    trimesterOptions.setVisible(false);
    setVisible(false);
  }

  private void readSelection() {
    GraphProperties graphProperties = null;
    StatisticType statisticType = (StatisticType) statisticOptions.getSelectedItem();
    int firstYear = (int) firstYearOptions.getSelectedItem();

    switch (graphFamily) {
      case YEAR -> graphProperties =
        new GraphProperties(generateGraphYears(firstYear, (int) lastYearOptions.getSelectedItem()));
      case TRIMESTER -> graphProperties =
        new GraphProperties(firstYear, (Trimester) trimesterOptions.getSelectedItem());

    }

    mainView.notify(IViewListener.Event.GENERATE_GRAPH,
                    new Tuple<>(graphFamily, new Tuple<>(statisticType, graphProperties)));
  }

  private List<Integer> generateGraphYears(int firstYear, int lastYear) {
    List<Integer> years = new ArrayList<>();

    while (firstYear <= lastYear) {
      years.add(firstYear++);
    }

    return years;
  }

  void initStatisticSelection(OperationsStatistics statistics) {
    List<Integer> operationsYears = statistics.getDifferentYears();

    firstYearOptions.removeAllItems();
    lastYearOptions.removeAllItems();

    for (int year: operationsYears) {
      firstYearOptions.addItem(year);
      lastYearOptions.addItem(year);
    }

    firstYearOptions.addItemListener(e -> {
      lastYearOptions.removeAllItems();

      for (int year: operationsYears) {
        if (year >= (int) e.getItem()) lastYearOptions.addItem(year);
      }
    });

    setVisible(true);
  }

  void changeStatisticSelection(GraphFactory.GraphFamily graphFamily) {
    switch (graphFamily) {
      case YEAR -> {
        lastYearOptions.setVisible(true);
        trimesterOptions.setVisible(false);
        statisticOptions.setModel(yearStatisticsOptions);
      }

      case TRIMESTER -> {
        lastYearOptions.setVisible(false);
        trimesterOptions.setVisible(true);
        statisticOptions.setModel(trimesterStatisticsOptions);
      }
    }

    this.graphFamily = graphFamily;
  }
}
