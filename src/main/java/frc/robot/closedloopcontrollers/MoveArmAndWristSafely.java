package frc.robot.closedloopcontrollers;

import frc.robot.Robot;
import frc.robot.exceptions.ArmOutOfBoundsException;

public class MoveArmAndWristSafely {

  private static final int maxWristRot = 1000;
  private static final int maxExtension = 1000;

  private static final double SHOULDERTICKSTODEGRESS = 1.0;
  private static final double EXTENSIONTICKSTOINCHES = 1.0;
  private static final double WRISTTICKSTODEGREES = 1.0;

  private static final double extensionVelocityConversion = 1.0;
  private static final double wristRotVelocityConversion = 1.0;
  private static final double shoulderRotVelocityConversion = 1.0;
  private static final double deltaTime = 1.0;

  /**
   * @param extensionVelocity
   * @param wristRotVelocity
   * @param shoulderRotVelocity
   * @throws ArmOutOfBoundsException
   */
  public static void move(double extensionVelocity, double wristRotVelocity, double shoulderRotVelocity) throws ArmOutOfBoundsException {
    double extensionIn = getExtensionIn(Robot.armExtensionEncoder1.getDistanceTicks(),
        Robot.armExtensionEncoder2.getDistanceTicks());
    double wristRotDeg = getWristRotDegrees(Robot.armRotateEncoder1.getDistanceTicks(),
        Robot.armExtensionEncoder2.getDistanceTicks());
    double shoulderRotDeg = getShoulderRotDeg(Robot.armRotateEncoder1.getDistanceTicks());

    double deltaExtension = extensionVelocity * extensionVelocityConversion * deltaTime;
    double deltaWristRot = wristRotVelocity * wristRotVelocityConversion * deltaTime;
    double deltaShoulderRot = shoulderRotVelocity * shoulderRotVelocityConversion * deltaTime;

    if (!isLocSafe(extensionIn + deltaExtension, wristRotDeg + deltaWristRot, shoulderRotDeg + deltaShoulderRot)){
      throw new ArmOutOfBoundsException(extensionIn + deltaExtension, wristRotDeg + deltaWristRot, shoulderRotDeg + deltaShoulderRot);
    }
    
    if(Robot.fullyExtendedArmLimitSwitch.isAtLimit()) {
    
    }

    Robot.extendableArmAndWrist.moveArmWrist(extensionVelocity, wristRotVelocity, shoulderRotVelocity);
  }

  public static boolean isLocSafe(double extensionIn, double wristRotDeg, double shoulderRotDeg) {

    if (shoulderRotDeg > 180 || shoulderRotDeg < -180 || extensionIn < 0 || extensionIn > maxExtension
        || wristRotDeg > maxWristRot || wristRotDeg < -maxWristRot) {
      return false;
    } else if (shoulderRotDeg < -150 && extensionIn > maxExtension - 1) {
      return false;
    } else if (shoulderRotDeg > 150 && extensionIn > maxExtension - 1) {
      return false;
    } else if (extensionIn < 10 && shoulderRotDeg > 53 && shoulderRotDeg < 127) {
      return false;
    } else if (extensionIn < 10 && shoulderRotDeg < -53 && shoulderRotDeg > -127) {
      return false;
    } else if (shoulderRotDeg < 50 && shoulderRotDeg > -50 && extensionIn < maxExtension - 1) {
      if (extensionIn > 13 && wristRotDeg < -90 && shoulderRotDeg > 0 && shoulderRotDeg < 15) {
        return true;
      } else if (extensionIn > 13 && wristRotDeg > 90 && shoulderRotDeg < 0 && shoulderRotDeg > -15) {
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