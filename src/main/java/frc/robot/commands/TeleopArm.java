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
    double extensionValue = EnumeratedRawAxis.LEFTSTICKHORIZONTAL.getRawAxis(armController);
    double wristRotateValue = EnumeratedRawAxis.RIGHTSTICKVERTICAL.getRawAxis(armController);

    wristRotateValue = wristRotateValue * 0.5;
    extensionValue = -extensionValue;
    double shoulderRotateValue = EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(armController);
    if (Math.abs(shoulderRotateValue) < 0.01) {
      shoulderRotateValue = 0.0;
    }
    // System.out.println("extensionValue: " + extensionValue);
    //try {
      // System.out.println("extensionValue: " + extensionValue);
      MoveArmAndWristSafely.setTeleopExtensionPower(extensionValue);
      MoveArmAndWristSafely.setTeleopShoulderPower(shoulderRotateValue);
      MoveArmAndWristSafely.setTeleopWristPower(wristRotateValue);
    //} catch (ArmOutOfBoundsException e) {
      // System.out.println(e.getMessage());
    //}

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
    //try {
      MoveArmAndWristSafely.stop();
    //} catch (ArmOutOfBoundsException e) {
      //System.out.println(e.getMessage());
    //}
  }

}