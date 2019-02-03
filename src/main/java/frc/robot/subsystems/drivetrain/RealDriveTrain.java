package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.commands.TeleOpDrive;

/**
 *
 */
public class RealDriveTrain extends DriveTrain {
  public static WPI_TalonSRX driveTrainLeftMaster;
  public static WPI_TalonSRX driveTrainLeftSlave;
  public static SpeedControllerGroup driveTrainLeftSpeedController;
  public static WPI_TalonSRX driveTrainRightMaster;
  public static WPI_TalonSRX driveTrainRightSlave;
  public static SpeedControllerGroup driveTrainRightSpeedController;
  public static DifferentialDrive differentialDrive;
  public static DoubleSolenoid shifterSolenoid;
  private boolean shifterPresentFlag = false;

  public boolean getShifterPresentFlag() {
    return shifterPresentFlag;
  }

  @Override
  public void initDefaultCommand() {
    Config conf = Robot.getConfig();
    Config driveConf = conf.getConfig("ports.driveTrain");
    setDefaultCommand(new TeleOpDrive());
    driveTrainLeftMaster = new WPI_TalonSRX(driveConf.getInt("leftMaster"));
    driveTrainLeftSlave = new WPI_TalonSRX(driveConf.getInt("leftSlave"));
    driveTrainLeftSpeedController = new SpeedControllerGroup(driveTrainLeftMaster, driveTrainLeftSlave);
    driveTrainRightMaster = new WPI_TalonSRX(driveConf.getInt("rightMaster"));
    driveTrainRightSlave = new WPI_TalonSRX(driveConf.getInt("rightSlave"));
    driveTrainRightSpeedController = new SpeedControllerGroup(driveTrainRightMaster, driveTrainRightSlave);
    differentialDrive = new DifferentialDrive(driveTrainLeftSpeedController, driveTrainRightSpeedController);

    // Gear Shift Solenoid
    if (Robot.getConfig().hasPath("subsystems.driveTrain.shifter")) {
      shifterPresentFlag = true;
      shifterSolenoid = new DoubleSolenoid(driveConf.getInt("pneumatics.forwardChannel"),
          driveConf.getInt("pneumatics.backwardsChannel"));
    }
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

  @Override
  public WPI_TalonSRX getLeftRearTalon() {
    return driveTrainLeftMaster;
  }

  public void shiftToLowGear() {
    shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void shiftToHighGear() {
    shifterSolenoid.set(DoubleSolenoid.Value.kForward);
  }

}