package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.linefollowersensor.BaseLineFollowerSensor;
import frc.robot.sensors.linefollowersensor.LineFollowArraySensorReading;

public class LineFollowerController {
  private MoveDrivetrainGyroCorrect gyroCorrectMove;
  private BaseLineFollowerSensor sensor;
  private final double kMinimumLineAngle = Math.toRadians(10);
  private final double kForwardSpeed = .39;
  private final double kRotateAmount = .6; // all constants are currently placeholders

  public LineFollowerController(MoveDrivetrainGyroCorrect theGyroCorrectMove,
      BaseLineFollowerSensor lineFollowerSensorArray) {
    gyroCorrectMove = theGyroCorrectMove;
    sensor = lineFollowerSensorArray;

  }

  private int debugCounter = 9;

  public void run() {
    LineFollowArraySensorReading v = sensor.getSensorReading();
    if (v.lineFound = true) {
      return;
    }
    debugCounter++;
    if (debugCounter == 10) {
      v = sensor.getSensorReading();
      debugCounter = 0;
    }
    SmartDashboard.putBoolean("IsLineFound", v.lineFound);
    SmartDashboard.putNumber("Angle", v.lineAngle);
    if (v.lineFound) {
      // System.out.println("I FOUND A LINE!! :D");
      if (v.lineAngle <= -kMinimumLineAngle) {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, kRotateAmount, false, false);
      } else if (v.lineAngle >= kMinimumLineAngle) {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, -kRotateAmount, false, false);
      } else {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, 0, false, false);
      }
    } else {
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