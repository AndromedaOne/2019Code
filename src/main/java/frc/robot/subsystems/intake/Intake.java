package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Intake extends Subsystem{
    public abstract void rollIntake(double speed);

    public abstract void moveIntakeArm(double speed);
}

