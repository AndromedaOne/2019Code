package frc.robot.subsystems.pneumaticstilts;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.subsystems.pneumaticstilts.RealPneumaticStilts.StiltLeg;

/**
 * The pneumatic climber subsystem for the robot
 */
public abstract class PneumaticStilts extends Subsystem {
  public static StiltLeg frontLeftStiltLeg;
  public static StiltLeg frontRightStiltLeg;
  public static StiltLeg rearLeftStiltLeg;
  public static StiltLeg rearRightStiltLeg;

  public void initDefaultCommand() {
    // setDefaultCommand(new RaiseRobot());
  }

  public abstract void stabilizedMove(double frontLeftLeg, double frontRightLeg, double rearLeftLeg,
      double rearRightLeg);

  public abstract void stopAllLegs();

  public abstract void extendFrontLeft();

  public abstract void extendFrontRight();

  public abstract void extendRearLeft();

  public abstract void extendRearRight();

  public abstract void stopFrontLeft();

  public abstract void stopFrontRight();

  public abstract void stopRearLeft();

  public abstract void stopRearRight();

  public abstract void retractFrontLeft();

  public abstract void retractFrontRight();

  public abstract void retractRearLeft();

  public abstract void retractRearRight();

}