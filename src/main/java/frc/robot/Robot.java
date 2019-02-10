/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainUltrasonicPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.commands.TeleOpDrive;
import frc.robot.sensors.anglesensor.AngleSensor;
import frc.robot.sensors.anglesensor.MockAngleSensor;
import frc.robot.sensors.anglesensor.RealAngleSensor;
import frc.robot.sensors.limitswitchsensor.LimitSwitchSensor;
import frc.robot.sensors.limitswitchsensor.MockLimitSwitchSensor;
import frc.robot.sensors.limitswitchsensor.RealLimitSwitchSensor;
import frc.robot.sensors.linefollowersensor.BaseLineFollowerSensor;
import frc.robot.sensors.linefollowersensor.LineFollowerSensorArray;
import frc.robot.sensors.linefollowersensor.MockLineFollowerSensorArray;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.sensors.magencodersensor.MockMagEncoderSensor;
import frc.robot.sensors.magencodersensor.RealMagEncoderSensor;
import frc.robot.sensors.ultrasonicsensor.MockUltrasonicSensor;
import frc.robot.sensors.ultrasonicsensor.RealUltrasonicSensor;
import frc.robot.sensors.ultrasonicsensor.UltrasonicSensor;
import frc.robot.subsystems.drivetrain.DriveTrain;
import frc.robot.subsystems.drivetrain.MockDriveTrain;
import frc.robot.subsystems.drivetrain.RealDriveTrain;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.MockIntake;
import frc.robot.subsystems.intake.RealIntake;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.I2CBusDriver;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static Compressor compressor;
  public static Joystick driveController;
  public static Joystick operatorController;

  public static DriveTrain driveTrain;
  public static DrivetrainEncoderPIDController encoderPID;
  public static DrivetrainUltrasonicPIDController ultrasonicPID;
  public static GyroPIDController gyroPID;
  public static MagEncoderSensor drivetrainLeftRearEncoder;
  public static UltrasonicSensor drivetrainFrontUltrasonic;
  public static BaseLineFollowerSensor lineFollowerSensorArray;
  public static Intake intake;
  public static AngleSensor intakeAngleSensor;
  public static LimitSwitchSensor intakeStowedSwitch;

  /**
   * This config should live on the robot and have hardware- specific configs.
   */
  private static Config environmentalConfig = ConfigFactory.parseFile(new File("/home/lvuser/robot.conf"));

  /**
   * This config lives in the jar and has hardware-independent configs.
   */
  private static Config defaultConfig = ConfigFactory.parseResources("application.conf");

  /**
   * Combined config
   */
  protected static Config conf = environmentalConfig.withFallback(defaultConfig).resolve();

  /**
   * Get the robot's config
   * 
   * @return the config
   */
  public static Config getConfig() {
    return conf;
  }

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    System.out.println("Here is my config: " + conf);

    if (conf.hasPath("subsystems.driveTrain")) {
      System.out.println("Using real drivetrain");
      driveTrain = new RealDriveTrain();
      if (conf.hasPath("sensors.drivetrainEncoders")) {
        drivetrainLeftRearEncoder = new RealMagEncoderSensor(driveTrain.getLeftRearTalon());
      } else {
        drivetrainLeftRearEncoder = new MockMagEncoderSensor();
      }
    } else {
      System.out.println("Using fake drivetrain");
      driveTrain = new MockDriveTrain();
      drivetrainLeftRearEncoder = new MockMagEncoderSensor();
    }
    if (conf.hasPath("sensors.drivetrainFrontUltrasonic")) {
      int ping = conf.getInt("sensors.drivetrainFrontUltrasonic.ping");
      int echo = conf.getInt("sensors.drivetrainFrontUltrasonic.echo");
      drivetrainFrontUltrasonic = new RealUltrasonicSensor(ping, echo);
    } else {
      drivetrainFrontUltrasonic = new MockUltrasonicSensor();
    }
    compressor = new Compressor();
    if (conf.hasPath("subsystems.intake")) {
      System.out.println("Using real intake");
      intake = new RealIntake();
    } else {
      System.out.println("Using fake intake");
      intake = new MockIntake();
    }
    if (conf.hasPath("sensors.intakeAngleSensor")) {
      System.out.println("Using real intakeAngleSensor");
      int intakeAngleSensorPort = conf.getInt("sensors.intakeAngleSensor");
      intakeAngleSensor = new RealAngleSensor(intakeAngleSensorPort);
    } else {
      System.out.println("Using mock intakeAngleSensor");
      intakeAngleSensor = new MockAngleSensor();
    }
    if (conf.hasPath("sensors.intakeStowedSwitch")) {
      System.out.println("Using real intakeStowedSwitch");
      int intakeStowedPort = conf.getInt("sensors.intakeStowedSwitch.port");
      intakeStowedSwitch = new RealLimitSwitchSensor(intakeStowedPort, false);
    } else {
      System.out.println("Using mock intakeStowedSwitch");
      intakeStowedSwitch = new MockLimitSwitchSensor();
    }
    operatorController = new Joystick(1);

    gyroPID = GyroPIDController.getInstance();

    encoderPID = DrivetrainEncoderPIDController.getInstance();
    ultrasonicPID = DrivetrainUltrasonicPIDController.getInstance();
    System.out.println("This is " + getName() + ".");
    driveController = new Joystick(0);
    I2CBusDriver sunfounderdevice = new I2CBusDriver(true, 9);
    I2C sunfounderbus = sunfounderdevice.getBus();
    lineFollowerSensorArray = new LineFollowerSensorArray(sunfounderbus, 200, 10, 0.5, 8 /* TODO: Change these! */);

    Config senseConf = conf.getConfig("sensors.lineFollowSensor");
    lineFollowerSensorArray = new LineFollowerSensorArray(sunfounderbus, senseConf.getInt("detectionThreshold"),
        senseConf.getDouble("distanceToSensor"), senseConf.getDouble("distanceBtSensors"),
        senseConf.getInt("numSensors"));

    // Camera Code
    if (conf.hasPath("cameras")) {
      Config cameraConf = conf.getConfig("cameras");

      UsbCamera camera0 = CameraServer.getInstance().startAutomaticCapture(cameraConf.getInt("camera0"));
      UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(cameraConf.getInt("camera1"));
      camera0.setResolution(320, 240);
      camera0.setFPS(10);
      camera1.setResolution(320, 240);
      camera1.setFPS(10);
    }

    if (conf.hasPath("sensors.lineFollowSensor")) {
      lineFollowerSensorArray = new LineFollowerSensorArray(sunfounderbus, senseConf.getInt("detectionThreshold"),
          senseConf.getDouble("distanceToSensor"), senseConf.getDouble("distanceBtSensors"),
          senseConf.getInt("numSensors"));
    } else {
      lineFollowerSensorArray = new MockLineFollowerSensorArray(sunfounderbus, 2, 10, 1, 8);
    }
    m_chooser.setDefaultOption("Default Auto", new TeleOpDrive());
    // chooser.addOption("My Auto", new MyAutoCommand());
    // SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
    Trace.getInstance().flushTraceFiles();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings and commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  /**
   * Get the robot name (set in the config)
   */
  public static String getName() {
    return conf.getString("robot.name");
  }

}
