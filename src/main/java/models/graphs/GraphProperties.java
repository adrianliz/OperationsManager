package models.graphs;

import models.enums.Trimester;

import java.util.List;

public class GraphProperties {
  private List<Integer> years;
  private Integer year;
  private Trimester trimester;

  public GraphProperties(List<Integer> years) {
    this.years = years;
  }

  public GraphProperties(Integer year, Trimester trimester) {
    this.year = year;
    this.trimester = trimester;
  }

  public List<Integer> getYears() {
    return years;
  }

  public Integer getYear() {
    return year;
  }

  public Trimester getTrimester() {
    return trimester;
  }
}
