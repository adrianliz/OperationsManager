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

  public static Trimester getTrimesterType(String s) {
    for (Trimester trimester: Trimester.values()) {
      if (trimester.toString().equals(s)) {
        return trimester;
      }
    }

    return null;
  }

  @Override
  public String toString() {
    return trimester;
  }
}
