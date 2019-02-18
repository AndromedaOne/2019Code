package frc.robot.closedloopcontrollers;

import frc.robot.Robot;
import frc.robot.exceptions.ArmOutOfBoundsException;

public class MoveArmAndWristSafely {

  public enum DontUsePIDHold {
    WRIST, EXTENSION, SHOULDER, HOLDALL
  }

  public static final double maxWristRotDegrees = 1000;
  public static final double maxExtensionInches = 1000;
  public static final double maxShoulderRotDegrees = 180;

  private static boolean shoulderPIDSetpointSet = false;
  private static boolean wristPIDSetpointSet = false;
  private static boolean extensionPIDSetpointSet = false;

  public static final double SHOULDERTICKSTODEGREES = 1.0;
  public static final double EXTENSIONTICKSTOINCHES = 1.0;
  public static final double WRISTTICKSTODEGREES = 1.0;

  private static final double deltaTime = 0.02;

  /**
   * @param extensionVelocity
   * @param wristRotVelocity
   * @param shoulderRotVelocity
   * @throws ArmOutOfBoundsException
   */

  public static void move(double extensionVelocity, double wristRotVelocity, double shoulderRotVelocity,
      DontUsePIDHold dontUsePidHold) throws ArmOutOfBoundsException {
    double topExtensionEncoderTicks = Robot.topArmExtensionEncoder.getDistanceTicks();
    double bottomExtensionEncoderTicks = Robot.bottomArmExtensionEncoder.getDistanceTicks();
    double shoulderTicks = Robot.shoulderEncoder.getDistanceTicks();

    double extensionIn = getExtensionIn(topExtensionEncoderTicks, bottomExtensionEncoderTicks);
    double wristRotDeg = getWristRotDegrees(topExtensionEncoderTicks, bottomExtensionEncoderTicks);
    double shoulderRotDeg = getShoulderRotDeg(shoulderTicks);

    double extensionVelocityConversion = getExtensionIn(Robot.topArmExtensionEncoder.getVelocity(), Robot.bottomArmExtensionEncoder.getVelocity());
    double wristRotVelocityConversion = getWristRotDegrees(Robot.topArmExtensionEncoder.getVelocity(), Robot.bottomArmExtensionEncoder.getVelocity());
    double shoulderRotVelocityConversion = getShoulderRotDeg(Robot.shoulderEncoder.getVelocity());
    
    // multiplying by 1.1 to try to look into the future and see where the 
    // deltaExtension can be if speed is increasing
    double deltaExtension = extensionIn + extensionVelocityConversion * deltaTime*1.1;
    double deltaWristRot = wristRotDeg + wristRotVelocityConversion * deltaTime*1.1;
    double deltaShoulderRot = shoulderRotDeg + shoulderRotVelocityConversion * deltaTime*1.1;

    if (dontUsePidHold != DontUsePIDHold.SHOULDER) {
      if (Math.abs(shoulderRotVelocity) <= 0.2) {
        shoulderRotVelocity = 0;
        if (!shoulderPIDSetpointSet) {
          Robot.shoulderPIDController.setSetpoint(shoulderRotDeg);
          Robot.shoulderPIDController.enable();
          System.out.println("Enabling PID at: " + shoulderRotDeg);
        }
        // System.out.println("Running PID");
        shoulderPIDSetpointSet = true;
      } else {
        shoulderPIDSetpointSet = false;
        System.out.println("Disabling");
        Robot.shoulderPIDController.disable();
        Robot.shoulderPIDController.reset();
      }
    }
    if (dontUsePidHold != DontUsePIDHold.EXTENSION) {
      if (Math.abs(extensionVelocity) <= 0.2) {
        extensionVelocity = 0;
        if (!extensionPIDSetpointSet) {
          Robot.extendableArmPIDController.setSetpoint(extensionIn);
          Robot.extendableArmPIDController.enable();
        }
        extensionPIDSetpointSet = true;
      } else {
        extensionPIDSetpointSet = false;
        Robot.extendableArmPIDController.disable();
        Robot.extendableArmPIDController.reset();
      }
    }
    if (dontUsePidHold != DontUsePIDHold.WRIST) {
      if (Math.abs(wristRotVelocity) <= 0.2) {
        wristRotVelocity = 0;
        if (!wristPIDSetpointSet) {
          Robot.wristPIDController.setSetpoint(wristRotDeg);
          Robot.wristPIDController.enable();
        }
        wristPIDSetpointSet = true;
      } else {
        wristPIDSetpointSet = false;
        Robot.wristPIDController.disable();
        Robot.wristPIDController.reset();
      }
    }
    if (!isLocSafe(extensionIn + deltaExtension, wristRotDeg + deltaWristRot, shoulderRotDeg + deltaShoulderRot)) {
      // throw new ArmOutOfBoundsException(extensionIn + deltaExtension, wristRotDeg +
      // deltaWristRot,
      // shoulderRotDeg + deltaShoulderRot);
    }

    if (Robot.wristLimitSwitchUp.isAtLimit()) {
      if (wristRotDeg > 0) {
        // wristRotDeg = maxWristRotDegrees;
        double topEncoderPosition = (extensionIn / EXTENSIONTICKSTOINCHES)
            + (maxWristRotDegrees / WRISTTICKSTODEGREES) / 2;
        double bottomEncoderPosition = (extensionIn / EXTENSIONTICKSTOINCHES)
            - (maxWristRotDegrees / WRISTTICKSTODEGREES) / 2;
        // Robot.topArmExtensionEncoder.resetTo(topEncoderPosition);
        // Robot.bottomArmExtensionEncoder.resetTo(bottomEncoderPosition);
        // if (wristRotVelocity > 0) {
        // wristRotVelocity = 0;
        // }
      } else {
        // wristRotDeg = -maxWristRotDegrees;
        double topEncoderPosition = (extensionIn / EXTENSIONTICKSTOINCHES)
            - (maxWristRotDegrees / WRISTTICKSTODEGREES) / 2;
        double bottomEncoderPosition = (extensionIn / EXTENSIONTICKSTOINCHES)
            + (maxWristRotDegrees / WRISTTICKSTODEGREES) / 2;
        // Robot.topArmExtensionEncoder.resetTo(topEncoderPosition);
        // Robot.bottomArmExtensionEncoder.resetTo(bottomEncoderPosition);
        // if (wristRotVelocity < 0) {
        // wristRotVelocity = 0;
        // }
      }
    }

    if(Robot.shoulderLimitSwitch.isAtLimit()) {
      if(shoulderRotDeg > 0) {
        //shoulderRotDeg = maxShoulderRotDegrees;
        
        //Robot.armRotateEncoder1.resetTo(maxShoulderRotDegrees/SHOULDERTICKSTODEGRESS);
        //if (shoulderRotVelocity > 0) {
        //  shoulderRotVelocity = 0;
        //}
      }else {
        //shoulderRotDeg = -maxShoulderRotDegrees;
        
        //Robot.armRotateEncoder1.resetTo(-maxShoulderRotDegrees/SHOULDERTICKSTODEGRESS);
        //if (shoulderRotVelocity < 0) {
        //  shoulderRotVelocity = 0;
        //}
      }
    }

    boolean fullyExtended = Robot.fullyExtendedArmLimitSwitch.isAtLimit();
    if (fullyExtended) {
      // extensionIn = 0;
      double topEncoderPosition = (wristRotDeg / WRISTTICKSTODEGREES) / 2;
      double bottomEncoderPosition = -(wristRotDeg / WRISTTICKSTODEGREES) / 2;
      // Robot.topArmExtensionEncoder.resetTo(topEncoderPosition);
      // Robot.bottomArmExtensionEncoder.resetTo(bottomEncoderPosition);
      if (extensionVelocity < 0) {
        extensionVelocity = 0;
      }
    } else if (Robot.fullyRetractedArmLimitSwitch.isAtLimit()) {
      // extensionIn = maxExtensionInches;
      double topEncoderPosition = (wristRotDeg / WRISTTICKSTODEGREES) / 2 + maxExtensionInches / EXTENSIONTICKSTOINCHES;
      double bottomEncoderPosition = -(wristRotDeg / WRISTTICKSTODEGREES) / 2
          + maxExtensionInches / EXTENSIONTICKSTOINCHES;
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
      return false;
    } else if (shoulderRotDeg < -150 && extensionIn > maxExtensionInches - 1) {
      return false;
    } else if (shoulderRotDeg > 150 && extensionIn > maxExtensionInches - 1) {
      return false;
    } else if (extensionIn < 10 && shoulderRotDeg > 53 && shoulderRotDeg < 127) {
      return false;
    } else if (extensionIn < 10 && shoulderRotDeg < -53 && shoulderRotDeg > -127) {
      return false;
    } else if (shoulderRotDeg < 50 && shoulderRotDeg > -50 && extensionIn < maxExtensionInches - 1) {
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

  public static double getExtensionIn(double topEncoderTicks, double bottomEncoderTicks) {
    return (bottomEncoderTicks - topEncoderTicks) / 2 * EXTENSIONTICKSTOINCHES;
  }

  public static double getWristRotDegrees(double topEncoderTicks, double bottomEncoderTicks) {
    return -(bottomEncoderTicks + topEncoderTicks) * WRISTTICKSTODEGREES;
  }

  public static double getShoulderRotDeg(double ticks) {
    return ticks * SHOULDERTICKSTODEGREES;
  }

}