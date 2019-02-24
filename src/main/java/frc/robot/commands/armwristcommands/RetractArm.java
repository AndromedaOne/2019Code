package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.ExtendableArmPIDController;
import frc.robot.groupcommands.armwristcommands.ArmPositions;

public class RetractArm extends Command {

  private double inchesExtension;
  ArmPositions armposition;
  private boolean usingArmPositionEnumFlag = false;
  private static boolean wristTuckExtensionToHigh = false;
  private static boolean overrideAndFinishCommand = false;
  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam){
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  public RetractArm(double inchesExtensionParam) {
    inchesExtension = inchesExtensionParam;
    requires(Robot.extendableArmAndWrist);
  }

  public RetractArm(ArmPositions armpositionParam) {
    armposition = armpositionParam;
    usingArmPositionEnumFlag = true;
  }

  @Override
  protected void initialize() {
    overrideAndFinishCommand = false;
    if(usingArmPositionEnumFlag) {
      switch(armposition){
        case LOWROCKETGAMEPIECE:
          if(DriveClawMotorsSafely.hasBall){
            inchesExtension = ArmPositionValues.LOWROCKETEXTENSIONFORCARGO.get();
          }else {
            inchesExtension = ArmPositionValues.LOWROCKETEXTENSIONFORHATCH.get();
          }
          break;
        case MIDDLEROCKETGAMEPIECE:
          if(DriveClawMotorsSafely.hasBall){
            inchesExtension = ArmPositionValues.MIDDLEROCKETEXTENSIONFORCARGO.get();
          }else {
            inchesExtension = ArmPositionValues.MIDDLEROCKETEXTENSIONFORHATCH.get();
          }
          break;
        case CARGOLOADINGSTATIONANDCARGOSHIP:
          if(DriveClawMotorsSafely.hasBall){
            inchesExtension = ArmPositionValues.CARGOSHIPEXTENSION.get();
          }else {
            inchesExtension = ArmPositionValues.CARGOLOADINGSTATIONEXTENSION.get();
          }
          break;
        case HIGHROCKETGAMEPIECE:
          if(DriveClawMotorsSafely.hasBall){
            inchesExtension = ArmPositionValues.HIGHROCKETEXTENSIONFORCARGO.get();
          }else {
            inchesExtension = ArmPositionValues.HIGHROCKETEXTENSIONFORHATCH.get();
          }
          break;
        case STOWEDFORROTATINGARM:
          if(wristTuckExtensionToHigh) {
            inchesExtension = ArmPositionValues.EXTENSIONSTOWEDHIGHFORROTATINGINCHES.get();
            wristTuckExtensionToHigh = false;
          }else {
            inchesExtension = ArmPositionValues.EXTENSIONSTOWEDFORROTATINGINCHES.get();
          }
      }
    }
    ExtendableArmPIDController.getInstance().setSetpoint(inchesExtension);
    ExtendableArmPIDController.getInstance().enable();
  }

  protected void execute() {

  }

  @Override
  protected void interrupted() {
    end();
  }

  protected void end() {
  }

  @Override
  protected boolean isFinished() {
    return overrideAndFinishCommand || ExtendableArmPIDController.getInstance().onTarget();
  }

  public static void setWristTuckExtensionToHigh(boolean wristTuckExtensionToHighParam) {
    if (wristTuckExtensionToHighParam) {
      wristTuckExtensionToHigh = true;
    }else {
      wristTuckExtensionToHigh = false;
    }
  }
}