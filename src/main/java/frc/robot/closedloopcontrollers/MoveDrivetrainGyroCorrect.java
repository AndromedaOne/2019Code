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
  private static final double kProportion = 0.0;// 0.05;

  public MoveDrivetrainGyroCorrect(NavXGyroSensor theNavX, DriveTrain theDriveTrain) {
    navX = theNavX;
    driveTrain = theDriveTrain;
  }

  public void setCurrentAngle() {
    savedAngle = navX.getZAngle();
  }

  public void stop() {
    moveUsingGyro(0, 0, false, false);
  }

  /**
   * This moves the robot and corrects for any rotation using the gyro
   * 
   * @param useDelay The delay will delay how long the gyro will wait to correct
   * after turning this allows the robot to drift naturally as you turn
   */
  public void moveUsingGyro(double forwardBackward, double rotation, boolean useDelay, boolean useSquaredInputs) {
    moveUsingGyro(forwardBackward, rotation, useDelay, useSquaredInputs, navX.getCompassHeading());
  }

  /**
   * This moves the robot and corrects for any rotation using the gyro
   * 
   * @param useDelay The delay will delay how long the gyro will wait to correct
   * after turning this allows the robot to drift naturally as you turn
   * @param heading Setting a heading will allow you to set what angle the robot
   * will correct to. This is useful in auto after the robot turns you can tell it
   * to correct to the heading it should have turn to.
   */
  public void moveUsingGyro(double forwardBackward, double rotation, boolean useDelay, boolean useSquaredInputs,
      double heading) {

    double robotDeltaAngle = navX.getCompassHeading() - heading;
    double robotAngle = navX.getZAngle() + robotDeltaAngle;

    /*
     * If we aren't rotating or our delay time is higher than our set Delay do not
     * use gyro correct This allows the robot to rotate naturally after we turn
     */
    if ((rotation != 0) || (useDelay && !(currentDelay > kDelay)) || (forwardBackward == 0.0)) {
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
      double correctionEquation = (savedAngle - robotAngle) * kProportion;
      newRotateValue = correctionEquation;
    } else {
      newRotateValue = rotation;
    }
    driveTrain.move(forwardBackward, newRotateValue, useSquaredInputs);
  }
}