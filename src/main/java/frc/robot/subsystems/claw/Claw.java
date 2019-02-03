package frc.robot.subsystems.claw;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Claw extends Subsystem {
    public abstract void openClaw();

    public abstract void closeClaw();

    public abstract void runIntake(double intakeSpeed);
}