package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;
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

    private final int kTimeoutMs = 30;
    
    // Inspired by https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java
    // and 
    private WPI_TalonSRX initTalonMaster(Config driveConf, String motorName) {
        WPI_TalonSRX _talon = new WPI_TalonSRX(driveConf.getInt(motorName));
       /* Factory Default all hardware to prevent unexpected behaviour */
        _talon.configFactoryDefault();

        /* Config sensor used for Primary PID [Velocity] */
        _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
        
        /**
		 * Phase sensor accordingly. 
         * Positive Sensor Reading should match Green (blinking) Leds on Talon
         */
        _talon.setSensorPhase(true);
        
        /* Config the peak and nominal outputs */
		_talon.configNominalOutputForward(0, kTimeoutMs);
		_talon.configNominalOutputReverse(0, kTimeoutMs);
		_talon.configPeakOutputForward(1, kTimeoutMs);
		_talon.configPeakOutputReverse(-1, kTimeoutMs);


        return _talon;
    }

    private WPI_TalonSRX initTalonSlave(Config driveConf, String motorName, WPI_TalonSRX master) {
        WPI_TalonSRX slaveMotor = new WPI_TalonSRX(driveConf.getInt(motorName));
        slaveMotor.configFactoryDefault();
        slaveMotor.follow(master);

        return slaveMotor;
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