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
import java.io.File;

public class OperationsView extends JFrame implements ActionListener {
  private IViewListener controller;

  private JPanel statisticSelectionPane;
  private JPanel chartPane;
  private JComboBox yearComboBox;
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
    
    yearComboBox = new JComboBox();
    statisticComboBox = new JComboBox();
    statisticSelectionPane.add(yearComboBox);
    statisticSelectionPane.add(statisticComboBox);

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
    for (int year: operationsStatistics.getDifferentYears()) {
      yearComboBox.addItem(year);
    }

    for (StatisticType statisticType: StatisticType.values()) {
      statisticComboBox.addItem(statisticType.toString());
    }

    statisticSelectionPane.setVisible(true);
  }

  public void initChartView(Chart chart) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        chartPane.removeAll();

        XChartPanel chartWrapper = new XChartPanel<>(chart);
        chartPane.add(chartWrapper);

        chartPane.revalidate();
      }
    });
  }

  private Tuple<Integer, StatisticType> readStatisticSelection() {
    return new Tuple((int) yearComboBox.getSelectedItem(),
                      (StatisticType) StatisticType.getStatisticType((String) statisticComboBox.getSelectedItem()));
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
