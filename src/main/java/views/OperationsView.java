package views;

import controllers.Config;
import controllers.IViewListener;
import controllers.graphfactory.GraphFactory;
import models.OperationsStatistics;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.chartpart.Chart;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OperationsView extends JFrame implements ActionListener {
  public enum FILE_ACTION {OPEN, SAVE}

  private final IViewListener controller;
  private final Config config;

  private JLabel stateStatus;
  private StatisticSelectionView statisticSelectionView;
  private JPanel chartPane;
  private JMenuItem saveChart;

  private GraphFactory.GraphFamily graphFamily;

  public OperationsView(IViewListener controller, Config config) {
    super(config.getString(Config.APP_NAME) + " " + config.getString(Config.APP_VERSION));
    this.config = config;
    this.controller = controller;
    graphFamily = GraphFactory.GraphFamily.YEAR;

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
    statisticSelectionView = new StatisticSelectionView(this, centerPane, graphFamily, config);

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

  private JRadioButtonMenuItem createButtonMenuItem (String label) {
    JRadioButtonMenuItem radioButtonMenuItem =
      new JRadioButtonMenuItem(config.getString(label));

    radioButtonMenuItem.addActionListener(this);
    radioButtonMenuItem.setActionCommand(label);

    return radioButtonMenuItem;
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

    saveChart = createMenuItem(Config.SAVE_GRAPH_MENU_ITEM, false);
    fileMenu.add(saveChart);

    return fileMenu;
  }

  private JMenu createSettingsMenu(String label) {
    JMenu settingsMenu = new JMenu(label);

    JMenu graphTypeMenu = new JMenu(config.getString(Config.GRAPH_FAMILY_MENU_ITEM));
    settingsMenu.add(graphTypeMenu);

    ButtonGroup buttonGroup = new ButtonGroup();
    JRadioButtonMenuItem yearButton = createButtonMenuItem(Config.YEAR_RADIO_BUTTON);
    JRadioButtonMenuItem trimesterButton = createButtonMenuItem(Config.TRIMESTER_RADIO_BUTTON);
    buttonGroup.add(yearButton);
    buttonGroup.add(trimesterButton);
    graphTypeMenu.add(yearButton);
    graphTypeMenu.add(trimesterButton);

    yearButton.setSelected(true);
    return settingsMenu;
  }

  private void createMenuBar(JPanel panel) {
    JMenuBar menuBar = new JMenuBar();

    menuBar.add(createFileMenu(config.getString(Config.FILE_MENU)));
    menuBar.add(createSettingsMenu(config.getString(Config.SETTINGS_MENU)));

    panel.add(menuBar);
  }

  public void notify(IViewListener.Event event, Object o) {
    controller.eventFired(event, o);
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

  public void initStatisticSelection(OperationsStatistics statistics) {
    statisticSelectionView.initStatisticSelection(statistics);
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

  public void enableEvent(IViewListener.Event event, boolean enable) {
    switch (event) {
      case SAVE_CHART -> saveChart.setEnabled(enable);
    }
  }

  public void changeStatisticSelection(GraphFactory.GraphFamily newGraphFamily) {
    if (newGraphFamily != graphFamily) {
      statisticSelectionView.changeStatisticSelection(newGraphFamily);
      graphFamily = newGraphFamily;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case Config.OPEN_FILE_MENU_ITEM -> controller.eventFired(IViewListener.Event.OPEN_FILE, null);
      case Config.SAVE_GRAPH_MENU_ITEM -> controller.eventFired(IViewListener.Event.SAVE_CHART, null);
      case Config.YEAR_RADIO_BUTTON -> changeStatisticSelection(GraphFactory.GraphFamily.YEAR);
      case Config.TRIMESTER_RADIO_BUTTON -> changeStatisticSelection(GraphFactory.GraphFamily.TRIMESTER);
    }
  }
}
