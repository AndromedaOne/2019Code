package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveIntakeSafely;
import frc.robot.subsystems.intake.IntakeArmPositionsEnum;
import frc.robot.telemetries.Trace;

public class StowIntakeArm extends Command {

  private double speed = 0.8;
  private boolean isFinishedFlag = false;

  /**
   * Construct an intake control command to make the intake arm go up or down
   * 
   * @param directionToMove - MoveIntakeArmDirection.UP or
   * MoveIntakeArmDirection.DOWN
   */
  public StowIntakeArm() {
    requires(Robot.intake);
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("StowIntakeArm");
    System.out.println("Stow intake command");
  }

  @Override
  protected void execute() {
    MoveIntakeSafely.moveIntake(speed);

  }

  @Override
  protected boolean isFinished() {
    return Robot.intakeStowedSwitch.isAtLimit();
  }

  @Override
  protected void end() {
    Robot.intake.setCurrentIntakeArmPosition(IntakeArmPositionsEnum.STOWED);
    Robot.intake.moveIntakeArm(0);
    Trace.getInstance().logCommandStop("StowIntakeArm");
  }

  @Override
  protected void interrupted() {
    end();
  }
}