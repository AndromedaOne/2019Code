package frc.robot.closedloopcontrollers;

import frc.robot.Robot;

public class MoveArmAndWristSafely {

  private static final int maxWristRot = 1000;
  private static final int maxExtension = 1000;

  private static final double SHOULDERTICKSTODEGRESS = 1.0;
  private static final double EXTENSIONTICKSTOINCHES = 1.0;
  private static final double WRISTTICKSTODEGREES = 1.0;

  /**
   * @param extensionValue
   * @param wristRotValue
   * @param shoulderRotValue
   */
  public static void move(double extensionValue, double wristRotValue, double shoulderRotValue) {
    double extensionIn = getExtensionIn(Robot.armExtensionEncoder1.getDistanceTicks(),
        Robot.armExtensionEncoder2.getDistanceTicks());
    double wristRotDeg = getWristRotDegrees(Robot.armRotateEncoder1.getDistanceTicks(),
        Robot.armExtensionEncoder2.getDistanceTicks());
    double shoulderRotDeg = getShoulderRotDeg(Robot.armRotateEncoder1.getDistanceTicks());

    if (isMovementSafe(extensionIn, wristRotDeg, shoulderRotDeg, extensionValue, wristRotValue, shoulderRotValue)) {
      Robot.extendableArmAndWrist.moveArmWrist(extensionValue, wristRotValue, shoulderRotValue);
    }
  }

  public static boolean isMovementSafe(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double extensionValue, double wristRotValue, double shoulderRotValue) {

    boolean wristRotatingClockwise = isWristRotatingClockwise(wristRotValue);
    boolean shoulderRotatingClockwise = isShoulderRotatingClockwise(shoulderRotValue);
    boolean armRetracting = isArmRetracting(extensionValue);

    if ((shoulderRotDeg > 180 && shoulderRotatingClockwise) || (shoulderRotDeg < -180 && !shoulderRotatingClockwise)
        || (extensionIn < 0 && !armRetracting) || (extensionIn > maxExtension && armRetracting)
        || (wristRotDeg > maxWristRot && wristRotatingClockwise)
        || (wristRotDeg < -maxWristRot && !wristRotatingClockwise)) {
      return false;
    } else if ((shoulderRotDeg > 150 || shoulderRotDeg < -150) && (extensionIn > maxExtension - 1 && !armRetracting)) {
      return false;
    } else if ((extensionIn < 10 && !armRetracting) && shoulderRotDeg > 53 && shoulderRotDeg < 127) {
      return false;
    } else if ((extensionIn < 10 && !armRetracting) && shoulderRotDeg < -53 && shoulderRotDeg > -127) {
      return false;
    } else if (shoulderRotDeg < 50 && shoulderRotDeg > -50 && extensionIn < maxExtension - 1) {
      if ((extensionIn > 13 || armRetracting) && (wristRotDeg < -90 || !wristRotatingClockwise) && shoulderRotDeg > 0
          && shoulderRotDeg < 15) {
        return true;
      } else if ((extensionIn > 13 || armRetracting) && (wristRotDeg > 90 || wristRotatingClockwise)
          && shoulderRotDeg < 0 && shoulderRotDeg > -15) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

  private static boolean isWristRotatingClockwise(double value) {
    return value > 0;
  }

  private static boolean isShoulderRotatingClockwise(double value) {
    return value > 0;
  }

  private static boolean isArmRetracting(double value) {
    return value > 0;
  }

  public static double getExtensionIn(double topEncoderTicks, double bottomEncoderTicks) {
    return (topEncoderTicks + bottomEncoderTicks) / 2 * EXTENSIONTICKSTOINCHES;
  }

  public static double getWristRotDegrees(double topEncoderTicks, double bottomEncoderTicks) {
    return (topEncoderTicks - bottomEncoderTicks) * WRISTTICKSTODEGREES;
  }

  public static double getShoulderRotDeg(double ticks) {
    return ticks * SHOULDERTICKSTODEGRESS;
  }

}