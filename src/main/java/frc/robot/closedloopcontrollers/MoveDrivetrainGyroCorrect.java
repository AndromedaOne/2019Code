package frc.robot.closedloopcontrollers;

import frc.robot.sensors.NavXGyroSensor;
import frc.robot.subsystems.drivetrain.DriveTrain;

public class MoveDrivetrainGyroCorrect {

  private NavXGyroSensor navX;
  private DriveTrain driveTrain;
  private double savedAngle = 0;
  private double newRotateValue = 0;
  private boolean gyroCorrect = false;
  private double currentDelay = 0;
  private static final double kDelay = 25;
  private static final double kProportion = 0.05;

  public MoveDrivetrainGyroCorrect(NavXGyroSensor theNavX, DriveTrain theDriveTrain) {
    navX = theNavX;
    driveTrain = theDriveTrain;
  }

  public void setCurrentAngle() {
      savedAngle = navX.getZAngle();
  }

  public void stop() {
    System.out.println(" - Stopping - ");
    moveUsingGyro(0, 0, false, false);
  }

  public void moveUsingGyro(double forwardBackward, double rotation, boolean useDelay, boolean useSquaredInputs) {
    moveUsingGyro(forwardBackward, rotation, useDelay, useSquaredInputs, navX.getCompassHeading());
  }

  public void moveUsingGyro(double forwardBackward, double rotation, boolean useDelay, boolean useSquaredInputs,
      double heading) {

    double robotDeltaAngle = navX.getCompassHeading() - heading;
    double robotAngle = navX.getZAngle() + robotDeltaAngle;

    if ((rotation != 0) || (useDelay && !(currentDelay > kDelay))) {
        gyroCorrect = false;
        savedAngle = robotAngle;
        currentDelay++;
    } else {
      gyroCorrect = true;
    }

    if (rotation != 0) {
        currentDelay = 0;
    }

    if (gyroCorrect) {
      double correctionEquation = (robotAngle - savedAngle) * kProportion;
      newRotateValue = correctionEquation;
    } else {
      newRotateValue = rotation;
    }
    driveTrain.move(forwardBackward, newRotateValue, useSquaredInputs);
  }
}