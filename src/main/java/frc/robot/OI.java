/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.CallLineFollowerController;
import frc.robot.commands.IntakeArmControl;
import frc.robot.commands.IntakeArmControl.MoveIntakeArmDirection;
import frc.robot.commands.RollIntakeIn;
import frc.robot.commands.StowIntakeArm;
import frc.robot.commands.armwristcommands.ResetArmPIDSetpoints;
import frc.robot.commands.stilts.*;
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

  // Subsystem Controller and Buttons
  JoystickButton raiseLeftFront;
  JoystickButton raiseRightFront;
  JoystickButton raiseLeftRear;
  JoystickButton raiseRightRear;
  private POVButton retractFrontLegs;
  private POVButton retractRearLegs;
  private POVButton extendAllLegs;
  private POVButton retractAllLegs;
  private JoystickButton extendFrontLegs;
  private JoystickButton extendRearLegs;

  // Subsystem Controller and Buttons
  Joystick subsystemController;
  JoystickButton raiseRobotButton;
  private static OI instance;

  private POVButton intakeUp;
  private POVButton intakeDown;
  private Button driveForward;
  private Button driveTrainPIDTest;
  private JoystickButton turnToNorth;
  private JoystickButton turnToEast;
  private JoystickButton turnToSouth;
  private JoystickButton turnToWest;
  private JoystickButton runIntakeIn;
  private JoystickButton runIntakeOut;

  private JoystickButton LowGamePieceButton;
  private JoystickButton MiddleGamePieceButton;
  private JoystickButton HighGamePieceButton;
  private JoystickButton CargoShipAndLoadingCommand;

  JoystickButton openClawButton;
  JoystickButton closeClawButton;
  // Controllers
  protected Joystick driveController; // TODO: Cleanup use of joysticks/controllers in the code.
  protected Joystick operatorController;

  public static final ButtonsEnumerated overRideSafetiesButton = ButtonsEnumerated.BACKBUTTON;
  public static final ButtonsEnumerated rollIntakeButton = ButtonsEnumerated.RIGHTBUMPERBUTTON;

  private OI() {
    System.out.println("Constructing OI");

    driveController = new Joystick(0);
    subsystemController = new Joystick(1);

    retractAllLegs = new POVButton(driveController, POVDirectionNames.SOUTH.getValue());
    retractAllLegs.whenPressed(new RetractAll());

    extendAllLegs = new POVButton(driveController, POVDirectionNames.NORTH.getValue());
    extendAllLegs.whenPressed(new RaiseAll());

    extendFrontLegs = new JoystickButton(driveController, ButtonsEnumerated.STARTBUTTON.getValue());
    extendFrontLegs.whenPressed(new RaiseFrontLegs());

    extendRearLegs = new JoystickButton(driveController, ButtonsEnumerated.BACKBUTTON.getValue());
    extendRearLegs.whenPressed(new RaiseBackLegs());

    retractFrontLegs = new POVButton(driveController, POVDirectionNames.EAST.getValue());
    retractFrontLegs.whenPressed(new RetractFrontLegs());

    retractRearLegs = new POVButton(driveController, POVDirectionNames.WEST.getValue());
    retractRearLegs.whenPressed(new RetractRearLegs());

    SmartDashboard.putData("Retract All", new RetractAll());

    driveController = new Joystick(0);
    operatorController = new Joystick(1);

    SmartDashboard.putData("CallLineFollowerController", new CallLineFollowerController());
    // Claw buttons are temp until I figure out the D-Pad

    /*
     * turnToNorth = new JoystickButton(driveController,
     * ButtonsEnumerated.YBUTTON.getValue()); turnToNorth.whenPressed(new
     * TurnToCompassHeading(0)); turnToEast = new JoystickButton(driveController,
     * ButtonsEnumerated.BBUTTON.getValue()); turnToEast.whenPressed(new
     * TurnToCompassHeading(90)); turnToSouth = new JoystickButton(driveController,
     * ButtonsEnumerated.ABUTTON.getValue()); turnToSouth.whenPressed(new
     * TurnToCompassHeading(180)); turnToWest = new JoystickButton(driveController,
     * ButtonsEnumerated.XBUTTON.getValue()); turnToWest.whenPressed(new
     * TurnToCompassHeading(270));
     */

    intakeUp = new POVButton(operatorController, POVDirectionNames.NORTH.getValue());
    intakeUp.whenPressed(new IntakeArmControl(MoveIntakeArmDirection.UP));
    SmartDashboard.putData("MoveIntakeUp", new IntakeArmControl(MoveIntakeArmDirection.UP));

    // TODO: Change these to actual buttons
    runIntakeIn = new JoystickButton(operatorController, ButtonsEnumerated.RIGHTBUMPERBUTTON.getValue());
    runIntakeIn.whileHeld(new RollIntakeIn());

    intakeDown = new POVButton(operatorController, POVDirectionNames.SOUTH.getValue());
    intakeDown.whenPressed(new IntakeArmControl(MoveIntakeArmDirection.DOWN));
    SmartDashboard.putData("StowIntake", new StowIntakeArm());

    SmartDashboard.putData("MoveIntakeDown", new IntakeArmControl(MoveIntakeArmDirection.DOWN));

    // LowGamePieceButton = new JoystickButton(operatorController,
    // ButtonsEnumerated.ABUTTON.getValue());
    // LowGamePieceButton.whileHeld(new TuckArm(40, true));
    // driveTrainPIDTest = new POVButton(driveController,
    // POVDirectionNames.SOUTH.getValue());
    // driveTrainPIDTest.whileHeld(new DriveTrainPIDTest());
    // driveTrainPIDTest.whileHeld(new DriveForward());
    /*
     * CargoShipAndLoadingCommand = new JoystickButton(operatorController,
     * ButtonsEnumerated.BBUTTON.getValue());
     * CargoShipAndLoadingCommand.whileHeld(new CargoShipAndLoadingCommand());
     *
     * MiddleGamePieceButton = new JoystickButton(operatorController,
     * ButtonsEnumerated.XBUTTON.getValue()); MiddleGamePieceButton.whileHeld(new
     * MiddleGamePieceArmCommand());
     *
     * HighGamePieceButton = new JoystickButton(operatorController,
     * ButtonsEnumerated.YBUTTON.getValue()); HighGamePieceButton.whileHeld(new
     * HighGamePieceArmCommand());
     */

    // rollIntakeButton.getJoystickButton(operatorController).whenPressed(new
    // //RollIntakeGroupCommandScheduler());
    overRideSafetiesButton.getJoystickButton(operatorController).whenPressed(new ResetArmPIDSetpoints());

  }

  public Joystick getDriveStick() {
    return driveController;
  }

  public Joystick getOperatorStick() {
    return operatorController;
  }

  public static OI getInstance() {
    if (instance == null) {
      instance = new OI();
    }

    return instance;
  }

}
