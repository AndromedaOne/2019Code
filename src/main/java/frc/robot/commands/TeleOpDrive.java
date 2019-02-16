package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

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

  private boolean slowMoEnabled;
  private double mod;
  private boolean shifterHigh = false;
  private int shifterDelayCounter = 0;
  private int delay = 4;
  private boolean shiftButtonPressed = false;
  private boolean slowModeButtonPressed = false;
  private double kSlowModeModifier = 0.6;

  public TeleOpDrive() {
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mod = 1;
    slowMoEnabled = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Joystick driveController = Robot.driveController;

    // This is the shifting code
    // This switches the motors coast and sets the move to zero
    // Then we shift the gears
    // Then wait a given time for the gears to shift
    // Then switch the motors back to break mode and reapply power
    if (ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON, driveController)
        && (shifterDelayCounter >= delay) && Robot.driveTrain.getShifterPresentFlag() && !shiftButtonPressed) {
      shifterDelayCounter = 0;
      shiftButtonPressed = true;
      Robot.driveTrain.changeControlMode(NeutralMode.Coast);
      Robot.gyroCorrectMove.stop();
      if (shifterHigh) {
        System.out.println(" - Shifting to Low Gear - ");
        Robot.driveTrain.shiftToLowGear();
        shifterHigh = false;
      } else {
        System.out.println(" - Shifting to High Gear - ");
        Robot.driveTrain.shiftToHighGear();
        shifterHigh = true;
      }
    }

    // This stops you from shifting over and over again while holding the button
    if (!ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON, driveController)) {
      shiftButtonPressed = false;
    }

    shifterDelayCounter++;
    double forwardBackwardStickValue = -EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(driveController);

    double rotateStickValue = EnumeratedRawAxis.RIGHTSTICKHORIZONTAL.getRawAxis(driveController);

    if (shifterDelayCounter >= delay) {
      Robot.gyroCorrectMove.moveUsingGyro(forwardBackwardStickValue * mod, rotateStickValue * mod, true, true);
    } else {
      Robot.gyroCorrectMove.stop();
    }

    if (shifterDelayCounter == delay) {
      Robot.driveTrain.changeControlMode(NeutralMode.Brake);
    }

    if (ButtonsEnumerated.RIGHTBUMPERBUTTON.isPressed(OI.getInstance().getDriveStick()) && !slowModeButtonPressed) {
      slowModeButtonPressed = true;
      if (!slowMoEnabled) {
        mod = kSlowModeModifier;
        slowMoEnabled = true;
        System.out.println("Slow Mode IS enabled!");
      } else {
        mod = 1;
        slowMoEnabled = false;
        System.out.println("SLOW MODE HAS ENDED!");
      }
    }
    // This stops you from switching in slow over and over again while holding the
    // button
    if (!ButtonsEnumerated.RIGHTBUMPERBUTTON.isPressed(OI.getInstance().getDriveStick())) {
      slowModeButtonPressed = false;
    }
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