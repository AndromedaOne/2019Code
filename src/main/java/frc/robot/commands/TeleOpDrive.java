package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;
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
  private double kFirstGearModifier = 0.6;
  private double kThirdGearModifier = 0.75;
  private double kAccelerationModifier = 0.6;
  private int slowModeCounter = 0;
  private double kSlowModeSlope = 1.0 / 50.0;
  private double previousSpeed = 0;
  private final double kAccelerationSlope = 1.0 / 12.5;

  public TeleOpDrive() {
    requires(Robot.driveTrain);
    System.out.println("Initializing Teleop Drivetrain Control...");
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("TeleOpDrive");
    mod = 1;
    slowMoEnabled = false;
    shifterDelayCounter = 0;
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
      slowModeCounter = 0;
      slowModeButtonPressed = true;
      Robot.driveTrain.changeControlMode(NeutralMode.Coast);
      Robot.gyroCorrectMove.stop();
      if (shifterHigh) {
        System.out.println(" - Shifting to Low Gear - ");
        Robot.driveTrain.shiftToLowGear();
        shifterHigh = false;
      } else {
        slowMoEnabled = true;
        System.out.println(" - Shifting to High Gear - ");
        Robot.driveTrain.shiftToHighGear();
        shifterHigh = true;
      }
      switchingLEDMode();
    }

    // This stops you from shifting over and over again while holding the button
    if (!ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON, driveController)) {
      shiftButtonPressed = false;
    }

    shifterDelayCounter++;
    double forwardBackwardStickValue = -EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(driveController);

    double rotateStickValue = EnumeratedRawAxis.RIGHTSTICKHORIZONTAL.getRawAxis(driveController);

    if (shifterDelayCounter == delay) {
      Robot.driveTrain.changeControlMode(NeutralMode.Brake);
    }

    // This adds one tick everytime time after we last clicked the slow mode button
    slowModeCounter++;
    if (ButtonsEnumerated.RIGHTBUMPERBUTTON.isPressed(OI.getInstance().getDriveStick()) && !slowModeButtonPressed) {
      slowModeCounter = 0;
      slowModeButtonPressed = true;
      if (!slowMoEnabled) {
        slowMoEnabled = true;
        System.out.println("Slow Mode IS enabled!");
      } else {
        slowMoEnabled = false;
        System.out.println("SLOW MODE HAS ENDED!");
      }
      switchingLEDMode();
    }

    if (slowMoEnabled) {
      mod = kFirstGearModifier;
      if (shifterHigh) {
        // This makes us drive faster when we are in slow mode high gear
        mod = kThirdGearModifier;
      }
    } else {
      mod = 1;
    }

    // This stops you from switching in slow over and over again while holding the
    // button
    if (!ButtonsEnumerated.RIGHTBUMPERBUTTON.isPressed(OI.getInstance().getDriveStick())) {
      slowModeButtonPressed = false;
    }
    double requestedSpeed = forwardBackwardStickValue * mod;
    if (requestedSpeed > previousSpeed) {
      previousSpeed += kAccelerationSlope;
      if (previousSpeed > requestedSpeed) {
        previousSpeed = requestedSpeed;
      }
    } else {
      previousSpeed -= kAccelerationSlope;
      if (previousSpeed < requestedSpeed) {
        previousSpeed = requestedSpeed;
      }
    }

    if (shifterDelayCounter >= delay) {
      Robot.gyroCorrectMove.moveUsingGyro(previousSpeed, rotateStickValue * mod, true, true);
    } else {
      Robot.gyroCorrectMove.stop();
    }
  }

  private void switchingLEDMode() {
    if (slowMoEnabled && shifterHigh) {
      Robot.rightLeds.setBlue(1.0);
      Robot.leftLeds.setBlue(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 3);
    } else if (slowMoEnabled && !shifterHigh) {
      Robot.rightLeds.setRed(1.0);
      Robot.leftLeds.setRed(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 1);
    } else if (!slowMoEnabled && shifterHigh) {
      Robot.rightLeds.setGreen(1.0);
      Robot.leftLeds.setGreen(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 4);
    } else {
      Robot.rightLeds.setWhite(1.0);
      Robot.leftLeds.setWhite(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 2);
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
    Trace.getInstance().logCommandStop("TeleOpDrive");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}