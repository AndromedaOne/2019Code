package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.sensors.NavXGyroSensor;

public class RaiseBackLegsForL2 extends Command {

  public RaiseBackLegsForL2() {
    requires(Robot.pneumaticStilts);
  }

  public void initialize() {
    System.out.println("Raising back legs for L2");
    Robot.pneumaticStilts.extendRearLegs();
  }

  @Override
  protected boolean isFinished() {
    // This should level us out after rasing the front legs
    return NavXGyroSensor.getInstance().getXAngle() < 5;
  }

  @Override
  protected void end() {
    System.out.println(" - We have reached our angle - ");
    Robot.pneumaticStilts.stopRearLegs();
  }

  @Override
  protected void interrupted() {
    end();
  }
}