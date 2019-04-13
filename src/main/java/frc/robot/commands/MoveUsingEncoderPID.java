package frc.robot.commands;

import com.typesafe.config.Config;

import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.telemetries.Trace;

public class MoveUsingEncoderPID extends MoveTurnBase {

  private Config config = Robot.getConfig().getConfig("subsystems");
  private double setpoint = 0;

  /**
   * Takes a Setpoint in inches
   */
  public MoveUsingEncoderPID(double setpointInInches) {
    setpoint = setpointInInches;
    System.out.println("Moving to " + setpointInInches + " in inches");
  }

  public void initialize() {
    Trace.getInstance().logCommandStart("MoveUsingEncoderPID");
    DrivetrainEncoderPIDController.getInstance().setSetpoint(setpoint);
    DrivetrainEncoderPIDController.getInstance().enable();
  }

  public boolean isFinished(){
    return DrivetrainEncoderPIDController.getInstance().onTarget(); 
  }
  public void end() {
    System.out.println("Error:" + ((Robot.drivetrainLeftRearEncoder.getDistanceTicks() / DrivetrainEncoderPIDController.getInstance().TICKSTOINCHESRATIO) - setpoint));
    DrivetrainEncoderPIDController.getInstance().disable();
    DrivetrainEncoderPIDController.getInstance().reset();
  }
}
