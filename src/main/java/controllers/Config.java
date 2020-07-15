package controllers;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class Config {
  private static final String CONFIG_FILE = "config.properties";

  public static final String OPERATIONS_FILE = "OPERATIONS_FILE";
  public static final String START_ROW = "START_ROW";
  public static final String LANDINGS_SHEET = "LANDINGS_SHEET";
  public static final String DEPARTURES_SHEET = "DEPARTURES_SHEET";
  public static final String DATE_FORMAT = "DATE_FORMAT";
  public static final String TIME_FORMAT = "TIME_FORMAT";

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

  public File getFile(String key) {
    return new File(this.getClass().getResource(config.getString(key)).getPath());
  }
}
