package frc.robot.closedloopcontrollers;

import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.ExtendableArmPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.WristPIDController;
import frc.robot.exceptions.ArmOutOfBoundsException;
import frc.robot.utilities.ButtonsEnumerated;

public class MoveArmAndWristSafely {

  public enum DontUsePIDHold {
    WRIST, EXTENSION, SHOULDER, HOLDALL
  }

  private static ReentrantLock mutex = new ReentrantLock();
  private static Notifier m_controlLoop = new Notifier(MoveArmAndWristSafely::calculate);
  static {

    m_controlLoop.startPeriodic(0.05);
  }
  public static final double maxWristRotDegrees = 1000;
  public static final double maxExtensionInches = 28.5;
  public static final double maxShoulderRotDegrees = 180;

  private static boolean shoulderPIDSetpointSet = false;
  private static boolean wristPIDSetpointSet = false;
  private static boolean extensionPIDSetpointSet = false;

  private static final double SHOULDERTICKSPERDEGREE = 916.2;
  private static final double EXTENSIONTICKSPERINCH = 7204.0;
  private static final double WRISTTICKSPERDEGREE = 444.922;

  public static final double SHOULDERDEGREESPERTICK = 1.0 / SHOULDERTICKSPERDEGREE;
  public static final double EXTENSIONINCHESPERTICK = 1.0 / EXTENSIONTICKSPERINCH;
  public static final double WRISTDEGREESPERTICK = 1.0 / WRISTTICKSPERDEGREE;

  private static final double deltaTime = 0.02;
  private static final double BUTTCAPLENGTH = 2;
  private static final double SHOULDEROFFSETFROMCENTER = 0.75;
  private static final double SHOULDERHEIGHT = 40;
  private static final double ELECTRONICSDANGERZONEHEIGHT = 14;
  private static final double TOPSIDEBOXHEIGHT = 10;
  private static final double TOPSIDEBOXWIDTH = 10;
  private static final double RULEEXTENSIONBOX = 30;
  private static final double ROBOTLENGTH = 29.5;
  private static final double MAXARMLENGTH = 35.75;
  private static final double CLAWLENGTH = 20.5;
  private static final double HATCHWIDTH = 24;
  private static final double HATCHPANELTIPANGLEOFFSET = Math.atan((HATCHWIDTH/2)/CLAWLENGTH);
  private static final double HATCHPANELTIPDISTTOWRIST = Math.sqrt(Math.pow(CLAWLENGTH, 2) + Math.pow(HATCHWIDTH/2, 2));

  private static double teleopShoulderPower = 0;
  private static double teleopWristPower = 0;
  private static double teleopExtensionPower = 0;
  private static boolean presetModeActive = false;

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

  /**
   * Call this function when starting to execute a preset command Relaxes the arm
   * extension limits
   */
  public static void setPresetModeActive() {
    mutex.lock();
    presetModeActive = true;
    mutex.unlock();
  }

  /**
   * Call this function when finished executing a preset command Resets the arm
   * extension limits for Teleop
   */
  public static void clearPresetModeActive() {
    mutex.lock();
    presetModeActive = false;
    mutex.unlock();
  }

  public static void calculate() {

    double topExtensionEncoderTicks = Robot.topArmExtensionEncoder.getDistanceTicks();
    double bottomExtensionEncoderTicks = Robot.bottomArmExtensionEncoder.getDistanceTicks();
    double shoulderTicks = Robot.shoulderEncoder.getDistanceTicks();

    double extensionIn = getExtensionIn(topExtensionEncoderTicks, bottomExtensionEncoderTicks);
    double wristRotDeg = getWristRotDegrees(topExtensionEncoderTicks, bottomExtensionEncoderTicks);
    double shoulderRotDeg = getShoulderRotDeg(shoulderTicks);

    double localTeleopShoulderPower;
    double localTeleopWristPower;
    double localTeleopExtensionPower;

    double localPIDShoulderPower;
    double localPIDWristPower;
    double localPIDExtensionPower;

    boolean teleOpModeActive;

    mutex.lock();
    localTeleopShoulderPower = teleopShoulderPower;
    localTeleopWristPower = teleopWristPower;
    localTeleopExtensionPower = teleopExtensionPower;

    localPIDShoulderPower = pidShoulderPower;
    localPIDWristPower = pidWristPower;
    localPIDExtensionPower = pidExtensionPower;

    teleOpModeActive = !presetModeActive;

    mutex.unlock();

    double shoulderPower = 0;

    if (Math.abs(localTeleopShoulderPower) >= 0.2) {
      shoulderPower = localTeleopShoulderPower;
      shoulderPIDSetpointSet = false;
    } else {
      if (!shoulderPIDSetpointSet) {
        ShoulderPIDController.getInstance().setSetpoint(shoulderRotDeg);
        ShoulderPIDController.getInstance().enable();
        shoulderPIDSetpointSet = true;
        shoulderPower = 0;
      } else {
        isMovementSafe(0, 0, shoulderPower, teleOpModeActive);
        shoulderPower = localPIDShoulderPower;
      }
    }

    double wristPower = 0;
    if (Math.abs(localTeleopWristPower) >= 0.2) {
      wristPower = localTeleopWristPower;
      wristPIDSetpointSet = false;
    } else {
      if (!wristPIDSetpointSet) {
        WristPIDController.getInstance().setSetpoint(wristRotDeg);
        WristPIDController.getInstance().enable();
        wristPIDSetpointSet = true;
        wristPower = 0;
      } else {
        isMovementSafe(0, localPIDWristPower, 0, teleOpModeActive);
        wristPower = localPIDWristPower;
      }
    }

    double extensionPower = 0;
    if (Math.abs(localTeleopExtensionPower) >= 0.2) {
      extensionPower = localTeleopExtensionPower;
      extensionPIDSetpointSet = false;
    } else {
      if (!extensionPIDSetpointSet) {
        ExtendableArmPIDController.getInstance().setSetpoint(extensionIn);
        ExtendableArmPIDController.getInstance().enable();
        extensionPIDSetpointSet = true;
        extensionPower = 0;
      } else {
        isMovementSafe(localPIDExtensionPower, 0, 0, teleOpModeActive);
        extensionPower = localPIDExtensionPower;
      }
    }
    SafeArmMovements safeArmMovements = new SafeArmMovements();
    if (!ButtonsEnumerated.BACKBUTTON.isPressed(Robot.operatorController)) {
      safeArmMovements = isMovementSafe(extensionPower, wristPower, shoulderPower, teleOpModeActive);
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
    Robot.extendableArmAndWrist.moveArmWrist(extensionPower, wristPower, shoulderPower);
  }

  /**
   * @param extensionVelocity
   * @param wristRotVelocity
   * @param shoulderRotVelocity
   * @throws ArmOutOfBoundsException
   */

  private static SafeArmMovements isMovementSafe(double extensionVelocity, double wristRotVelocity,
      double shoulderRotVelocity, boolean teleOpModeActive) {
    double topExtensionEncoderTicks = Robot.topArmExtensionEncoder.getDistanceTicks();
    double bottomExtensionEncoderTicks = Robot.bottomArmExtensionEncoder.getDistanceTicks();
    double shoulderTicks = Robot.shoulderEncoder.getDistanceTicks();

    double extensionIn = getExtensionIn(topExtensionEncoderTicks, bottomExtensionEncoderTicks);
    double wristRotDeg = getWristRotDegrees(topExtensionEncoderTicks, bottomExtensionEncoderTicks);
    double shoulderRotDeg = getShoulderRotDeg(shoulderTicks);

    double topArmEncoderVelocity = Robot.topArmExtensionEncoder.getVelocity();
    double bottomArmEncoderVelocity = Robot.bottomArmExtensionEncoder.getVelocity();
    double shoulderEncoderVelocity = Robot.shoulderEncoder.getVelocity();

    double extensionVelocityConversion = getExtensionInVelocity(topArmEncoderVelocity, bottomArmEncoderVelocity);
    double wristRotVelocityConversion = getWristRotDegreesVelocity(topArmEncoderVelocity, bottomArmEncoderVelocity);
    double shoulderRotVelocityConversion = getShoulderRotDegVelocity(shoulderEncoderVelocity);

    // multiplying by 1.0 to try to look into the future and see where the
    // deltaExtension can be if speed is increasing
    double deltaExtension = extensionVelocityConversion * deltaTime * 1.0;
    double deltaWristRot = wristRotVelocityConversion * deltaTime * 1.0;
    double deltaShoulderRot = shoulderRotVelocityConversion * deltaTime * 1.0;

    SafeArmMovements safeArmMovements = isNextMovementSafe(extensionIn + deltaExtension, wristRotDeg + deltaWristRot,
        shoulderRotDeg + deltaShoulderRot, extensionVelocity, wristRotVelocity, shoulderRotVelocity, teleOpModeActive);

    if (Robot.wristLimitSwitchUp.isAtLimit()) {
      if (wristRotDeg > 0) {
        // wristRotDeg = maxWristRotDegrees;
        double topEncoderPosition = (extensionIn / EXTENSIONINCHESPERTICK)
            + (maxWristRotDegrees / WRISTDEGREESPERTICK) / 2;
        double bottomEncoderPosition = (extensionIn / EXTENSIONINCHESPERTICK)
            - (maxWristRotDegrees / WRISTDEGREESPERTICK) / 2;
        // Robot.topArmExtensionEncoder.resetTo(topEncoderPosition);
        // Robot.bottomArmExtensionEncoder.resetTo(bottomEncoderPosition);
        // if (wristRotVelocity > 0) {
        // wristRotVelocity = 0;
        // throw new ArmOutOfBoundsException(extensionIn, wristRotDeg, shoulderRotDeg);
        // }
      } else {
        // wristRotDeg = -maxWristRotDegrees;
        double topEncoderPosition = (extensionIn / EXTENSIONINCHESPERTICK)
            - (maxWristRotDegrees / WRISTDEGREESPERTICK) / 2;
        double bottomEncoderPosition = (extensionIn / EXTENSIONINCHESPERTICK)
            + (maxWristRotDegrees / WRISTDEGREESPERTICK) / 2;
        // Robot.topArmExtensionEncoder.resetTo(topEncoderPosition);
        // Robot.bottomArmExtensionEncoder.resetTo(bottomEncoderPosition);
        // if (wristRotVelocity < 0) {
        // wristRotVelocity = 0;
        // throw new ArmOutOfBoundsException(extensionIn, wristRotDeg, shoulderRotDeg);
        // }
      }
    }

    if (Robot.shoulderLimitSwitch.isAtLimit()) {
      if (shoulderRotDeg > 0) {
        // shoulderRotDeg = maxShoulderRotDegrees;

        // Robot.armRotateEncoder1.resetTo(maxShoulderRotDegrees/SHOULDERTICKSTODEGRESS);
        // if (shoulderRotVelocity > 0) {
        // shoulderRotVelocity = 0;
        // throw new ArmOutOfBoundsException(extensionIn, wristRotDeg, shoulderRotDeg);
        // }
      } else {
        // shoulderRotDeg = -maxShoulderRotDegrees;

        // Robot.armRotateEncoder1.resetTo(-maxShoulderRotDegrees/SHOULDERTICKSTODEGRESS);
        // if (shoulderRotVelocity < 0) {
        // shoulderRotVelocity = 0;
        // throw new ArmOutOfBoundsException(extensionIn, wristRotDeg, shoulderRotDeg);
        // }
      }
    }

    boolean fullyExtended = Robot.fullyExtendedArmLimitSwitch.isAtLimit();
    if (fullyExtended) {
      // extensionIn = 0;
      double topEncoderPosition = (wristRotDeg / WRISTDEGREESPERTICK) / 2;
      double bottomEncoderPosition = -(wristRotDeg / WRISTDEGREESPERTICK) / 2;
      // Robot.topArmExtensionEncoder.resetTo(topEncoderPosition);
      // Robot.bottomArmExtensionEncoder.resetTo(bottomEncoderPosition);
      if (extensionVelocity < 0) {
        extensionVelocity = 0;
      }
    } else if (Robot.fullyRetractedArmLimitSwitch.isAtLimit()) {
      // extensionIn = maxExtensionInches;
      double topEncoderPosition = (wristRotDeg / WRISTDEGREESPERTICK) / 2 + maxExtensionInches / EXTENSIONINCHESPERTICK;
      double bottomEncoderPosition = -(wristRotDeg / WRISTDEGREESPERTICK) / 2
          + maxExtensionInches / EXTENSIONINCHESPERTICK;
      // Robot.topArmExtensionEncoder.resetTo(topEncoderPosition);
      // Robot.bottomArmExtensionEncoder.resetTo(bottomEncoderPosition);

      if (extensionVelocity > 0) {
        extensionVelocity = 0;
      }
    }
    return safeArmMovements;

  }

  public static SafeArmMovements isNextMovementSafe(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double extensionPower, double wristPower, double shoulderPower, boolean teleOpModeActive) {
    SafeArmMovements safeArmMovements = new SafeArmMovements();

    checkSafetyConstraints(extensionIn, wristRotDeg, shoulderRotDeg, safeArmMovements);
    if (teleOpModeActive && false) {
      // Temporary disabling until startup if sorted out
      checkTeleOpConstraints(extensionIn, wristRotDeg, shoulderRotDeg, wristPower, shoulderPower, safeArmMovements);
    }
    checkZoneConstraints(extensionIn, wristRotDeg, shoulderRotDeg, wristPower, shoulderPower, safeArmMovements);
    // checkZoneConstraintsNew(extensionIn, wristRotDeg, shoulderRotDeg, wristPower,
    // shoulderPower, safeArmMovements);

    return safeArmMovements;
  }

  /**
   * When in TeleOp, don't allow arm to extend beyond 30 inches. This ensures that
   * we are always within the 30 inch boundary of the robot. Ignores position of
   * claw, so this does not allow arm to reach its full potential distance. Does
   * not allow arm to swing through body
   */
  private static void checkTeleOpConstraints(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double wristPower, double shoulderPower, SafeArmMovements safeArmMovements) {
    if (extensionIn <= 10) {
      // Does not allow claw to extend if at boundary, it cant extent beyond the 30
      // inch boundary
      safeArmMovements.armExtension = false;
    }
    // Keeps the arm from going through the robot from the back to the front of the
    // robot
    // 40 degrees comes from the 15.5 inch long and 20.5 inch high deadzone on the
    // back of the robot (Was 37, but rounded to 40 for wiggle room)
    if (shoulderRotDeg >= -40) {
      safeArmMovements.shoulderRotateClockwise = false;
    }
    // Keeps the arm from going through the robot from the front to the back of the
    // robot
    // 40 degrees comes from the 15.5 inch long and 20.5 inch high deadzone on the
    // back of the robot (Was 37, but rounded to 40 for wiggle room)
    if (shoulderRotDeg <= 40) {
      safeArmMovements.shoulderRotateCounterClockwise = false;
    }
  }

  /**
   * Makes sure no-go zones are not being violated
   * 
   *
   *
   * 
   *
   *
   * 
   * 
   * 
   * _________________________________ | | |___________________________
   * 
   *
   *
   */
  private static void checkZoneConstraints(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double wristPower, double shoulderPower, SafeArmMovements safeArmMovements) {
    if (shoulderRotDeg < -165 && extensionIn > maxExtensionInches - 10) {
      // This is when the arm is retracted enough that when it is swung through
      // the robot the "butt" of the arm will hit the elctronics
      safeArmMovements.shoulderRotateCounterClockwise = false;
      safeArmMovements.armRetraction = false;

    }
    if ((shoulderRotDeg < -140) && (shoulderRotDeg >= -165) && (extensionIn > maxExtensionInches - 7.5)) {
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
      // This is when the arm is retracted enough that when it is swung through
      // the robot the "butt" of the arm will hit the elctronics
      safeArmMovements.shoulderRotateClockwise = false;
      safeArmMovements.armRetraction = false;
    }
    if (extensionIn < 13 && shoulderRotDeg > 68 && shoulderRotDeg < 127) {
      // This safety prevents the arm from extending over 30 inches out.
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
    if (extensionIn < 13 && shoulderRotDeg < -68 && shoulderRotDeg > -127) {
      // This safety prevents the arm from extending over 30 inches out.
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
    checkClawEndConstraints(extensionIn, wristRotDeg, shoulderRotDeg, wristPower, shoulderPower, true, safeArmMovements);
    
  }

  /**
   * Implements a way to find the butt end of the arm on a coordinate system
   */
  private static void checkButtEndConstraints(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double wristPower, double shoulderPower, SafeArmMovements safeArmMovements) {
    double shoulderRotationRadians = Math.toRadians(shoulderRotDeg);
    double buttPositionX = -extensionIn * Math.sin(shoulderRotationRadians) + BUTTCAPLENGTH - SHOULDEROFFSETFROMCENTER;
    double buttPositionY = SHOULDERHEIGHT + (extensionIn * Math.cos(shoulderRotationRadians) + BUTTCAPLENGTH);
    if (buttPositionY > ELECTRONICSDANGERZONEHEIGHT) {
      // We are safe
      return;
    }
    if (buttPositionX >= 0 && buttPositionX < ROBOTLENGTH / 2) {
      // This is when the arm is retracted enough that when it is swung through
      // the robot the "butt" of the arm will hit the electronics
      safeArmMovements.shoulderRotateCounterClockwise = false;
      safeArmMovements.armRetraction = false;
    }
    if (buttPositionX > -ROBOTLENGTH / 2 && buttPositionX <= 0) {
      // This is when the arm is retracted enough that when it is swung through
      // the robot the "butt" of the arm will hit the electronics
      safeArmMovements.shoulderRotateCounterClockwise = false;
      safeArmMovements.armRetraction = false;
    }
  }

  /**
   * Implements a way to find the end of the arm on a coordinate system
   */
  private static void checkClawEndConstraints(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double wristPower, double shoulderPower, boolean isHoldingHatchPanel, SafeArmMovements safeArmMovements) {
    double shoulderRotationRadians = Math.toRadians(shoulderRotDeg);
    double extensionOut = MAXARMLENGTH - extensionIn;
    double wristJointPositionX = extensionOut * Math.sin(shoulderRotationRadians) - SHOULDEROFFSETFROMCENTER;
    double wristJointPositionY = SHOULDERHEIGHT - (extensionOut * Math.cos(shoulderRotationRadians));

    if (wristJointPositionX > RULEEXTENSIONBOX) {
      safeArmMovements.armExtension = false;
      if (shoulderRotDeg < 90) {
        safeArmMovements.shoulderRotateClockwise = false;
      } else {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
    }
    if (wristJointPositionX < -RULEEXTENSIONBOX) {
      safeArmMovements.armExtension = false;
      if (shoulderRotDeg > -90) {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      } else {
        safeArmMovements.shoulderRotateClockwise = false;
      }

    }
    if (wristJointPositionY <= ELECTRONICSDANGERZONEHEIGHT) {

      if (wristJointPositionX >= 0 && wristJointPositionX < (ROBOTLENGTH / 2)) {
        safeArmMovements.armExtension = false;
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
      if (wristJointPositionX < 0 && wristJointPositionX > (-ROBOTLENGTH / 2)) {
        safeArmMovements.armExtension = false;
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
    }
    if (wristJointPositionY <= SHOULDERHEIGHT && wristJointPositionY >= (SHOULDERHEIGHT - TOPSIDEBOXHEIGHT)) {
        if (wristJointPositionX >= 0 && wristJointPositionX <= TOPSIDEBOXWIDTH) {
          safeArmMovements.armExtension = false;
        }
    }


    double wristRotRelativeToFloor = wristRotDeg - (180 - (shoulderRotDeg + 90));
    double xPosRelativeToArmJoint = (CLAWLENGTH) * Math.cos(wristRotRelativeToFloor);
    double yPosRelativeToArmJoint = (CLAWLENGTH) * Math.sin(wristRotRelativeToFloor);
    double clawTipXPos = wristJointPositionX + xPosRelativeToArmJoint;
    double clawTipYPos = wristJointPositionY + yPosRelativeToArmJoint;

    if (clawTipXPos > RULEEXTENSIONBOX) {
      safeArmMovements.armExtension = false;
      if (shoulderRotDeg < 90) {
        safeArmMovements.shoulderRotateClockwise = false;
      } else {
        safeArmMovements.shoulderRotateCounterClockwise = false;
      }
      if (wristRotRelativeToFloor < 90) {
        safeArmMovements.wristRotateClockwise = false;
      } else {
        safeArmMovements.wristRotateCounterClockwise = false;
      } 
    }

    if (isHoldingHatchPanel) {
      double hatchTopAngleRelativeToFloor = wristRotRelativeToFloor + HATCHPANELTIPANGLEOFFSET;
      double hatchBottomAngleRelativeToFloor = wristRotRelativeToFloor - HATCHPANELTIPANGLEOFFSET;
      
      double hatchTopRelativeXLength = HATCHPANELTIPDISTTOWRIST * Math.cos(hatchTopAngleRelativeToFloor);
      double hatchTopRelativeYLength = HATCHPANELTIPDISTTOWRIST * Math.sin(hatchTopAngleRelativeToFloor);

      double hatchBottomRelativeXLength = HATCHPANELTIPDISTTOWRIST * Math.cos(hatchBottomAngleRelativeToFloor);
      double hatchBottomRelativeYLength = HATCHPANELTIPDISTTOWRIST * Math.sin(hatchBottomAngleRelativeToFloor);

      double hatchTopXPos = clawTipXPos + hatchTopRelativeXLength;
      double hatchTopYPos = clawTipYPos + hatchTopRelativeYLength;

      double hatchBottomXPos = clawTipXPos + hatchBottomRelativeXLength;
      double hatchBottomYPos = clawTipYPos + hatchBottomRelativeYLength;
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
  }

  public static double getExtensionIn(double topEncoderTicks, double bottomEncoderTicks) {
    return (bottomEncoderTicks - topEncoderTicks) / 2 * EXTENSIONINCHESPERTICK - Robot.absoluteArmPositionError;
  }

  public static double getWristRotDegrees(double topEncoderTicks, double bottomEncoderTicks) {
    return -(bottomEncoderTicks + topEncoderTicks) * WRISTDEGREESPERTICK - Robot.absoluteWristPositionError;
  }

  public static double getShoulderRotDeg(double ticks) {
    return ticks * SHOULDERDEGREESPERTICK - Robot.absoluteShoulderPositionError;
  }

  private static double getExtensionInVelocity(double topEncoderTicks, double bottomEncoderTicks) {
    return (bottomEncoderTicks - topEncoderTicks) / 2 * EXTENSIONINCHESPERTICK;
  }

  public static double getWristRotDegreesVelocity(double topEncoderTicks, double bottomEncoderTicks) {
    return -(bottomEncoderTicks + topEncoderTicks) * WRISTDEGREESPERTICK;
  }

  public static double getShoulderRotDegVelocity(double ticks) {
    return ticks * SHOULDERDEGREESPERTICK;
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