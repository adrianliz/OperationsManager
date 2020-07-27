package views;

import controllers.Config;
import controllers.IViewListener;
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
import java.util.ArrayList;
import java.util.List;

public class OperationsView extends JFrame implements ActionListener {
  public enum FILE_ACTION {OPEN, SAVE}

  private final IViewListener controller;
  private final Config config;

  private JLabel stateStatus;
  private JPanel statisticPane;
  private JPanel chartPane;
  private JComboBox<Integer> firstYearSelection;
  private JComboBox<Integer> lastYearSelection;
  private JComboBox<String> statisticSelection;
  private JMenuItem saveChart;

  public OperationsView(IViewListener controller, Config config) {
    super(config.getString(Config.APP_NAME) + " " + config.getString(Config.APP_VERSION));
    this.config = config;
    this.controller = controller;

    createMainWindow();
  }

  private void createMainWindow()  {
    setLayout(new BorderLayout(0, config.getInt(Config.VERTICAL_GAP_MENU_BAR)));
    setIconImage(config.getIcon(Config.APP_ICON).getImage());

    JPanel northPane = new JPanel();
    northPane.setLayout(new GridLayout(1, config.getInt(Config.MENU_BAR_ITEMS)));
    createMenuBar(northPane);

    JPanel centerPane = new JPanel();
    centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
    createStatisticSelection(centerPane);

    chartPane = new JPanel();
    chartPane.setLayout(new GridLayout(1, 1));
    chartPane.setBorder(BorderFactory.createBevelBorder(1));
    chartPane.setVisible(false);
    centerPane.add(chartPane);

    stateStatus = new JLabel(config.getString(Config.INITIAL_STATE));

    add(northPane, BorderLayout.NORTH);
    add(centerPane, BorderLayout.CENTER);
    add(stateStatus, BorderLayout.SOUTH);

    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setSize(getEffectiveScreenSize());
    setMinimumSize(new Dimension(config.getInt(Config.MINIMUM_WINDOW_WIDTH),
                                 config.getInt(Config.MINIMUM_WINDOW_HEIGHT)));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);
  }

  private Dimension getEffectiveScreenSize() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
    int taskBarSize = screenInsets.bottom;

    return new Dimension(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
  }

  private JMenuItem createMenuItem(String label, boolean enable) {
    JMenuItem menuItem = new JMenuItem(config.getString(label), config.getString(label).charAt(0));

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
    fileMenu.add(createMenuItem(Config.OPEN_FILE_MENU_ITEM));
    fileMenu.addSeparator();

    saveChart = createMenuItem(Config.SAVE_CHART_MENU_ITEM, false);
    fileMenu.add(saveChart);

    return fileMenu;
  }

  private void createMenuBar(JPanel panel) {
    JMenuBar menuBar = new JMenuBar();

    menuBar.add(createFileMenu(config.getString(Config.FILE_MENU)));

    panel.add(menuBar);
  }

  private JButton createButton(String label, boolean enable) {
    JButton button = new JButton(config.getString(label));
    button.addActionListener(this);
    button.setActionCommand(label);
    button.setEnabled(enable);

    return button;
  }

  private JButton createButton(String label) {
    return createButton(label, true);
  }

  private void addStatistics() {
    for (StatisticType statisticType: StatisticType.values()) {
      statisticSelection.addItem(statisticType.toString());
    }

    statisticSelection.addItemListener(e -> {
      lastYearSelection.setVisible(StatisticType.getStatisticType((String) e.getItem()) != StatisticType.SCHENGEN);
    });
  }

  private void createStatisticSelection(JPanel panel) {
    statisticPane = new JPanel();
    statisticPane.setLayout(new FlowLayout(FlowLayout.CENTER, config.getInt(Config.HORIZONTAL_GAP_STATISTIC_PANE), 0));

    statisticSelection = new JComboBox<>();
    firstYearSelection = new JComboBox<>();
    lastYearSelection = new JComboBox<>();
    statisticPane.add(statisticSelection);
    statisticPane.add(firstYearSelection);
    statisticPane.add(lastYearSelection);

    addStatistics();
    statisticPane.add(createButton(Config.ACCEPT_STATISTIC_BUTTON));

    statisticPane.setVisible(false);
    panel.add(statisticPane);
  }

  public void showDialogMessage(String message, ImageIcon icon) {
      JOptionPane.showMessageDialog(this, message, config.getString(Config.APP_NAME) + " " +
                                    config.getString(Config.APP_VERSION), JOptionPane.INFORMATION_MESSAGE, icon);
  }

  public void setStateStatus(String message) {
    stateStatus.setText(message);
  }

  public String selectFilePath(FILE_ACTION fileAction) {
    int result = -1;
    String filePath = null;

    JFileChooser fileChooser = new JFileChooser(new File("."));
    FileNameExtensionFilter filter;

    switch (fileAction) {
      case OPEN -> {
        filter = new FileNameExtensionFilter(config.getString(Config.EXCEL_DESC), config.getString(Config.EXCEL_EXT));

        fileChooser.setFileFilter(filter);
        result = fileChooser.showOpenDialog(this);
      }
      case SAVE -> {
        filter = new FileNameExtensionFilter(config.getString(Config.PNG_DESC), config.getString(Config.PNG_EXT));

        fileChooser.setFileFilter(filter);
        result = fileChooser.showSaveDialog(this);
      }
    }

    if (result == JFileChooser.APPROVE_OPTION) {
      filePath = fileChooser.getSelectedFile().getAbsolutePath();
    }

    return filePath;
  }

  public void initStatisticSelection(OperationsStatistics operationsStatistics) {
    List<Integer> years = operationsStatistics.getDifferentYears();
    firstYearSelection.removeAllItems();
    lastYearSelection.removeAllItems();

    for (int year: years) {
      firstYearSelection.addItem(year);
      lastYearSelection.addItem(year);
    }

    firstYearSelection.addItemListener(e -> {
      lastYearSelection.removeAllItems();
      for (int year: getYearsAfter((int) e.getItem(), years.get(years.size() - 1))) {
        lastYearSelection.addItem(year);
      }
    });

    lastYearSelection.setVisible(false);
    statisticPane.setVisible(true);
  }

  public void disableChartPane() {
    chartPane.setVisible(false);
  }

  public void initChartView(Chart chart) {
    SwingUtilities.invokeLater(() -> {
      chartPane.removeAll();

      XChartPanel chartWrapper = new XChartPanel<>(chart);
      chartPane.add(chartWrapper);

      chartPane.revalidate();
      chartPane.setVisible(true);
    });
  }

  private List<Integer> getYearsAfter(int firstYear, int lastYear) {
    List <Integer> years = new ArrayList<>();

    while (firstYear <= lastYear) {
      years.add(firstYear++);
    }

    return years;
  }

  private Tuple<StatisticType, List<Integer>> readStatisticSelection() {
    return new Tuple<>(StatisticType.getStatisticType((String) statisticSelection.getSelectedItem()),
                       getYearsAfter((int) firstYearSelection.getSelectedItem(),
                                     (int) lastYearSelection.getSelectedItem()));
  }

  public void enableEvent(IViewListener.Event event, boolean enable) {
    switch (event) {
      case SAVE_CHART -> saveChart.setEnabled(enable);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case Config.OPEN_FILE_MENU_ITEM -> controller.eventFired(IViewListener.Event.OPEN_FILE, null);
      case Config.ACCEPT_STATISTIC_BUTTON -> controller.eventFired(IViewListener.Event.GENERATE_CHART,
                                                                   readStatisticSelection());
      case Config.SAVE_CHART_MENU_ITEM -> controller.eventFired(IViewListener.Event.SAVE_CHART, null);
    }
  }
}
