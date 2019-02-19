package frc.robot.closedloopcontrollers;

import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.wpilibj.Notifier;
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
  static{
    
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

  public static final double SHOULDERDEGREESPERTICK = 1.0/SHOULDERTICKSPERDEGREE;
  public static final double EXTENSIONINCHESPERTICK = 1.0/EXTENSIONTICKSPERINCH;
  public static final double WRISTDEGREESPERTICK = 1.0/WRISTTICKSPERDEGREE;

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

  public static void calculate(){
    
    double topExtensionEncoderTicks = Robot.topArmExtensionEncoder.getDistanceTicks();
    double bottomExtensionEncoderTicks = Robot.bottomArmExtensionEncoder.getDistanceTicks();
    double shoulderTicks = Robot.shoulderEncoder.getDistanceTicks();

    double extensionIn = getExtensionIn(topExtensionEncoderTicks, bottomExtensionEncoderTicks);
    double wristRotDeg = getWristRotDegrees(topExtensionEncoderTicks, bottomExtensionEncoderTicks);
    double shoulderRotDeg = getShoulderRotDeg(shoulderTicks);
    //System.out.println("shoulderRotDeg: " + shoulderRotDeg);
    //System.out.println("wristRotDeg: " + wristRotDeg);
    //System.out.println("extensionIn: " + extensionIn);

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

    if(Math.abs(localTeleopShoulderPower) >= 0.2 && ) {
      shoulderPower = localTeleopShoulderPower;
      shoulderPIDSetpointSet = false;
    }else {
      if (!shoulderPIDSetpointSet) {
        ShoulderPIDController.getInstance().setSetpoint(shoulderRotDeg);
        ShoulderPIDController.getInstance().enable();
        shoulderPIDSetpointSet = true;
        shoulderPower = 0;
      }else {
        shoulderPower = localPIDShoulderPower;
      }
  
    }

    double wristPower = 0;
    if(Math.abs(localTeleopWristPower) >= 0.2) {
      wristPower = localTeleopWristPower;
      wristPIDSetpointSet = false;
    }else {
      if (!wristPIDSetpointSet) {
        WristPIDController.getInstance().setSetpoint(wristRotDeg);
        WristPIDController.getInstance().enable();
        wristPIDSetpointSet = true;
        wristPower = 0;
      }else {
        wristPower = localPIDWristPower;
      }
    }

    double extensionPower = 0;
    if(Math.abs(localTeleopExtensionPower) >= 0.2) {
      extensionPower = localTeleopExtensionPower;
      extensionPIDSetpointSet = false;
    }else {
      if (!extensionPIDSetpointSet) {
        ExtendableArmPIDController.getInstance().setSetpoint(extensionIn);
        ExtendableArmPIDController.getInstance().enable();
        extensionPIDSetpointSet = true;
        extensionPower = 0;
      }else {
        extensionPower = localPIDExtensionPower;
      }
    }

    move(extensionPower, wristPower, shoulderPower);
  }
  /**
   * @param extensionVelocity
   * @param wristRotVelocity
   * @param shoulderRotVelocity
   * @throws ArmOutOfBoundsException
   */

  private static void move(double extensionVelocity, double wristRotVelocity, double shoulderRotVelocity) {
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
    double wristRotVelocityConversion = getWristRotDegreesVelocity(topArmEncoderVelocity,
        bottomArmEncoderVelocity);
    double shoulderRotVelocityConversion = getShoulderRotDegVelocity(shoulderEncoderVelocity);

    // multiplying by 1.0 to try to look into the future and see where the
    // deltaExtension can be if speed is increasing
    double deltaExtension = extensionVelocityConversion * deltaTime * 1.0;
    double deltaWristRot = wristRotVelocityConversion * deltaTime * 1.0;
    double deltaShoulderRot = shoulderRotVelocityConversion * deltaTime * 1.0;
    
    System.out.println("deltaShoulderRot: " + deltaShoulderRot);
    System.out.println("deltaExtension: " + deltaExtension);
    System.out.println("deltaWristRot: " + deltaWristRot);
    boolean locationSafe = isLocSafe(extensionIn + deltaExtension, wristRotDeg + deltaWristRot, shoulderRotDeg + deltaShoulderRot);
    System.out.println("locationSafe: " + locationSafe);
    if (!isLocSafe(extensionIn + deltaExtension, wristRotDeg + deltaWristRot, shoulderRotDeg + deltaShoulderRot)) {
      // throw new ArmOutOfBoundsException(extensionIn + deltaExtension, wristRotDeg +
      // deltaWristRot,
      // shoulderRotDeg + deltaShoulderRot);
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
        // }
      }
    }

    if (Robot.shoulderLimitSwitch.isAtLimit()) {
      if (shoulderRotDeg > 0) {
        // shoulderRotDeg = maxShoulderRotDegrees;

        // Robot.armRotateEncoder1.resetTo(maxShoulderRotDegrees/SHOULDERTICKSTODEGRESS);
        // if (shoulderRotVelocity > 0) {
        // shoulderRotVelocity = 0;
        // }
      } else {
        // shoulderRotDeg = -maxShoulderRotDegrees;

        // Robot.armRotateEncoder1.resetTo(-maxShoulderRotDegrees/SHOULDERTICKSTODEGRESS);
        // if (shoulderRotVelocity < 0) {
        // shoulderRotVelocity = 0;
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

    Robot.extendableArmAndWrist.moveArmWrist(extensionVelocity, wristRotVelocity, shoulderRotVelocity);
  }

  public static boolean isLocSafe(double extensionIn, double wristRotDeg, double shoulderRotDeg) {

    if (shoulderRotDeg > 180 || shoulderRotDeg < -180 || extensionIn < 0 || extensionIn > maxExtensionInches
        || wristRotDeg > maxWristRotDegrees || wristRotDeg < -maxWristRotDegrees) {
      System.out.println("Failing 1");
      return false;
    } else if (shoulderRotDeg < -150 && extensionIn > maxExtensionInches - 1) {
      System.out.println("Failing 2");
      return false;
    } else if (shoulderRotDeg > 150 && extensionIn > maxExtensionInches - 1) {
      System.out.println("Failing 3");
      return false;
    } else if (extensionIn < 10 && shoulderRotDeg > 53 && shoulderRotDeg < 127) {
      System.out.println("Failing 4");
      return false;
    } else if (extensionIn < 10 && shoulderRotDeg < -53 && shoulderRotDeg > -127) {
      System.out.println("Failing 5");
      return false;
    } else if (shoulderRotDeg < 50 && shoulderRotDeg > -50 && extensionIn < maxExtensionInches - 1) {
      if (extensionIn > 13 && wristRotDeg < -90 && shoulderRotDeg > 0 && shoulderRotDeg < 15) {
        return true;
      } else if (extensionIn > 13 && wristRotDeg > 90 && shoulderRotDeg < 0 && shoulderRotDeg > -15) {
        return true;
      } else {
        System.out.println("Failing 6");
        return false;
      }
    } else {
      return true;
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

  public static void stop(){
    mutex.lock();
    teleopShoulderPower = 0;
    teleopWristPower = 0;
    teleopExtensionPower = 0;

    pidShoulderPower = 0;
    pidWristPower = 0;
    pidExtensionPower = 0;
    mutex.unlock();
  }

}