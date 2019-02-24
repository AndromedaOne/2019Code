package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.WristPIDController;
import frc.robot.groupcommands.armwristcommands.ArmPositions;

public class RotateWrist extends Command {

  private double encDegrees;
  private static double wristDirectionFactor = 0;
  ArmPositions armposition;
  private boolean usingArmPositionEnumFlag = false;
  private static boolean overrideAndFinishCommand = false;
  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam){
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  public RotateWrist(double angle) {
    encDegrees = angle;
    requires(Robot.extendableArmAndWrist);
  }

  public RotateWrist(ArmPositions armpositionParam) {
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
            encDegrees = ArmPositionValues.LOWROCKETWRISTANGLEFORCARGO.get();
          }else {
            encDegrees = ArmPositionValues.LOWROCKETWRISTANGLEFORHATCH.get();
          }
          break;
        case MIDDLEROCKETGAMEPIECE:
          if(DriveClawMotorsSafely.hasBall){
            encDegrees = ArmPositionValues.MIDDLEROCKETWRISTANGLEFORCARGO.get();
          }else {
            encDegrees = ArmPositionValues.MIDDLEROCKETWRISTANGLEFORHATCH.get();
          }
          break;
        case CARGOLOADINGSTATIONANDCARGOSHIP:
          if(DriveClawMotorsSafely.hasBall){
            encDegrees = ArmPositionValues.CARGOSHIPWRISTANGLE.get();
          }else {
            encDegrees = ArmPositionValues.CARGOLOADINGSTATIONWRISTANGLE.get();
          }
          break;
        case HIGHROCKETGAMEPIECE:
          if(DriveClawMotorsSafely.hasBall){
            encDegrees = ArmPositionValues.HIGHROCKETWRISTANGLEFORCARGO.get();
          }else {
            encDegrees = ArmPositionValues.HIGHROCKETWRISTANGLEFORHATCH.get();
          }
          break;
        case STOWEDFORROTATINGARM:
          
          encDegrees = ArmPositionValues.WRISTSTOWEDFORROTATINGDEGREES.get();
      }
      encDegrees *= wristDirectionFactor;
    }
    WristPIDController.getInstance().setSetpoint(encDegrees);
    WristPIDController.getInstance().enable();
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
    return overrideAndFinishCommand || WristPIDController.getInstance().isEnabled();
  }

  public static void setWristDirectionFactor(double wristDirectionFactorParam) {
    wristDirectionFactor = wristDirectionFactorParam;
  }
}