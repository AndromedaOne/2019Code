package frc.robot.subsystems.pneumaticstilts;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The pneumatic climber subsystem for the robot
 */
public abstract class PneumaticStilts extends Subsystem {

  public void initDefaultCommand() {
    // setDefaultCommand(new RaiseRobot());
  }

  public abstract void stopAllLegs();

  public abstract void extendFrontLegs();

  public abstract void extendRearLegs();

  public abstract void stopFrontLegs();

  public abstract void stopRearLegs();

  public abstract void retractFrontLegs();

  public abstract void retractRearLegs();

}