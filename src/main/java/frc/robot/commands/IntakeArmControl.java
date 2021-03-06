package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.IntakePIDController;
import frc.robot.commands.stilts.RaiseAll;
import frc.robot.subsystems.intake.IntakeArmPositionsEnum;
import frc.robot.telemetries.Trace;

public class IntakeArmControl extends Command {

  public enum MoveIntakeArmDirection {
    UP, DOWN
  };

  private IntakePIDController intakePositionsPID = IntakePIDController.getInstance();

  private MoveIntakeArmDirection directionToMove = MoveIntakeArmDirection.UP;
  private IntakeArmPositionsEnum nextIntakePosition = IntakeArmPositionsEnum.UNKNOWN;

  private int counter = 0;
  private boolean isFinished = false;

  /**
   * Construct an intake control command to make the intake arm go up or down
   * 
   * @param directionToMove - MoveIntakeArmDirection.UP or
   * MoveIntakeArmDirection.DOWN
   */
  public IntakeArmControl(MoveIntakeArmDirection directionToMove) {
    // super(1.0);
    this.directionToMove = directionToMove;
    requires(Robot.intake);
  }

  protected void initialize() {
    isFinished = false;
    Trace.getInstance().logCommandStart("IntakeArmControl");
    counter = 0;
    switch (directionToMove) {
    case UP:
      System.out.println("Setting to move up");
      setUpSetPoint();
      break;

    case DOWN:
      System.out.println("Setting to move down");
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
    intakePositionsPID.pidMultiton.setPIDTerms(7, 0, 0.40);
    switch (Robot.intake.getCurrentIntakeArmPosition()) {
    case STOWED:
      intakePositionsPID.setSetpoint(Robot.intake.getCargoSetpoint());
      nextIntakePosition = IntakeArmPositionsEnum.CARGOHEIGHT;
      System.out.println("We are stowed and trying to go to Cargoheight");
      break;
    case CARGOHEIGHT:
      if (RaiseAll.isExtended) {
        System.out.println("RaiseAll.isExtended: " + RaiseAll.isExtended);
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
    counter++;
    if (counter >= 70) {
      isFinished = true;
    }
  }

  @Override
  protected boolean isFinished() {
    return intakePositionsPID.onTarget() || !intakePositionsPID.isEnabled() || isFinished;
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("IntakeArmControl");
    intakePositionsPID.disable();
    System.out.println("Next Intake Position: " + nextIntakePosition);
    Robot.intake.setCurrentIntakeArmPosition(nextIntakePosition);
  }

  @Override
  protected void interrupted() {
    end();
  }
}