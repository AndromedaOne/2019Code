package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.ExtendableArmPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.WristPIDController;
import frc.robot.subsystems.extendablearmandwrist.ExtendableArmAndWrist;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.ButtonsEnumerated;
import frc.robot.utilities.EnumeratedRawAxis;

public class TeleopArm extends Command {

  Joystick armController = Robot.armController;
  ExtendableArmAndWrist extendableArmAndWrist = Robot.extendableArmAndWrist;
  final double sinPi4 = Math.sin(Math.PI / 4);
  final double cosPi4 = Math.cos(Math.PI / 4);
  private final double WRISTSTICKVALUETODEGREES = 8;
  private final double SHOULDERSTICKVALUETODEGREES = 15
  ;
  private final double EXTENSIONSTICKVALUETOINCHES = 6;

  public TeleopArm() {
    requires(Robot.extendableArmAndWrist);
    System.out.println("Initializing teleop arm control");
  }


  @Override
  protected void execute() {
    double extensionValue = EnumeratedRawAxis.LEFTSTICKHORIZONTAL.getRawAxis(armController);
    double wristRotateValue = EnumeratedRawAxis.RIGHTSTICKVERTICAL.getRawAxis(armController);

    wristRotateValue = -wristRotateValue;
    extensionValue = -extensionValue;
    double shoulderRotateValue = -EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(armController);
    if (Math.abs(shoulderRotateValue) < 0.01) {
      shoulderRotateValue = 0.0;
    }
    if (ButtonsEnumerated.isPressed(ButtonsEnumerated.STARTBUTTON, Robot.operatorController)) {
      extensionValue = 0.25;
    }
    ArmPosition currentArmPosition = Robot.getCurrentArmPosition();

    if(Math.abs(wristRotateValue) >= 0.2){
      
      double wristSetpoint = currentArmPosition.getWristAngle() + wristRotateValue * WRISTSTICKVALUETODEGREES;
      System.out.println("wristSetpoint: " + wristSetpoint);
      WristPIDController.getInstance().setSetpoint(wristSetpoint);
    }

    if(Math.abs(shoulderRotateValue) >= 0.2){
      double shoulderSetpoint = currentArmPosition.getShoulderAngle() + shoulderRotateValue * SHOULDERSTICKVALUETODEGREES;
      System.out.println("shoulderSetpoint: " + shoulderSetpoint);
      ShoulderPIDController.getInstance().setSetpoint(shoulderSetpoint);
    }

    if(Math.abs(extensionValue) >= 0.2){
      double extensionSetpoint = currentArmPosition.getArmRetraction() + extensionValue * EXTENSIONSTICKVALUETOINCHES;
      System.out.println("extensionSetpoint: " + extensionSetpoint);
      ExtendableArmPIDController.getInstance().setSetpoint(extensionSetpoint);
    }
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("TeleopArm");
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
    Trace.getInstance().logCommandStop("TeleopArm");
    // try {
    MoveArmAndWristSafely.stop();
    // } catch (ArmOutOfBoundsException e) {
    // System.out.println(e.getMessage());
    // }
  }

}