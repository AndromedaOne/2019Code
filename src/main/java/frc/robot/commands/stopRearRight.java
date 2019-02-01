package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class stopRearRight extends Command {

    public void initialize() {
        Robot.pneumaticStilts.stopRearRight();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}