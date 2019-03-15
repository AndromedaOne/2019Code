package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.IntakePIDController;
import frc.robot.commands.stilts.RaiseAll;
import frc.robot.subsystems.intake.IntakeArmPositionsEnum;

public class IntakeArmControl extends Command {

  public enum MoveIntakeArmDirection {
    UP, DOWN
  };

  private IntakePIDController intakePositionsPID = IntakePIDController.getInstance();

  private MoveIntakeArmDirection directionToMove;
  private IntakeArmPositionsEnum nextIntakePosition;

  /**
   * Construct an intake control command to make the intake arm go up or down
   * 
   * @param directionToMove - MoveIntakeArmDirection.UP or
   * MoveIntakeArmDirection.DOWN
   */
  public IntakeArmControl(MoveIntakeArmDirection directionToMove) {
    this.directionToMove = directionToMove;
    requires(Robot.intake);
  }

  protected void initialize() {
    System.out.println(directionToMove.toString());
    switch (directionToMove) {
    case UP:
      setUpSetPoint();
      break;

    case DOWN:
      setDownSetpoint();
      break;
    }
    System.out.println("Enabling Intake PID");
    intakePositionsPID.enable();
  }

  /**
   * Tells intake to go up and does not try to go further when at Stowed
   */
  private void setUpSetPoint() {
    intakePositionsPID.pidMultiton.setPIDTerms(5, 0, 0.3);
    switch (Robot.intake.getCurrentIntakeArmPosition()) {
    case STOWED:
      intakePositionsPID.setSetpoint(Robot.intake.getStowedSetpoint());
      nextIntakePosition = IntakeArmPositionsEnum.STOWED;
      System.out.println("We are in Stowed and trying to move to Stowed");
      break;
    case CARGOHEIGHT:
      intakePositionsPID.setSetpoint(Robot.intake.getStowedSetpoint());
      nextIntakePosition = IntakeArmPositionsEnum.STOWED;
      System.out.println("We are in Cargoheight and trying to move to Stowed");
      break;
    case GROUNDHEIGHT:
      intakePositionsPID.setSetpoint(Robot.intake.getGroundSetpoint());
      nextIntakePosition = IntakeArmPositionsEnum.GROUNDHEIGHT;
      System.out.println("We are at the Ground, but we're not moving");
      break;
    case UNKNOWN:
      // TODO: Don't move
      intakePositionsPID.setSetpoint(Robot.intake.getStowedSetpoint());
      nextIntakePosition = IntakeArmPositionsEnum.STOWED;
      System.out.println("We have no idea where we are and we're going to stowed");
      break;
    }
  }

  /**
   * Tells intake to go down and does not try to go further when at Groundheight
   */
  private void setDownSetpoint() {
    intakePositionsPID.pidMultiton.setPIDTerms(5, 0, 0.25);
    switch (Robot.intake.getCurrentIntakeArmPosition()) {
    case STOWED:
      intakePositionsPID.setSetpoint(Robot.intake.getCargoSetpoint());
      nextIntakePosition = IntakeArmPositionsEnum.CARGOHEIGHT;
      System.out.println("We are stowed and trying to go to Cargoheight");
      break;
    case CARGOHEIGHT:
      if (RaiseAll.isExtended) {
        intakePositionsPID.setSetpoint(Robot.intake.getGroundSetpoint());
        nextIntakePosition = IntakeArmPositionsEnum.GROUNDHEIGHT;
        System.out.println("We are at the cargoheight and trying to go to the ground");
      }
      break;
    case GROUNDHEIGHT:
      intakePositionsPID.setSetpoint(Robot.intake.getGroundSetpoint());
      nextIntakePosition = IntakeArmPositionsEnum.GROUNDHEIGHT;
      System.out.println("We are at the ground and trying to ground");
      break;
    case UNKNOWN:
      // TODO: Don't move
      intakePositionsPID.setSetpoint(Robot.intake.getStowedSetpoint());
      nextIntakePosition = IntakeArmPositionsEnum.STOWED;
      System.out.println("We don't know where we are and we're trying to stow.");
      break;
    }
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return intakePositionsPID.onTarget() || !intakePositionsPID.isEnabled();
  }

  @Override
  protected void end() {
    intakePositionsPID.disable();
    System.out.println("Next Intake Position: " + nextIntakePosition);
    Robot.intake.setCurrentIntakeArmPosition(nextIntakePosition);
  }

  @Override
  protected void interrupted() {
    end();
  }
}