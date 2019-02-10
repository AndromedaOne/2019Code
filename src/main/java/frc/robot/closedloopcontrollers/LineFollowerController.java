package frc.robot.closedloopcontrollers;

import frc.robot.sensors.linefollowersensor.BaseLineFollowerSensor;
import frc.robot.sensors.linefollowersensor.LineFollowArraySensorReading;

public class LineFollowerController {
  private MoveDrivetrainGyroCorrect gyroCorrectMove;
  private BaseLineFollowerSensor sensor;
  private final double kMinimumLineAngle = Math.toRadians(10);
  private final double kForwardSpeed = .1;
  private final double kRotateAmount = .1; // all constants are currently placeholders

  public LineFollowerController(MoveDrivetrainGyroCorrect theGyroCorrectMove,
      BaseLineFollowerSensor lineFollowerSensorArray) {
    gyroCorrectMove = theGyroCorrectMove;
    sensor = lineFollowerSensorArray;

  }

  public void run() {
    LineFollowArraySensorReading v = sensor.getSensorReading();
    if (v.lineFound = true) {
      // System.out.println("I FOUND A LINE!! :D");
      if (v.lineAngle <= -kMinimumLineAngle) {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, kRotateAmount, false, false);
      } else if (v.lineAngle >= kMinimumLineAngle) {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, -kRotateAmount, false, false);
      } else {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, 0, false, false);
      }
    } else {
      System.out.println("Line Not Found! :c");
      gyroCorrectMove.stop();
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
    return false;
  }

  public void enable(double setpoint) {

  }

}