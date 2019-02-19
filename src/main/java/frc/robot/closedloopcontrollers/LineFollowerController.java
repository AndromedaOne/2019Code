package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.linefollowersensor.LineFollowArraySensorReading;
import frc.robot.sensors.linefollowersensor.LineFollowerSensorBase;

public class LineFollowerController {
  private MoveDrivetrainGyroCorrect gyroCorrectMove;
  private LineFollowerSensorBase sensor;
  private LineFollowArraySensorReading values;
  private final double kMinimumLineAngle = Math.toRadians(10);
  private final double kForwardSpeed = .39;
  private final double kRotateAmount = .6; // all constants are currently placeholders

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
      System.out.println("I FOUND A LINE!! :D");
      if (values.lineAngle <= -kMinimumLineAngle) {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, kRotateAmount, true, false);
      } else if (values.lineAngle >= kMinimumLineAngle) {
        gyroCorrectMove.moveUsingGyro(kForwardSpeed, -kRotateAmount, true, false);
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