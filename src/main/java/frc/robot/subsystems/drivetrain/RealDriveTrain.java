package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.commands.TeleOpDrive;

/**
 *
 */
public class RealDriveTrain extends DriveTrain {
  public static WPI_TalonSRX driveTrainLeftTalon1;
  public static WPI_TalonSRX driveTrainLeftTalon2;
  public static SpeedControllerGroup driveTrainLeftSpeedController;
  public static WPI_TalonSRX driveTrainRightTalon3;
  public static WPI_TalonSRX driveTrainRightTalon4;
  public static SpeedControllerGroup driveTrainRightSpeedController;
  public static DifferentialDrive differentialDrive;
  public static DoubleSolenoid shifterSolenoid;
  private boolean shifterPresentFlag = false;
  public boolean getShifterPresentFlag(){return shifterPresentFlag;}

  @Override
  public void initDefaultCommand() {
    Config conf = Robot.getConfig();
    Config driveConf = conf.getConfig("ports.driveTrain");
    setDefaultCommand(new TeleOpDrive());
    driveTrainLeftTalon1 = new WPI_TalonSRX(driveConf.getInt("leftTalon1"));
    driveTrainLeftTalon2 = new WPI_TalonSRX(driveConf.getInt("leftTalon2"));
    driveTrainLeftSpeedController = new SpeedControllerGroup(driveTrainLeftTalon1, driveTrainLeftTalon2);
    driveTrainRightTalon3 = new WPI_TalonSRX(driveConf.getInt("rightTalon3"));
    driveTrainRightTalon4 = new WPI_TalonSRX(driveConf.getInt("rightTalon4"));
    driveTrainRightSpeedController = new SpeedControllerGroup(driveTrainRightTalon3, driveTrainRightTalon4);
    differentialDrive = new DifferentialDrive(driveTrainLeftSpeedController, driveTrainRightSpeedController);

    // Gear Shift Solenoid
    if(Robot.getConfig().hasPath("subsystems.driveTrain.shifter")) {
      shifterPresentFlag = true;
      shifterSolenoid = new DoubleSolenoid(driveConf.getInt("pneumatics.forwardChannel"), driveConf.getInt("pneumatics.backwardsChannel"));  
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

  public void shiftToLowGear() {
    shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void shiftToHighGear() {
    shifterSolenoid.set(DoubleSolenoid.Value.kForward);
  }

}