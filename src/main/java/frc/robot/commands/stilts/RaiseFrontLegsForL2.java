package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.sensors.NavXGyroSensor;

public class RaiseFrontLegsForL2 extends Command {

  private final double raiseAngleThreshold = 11.94;

  public RaiseFrontLegsForL2() {
    requires(Robot.pneumaticStilts);
  }

  public void initialize() {
    System.out.println("Raising front legs for L2");
    Robot.pneumaticStilts.extendFrontLegs();
  }

  @Override
  protected boolean isFinished() {
    return NavXGyroSensor.getInstance().getXAngle() > raiseAngleThreshold;
  }

  @Override
  protected void end() {
    System.out.println(" - We have reached our angle - ");
    Robot.pneumaticStilts.stopFrontLegs();
  }

  @Override
  protected void interrupted() {
    end();
  }
}