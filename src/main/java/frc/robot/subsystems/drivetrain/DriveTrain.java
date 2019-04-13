package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public abstract class DriveTrain extends Subsystem {

  public enum RobotGear {
    SLOWLOWGEAR, LOWGEAR, SLOWHIGHGEAR, HIGHGEAR
  }

  public class blab {

  }

  @Override
  public void periodic() {
  }

  public abstract boolean getShifterPresentFlag();

  public abstract void move(double forwardBackSpeed, double rotateAmount, boolean squaredInputs, boolean useAccelLimits);

  public abstract void stop();

  public abstract void shiftToLowGear();

  public abstract void shiftToHighGear();

  public abstract void setGear(RobotGear gear);

  public abstract RobotGear getGear();

  public abstract void changeControlMode(NeutralMode mode);

  public abstract WPI_TalonSRX getLeftRearTalon();

  public abstract void toggleSlowMode();

  public abstract void toggleShifter();
}
