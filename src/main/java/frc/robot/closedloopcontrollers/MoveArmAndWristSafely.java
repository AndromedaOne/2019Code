package frc.robot.closedloopcontrollers;

import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
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

  private static double teleopShoulderPower = 0;
  private static double teleopWristPower = 0;
  private static double teleopExtensionPower = 0;

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
        ShoulderPIDController.getInstance().setSetpoint(shoulderRotDeg);
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
        WristPIDController.getInstance().setSetpoint(wristRotDeg);
        WristPIDController.getInstance().enable();
        wristPIDSetpointSet = true;
        wristPower = 0;
      } else {
        isMovementSafe(0, localPIDWristPower, 0);
        wristPower = localPIDWristPower;
      }
      
      if(ButtonsEnumerated.RIGHTBUMPERBUTTON.isPressed(OI.getInstance().getOperatorStick())) {
        double wristSetpoint = 0;
        if(shoulderRotDeg > 0) {
          wristSetpoint = 90 - shoulderRotDeg;
        }else {
          wristSetpoint = -90 - shoulderRotDeg;
        }
        if(Math.abs(wristSetpoint - wristRotDeg) < 15) {
          WristPIDController.getInstance().setSetpoint(wristSetpoint);
        }
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
        shoulderRotDeg = maxShoulderRotDegrees;

        // Robot.shoulderEncoder.resetTo(maxShoulderRotDegrees/SHOULDERTICKSPERDEGREE);
        if (shoulderPower > 0) {
          shoulderPower = 0;
          // throw new ArmOutOfBoundsException(extensionIn, wristRotDeg, shoulderRotDeg);
        }
      } else {
        shoulderRotDeg = -maxShoulderRotDegrees;

        // Robot.shoulderEncoder.resetTo(-maxShoulderRotDegrees/SHOULDERTICKSPERDEGREE);
        if (shoulderPower < 0) {
          shoulderPower = 0;
          // throw new ArmOutOfBoundsException(extensionIn, wristRotDeg, shoulderRotDeg);
        }
      }
    }

    boolean fullyExtended = Robot.fullyExtendedArmLimitSwitch.isAtLimit();
    if (fullyExtended) {
      // extensionIn = 0;
      double topEncoderPosition = (wristRotDeg / WRISTDEGREESPERTICK) / 2;
      double bottomEncoderPosition = -(wristRotDeg / WRISTDEGREESPERTICK) / 2;
      // Robot.topArmExtensionEncoder.resetTo(topEncoderPosition);
      // Robot.bottomArmExtensionEncoder.resetTo(bottomEncoderPosition);
      if (extensionPower < 0) {
        extensionPower = 0;
      }
    } else if (Robot.fullyRetractedArmLimitSwitch.isAtLimit()) {
      extensionIn = maxExtensionInches;
      double topEncoderPosition = (wristRotDeg / WRISTDEGREESPERTICK) / 2 + maxExtensionInches / EXTENSIONINCHESPERTICK;
      double bottomEncoderPosition = -(wristRotDeg / WRISTDEGREESPERTICK) / 2
          + maxExtensionInches / EXTENSIONINCHESPERTICK;
      // Robot.topArmExtensionEncoder.resetTo(topEncoderPosition);
      // Robot.bottomArmExtensionEncoder.resetTo(bottomEncoderPosition);

      if (extensionPower > 0) {
        extensionPower = 0;
      }
    }

    Robot.extendableArmAndWrist.moveArmWrist(extensionPower, wristPower, shoulderPower);

    SmartDashboard.putNumber("ShoulderAngle", shoulderRotDeg);
    SmartDashboard.putNumber("WristAngle", wristRotDeg);
    SmartDashboard.putNumber("ExtensionIn", extensionIn);

    SmartDashboard.putNumber("topExtensionEncoderTicks", topExtensionEncoderTicks);
    SmartDashboard.putNumber("bottomExtensionEncoderTicks", bottomExtensionEncoderTicks);
  }

  /**
   * @param extensionVelocity
   * @param wristRotVelocity
   * @param shoulderRotVelocity
   * @throws ArmOutOfBoundsException
   */

  private static SafeArmMovements isMovementSafe(double extensionVelocity, double wristRotVelocity,
      double shoulderRotVelocity) {
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

    SafeArmMovements safeArmMovements = isLocSafe(extensionIn + deltaExtension, wristRotDeg + deltaWristRot,
        shoulderRotDeg + deltaShoulderRot, extensionVelocity, wristRotVelocity, shoulderRotVelocity);

    return safeArmMovements;

  }

  public static SafeArmMovements isLocSafe(double extensionIn, double wristRotDeg, double shoulderRotDeg,
      double extensionPower, double wristPower, double shoulderPower) {

    SafeArmMovements safeArmMovements = new SafeArmMovements();
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

    if (shoulderRotDeg > -30 && shoulderRotDeg < -10) {
      if (wristRotDeg < -80 && extensionIn > maxExtensionInches - 10) {
        // TODO: Get the right number instead of 10 and set the safeArmMovements
        // we are hitting the shoulder cim motor

      }

    }

    return safeArmMovements;
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