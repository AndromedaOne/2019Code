/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.stilts.*;
import frc.robot.commands.stilts.PulseLeg.stiltLeg;
import frc.robot.utilities.ButtonsEnumerated;

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
  JoystickButton raiseLeftFront;
  JoystickButton raiseRightFront;
  JoystickButton raiseLeftRear;
  JoystickButton raiseRightRear;

  public OI() {
    driveController = new Joystick(0);
    subsystemController = new Joystick(1);
    raiseRobotButton = new JoystickButton(driveController, ButtonsEnumerated.LEFTBUMPERBUTTON.getValue());
    raiseRobotButton.whenPressed(new RaiseRobot());

    raiseLeftFront = new JoystickButton(driveController, ButtonsEnumerated.YBUTTON.getValue());
    raiseLeftFront.whenPressed(new PulseLeg(stiltLeg.FRONTLEFT));

    raiseRightFront = new JoystickButton(driveController, ButtonsEnumerated.BBUTTON.getValue());
    raiseRightFront.whenPressed(new PulseLeg(stiltLeg.FRONTRIGHT));

    raiseRightRear = new JoystickButton(driveController, ButtonsEnumerated.ABUTTON.getValue());
    raiseRightRear.whenPressed(new PulseLeg(stiltLeg.REARRIGHT));

    raiseLeftRear = new JoystickButton(driveController, ButtonsEnumerated.XBUTTON.getValue());
    raiseLeftRear.whenPressed(new PulseLeg(stiltLeg.REARLEFT));

    SmartDashboard.putData("Extend Front Left", new RaiseFrontLeft());
    SmartDashboard.putData("Extend Front Right", new RaiseFrontRight());
    SmartDashboard.putData("Extend Rear Left", new RaiseRearLeft());
    SmartDashboard.putData("Extend Rear Right", new RaiseRearRight());
    SmartDashboard.putData("Stop Front Left", new StopFrontLeft());
    SmartDashboard.putData("Stop Front Right", new StopFrontRight());
    SmartDashboard.putData("Stop Rear Left", new StopRearLeft());
    SmartDashboard.putData("Stop Rear Right", new StopRearRight());
    SmartDashboard.putData("Retract Front Left", new RetractFrontLeft());
    SmartDashboard.putData("Retract Front Right", new RetractFrontRight());
    SmartDashboard.putData("Retract Rear Left", new RetractRearLeft());
    SmartDashboard.putData("Retract Rear Right", new RetractRearRight());
    SmartDashboard.putData("Retract All", new RetractAll());
  }

  public Joystick getDriveController() {
    return driveController;
  }

}
