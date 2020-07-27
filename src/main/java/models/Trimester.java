package models;

public enum Trimester {
  PRIMERO("Primer trimestre"),
  SEGUNDO("Segundo trimestre"),
  TERCERO("Tercer trimestre"),
  CUARTO("Cuarto trimestre");

  private final String trimester;

  Trimester(final String trimester) {
    this.trimester = trimester;
  }

  @Override
  public String toString() {
    return trimester;
  }
}
