package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.sensors.NavXGyroSensor;
import frc.robot.telemetries.Trace;

public class TurnToDeltaAngle extends Command {
  private GyroPIDController gyroPID = GyroPIDController.getInstance();
  private double deltaAngle;

  public TurnToDeltaAngle(double theDeltaAngle) {
    deltaAngle = theDeltaAngle;
    requires(Robot.driveTrain);
    if (Robot.getConfig().hasPath("sensors.lineFollowSensor.lineFollowSensor4905.invertRotation")) {
      deltaAngle *= -1;
    }
  }

  @Override
  protected void initialize() {
    Robot.driveTrain.shiftToLowGear();
    double setPoint = NavXGyroSensor.getInstance().getZAngle() + deltaAngle;
    Trace.getInstance().logCommandStart("TurnToDeltaAngle");
    gyroPID.setSetpoint(setPoint);
    gyroPID.enable();
  }

  @Override
  protected boolean isFinished() {
    return gyroPID.onTarget();
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("TurnToDeltaAngle");
    gyroPID.disable();
  }

}