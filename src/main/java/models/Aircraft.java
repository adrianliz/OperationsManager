package models;

public class Aircraft {
  private String type;
  private String model;
  private String numberPlate;
  private double MTOW;
  private String CIA;

  public Aircraft(String type, String model, String numberPlate, double MTOW, String CIA) {
    this.type = type;
    this.model = model;
    this.numberPlate = numberPlate;
    this.MTOW = MTOW;
    this.CIA = CIA;
  }

  @Override
  public String toString() {
    return "Aircraft{" +
      "type='" + type + '\'' +
      ", model='" + model + '\'' +
      ", numberPlate='" + numberPlate + '\'' +
      ", MTOW=" + MTOW +
      ", CIA='" + CIA + '\'' +
      '}';
  }
}
