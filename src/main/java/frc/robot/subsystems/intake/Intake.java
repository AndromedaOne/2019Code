package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Intake extends Subsystem {
  public abstract void rollIntake(double speed);

  public abstract void moveIntakeArm(double speed);

  public abstract IntakeArmPositionsEnum getCurrentIntakeArmPosition();

  public abstract void setCurrentIntakeArmPosition(IntakeArmPositionsEnum position);

  public abstract boolean isAtLimitSwitch();

  public abstract boolean isAtGround();

  public abstract double getCargoSetpoint();

  public abstract double getGroundSetpoint();

  public abstract double getStowedSetpoint();
}
