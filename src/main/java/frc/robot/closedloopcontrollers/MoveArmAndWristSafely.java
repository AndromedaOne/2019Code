package frc.robot.closedloopcontrollers;

import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ArmPosition;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.ExtendableArmPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.WristPIDController;
import frc.robot.exceptions.ArmOutOfBoundsException;

public class MoveArmAndWristSafely {

  public enum DontUsePIDHold {
    WRIST, EXTENSION, SHOULDER, HOLDALL
  }

  private static ReentrantLock mutex = new ReentrantLock();
  private static Notifier m_controlLoop = new Notifier(MoveArmAndWristSafely::calculate);
  static {

    m_controlLoop.startPeriodic(0.05);
  }
  public static final boolean ENABLE_SMARTDASHBOARD_DEBUG = true;

  public static final double maxWristRotDegrees = 1000;
  public static final double maxExtensionInches = 28.5;
  public static final double maxShoulderRotDegrees = 180;

  private static boolean shoulderPIDSetpointSet = false;
  private static boolean wristPIDSetpointSet = false;
  private static boolean extensionPIDSetpointSet = false;

  private static final double deltaTime = 0.02;
  private static final double BUTTCAPLENGTH = 2;
  private static final double SHOULDEROFFSETFROMCENTER = 0.75;
  private static final double SHOULDERHEIGHT = 40;
  private static final double ELECTRONICSDANGERZONEHEIGHT = 14;
  private static final double TOPSIDEBOXHEIGHT = 10;
  private static final double TOPSIDEBOXWIDTH = 10;
  private static final double RULEEXTENSIONBOX = 30;
  private static final double ROBOTLENGTH = 29.5;
  private static final double THIRTY_INCH_RULE = ROBOTLENGTH / 2 + RULEEXTENSIONBOX;
  private static final double MAXARMLENGTH = 35.75;
  private static final double CLAWLENGTH = 20.5;
  private static final double HATCHWIDTH = 24;
  private static final double HATCHPANELTIPANGLEOFFSET = Math.atan((HATCHWIDTH / 2) / CLAWLENGTH);
  private static final double HATCHPANELTIPDISTTOWRIST = Math
      .sqrt(Math.pow(CLAWLENGTH, 2) + Math.pow(HATCHWIDTH / 2, 2));

  private static double teleopShoulderPower = 0;
  private static double teleopWristPower = 0;
  private static double teleopExtensionPower = 0;

  public static class Point {
    double x;
    double y;

    private String name;

    public Point(String smartDashboardName) {
      this.x = 0;
      this.y = 0;
      this.name = smartDashboardName;
    }

    public void putOnSmartDashboard() {
      if (ENABLE_SMARTDASHBOARD_DEBUG) {
        SmartDashboard.putNumber(name + ".x", x);
        SmartDashboard.putNumber(name + ".y", y);
      }
    }
  }

  /**
   * @param teleopShoulderPower the teleopShoulderPower to set
   */
  public static void setTeleopShoulderPower(double teleopShoulderPowerParam) {
    mutex.lock();
    teleopShoulderPower = teleopShoulderPowerParam;
    mutex.unlock();
  }

  /**
   * @param teleopExtensionPower the teleopExtensionPower to set
   */
  public static void setTeleopExtensionPower(double teleopExtensionPowerParam) {
    mutex.lock();
    teleopExtensionPower = teleopExtensionPowerParam;
    mutex.unlock();
  }

  /**
   * @param teleopWristPower the teleopWristPower to set
   */
  public static void setTeleopWristPower(double teleopWristPowerParam) {
    mutex.lock();
    teleopWristPower = teleopWristPowerParam;
    mutex.unlock();
  }

  private static double pidShoulderPower = 0;
  private static double pidWristPower = 0;
  private static double pidExtensionPower = 0;

  /**
   * @param pidExtensionPower the pidExtensionPower to set
   */
  public static void setPidExtensionPower(double pidExtensionPowerParam) {
    mutex.lock();
    pidExtensionPower = pidExtensionPowerParam;
    mutex.unlock();
  }

  /**
   * @param pidShoulderPower the pidShoulderPower to set
   */
  public static void setPidShoulderPower(double pidShoulderPowerParam) {
    mutex.lock();
    pidShoulderPower = pidShoulderPowerParam;
    mutex.unlock();
  }

  /**
   * @param pidWristPower the pidWristPower to set
   */
  public static void setPidWristPower(double pidWristPowerParam) {
    mutex.lock();
    pidWristPower = pidWristPowerParam;
    mutex.unlock();
  }

  public static void calculate() {
    /*
     * Robot robotInstance = Robot.getInstance(); if (robotInstance == null) {
     * return; } if (robotInstance.isDisabled()) { shoulderPIDSetpointSet = false;
     * wristPIDSetpointSet = false; extensionPIDSetpointSet = false;
     * Robot.extendableArmAndWrist.moveArmWrist(0, 0, 0); return; }
     */

    ArmPosition currentArmPosition = Robot.getCurrentArmPosition();

    double localTeleopShoulderPower;
    double localTeleopWristPower;
    double localTeleopExtensionPower;

    double localPIDShoulderPower;
    double localPIDWristPower;
    double localPIDExtensionPower;

    mutex.lock();
    localTeleopShoulderPower = teleopShoulderPower;
    localTeleopWristPower = teleopWristPower;
    localTeleopExtensionPower = teleopExtensionPower;

    localPIDShoulderPower = pidShoulderPower;
    localPIDWristPower = pidWristPower;
    localPIDExtensionPower = pidExtensionPower;
    mutex.unlock();

    double shoulderPower = 0;

    if (Math.abs(localTeleopShoulderPower) >= 0.2) {
      shoulderPower = localTeleopShoulderPower;
      shoulderPIDSetpointSet = false;
    } else {
      if (!shoulderPIDSetpointSet) {
        ShoulderPIDController.getInstance().setSetpoint(currentArmPosition.getShoulderAngle());
        ShoulderPIDController.getInstance().enable();
        shoulderPIDSetpointSet = true;
        shoulderPower = 0;
      } else {
        isMovementSafe(0, 0, shoulderPower);
        shoulderPower = localPIDShoulderPower;
      }

    }

    double wristPower = 0;
    if (Math.abs(localTeleopWristPower) >= 0.2) {
      wristPower = localTeleopWristPower;
      wristPIDSetpointSet = false;
    } else {
      if (!wristPIDSetpointSet) {
        WristPIDController.getInstance().setSetpoint(currentArmPosition.getWristAngle());
        WristPIDController.getInstance().enable();
        wristPIDSetpointSet = true;
        wristPower = 0;
      } else {
        isMovementSafe(0, localPIDWristPower, 0);
        wristPower = localPIDWristPower;
      }
    }

    double extensionPower = 0;
    if (Math.abs(localTeleopExtensionPower) >= 0.2) {
      extensionPower = localTeleopExtensionPower;
      extensionPIDSetpointSet = false;
    } else {
      if (!extensionPIDSetpointSet) {
        ExtendableArmPIDController.getInstance().setSetpoint(currentArmPosition.getArmRetraction());
        ExtendableArmPIDController.getInstance().enable();
        extensionPIDSetpointSet = true;
        extensionPower = 0;
      } else {
        isMovementSafe(localPIDExtensionPower, 0, 0);
        extensionPower = localPIDExtensionPower;
      }
    }
    SafeArmMovements safeArmMovements = new SafeArmMovements();

    if (!OI.overRideSafetiesButton.isPressed(Robot.operatorController)) {
      safeArmMovements = isMovementSafe(extensionPower, wristPower, shoulderPower);
    }

    if (extensionPower > 0 && !safeArmMovements.armRetraction) {
      extensionPower = 0;
    } else if (extensionPower < 0 && !safeArmMovements.armExtension) {
      extensionPower = 0;
    }

    if (wristPower > 0 && !safeArmMovements.wristRotateClockwise) {
      wristPower = 0;
    } else if (wristPower < 0 && !safeArmMovements.wristRotateCounterClockwise) {
      wristPower = 0;
    }

    if (shoulderPower > 0 && !safeArmMovements.shoulderRotateClockwise) {
      shoulderPower = 0;
    } else if (shoulderPower < 0 && !safeArmMovements.shoulderRotateCounterClockwise) {
      shoulderPower = 0;
    }

    if (Robot.wristLimitSwitchUp.isAtLimit()) {
      if (currentArmPosition.getWristAngle() > 0) {
        // wristRotDeg = maxWristRotDegrees;

        // if (wristRotVelocity > 0) {
        // wristRotVelocity = 0;
        // throw new ArmOutOfBoundsException(extensionIn, wristRotDeg, shoulderRotDeg);
        // }
      } else {
        // wristRotDeg = -maxWristRotDegrees;

        // if (wristRotVelocity < 0) {
        // wristRotVelocity = 0;
        // throw new ArmOutOfBoundsException(extensionIn, wristRotDeg, shoulderRotDeg);
        // }
      }
    }

    if (Robot.shoulderLimitSwitch.isAtLimit()) {
      if (currentArmPosition.getShoulderAngle() > 0) {

        if (shoulderPower > 0) {
          shoulderPower = 0;
        }
      } else {
        if (shoulderPower < 0) {
          shoulderPower = 0;
        }
      }
    }

    boolean fullyExtended = Robot.fullyExtendedArmLimitSwitch.isAtLimit();
    if (fullyExtended) {
      if (extensionPower < 0) {
        extensionPower = 0;
      }
    } else if (Robot.fullyRetractedArmLimitSwitch.isAtLimit()) {

      if (extensionPower > 0) {
        extensionPower = 0;
      }
    }

    Robot.extendableArmAndWrist.moveArmWrist(extensionPower, wristPower, shoulderPower);

    if (ENABLE_SMARTDASHBOARD_DEBUG) {
      SmartDashboard.putNumber("ShoulderAngle", currentArmPosition.getShoulderAngle());
      SmartDashboard.putNumber("WristAngle", currentArmPosition.getWristAngle());
      SmartDashboard.putNumber("ExtensionIn", currentArmPosition.getArmRetraction());
    }
  }

  /**
   * @param extensionVelocity
   * @param wristRotVelocity
   * @param shoulderRotVelocity
   * @throws ArmOutOfBoundsException
   */

  private static SafeArmMovements isMovementSafe(double extensionVelocity, double wristRotVelocity,
      double shoulderRotVelocity) {
    ArmPosition armPosition = Robot.getCurrentArmPosition();

    double extensionVelocityConversion = Robot.getExtensionInVelocity();
    double wristRotVelocityConversion = Robot.getWristRotDegreesVelocity();
    double shoulderRotVelocityConversion = Robot.getShoulderRotDegVelocity();

    // multiplying by 1.0 to try to look into the future and see where the
    // deltaExtension can be if speed is increasing
    double deltaExtension = extensionVelocityConversion * deltaTime * 1.0;
    double deltaWristRot = wristRotVelocityConversion * deltaTime * 1.0;
    double deltaShoulderRot = shoulderRotVelocityConversion * deltaTime * 1.0;

    SafeArmMovements safeArmMovements = isNextMovementSafe(armPosition.getArmRetraction() + deltaExtension,
        armPosition.getWristAngle() + deltaWristRot, armPosition.getShoulderAngle() + deltaShoulderRot,
        extensionVelocity, wristRotVelocity, shoulderRotVelocity);

    return safeArmMovements;

  }

  public static SafeArmMovements isNextMovementSafe(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double extensionPower, double wristPower, double shoulderPower) {
    SafeArmMovements safeArmMovements = new SafeArmMovements();

    checkSafetyConstraints(extensionIn, wristRotDeg, shoulderRotDeg, safeArmMovements);
    // checkZoneConstraintsOld(extensionIn, wristRotDeg, shoulderRotDeg, wristPower,
    // shoulderPower, safeArmMovements);
    checkZoneConstraintsNew(extensionIn, wristRotDeg, shoulderRotDeg, wristPower, shoulderPower, safeArmMovements);

    return safeArmMovements;
  }

  private static void checkZoneConstraintsOld(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double wristPower, double shoulderPower, SafeArmMovements safeArmMovements) {
    if (shoulderRotDeg > 180) {
      safeArmMovements.shoulderRotateClockwise = false;
    }
    if (shoulderRotDeg < -180) {
      safeArmMovements.shoulderRotateCounterClockwise = false;
    }
    if (extensionIn < 0) {
      safeArmMovements.armExtension = false;
    }
    if (extensionIn > maxExtensionInches) {
      safeArmMovements.armRetraction = false;
    }
    if (wristRotDeg > maxWristRotDegrees) {
      safeArmMovements.wristRotateClockwise = false;

    }
    if (wristRotDeg < -maxWristRotDegrees) {
      safeArmMovements.wristRotateCounterClockwise = false;
    }
    if (shoulderRotDeg < -165 && extensionIn > maxExtensionInches - 10) {
      // System.out.println("safety 1");
      // This is when the arm is retracted enough that when it is swung through
      // the robot the "butt" of the arm will hit the elctronics
      safeArmMovements.shoulderRotateCounterClockwise = false;
      safeArmMovements.armRetraction = false;

    }
    if ((shoulderRotDeg < -140) && (shoulderRotDeg >= -165) && (extensionIn > maxExtensionInches - 7.5)) {
      // System.out.println("safety 2");
      // This safety prevents the arm from hitting the metal bar that the intake
      // rotates on
      boolean shoulderRotateDegreeBelowMiddleOfDeadzone = shoulderRotDeg < ((-140 + -165) / 2);
      if (shoulderPower > 0 && shoulderRotateDegreeBelowMiddleOfDeadzone) {
        safeArmMovements.shoulderRotateClockwise = false;
      } else if (shoulderPower < 0 && shoulderRotateDegreeBelowMiddleOfDeadzone) {
        safeArmMovements.shoulderRotateClockwise = false;
      }
      safeArmMovements.shoulderRotateCounterClockwise = false;
      safeArmMovements.armRetraction = false;
    }
    if (shoulderRotDeg > 150 && extensionIn > maxExtensionInches - 10) {
      // System.out.println("safety 3");
      // This is when the arm is retracted enough that when it is swung through
      // the robot the "butt" of the arm will hit the elctronics
      safeArmMovements.shoulderRotateClockwise = false;
      safeArmMovements.armRetraction = false;
    }
    if (extensionIn < 13 && shoulderRotDeg > 60 && shoulderRotDeg < 145) {
      // System.out.println("safety 4");
      // This safety prevents the arm from extrending over 30 inches out.
      boolean shoulderRotateDegreeBelowMiddleOfDeadzone = shoulderRotDeg < ((53 + 127) / 2);
      if (shoulderPower > 0 && shoulderRotateDegreeBelowMiddleOfDeadzone) {
        safeArmMovements.shoulderRotateClockwise = false;
      } else if (shoulderPower > 0 && !shoulderRotateDegreeBelowMiddleOfDeadzone) {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      } else if (shoulderPower < 0 && shoulderRotateDegreeBelowMiddleOfDeadzone) {
        safeArmMovements.shoulderRotateClockwise = false;
      } else {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
      safeArmMovements.armExtension = false;
    }
    if (extensionIn < 13 && shoulderRotDeg < -60 && shoulderRotDeg > -145) {
      // System.out.println("safety 5");
      // This safety prevents the arm from extrending over 30 inches out.
      boolean shoulderRotateDegreeBelowMiddleOfDeadzone = shoulderRotDeg < ((-53 + -127) / 2);
      if (shoulderPower > 0 && shoulderRotateDegreeBelowMiddleOfDeadzone) {
        safeArmMovements.shoulderRotateClockwise = false;
      } else if (shoulderPower > 0 && !shoulderRotateDegreeBelowMiddleOfDeadzone) {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      } else if (shoulderPower < 0 && shoulderRotateDegreeBelowMiddleOfDeadzone) {
        safeArmMovements.shoulderRotateClockwise = false;
      } else {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
      safeArmMovements.armExtension = false;
    }
    if (shoulderRotDeg < 50 && shoulderRotDeg > -50 && extensionIn < maxExtensionInches - 1) {

      // This safety is preventing the claw from hitting the elctronics when it
      // is being swung through the robot
      if (extensionIn > 15 && wristRotDeg < -95) {

      } else if (extensionIn > 15 && wristRotDeg > 95) {

      } else {
        // System.out.println("safety 6");
        boolean shoulderRotateDegreeBelowMiddleOfDeadzone = shoulderRotDeg < ((-50 + 50) / 2);
        if (shoulderPower > 0 && shoulderRotateDegreeBelowMiddleOfDeadzone) {
          safeArmMovements.shoulderRotateClockwise = false;
        } else if (shoulderPower > 0 && !shoulderRotateDegreeBelowMiddleOfDeadzone) {
          safeArmMovements.shoulderRotateCounterClockwise = false;
        } else if (shoulderPower < 0 && shoulderRotateDegreeBelowMiddleOfDeadzone) {
          safeArmMovements.shoulderRotateClockwise = false;
        } else {
          safeArmMovements.shoulderRotateCounterClockwise = false;
        }
        if (wristPower > 0 && wristRotDeg > 0) {
          safeArmMovements.wristRotateCounterClockwise = false;
        } else if (wristPower > 0 && !(wristRotDeg > 0)) {
          safeArmMovements.wristRotateClockwise = false;
        } else if (wristPower < 0 && (wristRotDeg > 0)) {
          safeArmMovements.wristRotateCounterClockwise = false;
        } else {
          safeArmMovements.wristRotateClockwise = false;
        }
        safeArmMovements.armExtension = false;
      }
    }
  }

  /**
   * Makes sure no-go zones are not being violated
   */
  private static void checkZoneConstraintsNew(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double wristPower, double shoulderPower, SafeArmMovements safeArmMovements) {
    checkButtEndConstraints(extensionIn, wristRotDeg, shoulderRotDeg, wristPower, shoulderPower, safeArmMovements);
    checkClawEndConstraints(extensionIn, wristRotDeg, shoulderRotDeg, wristPower, shoulderPower, true,
        safeArmMovements);

  }

  /**
   * Implements a way to find the butt end of the arm on a coordinate system
   */
  private static void checkButtEndConstraints(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double wristPower, double shoulderPower, SafeArmMovements safeArmMovements) {
    double shoulderRotationRadians = Math.toRadians(shoulderRotDeg);
    Point buttPosition = new Point("buttPosition");
    buttPosition.x = -(extensionIn + BUTTCAPLENGTH) * Math.sin(shoulderRotationRadians) - SHOULDEROFFSETFROMCENTER;
    buttPosition.y = SHOULDERHEIGHT + ((extensionIn + BUTTCAPLENGTH) * Math.cos(shoulderRotationRadians));
    buttPosition.putOnSmartDashboard();

    if (buttPosition.y > ELECTRONICSDANGERZONEHEIGHT) {
      // We are safe
      return;
    }
    if (buttPosition.x >= 0 && buttPosition.x < ROBOTLENGTH / 2) {
      // This is when the arm is retracted enough that when it is swung through
      // the robot the "butt" of the arm will hit the electronics
      safeArmMovements.shoulderRotateCounterClockwise = false;
      safeArmMovements.armRetraction = false;
    }
    if (buttPosition.x > -ROBOTLENGTH / 2 && buttPosition.x < 0) {
      // This is when the arm is retracted enough that when it is swung through
      // the robot the "butt" of the arm will hit the electronics
      safeArmMovements.shoulderRotateClockwise = false;
      safeArmMovements.armRetraction = false;
    }
  }

  /**
   * Implements a way to find the end of the arm on a coordinate system
   */
  private static void checkClawEndConstraints(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double wristPower, double shoulderPower, boolean isHoldingHatchPanel, SafeArmMovements safeArmMovements) {
    double shoulderRotRadians = Math.toRadians(shoulderRotDeg);
    double extensionOut = MAXARMLENGTH - extensionIn;
    Point wristJointPos = new Point("wristJointPos");
    wristJointPos.x = extensionOut * Math.sin(shoulderRotRadians) - SHOULDEROFFSETFROMCENTER;
    wristJointPos.y = SHOULDERHEIGHT - (extensionOut * Math.cos(shoulderRotRadians));
    wristJointPos.putOnSmartDashboard();

    if (wristJointPos.x > THIRTY_INCH_RULE) {
      safeArmMovements.armExtension = false;
      if (shoulderRotDeg < 90) {
        safeArmMovements.shoulderRotateClockwise = false;
      } else {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
    }
    if (wristJointPos.x < -THIRTY_INCH_RULE) {
      safeArmMovements.armExtension = false;
      if (shoulderRotDeg > -90) {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      } else {
        safeArmMovements.shoulderRotateClockwise = false;
      }

    }
    if (wristJointPos.y <= ELECTRONICSDANGERZONEHEIGHT) {

      if (wristJointPos.x >= 0 && wristJointPos.x < (ROBOTLENGTH / 2)) {
        safeArmMovements.armExtension = false;
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
      if (wristJointPos.x < 0 && wristJointPos.x > (-ROBOTLENGTH / 2)) {
        safeArmMovements.armExtension = false;
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
    }
    if (wristJointPos.y <= SHOULDERHEIGHT && wristJointPos.y >= (SHOULDERHEIGHT - TOPSIDEBOXHEIGHT)) {
      if (wristJointPos.x >= 0 && wristJointPos.x <= TOPSIDEBOXWIDTH) {
        safeArmMovements.armExtension = false;
      }
    }

    if (wristJointPos.x <= TOPSIDEBOXWIDTH / 2 && wristJointPos.x >= 0) {
      if (wristJointPos.y >= SHOULDERHEIGHT - TOPSIDEBOXHEIGHT && wristJointPos.y < SHOULDERHEIGHT) {
        safeArmMovements.armRetraction = false;
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
    }
    if (wristJointPos.x < 0 && wristJointPos.x >= -TOPSIDEBOXWIDTH / 2) {
      if (wristJointPos.y >= SHOULDERHEIGHT - TOPSIDEBOXHEIGHT && wristJointPos.y < SHOULDERHEIGHT) {
        safeArmMovements.armRetraction = false;
        safeArmMovements.shoulderRotateClockwise = false;
      }
    }

    double wristRotRelativeToFloor = wristRotDeg - (180 - (shoulderRotDeg + 90));

    double wristRotRelativeToFloorRadians = Math.toRadians(wristRotRelativeToFloor);

    double ClawXPosRelativeToArmJoint = (CLAWLENGTH) * Math.cos(wristRotRelativeToFloorRadians);

    double ClawYPosRelativeToArmJoint = (CLAWLENGTH) * Math.sin(wristRotRelativeToFloorRadians);
    Point clawTipPos = new Point("clawTipPos");
    clawTipPos.x = wristJointPos.x + ClawXPosRelativeToArmJoint;
    clawTipPos.y = wristJointPos.y + ClawYPosRelativeToArmJoint;
    clawTipPos.putOnSmartDashboard();

    if (ENABLE_SMARTDASHBOARD_DEBUG) {
      SmartDashboard.putNumber("ClawXPosRelativeToArmJoint", ClawXPosRelativeToArmJoint);
      SmartDashboard.putNumber("wristRotRelativeToFloor", wristRotRelativeToFloor);
      SmartDashboard.putNumber("ClawYPosRelativeToArmJoint", ClawYPosRelativeToArmJoint);
    }

    // If the arm is at the 30 inch box it prevents the arm from extending and
    // checks the other if statements inside
    if (clawTipPos.x >= THIRTY_INCH_RULE) {
      safeArmMovements.armExtension = false;
      // This basically can tell if the claw tip is at a relative angle less than 90
      // and prevents the arm from rotating clockwise
      if (clawTipPos.y < SHOULDERHEIGHT) {
        safeArmMovements.shoulderRotateClockwise = false;
      } else {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
      // If the wrist is at an angle less than 90 then the wrist can't rotate
      // clockwise
      if (wristRotRelativeToFloor < 90) {
        safeArmMovements.wristRotateClockwise = false;
      } else {
        safeArmMovements.wristRotateCounterClockwise = false;
      }
    }

    // If the arm is at the 30 inch box it prevents the arm from extending and
    // checks the other if statements inside
    if (clawTipPos.x <= -THIRTY_INCH_RULE) {
      safeArmMovements.armExtension = false;
      // This basically can tell if the claw tip is at a relative angle less than 90
      // and prevents the arm from rotating clockwise
      if (clawTipPos.y < SHOULDERHEIGHT) {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      } else {
        safeArmMovements.shoulderRotateClockwise = false;
      }
      // If the wrist is at an angle less than 90 then the wrist can't rotate
      // clockwise
      if (wristRotRelativeToFloor > -90) {
        safeArmMovements.wristRotateCounterClockwise = false;
      } else {
        safeArmMovements.wristRotateClockwise = false;
      }
    }

    if (clawTipPos.y < ELECTRONICSDANGERZONEHEIGHT) {
      if (clawTipPos.x < ROBOTLENGTH / 2 && clawTipPos.x >= 0) {
        safeArmMovements.armExtension = false;
        safeArmMovements.shoulderRotateCounterClockwise = false;
        if (wristRotRelativeToFloor >= -90) {
          safeArmMovements.wristRotateCounterClockwise = false;
        } else {
          safeArmMovements.wristRotateClockwise = false;
        }
      }
      if (clawTipPos.x > -ROBOTLENGTH / 2 && clawTipPos.x < 0) {
        safeArmMovements.armExtension = false;
        safeArmMovements.shoulderRotateClockwise = false;
        if (wristRotRelativeToFloor <= 90) {
          safeArmMovements.wristRotateClockwise = false;
        } else {
          safeArmMovements.wristRotateCounterClockwise = false;
        }
      }
    }

    if (clawTipPos.x <= TOPSIDEBOXWIDTH / 2 && clawTipPos.x >= -TOPSIDEBOXWIDTH / 2) {
      if (clawTipPos.y < SHOULDERHEIGHT && clawTipPos.y >= SHOULDERHEIGHT - TOPSIDEBOXHEIGHT) {
        safeArmMovements.armRetraction = false;
        if (wristRotDeg <= 0) {
          safeArmMovements.shoulderRotateCounterClockwise = false;
          safeArmMovements.wristRotateCounterClockwise = false;
        } else {
          safeArmMovements.shoulderRotateClockwise = false;
          safeArmMovements.wristRotateClockwise = false;
        }
      }
    }

    if (isHoldingHatchPanel) {
      double hatchTopAngleRelativeToFloor = wristRotRelativeToFloor + HATCHPANELTIPANGLEOFFSET;
      double hatchBottomAngleRelativeToFloor = wristRotRelativeToFloor - HATCHPANELTIPANGLEOFFSET;

      double hatchTopRelativeXLength = HATCHPANELTIPDISTTOWRIST * Math.cos(hatchTopAngleRelativeToFloor);
      double hatchTopRelativeYLength = HATCHPANELTIPDISTTOWRIST * Math.sin(hatchTopAngleRelativeToFloor);

      double hatchBottomRelativeXLength = HATCHPANELTIPDISTTOWRIST * Math.cos(hatchBottomAngleRelativeToFloor);
      double hatchBottomRelativeYLength = HATCHPANELTIPDISTTOWRIST * Math.sin(hatchBottomAngleRelativeToFloor);

      Point hatchTopPos = new Point("hatchTopPos");
      hatchTopPos.x = clawTipPos.x + hatchTopRelativeXLength;
      hatchTopPos.y = clawTipPos.y + hatchTopRelativeYLength;

      Point hatchBottomPos = new Point("hatchBottomPos");
      hatchBottomPos.x = clawTipPos.x + hatchBottomRelativeXLength;
      hatchBottomPos.y = clawTipPos.y + hatchBottomRelativeYLength;

    }
  }

  /**
   * Check to make sure we dont break anything on the robot
   */
  private static void checkSafetyConstraints(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      SafeArmMovements safeArmMovements) {
    if (shoulderRotDeg > 180) {
      safeArmMovements.shoulderRotateClockwise = false;
    }
    if (shoulderRotDeg < -180) {
      safeArmMovements.shoulderRotateCounterClockwise = false;
    }
    if (extensionIn < 0) {
      safeArmMovements.armExtension = false;
    }
    if (extensionIn > maxExtensionInches) {
      safeArmMovements.armRetraction = false;
    }
    if (wristRotDeg > maxWristRotDegrees) {
      safeArmMovements.wristRotateClockwise = false;

    }
    if (wristRotDeg < -maxWristRotDegrees) {
      safeArmMovements.wristRotateCounterClockwise = false;
    }

    if (shoulderRotDeg > -30 && shoulderRotDeg < -10) {
      if (wristRotDeg < -80 && extensionIn > maxExtensionInches - 10) {
        // TODO: Get the right number instead of 10 and set the safeArmMovements
        // we are hitting the shoulder cim motor

      }

    }
  }

  public static void stop() {
    mutex.lock();
    teleopShoulderPower = 0;
    teleopWristPower = 0;
    teleopExtensionPower = 0;

    pidShoulderPower = 0;
    pidWristPower = 0;
    pidExtensionPower = 0;

    extensionPIDSetpointSet = false;
    wristPIDSetpointSet = false;
    shoulderPIDSetpointSet = false;
    mutex.unlock();
  }

  private static class SafeArmMovements {
    public boolean armExtension = true;
    public boolean armRetraction = true;
    public boolean wristRotateClockwise = true;
    public boolean wristRotateCounterClockwise = true;
    public boolean shoulderRotateClockwise = true;
    public boolean shoulderRotateCounterClockwise = true;

  }
}