package frc.robot.commands;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.telemetries.Trace;

public class MoveUsingEncoderPID extends Command {

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
    DrivetrainEncoderPIDController.getInstance().getPIDMultiton()
        .setSetpoint(Robot.drivetrainLeftRearEncoder.getDistanceTicks()
            / DrivetrainEncoderPIDController.getInstance().TICKSTOINCHESRATIO + setpoint);
    System.out.println("Current Pos: " + Robot.drivetrainLeftRearEncoder.getDistanceTicks()
        / DrivetrainEncoderPIDController.getInstance().TICKSTOINCHESRATIO);
    System.out.println("Setpoint: " + Robot.drivetrainLeftRearEncoder.getDistanceTicks()
        / DrivetrainEncoderPIDController.getInstance().TICKSTOINCHESRATIO + setpoint);
    DrivetrainEncoderPIDController.getInstance().getPIDMultiton().enable();
  }

  public boolean isFinished() {
    return DrivetrainEncoderPIDController.getInstance().getPIDMultiton().onTarget();
  }

  public void end() {
    System.out.println("Error:" + ((Robot.drivetrainLeftRearEncoder.getDistanceTicks()
        / DrivetrainEncoderPIDController.getInstance().TICKSTOINCHESRATIO) - setpoint));
    DrivetrainEncoderPIDController.getInstance().getPIDMultiton().disable();
    DrivetrainEncoderPIDController.getInstance().getPIDMultiton().reset();
  }
}
