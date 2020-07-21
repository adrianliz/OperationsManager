package views;

import controllers.Config;
import controllers.IViewListener;
import models.AircraftType;
import models.OperationsStatistics;
import models.StatisticType;
import models.Tuple;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.chartpart.Chart;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

public class OperationsView extends JFrame implements ActionListener {
  private IViewListener controller;

  private JPanel statisticSelectionPane;
  private JPanel chartPane;
  private JComboBox firstYearComboBox;
  private JComboBox secondYearComboBox;
  private JComboBox statisticComboBox;

  public OperationsView(IViewListener controller, Config config) {
    super(config.getString(Config.APP_NAME));
    this.controller = controller;

    createMainWindow();
  }

  private void createMainWindow()  {
    setLayout(new BorderLayout(0, 10));

    JPanel northPane = new JPanel();
    northPane.setLayout(new GridLayout(1,1));
    createMenuBar(northPane);

    JPanel centerPane = new JPanel();
    centerPane.setLayout(new BorderLayout());
    createStatisticSelection(centerPane);

    chartPane = new JPanel();
    centerPane.add(chartPane, BorderLayout.CENTER);

    add(northPane, BorderLayout.NORTH);
    add(centerPane, BorderLayout.CENTER);

    setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    setResizable(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);
  }

  private JMenuItem createMenuItem(String label, boolean enable) {
    JMenuItem menuItem = new JMenuItem(label, label.charAt(0));

    menuItem.addActionListener(this);
    menuItem.setActionCommand(label);
    menuItem.setEnabled(enable);

    return menuItem;
  }

  private JMenuItem createMenuItem(String label) {
    return createMenuItem(label, true);
  }

  private JMenu createFileMenu(String label) {
    JMenu fileMenu = new JMenu(label);
    fileMenu.add(createMenuItem(Config.OPEN_MENU_ITEM));

    return fileMenu;
  }

  private void createMenuBar(JPanel panel) {
    JMenuBar menuBar = new JMenuBar();

    menuBar.add(createFileMenu(Config.FILE_MENU));

    panel.add(menuBar);
  }

  private void createStatisticSelection(JPanel panel) {
    statisticSelectionPane = new JPanel();
    statisticSelectionPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 25, 0));

    statisticComboBox = new JComboBox();
    firstYearComboBox = new JComboBox();
    secondYearComboBox = new JComboBox();
    statisticSelectionPane.add(statisticComboBox);
    statisticSelectionPane.add(firstYearComboBox);
    statisticSelectionPane.add(secondYearComboBox);

    JButton acceptStatisticButton = new JButton(Config.ACCEPT_STATISTIC_BUTTON);
    acceptStatisticButton.setPreferredSize(new Dimension(80, 40));
    acceptStatisticButton.addActionListener(this);
    acceptStatisticButton.setActionCommand(Config.ACCEPT_STATISTIC_BUTTON);
    statisticSelectionPane.add(acceptStatisticButton);

    statisticSelectionPane.setVisible(false);
    panel.add(statisticSelectionPane, BorderLayout.NORTH);
  }

  public String selectFile() {
    int result = -1;
    String filePath = null;

    JFileChooser fileChooser = new JFileChooser(new File("."));
    FileNameExtensionFilter filter =
      new FileNameExtensionFilter(Config.EXCEL_DESC, Config.EXCEL_EXT); //TODO config

    fileChooser.setFileFilter(filter);
    result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      filePath = fileChooser.getSelectedFile().getAbsolutePath();
    }

    return filePath;
  }

  public void initStatisticSelection(OperationsStatistics operationsStatistics) {
    for (StatisticType statisticType: StatisticType.values()) {
      statisticComboBox.addItem(statisticType.toString());
    }

    statisticComboBox.addItemListener(e -> {
      if (StatisticType.getStatisticType((String) e.getItem()) != StatisticType.SCHENGEN) {
        secondYearComboBox.setVisible(true);
      } else {
        secondYearComboBox.setVisible(false);
      }
    });

    for (int year: operationsStatistics.getDifferentYears()) {
      firstYearComboBox.addItem(year);
      secondYearComboBox.addItem(year);
    }

    secondYearComboBox.setVisible(false);
    statisticSelectionPane.setVisible(true);
  }

  public void initChartView(Chart chart) {
    SwingUtilities.invokeLater(() -> {
      chartPane.removeAll();

      XChartPanel chartWrapper = new XChartPanel<>(chart);
      chartPane.add(chartWrapper);

      chartPane.revalidate();
    });
  }

  private Tuple<Tuple<Integer, Integer>, StatisticType> readStatisticSelection() {
    return new Tuple(StatisticType.getStatisticType((String) statisticComboBox.getSelectedItem()),
      new Tuple(firstYearComboBox.getSelectedItem(), secondYearComboBox.getSelectedItem()));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case Config.OPEN_MENU_ITEM -> controller.eventFired(IViewListener.Event.OPEN, null);
      case Config.ACCEPT_STATISTIC_BUTTON -> controller.eventFired(IViewListener.Event.ACCEPT_STATISTIC,
                                                                   readStatisticSelection());
    }
  }
}
