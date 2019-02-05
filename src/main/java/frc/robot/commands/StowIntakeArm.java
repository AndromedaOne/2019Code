package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.IntakePIDController;
import frc.robot.subsystems.intake.IntakeArmPositionsEnum;

public class StowIntakeArm extends Command {

  private IntakePIDController intakePositionsPID = IntakePIDController.getInstance();

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
    intakePositionsPID.setSetpoint(Double.NEGATIVE_INFINITY);
    intakePositionsPID.enable();
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return !intakePositionsPID.isEnabled();

  }

  @Override
  protected void end() {
    if (!intakePositionsPID.isEnabled()) {
      Robot.intake.setCurrentIntakeArmPosition(IntakeArmPositionsEnum.STOWED);
    }
    intakePositionsPID.disable();
  }

  @Override
  protected void interrupted() {
    end();
  }
}