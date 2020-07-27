package controllers;

import models.StatisticType;
import models.charts.*;

public class ChartModelsFactory {
  IStatisticModel newStatisticModel(StatisticType statisticType) {
    switch (statisticType) {
      case SCHENGEN -> {
        return new SchengenStatistic();
      }
      case TIPO_AERONAVE -> {
        return new AircraftTypeStatistic();
      }
      case MEDIA_MTOW -> {
        return new MTOWStatistic();
      }
      default -> {
        return null;
      }
    }
  }
}
