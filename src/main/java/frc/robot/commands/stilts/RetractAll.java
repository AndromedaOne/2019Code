package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;


public class RetractAll extends Command {

  public void initialize() {
    System.out.println("Retracting All Legs");
    Robot.pneumaticStilts.retractFrontLegs();
    Robot.pneumaticStilts.retractRearLegs();
  }

  @Override
  protected boolean isFinished() {
    System.out.println("Done Retracting All Legs");
    return !ButtonsEnumerated.isPressed(ButtonsEnumerated.BACKBUTTON, OI.getInstance().getDriveStick());
  }

}