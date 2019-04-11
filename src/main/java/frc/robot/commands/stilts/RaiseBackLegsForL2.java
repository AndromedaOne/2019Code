package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.sensors.NavXGyroSensor;
import frc.robot.telemetries.Trace;

public class RaiseBackLegsForL2 extends Command {
  private final double kLevelAngleTolerance = 2;
  private boolean robotLevel = false;
  private final double raiseAngleThreshold = -6;

  public RaiseBackLegsForL2() {
    requires(Robot.pneumaticStilts);
  }

  public void initialize() {
    System.out.println("Raising back legs for L2");
    if(NavXGyroSensor.getInstance().getXAngle() < kLevelAngleTolerance && NavXGyroSensor.getInstance().getXAngle() > -kLevelAngleTolerance) {
      robotLevel = true;
    } else {
      robotLevel = false;
    }
    Robot.pneumaticStilts.extendRearLegs();
  }

  @Override
  protected boolean isFinished() {
    // This will determine wether we are tying to level the robot or raise the back
    // This allows us to climb onto L2 from the front or back
    boolean makeRobotLevel = robotLevel ? NavXGyroSensor.getInstance().getXAngle() < raiseAngleThreshold : NavXGyroSensor.getInstance().getXAngle() > -5;
    return makeRobotLevel;
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("RaiseBackLegsForL2");
    Robot.pneumaticStilts.stopRearLegs();
  }

  @Override
  protected void interrupted() {
    end();
  }
}