package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.LineFollowerController;
import frc.robot.subsystems.drivetrain.DriveTrain.RobotGear;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.ButtonsEnumerated;

/**
 *
 */
public class MoveUsingFrontLineFollower extends Command {
  private LineFollowerController controller;
  private int counter = 0;
  private RobotGear savedGear = RobotGear.LOWGEAR;

  public MoveUsingFrontLineFollower() {
    controller = new LineFollowerController(Robot.gyroCorrectMove, Robot.frontLineSensor);
    requires(Robot.driveTrain);

  }

  @Override
  protected void initialize() {
    System.out.println("Running MUFLF");
    Trace.getInstance().logCommandStart("CallLineFollowerController");
    savedGear = Robot.driveTrain.getGear();
    controller.initialize();
  }

  @Override
  protected void execute() {
    SmartDashboard.putNumber("Counter", counter++);
    controller.run();
  }

  @Override
  protected boolean isFinished() {
    return controller.isDone()
        || !ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTSTICKBUTTON, Robot.driveController);
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("CallLineFollowerController");
    Robot.driveTrain.setGear(savedGear);
    controller.stop();
  }

  @Override
  protected void interrupted() {
  }
}