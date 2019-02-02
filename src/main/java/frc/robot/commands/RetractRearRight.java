package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractRearRight extends Command {

    public void initialize() {
        Robot.pneumaticStilts.retractRearRight();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}