package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.exceptions.ArmOutOfBoundsException;
import frc.robot.subsystems.extendablearmandwrist.ExtendableArmAndWrist;
import frc.robot.utilities.EnumeratedRawAxis;

public class TeleopArm extends Command {

  Joystick armController = Robot.armController;
  ExtendableArmAndWrist extendableArmAndWrist = Robot.extendableArmAndWrist;
  final double sinPi4 = Math.sin(Math.PI / 4);
  final double cosPi4 = Math.cos(Math.PI / 4);

  public TeleopArm() {
    requires(Robot.extendableArmAndWrist);
  }

  @Override
  protected void execute() {
    double armWristValue = EnumeratedRawAxis.LEFTSTICKHORIZONTAL.getRawAxis(armController);
    double rotateValue = EnumeratedRawAxis.RIGHTSTICKVERTICAL.getRawAxis(armController);

    double actualArmWristVal = rotateValue * 0.5;
    double extensionValue = -armWristValue;
    double shoulderRotateValue = EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(armController);
    if (Math.abs(shoulderRotateValue) < 0.01) {
      shoulderRotateValue = 0.0;
    }
    // System.out.println("extensionValue: " + extensionValue);
    try {
      MoveArmAndWristSafely.move(extensionValue, actualArmWristVal, shoulderRotateValue, MoveArmAndWristSafely.DontUsePIDHold.HOLDALL);
    } catch (ArmOutOfBoundsException e) {
      // System.out.println(e.getMessage());
    }

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void interrupted() {
    end();
  }

  @Override
  protected void end() {
    try {
      MoveArmAndWristSafely.move(0, 0, 0, MoveArmAndWristSafely.DontUsePIDHold.HOLDALL);
    } catch (ArmOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
  }

}