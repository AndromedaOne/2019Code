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
  }

  protected void initialize() {
    double deltaAngle = NavXGyroSensor.getInstance().getCompassHeading() - heading;
    double setPoint = deltaAngle + NavXGyroSensor.getInstance().getZAngle();
    System.out.println(" - Turn to Compass Heading  - ");
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