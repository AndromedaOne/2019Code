package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.linefollowersensor.LineFollowArraySensorReading;
import frc.robot.sensors.linefollowersensor.LineFollowerSensorBase;

public class LineFollowerController {
  private MoveDrivetrainGyroCorrect gyroCorrectMove;
  private LineFollowerSensorBase sensor;
  private LineFollowArraySensorReading values;
  private final double kMinimumLineAngle = Math.toRadians(2);
  private final double kForwardSpeed = .1;
  private final double kRotateScaleFactor = .5; // all constants are currently placeholders
  private int lineNotFoundCounter = 0;

  public LineFollowerController(MoveDrivetrainGyroCorrect theGyroCorrectMove,
      LineFollowerSensorBase lineFollowerSensorArray) {
    gyroCorrectMove = theGyroCorrectMove;
    sensor = lineFollowerSensorArray;

  }

  public void run() {
    values = sensor.findLine();
    SmartDashboard.putBoolean("IsLineFound", values.lineFound);
    SmartDashboard.putNumber("Angle", values.lineAngle);
    if (values.lineFound) {
      lineNotFoundCounter = 0;
      // System.out.println("I FOUND A LINE!! :D");
      if (values.lineAngle <= -kMinimumLineAngle) {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, kRotateScaleFactor * values.lineAngle, true, false);
      } else if (values.lineAngle >= kMinimumLineAngle) {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, kRotateScaleFactor * values.lineAngle, true, false);
      } else {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, 0, true, false);
      }
    } else {
      lineNotFoundCounter++;
      if (lineNotFoundCounter >= 10) {
        gyroCorrectMove.stop();
      } else {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, 0, true, false);
      }
    }

  }

  public void reset() {

  }

  public void stop() {
    gyroCorrectMove.stop();

  }

  public void initialize() {

  }

  public boolean isDone() {
    if (lineNotFoundCounter >= 10) {
      return true;
    } else {
      return false;
    }

  }

  public void enable(double setpoint) {

  }

}