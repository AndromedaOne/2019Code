package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.linefollowersensor.LineFollowerSensorBase;

public class LineFollowerController {
  private MoveDrivetrainGyroCorrect gyroCorrectMove;
  private LineFollowerSensorBase sensor;
  private final double kMinimumLineAngle = Math.toRadians(10);
  private final double kForwardSpeed = .39;
  private final double kRotateAmount = .6; // all constants are currently placeholders

  public LineFollowerController(MoveDrivetrainGyroCorrect theGyroCorrectMove,
      LineFollowerSensorBase lineFollowerSensorArray) {
    gyroCorrectMove = theGyroCorrectMove;
    sensor = lineFollowerSensorArray;

  }

  private int debugCounter = 9;

  public void run() {
    if (sensor.lineFound()) {
      return;
    }
    debugCounter++;
    if (debugCounter == 10) {
      //v = sensor.getSensorReading();
      debugCounter = 0;
    }
    //SmartDashboard.putBoolean("IsLineFound", sensor.lineFound());
    //SmartDashboard.putNumber("Angle", v.lineAngle);
    if (sensor.lineFound()) {
      // System.out.println("I FOUND A LINE!! :D");
      if (sensor.lineAngle() <= -kMinimumLineAngle) {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, kRotateAmount, false, false);
      } else if (sensor.lineAngle() >= kMinimumLineAngle) {
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