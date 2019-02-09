package frc.robot.closedloopcontrollers;

public class MoveArmAndWristSafely {

  private final int maxWristRot = 1000;
  private final int maxExtension = 1000;

  /**
   * @param extensionValue
   * @param wristRotValue
   * @param shoulderRotValue
   */
  public static void moveArmWristShoulder(double extensionValue, double wristRotValue, double shoulderRotValue) {

  }

  public boolean isLocSafe(double extensionIn, double wristRotDeg, double shoulderRotDeg) {

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
}