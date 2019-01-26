package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.utilities.TalonSRXDifferentialDrive;
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
    public static ArbitraryModeWPI_TalonSRX driveTrainLeftRearTalon;
    public static SpeedControllerGroup driveTrainLeftSpeedController;
    public static WPI_TalonSRX driveTrainRightFrontTalon;
    public static ArbitraryModeWPI_TalonSRX driveTrainRightRearTalon;
    public static SpeedControllerGroup driveTrainRightSpeedController;
    public static TalonSRXDifferentialDrive differentialDrive;

    private final int kTimeoutMs = 30;
    /*100% throttle corresponds to 3600 RPM*/
    private final double kMaxRPM = 3600;
    /*Implement math according to section 12.4.2 of the TALON SRX Software Reference manual Rev 1.22*/
    private final double kF = 1023 / kMaxRPM; 
    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;

    class ArbitraryModeWPI_TalonSRX extends WPI_TalonSRX {
        private ControlMode controlMode = ControlMode.PercentOutput;
        private double speed;

        ArbitraryModeWPI_TalonSRX(int deviceNumber) {
            super(deviceNumber);
        }

        public void setControlMode(ControlMode controlMode) {
            this.controlMode = controlMode;
        }

        @Override
        public void set(double speed) {
            this.speed = speed;
            set(controlMode, speed);
            feed();    
        }
        @Override
        public double get() {
            return speed;
        }
    }

    public void setVBusMode() {
        setVBusMode(driveTrainLeftRearTalon);
        setVBusMode(driveTrainRightRearTalon);
        differentialDrive.setMaxOutput(1.0);
    }
    private void setVBusMode(ArbitraryModeWPI_TalonSRX talon) {
        talon.setControlMode(ControlMode.PercentOutput);
    }
    public void setVelocityMode() {
        setVelocityMode(driveTrainLeftRearTalon);
        setVelocityMode(driveTrainRightRearTalon);
        differentialDrive.setMaxOutput(kMaxRPM * 4096 / 600);
    }
    private void setVelocityMode(ArbitraryModeWPI_TalonSRX talon) {
        talon.setControlMode(ControlMode.Velocity);

    }
    // Inspired by https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java
    // and 
    private ArbitraryModeWPI_TalonSRX initTalonMaster(Config driveConf, String motorName) {
        ArbitraryModeWPI_TalonSRX _talon = new ArbitraryModeWPI_TalonSRX(driveConf.getInt(motorName));
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
        
		/* Config the Velocity closed loop gains in slot0 */
		_talon.config_kF(0, kF, kTimeoutMs);
		_talon.config_kP(0, kP, kTimeoutMs);
		_talon.config_kI(0, kI, kTimeoutMs);
		_talon.config_kD(0, kD, kTimeoutMs);
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
        differentialDrive = new TalonSRXDifferentialDrive(driveTrainLeftRearTalon, driveTrainRightRearTalon);
        /*For testing only try this in velocity mode*/
        setVelocityMode();
    }
    

    @Override
    public void periodic() {
    }

    public void move(double forwardBackSpeed, double rotateAmount) {
        printMeasurements("Left ", driveTrainLeftRearTalon, forwardBackSpeed, false);
        printMeasurements("Right", driveTrainRightRearTalon, forwardBackSpeed, true);
    	differentialDrive.arcadeDrive(forwardBackSpeed, rotateAmount);
    }
    /* String for output */
    StringBuilder _sb = new StringBuilder();
    int _loops = 0;
    private void printMeasurements(String side, WPI_TalonSRX _talon, double targetVelocity, boolean doneMeasuring) {
        /* Get Talon/Victor's current output percentage */
		double motorOutput = _talon.getMotorOutputPercent(); 
		
        /* Prepare line to print */
        _sb.append(side);
		_sb.append("\tout:");
		/* Cast to int to remove decimal places */
		_sb.append((int) (motorOutput * 100));
		_sb.append("%");	// Percent
		_sb.append("\tspd:");
		_sb.append(_talon.getSelectedSensorVelocity(0));
        _sb.append("u"); 	// Native units
        /* Append more signals to print when in speed mode. */
        _sb.append("\terr:");
        _sb.append(_talon.getClosedLoopError(0));
        _sb.append("\ttrg:");
        _sb.append(targetVelocity);
            /* Print built string every 10 loops */
        if(doneMeasuring) {
		    if (++_loops >= 10) {
			    _loops = 0;
			    System.out.println(_sb.toString());
            }
            /* Reset built string */
            _sb.setLength(0);
        } else {
            _sb.append("\n");
        }
    }

    public void stop() {
    	differentialDrive.stopMotor();
    }
    
}