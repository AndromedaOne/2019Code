package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.sensors.NavXGyroSensor;
import frc.robot.subsystems.pneumaticstilts.PneumaticStilts;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class RaiseRobot extends Command {

  private NavXGyroSensor gyro = NavXGyroSensor.getInstance();
  private PneumaticStilts pneumaticStilts = Robot.pneumaticStilts;
  private double initialXValue = 0;
  private double initialYValue = 0;
  private double frontLeftError = 1;
  private double frontRightError = 1;
  private double rearLeftError = 1;
  private double rearRightError = 1;
  private double p = 0.1;

  public void initialize() {
    initialXValue = gyro.getXAngle();
    initialYValue = gyro.getYAngle();
  }

  private double calculateError(double x, double y) {
    double error = 1 - (p * (x + y));
    if(error > 1) {
      error = 1;
    }
    if (error < 0) {
      error = 0;
    }
    return error; 
  }

  public void execute() {

    double angleY = gyro.getYAngle() - initialYValue;
    double angleX = gyro.getXAngle() - initialXValue;

    // Left Front Leg
    if ((angleY < 0) || (angleX < 0)) {
      frontLeftError = calculateError(-angleX, -angleY);
    }

    // Left Rear Leg
    if ((angleY > 0) || (angleX < 0)) {
      rearLeftError = calculateError(-angleX, angleY);
    }

    // Right Front Leg
    if ((angleY < 0) || (angleX > 0)) {
      frontRightError = calculateError(angleX, -angleY);
    }

    // Right Rear Leg
    if ((angleY > 0) || (angleX > 0)) {
      rearRightError = calculateError(angleX, angleY);
    }

    Trace.getInstance().addTrace(true, "Pneumatic Stilts",
        new TracePair("Angle X", angleX),
        new TracePair("Angle Y", angleY),
        new TracePair("Front Left Leg", frontLeftError),
        new TracePair("Front Right Leg", frontRightError),
        new TracePair("Rear Left Leg", rearLeftError),
        new TracePair("Rear Right Leg", rearRightError));

    pneumaticStilts.stabilizedMove(frontLeftError, frontRightError, rearLeftError, rearRightError);

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  public void end() {

  }

}