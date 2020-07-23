package controllers;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import javax.swing.*;
import java.io.File;

public class Config {
  private static final String CONFIG_FILE = "config.properties";
  public static final String READ_CONFIG_ERROR = "No se pudo leer el fichero de configuración";

  /* APP CONFIG */
  public static final String APP_NAME = "APP_NAME";
  public static final String APP_VERSION = "APP_VERSION";
  public static final String APP_ICON = "APP_ICON";
  public static final String ALERT_ICON = "ALERT_ICON";
  public static final String SUCCESS_ICON = "SUCCESS_ICON";

  /* EXCEL CONFIG */
  public static final String EXCEL_HEADER_ROW = "EXCEL_HEADER_ROW";
  public static final String EXCEL_DATE_FORMAT_INDEX = "EXCEL_DATE_FORMAT_INDEX";
  public static final String LANDINGS_SHEET = "LANDINGS_SHEET";
  public static final String DEPARTURES_SHEET = "DEPARTURES_SHEET";
  public static final String EXCEL_DATE_FORMAT = "EXCEL_DATE_FORMAT";

  /* VIEW CONFIG */
  public static final String MINIMUM_WINDOW_WIDTH = "MINIMUM_WINDOW_WIDTH";
  public static final String MINIMUM_WINDOW_HEIGHT = "MINIMUM_WINDOW_HEIGHT";
  public static final String VERTICAL_GAP_MENU_BAR = "VERTICAL_GAP_MENU_BAR";
  public static final String MENU_BAR_ITEMS = "MENU_BAR_ITEMS";
  public static final String HORIZONTAL_GAP_STATISTIC_PANE = "HORIZONTAL_GAP_STATISTIC_PANE";

  public static final String FILE_MENU = "FILE_MENU";
  public static final String OPEN_FILE_MENU_ITEM = "OPEN_FILE_MENU_ITEM";
  public static final String SAVE_CHART_MENU_ITEM = "SAVE_CHART_MENU_ITEM";
  public static final String ACCEPT_STATISTIC_BUTTON = "ACCEPT_STATISTIC_BUTTON";

  public static final String EXCEL_DESC = "EXCEL_DESC";
  public static final String PNG_DESC = "PNG_DESC";
  public static final String EXCEL_EXT = "EXCEL_EXT";
  public static final String PNG_EXT = "PNG_EXT";
  public static final String PNG_PDI = "PNG_PDI";

  public static final String INITIAL_STATE = "INITIAL_STATE";
  public static final String FILE_SELECTED_STATE = "FILE_SELECTED_STATE";

  /* MESSAGES */
  public static final String CHART_SAVED = "CHART_SAVED";
  public static final String FILE_OPEN_LOG = "FILE_OPEN_LOG";
  public static final String CHART_GENERATED_LOG = "CHART_GENERATED_LOG";
  public static final String CHART_SAVED_LOG = "CHART_SAVED_lOG";

  public static final String LAF_ERROR = "LAF_ERROR";
  public static final String FILE_EXT_ERROR = "FILE_EXT_ERROR";
  public static final String OPERATIONS_NOT_FOUND = "OPERATIONS_NOT_FOUND";
  public static final String SAVE_CHART_ERROR = "SAVE_CHART_ERROR";

  /* CHARTS CONFIG */
  public static final String SCHENGEN_CHART_TITLE = "SCHENGEN_CHART_TITLE";
  public static final String SCHENGEN_SERIES = "SCHENGEN_SERIES";
  public static final String NO_SCHENGEN_SERIES = "NO_SCHENGEN_SERIES";
  public static final String AIRCRAFT_TYPE_CHART_TITLE = "AIRCRAFT_TYPE_CHART_TITLE";
  public static final String X_AIRCRAFT_TYPE_SERIES = "X_AIRCRAFT_TYPE_SERIES";
  public static final String Y_AIRCRAFT_TYPE_SERIES = "Y_AIRCRAFT_TYPE_SERIES";
  public static final String MTOW_CHART_TITLE = "MTOW_CHART_TITLE";
  public static final String X_MTOW_SERIES = "X_MTOW_SERIES";
  public static final String Y_MTOW_SERIES = "Y_MTOW_SERIES";

  private Configuration config;

  public Config() throws ConfigurationException {
    Configurations configs = new Configurations();
    config = configs.properties(new File(CONFIG_FILE));
  }

  public int getInt(String key) {
    return config.getInt(key);
  }

  public String getString(String key) {
    return config.getString(key);
  }

  public ImageIcon getIcon(String key) {
    return new ImageIcon(this.getClass().getResource(getString(key)));
  }
}
