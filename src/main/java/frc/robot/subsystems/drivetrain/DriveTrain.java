package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public abstract class DriveTrain extends Subsystem {

  public abstract WPI_TalonSRX getLeftRearTalon();

  @Override
  public void periodic() {
  }

  public abstract boolean getShifterPresentFlag();

  public abstract void move(double forwardBackSpeed, double rotateAmount);

  public abstract void stop();

  public abstract void shiftToLowGear();

  public abstract void shiftToHighGear();

}
