package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopFrontRight extends Command {

    public void initialize() {
        Robot.pneumaticStilts.stopFrontRight();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}