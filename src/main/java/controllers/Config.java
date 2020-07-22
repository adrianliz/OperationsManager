package controllers;

import java.awt.*;
import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import javax.swing.*;

public class Config {
  private static final String CONFIG_FILE = "config.properties";

  /* Dinamic config */
  public static final String APP_NAME = "APP_NAME";
  public static final String APP_VERSION = "APP_VERSION";
  public static final String APP_ICON = "APP_ICON";
  public static final String ALERT_ICON = "ALERT_ICON";
  public static final String HEADER_START = "HEADER_START";
  public static final String LANDINGS_SHEET = "LANDINGS_SHEET";
  public static final String DEPARTURES_SHEET = "DEPARTURES_SHEET";
  public static final String DATE_FORMAT = "DATE_FORMAT";

  /* Static config */
  public static final int DATE_FORMAT_INDEX = 14;
  public static final String SCHENGEN = "SCHENGEN";

  /* View */
  public static final int MINIMUM_WINDOW_W = 800;
  public static final int MINIMUM_WINDOW_H = 800;
  public static final int VERTICAL_GAP_MENU = 10;
  public static final int MENU_ITEMS = 1;
  public static final int H_GAP_STATISTIC_PANE = 25;

  public static final String FILE_MENU = "Fichero";
  public static final String OPEN_MENU_ITEM = "Abrir...";
  public static final String ACCEPT_STATISTIC_BUTTON = "OK";

  public static final String EXCEL_DESC = "Excel file";
  public static final String EXCEL_EXT = "xlsx";

  public static final String SCHENGEN_CHART = "Operaciones Schengen";
  public static final String SCHENGEN_SERIES = "Schengen";
  public static final String NO_SCHENGEN_SERIES = "No schengen";
  public static final String AIRCRAFT_TYPE_CHART = "Tipos de aeronaves";
  public static final String X_AIRCRAFT_TYPE = "Año";
  public static final String Y_AIRCRAFT_TYPE = "Total";
  public static final String MTOW_CHART = "Media MTOW";
  public static final String X_MTOW = "Año";
  public static final String Y_MTOW = "Media";

  public static final String INITIAL_STATE = "Abre un nuevo fichero de operaciones";
  public static final String FILE_STATE = "Fichero:";

  public static final String LAF_ERROR = "No se pudo aplicar el LAF";
  public static final String CONFIG_ERROR = "No se pudo leer el fichero de configuración";
  public static final String FILE_EXT_ERROR = "Extensión de fichero inválida";
  public static final String OP_NOT_FOUND = "No se han encontrado operaciones";

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
