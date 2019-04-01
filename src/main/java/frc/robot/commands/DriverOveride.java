package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriverOveride extends Command {
  /*
   * DriverOveride stops any automatic movement using Gyro, Ultrasonic,
   * Linesensor, ect. Eric asked for it
   */

  public DriverOveride() {
    requires(Robot.driveTrain);
    System.out.println("DriverOveride");
  }

  protected void initialize() {

  }

  protected void execute() {

  }

  protected boolean isFinished() {
    return true;
  }

  protected void end() {

  }

  protected void interrupted() {
    end();
  }
}