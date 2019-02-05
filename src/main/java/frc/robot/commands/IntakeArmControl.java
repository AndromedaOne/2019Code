package frc.robot.commands;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.IntakePIDController;
import frc.robot.subsystems.intake.IntakeArmPositionsEnum;

public class IntakeArmControl extends Command {

  public enum MoveIntakeArmDirection {
    UP, DOWN
  };

  private IntakePIDController intakePositionsPID = IntakePIDController.getInstance();

  private MoveIntakeArmDirection directionToMove;
  private IntakeArmPositionsEnum nextIntakePosition;

  private static double stowedPositionSetPoint;
  private static double cargoPositionSetPoint;
  private static double groundPositionSetPoint;
  static {
    Config conf = Robot.getConfig();
    if (conf.hasPath("subsystems.intake")) {
      Config intakeConf = conf.getConfig("subsystems.intake");
      stowedPositionSetPoint = 0;
      cargoPositionSetPoint = intakeConf.getDouble("CargoPositionSetpoint");
      groundPositionSetPoint = intakeConf.getDouble("GroundPositionSetPoint");
    } else {
      stowedPositionSetPoint = 0;
      cargoPositionSetPoint = 7;
      groundPositionSetPoint = 6;
    }
  }

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

  @Override
  protected void initialize() {
    switch (directionToMove) {
    case UP:
      setUpSetPoint();
      break;

    case DOWN:
      setDownSetpoint();
      break;
    }
    intakePositionsPID.enable();
  }

  /**
   * Tells intake to go up and does not try to go further when at Stowed
   */
  private void setUpSetPoint() {
    switch (Robot.intake.getCurrentIntakeArmPosition()) {
    case STOWED:
      intakePositionsPID.setRelativeSetpoint(0);
      nextIntakePosition = IntakeArmPositionsEnum.STOWED;
      break;
    case CARGOHEIGHT:
      intakePositionsPID.setRelativeSetpoint(-cargoPositionSetPoint);
      nextIntakePosition = IntakeArmPositionsEnum.STOWED;
      break;
    case GROUNDHEIGHT:
      intakePositionsPID.setRelativeSetpoint(cargoPositionSetPoint - groundPositionSetPoint);
      nextIntakePosition = IntakeArmPositionsEnum.CARGOHEIGHT;
      break;
    case UNKNOWN:
      // TODO: Don't move
      intakePositionsPID.setRelativeSetpoint(0);
      nextIntakePosition = IntakeArmPositionsEnum.UNKNOWN;
      break;
    }
  }

  /**
   * Tells intake to go down and does not try to go further when at Groundheight
   */
  private void setDownSetpoint() {
    switch (Robot.intake.getCurrentIntakeArmPosition()) {
    case STOWED:
      intakePositionsPID.setRelativeSetpoint(cargoPositionSetPoint);
      nextIntakePosition = IntakeArmPositionsEnum.CARGOHEIGHT;
      break;
    case CARGOHEIGHT:
      intakePositionsPID.setRelativeSetpoint(groundPositionSetPoint - cargoPositionSetPoint);
      nextIntakePosition = IntakeArmPositionsEnum.GROUNDHEIGHT;
      break;
    case GROUNDHEIGHT:
      intakePositionsPID.setRelativeSetpoint(0);
      nextIntakePosition = IntakeArmPositionsEnum.GROUNDHEIGHT;
      break;
    case UNKNOWN:
      // TODO: Don't move
      intakePositionsPID.setRelativeSetpoint(0);
      nextIntakePosition = IntakeArmPositionsEnum.UNKNOWN;
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
    if (intakePositionsPID.onTarget()) {
      Robot.intake.setCurrentIntakeArmPosition(nextIntakePosition);
    }
  }

  @Override
  protected void interrupted() {
    end();
  }
}