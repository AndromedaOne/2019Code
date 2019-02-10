package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveForward extends Command {

    public DriveForward() {
        requires(Robot.driveTrain);
    }


    @Override
    protected void initialize() {
    }

    @Override
  protected void execute() {
      Robot.driveTrain.move(.5, 0);
  }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
  protected void end() {
      Robot.driveTrain.move(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }

}