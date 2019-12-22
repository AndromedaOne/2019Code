package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.sensors.NavXGyroSensor;
import frc.robot.subsystems.drivetrain.DriveTrain;
import frc.robot.subsystems.drivetrain.DriveTrain.RobotGear;
import frc.robot.telemetries.Trace;

public class TurnToCompassHeading extends Command {
  private double heading = 0;
  private GyroPIDController gyroPID = GyroPIDController.getInstance();
  private RobotGear savedGear = RobotGear.LOWGEAR;

  public TurnToCompassHeading(double theHeading) {
    heading = theHeading;
    requires(Robot.driveTrain);
    System.out.println("Moving to heading " + theHeading + " using the NavX");
  }

  protected void initialize() {
    Trace.getInstance().logCommandStart("TurnToCompassHeading");
    savedGear = Robot.driveTrain.getGear();
    Robot.driveTrain.setGear(DriveTrain.RobotGear.LOWGEAR);
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

    System.out.println(" - Turn to Compass Heading  - ");
    System.out.println("Heading: " + heading);
    System.out.println("Delta Angle: " + deltaAngle);
    System.out.println("SetPoint: " + setPoint);
    System.out.println("Current Heading: " + NavXGyroSensor.getInstance().getCompassHeading());
    System.out.println("Current Z Angle: " + NavXGyroSensor.getInstance().getZAngle());
    gyroPID.getPIDMultiton().setSetpoint(setPoint);
    gyroPID.getPIDMultiton().enable();
  }

  @Override
  protected boolean isFinished() {
    return gyroPID.getPIDMultiton().onTarget();
  }

  protected void end() {
    System.out.println(" - Gyro PID Finished - ");
    System.out.println("Current Compass Heading: " + NavXGyroSensor.getInstance().getCompassHeading());
    Trace.getInstance().logCommandStop("TurnToCompassHeading");
    Robot.driveTrain.setGear(savedGear);
    gyroPID.getPIDMultiton().reset();
  }

}