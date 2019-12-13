package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.ButtonsEnumerated;
import frc.robot.utilities.EnumeratedRawAxis;

/**
 *
 */
public class TeleOpDrive extends Command {
  private boolean shiftButtonPressed = false;
  private boolean slowModeButtonPressed = false;

  public TeleOpDrive() {
    requires(Robot.driveTrain);
    System.out.println("Initializing Teleop Drivetrain Control...");
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("TeleOpDrive");
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
        && Robot.driveTrain.getShifterPresentFlag() && !shiftButtonPressed) {
      Robot.driveTrain.toggleShifter();
      shiftButtonPressed = true;
    }

    // This stops you from shifting over and over again while holding the button
    if (!ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON, driveController)) {
      shiftButtonPressed = false;
    }

    double forwardBackwardStickValue = -EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(driveController);
    double rotateStickValue = EnumeratedRawAxis.RIGHTSTICKHORIZONTAL.getRawAxis(driveController);

    if (ButtonsEnumerated.RIGHTBUMPERBUTTON.isPressed(OI.getInstance().getDriveStick()) && !slowModeButtonPressed) {
      slowModeButtonPressed = true;
      Robot.driveTrain.toggleSlowMode();
    }

    // This stops you from switching in slow over and over again while holding the
    // button
    if (!ButtonsEnumerated.RIGHTBUMPERBUTTON.isPressed(OI.getInstance().getDriveStick())) {
      slowModeButtonPressed = false;
    }

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

//read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    if (ButtonsEnumerated.ABUTTON.isPressed(OI.getInstance().getDriveStick())) {
      rotateStickValue = x * 0.1;
    }

    Robot.gyroCorrectMove.moveUsingGyro(forwardBackwardStickValue, rotateStickValue, true, true);
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
    Trace.getInstance().logCommandStop("TeleOpDrive");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}