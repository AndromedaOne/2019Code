package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractRearLeft extends Command {

    public void initialize() {
        Robot.pneumaticStilts.retractRearLeft();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}