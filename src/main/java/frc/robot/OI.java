/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.CallLineFollowerController;
import frc.robot.commands.DriveForward;
import frc.robot.commands.IntakeArmControl;
import frc.robot.commands.IntakeArmControl.MoveIntakeArmDirection;
import frc.robot.commands.MoveUsingEncoderPID;
import frc.robot.commands.TurnToCompassHeading;
import frc.robot.utilities.ButtonsEnumerated;
import frc.robot.utilities.POVDirectionNames;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  // Drive Controller and Buttons
  Joystick driveController;

  // Subsystem Controller and Buttons
  Joystick subsystemController;
  JoystickButton raiseRobotButton;

  private static OI instance = new OI();

  private POVButton intakeUp;
  private POVButton intakeDown;
  private JoystickButton turnToNorth;
  private JoystickButton turnToEast;
  private JoystickButton turnToSouth;
  private JoystickButton turnToWest;
  private Button driveForward;

  JoystickButton openClawButton;
  JoystickButton closeClawButton;

  private OI() {
    JoystickButton testEncoder = new JoystickButton(driveController, 6);
    testEncoder.whenPressed(new MoveUsingEncoderPID(1500));

    SmartDashboard.putData("CallLineFollowerController", new CallLineFollowerController());
    // Claw buttons are temp until I figure out the D-Pad
    openClawButton = new JoystickButton(subsystemController, ButtonsEnumerated.ABUTTON.getValue());
    closeClawButton = new JoystickButton(subsystemController, ButtonsEnumerated.BBUTTON.getValue());

    turnToNorth = new JoystickButton(driveController, ButtonsEnumerated.YBUTTON.getValue());
    turnToNorth.whenPressed(new TurnToCompassHeading(0));
    turnToEast = new JoystickButton(driveController, ButtonsEnumerated.BBUTTON.getValue());
    turnToEast.whenPressed(new TurnToCompassHeading(90));
    turnToSouth = new JoystickButton(driveController, ButtonsEnumerated.ABUTTON.getValue());
    turnToSouth.whenPressed(new TurnToCompassHeading(180));
    turnToWest = new JoystickButton(driveController, ButtonsEnumerated.XBUTTON.getValue());
    turnToWest.whenPressed(new TurnToCompassHeading(270));

    intakeUp = new POVButton(operatorController, POVDirectionNames.NORTH.getValue());
    intakeUp.whenPressed(new IntakeArmControl(MoveIntakeArmDirection.UP));
    SmartDashboard.putData("MoveIntakeUp", new IntakeArmControl(MoveIntakeArmDirection.UP));

    intakeDown = new POVButton(operatorController, POVDirectionNames.SOUTH.getValue());
    intakeDown.whenPressed(new IntakeArmControl(MoveIntakeArmDirection.DOWN));
    SmartDashboard.putData("MoveIntakeDown", new IntakeArmControl(MoveIntakeArmDirection.DOWN));

    driveForward = new POVButton(driveStick, POVDirectionNames.SOUTH.getValue());
    driveForward.whileHeld(new DriveForward());
  }

  // Controllers
  protected Joystick driveController = new Joystick(0); // TODO: Cleanup use of joysticks/controllers in the code.
  protected Joystick operatorController = Robot.operatorController;

  public Joystick getDriveStick() {
    return driveController;
  }

  public Joystick getOperatorStick() {
    return operatorController;
  }

  public static OI getInstance() {
    return instance;
  }

}
