package models.enums;

public enum Trimester {
  FIRST("Primer trimestre"),
  SECOND("Segundo trimestre"),
  THIRD("Tercer trimestre"),
  QUARTER("Cuarto trimestre");

  private final String trimester;

  Trimester(final String trimester) {
    this.trimester = trimester;
  }

  @Override
  public String toString() {
    return trimester;
  }
}
