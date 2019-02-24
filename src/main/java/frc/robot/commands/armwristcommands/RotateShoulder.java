package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;
import frc.robot.groupcommands.armwristcommands.ArmPositions;

public class RotateShoulder extends Command {

  private double encDegrees;
  ShoulderPIDController sPidController;
  private static double shoulderDirectionFactor = 1;
  private ArmPositions armposition;
  private boolean usingArmPositionEnumFlag = false;
  private static boolean overrideAndFinishCommand = false;
  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam){
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  public RotateShoulder(double angle) {
    requires(Robot.extendableArmAndWrist);
    encDegrees = angle;
    sPidController = ShoulderPIDController.getInstance();
  }
  public RotateShoulder(ArmPositions armpositionParam) {
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
            encDegrees = ArmPositionValues.LOWROCKETSHOULDERANGLEFORCARGO.get();
          }else {
            encDegrees = ArmPositionValues.LOWROCKETSHOULDERANGLEFORHATCH.get();
          }
          break;
        case MIDDLEROCKETGAMEPIECE:
          if(DriveClawMotorsSafely.hasBall){
            encDegrees = ArmPositionValues.MIDDLEROCKETSHOULDERANGLEFORCARGO.get();
          }else {
            encDegrees = ArmPositionValues.MIDDLEROCKETSHOULDERANGLEFORHATCH.get();
          }
          break;
        case CARGOLOADINGSTATIONANDCARGOSHIP:
          if(DriveClawMotorsSafely.hasBall){
            encDegrees = ArmPositionValues.CARGOSHIPSHOULDERANGLE.get();
          }else {
            encDegrees = ArmPositionValues.CARGOLOADINGSTATIONSHOULDERANGLE.get();
          }
          break;
        case HIGHROCKETGAMEPIECE:
          if(DriveClawMotorsSafely.hasBall){
            encDegrees = ArmPositionValues.HIGHROCKETSHOULDERANGLEFORCARGO.get();
          }else {
            encDegrees = ArmPositionValues.HIGHROCKETSHOULDERANGLEFORHATCH.get();
          }
          break;
      }
      encDegrees *=shoulderDirectionFactor;
    }
    sPidController.setSetpoint(encDegrees);
    sPidController.enable();
  }

  protected void execute() {

  }

  protected void interrupted() {
    end();
  }

  protected void end() {
  }

  @Override
  protected boolean isFinished() {
    return overrideAndFinishCommand || sPidController.onTarget();
  }

  public static void setShoulderDirectionFactor(double shoulderDirectionFactorParam) {
    shoulderDirectionFactor = shoulderDirectionFactorParam;
  }
}