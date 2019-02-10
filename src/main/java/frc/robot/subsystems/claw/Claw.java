package frc.robot.subsystems.claw;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Claw extends Subsystem {
  public abstract void openClaw();

  public abstract void stop();

  public abstract void driveIntakeMotors(double speed);

  public abstract void closeClaw();
}