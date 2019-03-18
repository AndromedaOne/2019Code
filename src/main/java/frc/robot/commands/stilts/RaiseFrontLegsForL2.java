package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.sensors.NavXGyroSensor;
import frc.robot.telemetries.Trace;

public class RaiseFrontLegsForL2 extends Command {

  private final double raiseAngleThreshold = 6;

  public RaiseFrontLegsForL2() {
    requires(Robot.pneumaticStilts);
  }

  public void initialize() {
    Robot.pneumaticStilts.extendFrontLegs();
  }

  @Override
  protected boolean isFinished() {
    return NavXGyroSensor.getInstance().getXAngle() > raiseAngleThreshold;
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("RaiseFrontLegsForL2");
    Robot.pneumaticStilts.stopFrontLegs();
  }

  @Override
  protected void interrupted() {
    end();
  }
}