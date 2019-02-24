package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;

public class ReleaseCurrentGamepiece extends Command {

    public ReleaseCurrentGamepiece() {
        requires(Robot.claw);
    }

    public void initialize() {
        
    }

    public void execute() {
        if(DriveClawMotorsSafely.hasBall) {
            Robot.intake.rollIntake(-0.75);
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}