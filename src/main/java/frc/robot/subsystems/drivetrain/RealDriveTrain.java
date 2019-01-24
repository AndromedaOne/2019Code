package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.commands.TeleOpDrive;

/**
 *
 */
public class RealDriveTrain extends DriveTrain {
  public static WPI_TalonSRX driveTrainLeftFrontTalon;
  public static WPI_TalonSRX driveTrainLeftRearTalon;
  public static SpeedControllerGroup driveTrainLeftSpeedController;
  public static WPI_TalonSRX driveTrainRightFrontTalon;
  public static WPI_TalonSRX driveTrainRightRearTalon;
  public static SpeedControllerGroup driveTrainRightSpeedController;
  public static DifferentialDrive differentialDrive;

  @Override
  public void initDefaultCommand() {
    Config conf = Robot.getConfig();
    Config driveConf = conf.getConfig("ports.can");
    setDefaultCommand(new TeleOpDrive());
    driveTrainLeftFrontTalon = new WPI_TalonSRX(driveConf.getInt("leftFrontTalon"));
    driveTrainLeftRearTalon = new WPI_TalonSRX(driveConf.getInt("leftRearTalon"));
    driveTrainLeftSpeedController = new SpeedControllerGroup(driveTrainLeftFrontTalon, driveTrainLeftRearTalon);
    driveTrainRightFrontTalon = new WPI_TalonSRX(driveConf.getInt("rightFrontTalon"));
    driveTrainRightRearTalon = new WPI_TalonSRX(driveConf.getInt("rightRearTalon"));
    driveTrainRightSpeedController = new SpeedControllerGroup(driveTrainRightFrontTalon, driveTrainRightRearTalon);
    differentialDrive = new DifferentialDrive(driveTrainLeftSpeedController, driveTrainRightSpeedController);
  }

  @Override
  public void periodic() {
  }

  public void move(double forwardBackSpeed, double rotateAmount) {
    differentialDrive.arcadeDrive(forwardBackSpeed, rotateAmount);
  }

  public void stop() {
    differentialDrive.stopMotor();
  }

}