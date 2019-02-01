package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.LineFollowerSensorArray;
import frc.robot.subsystems.drivetrain.DriveTrain;

public class LineFollowerController implements ClosedLoopControllerBase {
  private DriveTrain driveTrain;
  private LineFollowerSensorArray sensor;
  private final double kMinimumLineAngle = Math.toRadians(10);
  private final double kForwardSpeed = .39;
  private final double kRotateAmount = .6; // all constants are currently placeholders

  public LineFollowerController(DriveTrain driveTrain1, LineFollowerSensorArray lineFollowerSensorArray) {
    driveTrain = driveTrain1;
    sensor = lineFollowerSensorArray;

  }

  private int debugCounter = 9;
  private LineFollowerSensorArray.LineFollowArraySensorReading v;

  public void run() {
    LineFollowerSensorArray.LineFollowArraySensorReading v = sensor.getSensorReading();
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
        driveTrain.move(kForwardSpeed, kRotateAmount);
      } else if (v.lineAngle >= kMinimumLineAngle) {
        driveTrain.move(kForwardSpeed, -kRotateAmount);
      } else {
        driveTrain.move(kForwardSpeed, 0);
      }
    } else {
      driveTrain.move(0, 0);
    }

  }

  public void reset() {

  }

  public void stop() {
    driveTrain.stop();

  }

  public void initialize() {

  }

  public boolean isDone() {
    return false;
  }

}