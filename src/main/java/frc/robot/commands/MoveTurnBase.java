package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainRearUltrasonicPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.sensors.NavXGyroSensor;

public class MoveTurnBase extends Command {

  private DrivetrainRearUltrasonicPIDController frontUltrasonic = DrivetrainRearUltrasonicPIDController.getInstance();
  private DrivetrainEncoderPIDController encoderPID = DrivetrainEncoderPIDController.getInstance();
  private GyroPIDController gyroPID = GyroPIDController.getInstance();
  public MoveTurnBase() {
    requires(Robot.driveTrain);
  }

  /**
   * Takes a Setpoint in inches This is the distance you will be from the wall
   */
  public void moveUsingFrontUltrasonic(double setpoint) {
    System.out.println(" -- Moving With Front Ultrasonic -- ");
    frontUltrasonic.setRelativeSetpoint(setpoint);
    frontUltrasonic.enable();
  }

  /**
   * Takes a Setpoint in inches This is the distance you will be from the wall
   */
  public void moveUsingBackUltrasonic(double setpoint) {
    System.out.println(" -- Moving With Back Ultrasonic -- ");
    // backUltrasonic.setRelativeSetpoint(setpoint);
    // backUltrasonic.enable();
  }

  public void moveUsingEncoderPID(double setpoint) {
    System.out.println(" -- Moving With Encoder -- ");
    encoderPID.setRelativeSetpoint(setpoint);
    encoderPID.enable();
  }


  public void end() {
    gyroPID.disable();
    encoderPID.disable();
    frontUltrasonic.disable();
    // backUltrasonic.disable();
  }

  public void interrupt() {
    end();
  }

  public boolean onTarget() {
    boolean done = frontUltrasonic.onTarget() || encoderPID.onTarget() || gyroPID.onTarget(); // ||
                                                                                              // backUltrasonic.onTarget();
    return done;
  }

  @Override
  protected boolean isFinished() {
    return onTarget();
  }

}