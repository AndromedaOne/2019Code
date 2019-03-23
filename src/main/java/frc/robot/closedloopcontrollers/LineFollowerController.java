package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.sensors.linefollowersensor.LineFollowArraySensorReading;
import frc.robot.sensors.linefollowersensor.LineFollowerSensorBase;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class LineFollowerController {
  private MoveDrivetrainGyroCorrect gyroCorrectMove;
  private LineFollowerSensorBase sensor;
  private LineFollowArraySensorReading values;
  private final double kMinimumLineAngle = Math.toRadians(2); // appx. 0.035 Radians.
  private final double kForwardSpeed = .15;
  private final double kRotateScaleFactor = .5; // all constants are currently placeholders
  private final double kDistanceFromWall = 3; // Inches
  private int lineNotFoundCounter = 0;
  private double minDistanceToWall = 3;

  public LineFollowerController(MoveDrivetrainGyroCorrect theGyroCorrectMove,
      LineFollowerSensorBase lineFollowerSensorArray) {
    gyroCorrectMove = theGyroCorrectMove;
    sensor = lineFollowerSensorArray;
    if (Robot.getConfig().hasPath("sensors.lineFollowSensor.lineFollowSensor4905.minDistToWall")) {
      minDistanceToWall = Robot.getConfig().getDouble("sensors.lineFollowSensor.lineFollowSensor4905.minDistToWall");
      System.out.println("Setting Line Follower Controller to " + minDistanceToWall + "...");
    }

  }

  public void run() {
    double frontUltrasonicRange = Robot.drivetrainFrontUltrasonic.getDistanceInches();
    values = sensor.findLine();
    SmartDashboard.putBoolean("IsLineFound", values.lineFound);
    SmartDashboard.putNumber("Angle", values.lineAngle);
    if (values.lineFound && frontUltrasonicRange > kDistanceFromWall) {
      lineNotFoundCounter = 0;
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
    Trace.getInstance().addTrace(true, "LineFollowerController", new TracePair<>("UltrasonicRange", frontUltrasonicRange), 
    new TracePair<>("IsLineFound", values.lineFound), new TracePair<>("LineAngle", values.lineAngle),
    new TracePair<>("LineNotFoundCounter", lineNotFoundCounter));
    System.out.println("Front Ultrasonic: " + frontUltrasonicRange + ", Line Angle: " + values.lineAngle + ", Line not found Counter: " + lineNotFoundCounter);
  }

  public void reset() {

  }

  public void stop() {
    gyroCorrectMove.stop();

  }

  public void initialize() {

  }

  public boolean isDone() {
    if ((lineNotFoundCounter >= 10) || (minDistanceToWall >= Robot.drivetrainFrontUltrasonic.getDistanceInches())) {
      return true;
    } else {
      return false;
    }

  }

  public void enable(double setpoint) {

  }

}