package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;
import frc.robot.utilities.EnumeratedRawAxis;

/**
 *
 */
public class TeleOpDrive extends Command {

  private int m_slowmodedelaycounter;
  private boolean slowMoEnabled;
  private double mod;
  private boolean shifterHigh = false;
  private int shifterDelayCounter = 0;

  public TeleOpDrive() {
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_slowmodedelaycounter = 0;

    mod = 1;
    slowMoEnabled = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Joystick driveController = Robot.driveController;

    if (ButtonsEnumerated.isPressed(ButtonsEnumerated.BACKBUTTON, driveController) && shifterDelayCounter >= 24) {
      shifterDelayCounter = 0;
      if (shifterHigh) {
        Robot.driveTrain.shiftToLowGear();
        shifterHigh = false;
      } else {
        Robot.driveTrain.shiftToHighGear();
        shifterHigh = true;
      }
    }

    shifterDelayCounter++;
    double forwardBackwardStickValue = -EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(driveController);

    double rotateStickValue = EnumeratedRawAxis.RIGHTSTICKHORIZONTAL.getRawAxis(driveController);
    if (shifterDelayCounter >= 24) {
      Robot.driveTrain.move(forwardBackwardStickValue * mod, -rotateStickValue * mod);
    }

    // 48 on slowmodedelaycounter is about a second
    if (m_slowmodedelaycounter > 12 && ButtonsEnumerated.LEFTBUMPERBUTTON.isPressed(OI.getInstance().getDriveStick())) {
      m_slowmodedelaycounter = 0;
      if (!slowMoEnabled) {
        mod = 0.6;
        slowMoEnabled = true;
        System.out.println("Slow Mode IS enabled!");
      } else {
        mod = 1;
        slowMoEnabled = false;
        System.out.println("SLOW MODE HAS ENDED!");
      }
    }
    m_slowmodedelaycounter++;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}