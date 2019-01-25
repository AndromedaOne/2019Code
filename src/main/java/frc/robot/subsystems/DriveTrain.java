package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Robot;
import frc.robot.commands.TeleOpDrive;
/**
 *
 */
public class DriveTrain extends Subsystem {
    public static WPI_TalonSRX driveTrainLeftFrontTalon;
    public static WPI_TalonSRX driveTrainLeftRearTalon;
    public static SpeedControllerGroup driveTrainLeftSpeedController;
    public static WPI_TalonSRX driveTrainRightFrontTalon;
    public static WPI_TalonSRX driveTrainRightRearTalon;
    public static SpeedControllerGroup driveTrainRightSpeedController;
    public static DifferentialDrive differentialDrive;
    
    
    private WPI_TalonSRX initTalonMaster(Config driveConf, String motorName) {
        WPI_TalonSRX _talon = new WPI_TalonSRX(driveConf.getInt(motorName));
        _talon.configFactoryDefault();

        return _talon;
    }

    private WPI_TalonSRX initTalonSlave(Config driveConf, String motorName, WPI_TalonSRX master) {
        WPI_TalonSRX _talon = new WPI_TalonSRX(driveConf.getInt(motorName));
        _talon.configFactoryDefault();
        _talon.follow(master);

        return _talon;
    }

    @Override
    public void initDefaultCommand(){
        Config conf = Robot.getConfig();
        Config driveConf = conf.getConfig("ports.driveTrain");
        setDefaultCommand(new TeleOpDrive());
        driveTrainLeftRearTalon = initTalonMaster(driveConf, "leftRearTalon");
        driveTrainLeftFrontTalon = initTalonSlave(driveConf, "leftFrontTalon", driveTrainLeftRearTalon);
        driveTrainRightRearTalon = initTalonMaster(driveConf, "rightRearTalon");
        driveTrainRightFrontTalon = initTalonSlave(driveConf, "rightFrontTalon", driveTrainRightRearTalon);
        differentialDrive = new DifferentialDrive(driveTrainLeftRearTalon, driveTrainRightRearTalon);
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