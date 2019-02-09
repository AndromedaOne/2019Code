package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveIntakeSafely;
import frc.robot.sensors.limitswitchsensor.LimitSwitchSensor.IsAtLimitException;
import frc.robot.subsystems.intake.IntakeArmPositionsEnum;

public class StowIntakeArm extends Command {

  private double speed = 0.5;
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
  }

  @Override
  protected void execute() {
    try {
      // This will throw an exception when the limitswitch is hit and resets the
      // angle sensor
      MoveIntakeSafely.moveIntake(speed);
    } catch (IsAtLimitException e) {
      isFinishedFlag = true;
    }
  }

  @Override
  protected boolean isFinished() {
    return isFinishedFlag;
  }

  @Override
  protected void end() {
    Robot.intake.setCurrentIntakeArmPosition(IntakeArmPositionsEnum.STOWED);
  }

  @Override
  protected void interrupted() {
    end();
  }
}