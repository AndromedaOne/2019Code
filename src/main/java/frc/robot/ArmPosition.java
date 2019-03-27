package frc.robot;

public class ArmPosition {
  private double shoulderAngle = 0;
  private double armRetraction = 0;
  private double wristAngle = 0;

  

  public ArmPosition(double shoulderAngle, double armRetraction, double wristAngle) {
    this.shoulderAngle = shoulderAngle;
    this.armRetraction = armRetraction;
    this.wristAngle = wristAngle;
  }

  public double getShoulderAngle() {
    return shoulderAngle;
  }

  public double getArmRetraction() {
    return armRetraction;
  }

  public double getWristAngle() {
    return wristAngle;
  }

  public ArmPosition getMirror() {
    return new ArmPosition(-shoulderAngle, armRetraction, -wristAngle);
  }

  public boolean isBetween(ArmPosition lowLimit, ArmPosition highLimit) {
    boolean shoulderBetween = (lowLimit.shoulderAngle < shoulderAngle) && (shoulderAngle < highLimit.shoulderAngle);
    boolean wristBetween = (lowLimit.wristAngle < wristAngle) && (wristAngle < highLimit.wristAngle);
    boolean armRetrationBetween = (lowLimit.armRetraction < armRetraction) && (armRetraction < highLimit.armRetraction);

    return shoulderBetween && wristBetween && armRetrationBetween;
  }

  public boolean isFore() {
    return shoulderAngle > 0;
  }

}
