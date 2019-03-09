package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.sensors.NavXGyroSensor;

public class TurnToCompassHeading extends Command {

  private double heading = 0;
  private static GyroPIDController gyroPID = GyroPIDController.getInstance();

  public TurnToCompassHeading(double theHeading) {
    requires(Robot.driveTrain);
    heading = theHeading;
    System.out.println("Moving to heading " + theHeading + " using the NavX");
  }

  protected void initialize() {
    double deltaAngle = heading - NavXGyroSensor.getInstance().getCompassHeading();
    System.out.println("Raw Delta Angle: " + deltaAngle);
    // This corrects turn that are over 180
    if (deltaAngle > 180) {
      deltaAngle = -(360 - deltaAngle);
      System.out.println("Angle corrected for shortest method, New Delta: " + deltaAngle);
    } else if (deltaAngle < -180) {
      deltaAngle = 360 + deltaAngle;
      System.out.println("Angle corrected for shortest method, New Delta: " + deltaAngle);
    }

    double setPoint = deltaAngle + NavXGyroSensor.getInstance().getZAngle();

    if (Math.abs(deltaAngle) < gyroPID.getAbsoluteTolerance()) {
      System.out.println("Delta is to small, not moving!");
      setPoint = NavXGyroSensor.getInstance().getZAngle();
    }

    System.out.println(" - Turn to Compass Heading  - ");
    System.out.println("Tolerance: " + gyroPID.getAbsoluteTolerance());
    System.out.println("Heading: " + heading);
    System.out.println("Delta Angle: " + deltaAngle);
    System.out.println("SetPoint: " + setPoint);
    System.out.println("Current Heading: " + NavXGyroSensor.getInstance().getCompassHeading());
    System.out.println("Current Z Angle: " + NavXGyroSensor.getInstance().getZAngle());
    gyroPID.setSetpoint(setPoint);
    gyroPID.enable();
  }

  protected void end() {
    System.out.println("Turn to Compass Heading Finished");
    System.out.println("Ending Heading: " + NavXGyroSensor.getInstance().getCompassHeading());
    System.out.println("Ending Z Angle: " + NavXGyroSensor.getInstance().getZAngle());
    gyroPID.reset();
  }

  @Override
  protected boolean isFinished() {
    return gyroPID.onTarget();
  }

}