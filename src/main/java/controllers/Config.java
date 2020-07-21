package controllers;

import java.awt.*;
import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class Config {
  private static final String CONFIG_FILE = "config.properties";

  /* Static config */
  public static final int DATE_FORMAT_INDEX = 14;
  public static final String SCHENGEN = "SCHENGEN";

  /* View */
  public static final int WINDOW_WIDTH = 1080;
  public static final int WINDOW_HEIGHT = 720;

  public static final String FILE_MENU = "File";
  public static final String OPEN_MENU_ITEM = "Open...";
  public static final String ACCEPT_STATISTIC_BUTTON = "Ok";

  public static final String EXCEL_DESC = "Excel file";
  public static final String EXCEL_EXT = "xlsx";

  public static final String SCHENGEN_CHART_TITLE = "Schengen operations";

  /* Dinamic config */
  public static final String APP_NAME = "APP_NAME";
  public static final String HEADER_START = "HEADER_START";
  public static final String LANDINGS_SHEET = "LANDINGS_SHEET";
  public static final String DEPARTURES_SHEET = "DEPARTURES_SHEET";
  public static final String DATE_FORMAT = "DATE_FORMAT";
  public static Dimension screenDimension;

  private Configuration config;

  public Config() throws ConfigurationException {
    Configurations configs = new Configurations();
    config = configs.properties(new File(CONFIG_FILE));
    screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
  }

  public int getInt(String key) {
    return config.getInt(key);
  }

  public String getString(String key) {
    return config.getString(key);
  }

  public File getFile(String key) {
    return new File(this.getClass().getResource(config.getString(key)).getPath());
  }
}
