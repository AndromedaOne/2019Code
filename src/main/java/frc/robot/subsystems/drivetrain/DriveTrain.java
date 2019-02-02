package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public abstract class DriveTrain extends Subsystem {

  @Override
  public void periodic() {
  }
  public abstract boolean getShifterPresentFlag();
  public abstract void move(double forwardBackSpeed, double rotateAmount);

  public abstract void stop();

  public abstract void shiftToLowGear();

  public abstract void shiftToHighGear();

}