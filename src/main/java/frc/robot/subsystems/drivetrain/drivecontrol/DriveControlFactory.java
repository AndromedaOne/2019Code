package frc.robot.subsystems.drivetrain.drivecontrol;

import java.util.Vector;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import frc.robot.Robot;

import frc.robot.TalonSRX_4905;
import frc.robot.closedloopcontrollers.pidcontrollers.VelocityWPIPidTalon;

public class DriveControlFactory {

    public static final int kTimeoutMs = 30;

    private static TalonSRX_4905 leftMaster;
    private static TalonSRX_4905 rightMaster;

    private static TalonSRX_4905 leftSlave;
    private static TalonSRX_4905 rightSlave;

    private static Vector<TalonSRX_4905> initializedSpeedControllers;

    public static DriveControl create(){
        Config conf = Robot.getConfig();
        Config driveConf = conf.getConfig("ports.driveTrain");
        boolean isPercentVBus = driveConf.getBoolean("isPercentVBus");

        if(isPercentVBus){
          leftMaster = initTalonMaster(driveConf, "left");
          rightMaster = initTalonMaster(driveConf, "right");
          initializedSpeedControllers.add(leftMaster);
          initializedSpeedControllers.add(rightMaster);

          leftSlave = initTalonSlave(driveConf, "leftSlave", leftMaster, driveConf.getBoolean("leftSideInverted"));
          rightSlave = initTalonSlave(driveConf, "rightSlave", rightMaster, driveConf.getBoolean("rightSideInverted"));
          initializedSpeedControllers.add(leftSlave);
          initializedSpeedControllers.add(rightSlave);
          return new PercentVBusControl(leftMaster, rightMaster);
        }
        
        initializedSpeedControllers.clear();
        leftMaster = new VelocityWPIPidTalon(leftMaster.getDeviceID(), 0, 0, 0, 0, "DriveTrain", "velocityWPIPidLeftMaster");
        rightMaster = new VelocityWPIPidTalon(rightMaster.getDeviceID(), 0, 0, 0, 0, "DriveTrain", "velocityWPIPidRightMaster");
        initializedSpeedControllers.add(leftMaster);
        initializedSpeedControllers.add(rightMaster);
        leftSlave = initTalonSlave(driveConf, "leftSlave", leftMaster, driveConf.getBoolean("leftSideInverted"));
        rightSlave = initTalonSlave(driveConf, "rightSlave", rightMaster, driveConf.getBoolean("rightSideInverted"));
        initializedSpeedControllers.add(leftSlave);
        initializedSpeedControllers.add(rightSlave);

        return new VelocityWPIPidControl(leftMaster, rightMaster);
        
    } 
    // Inspired by
  // https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java
  // and
  private static TalonSRX_4905 initTalonMaster(Config driveConf, String side) {
    TalonSRX_4905 _talon = new TalonSRX_4905(driveConf.getInt(side + "Master"));

    /* Factory Default all hardware to prevent unexpected behaviour */
    _talon.configFactoryDefault();
    _talon.setInverted(driveConf.getBoolean(side + "SideInverted"));
    _talon.setSensorPhase(driveConf.getBoolean(side + "SideSensorInverted"));

    _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
    /**
     * Phase sensor accordingly. Positive Sensor Reading should match Green
     * (blinking) Leds on Talon
     * 
     * /* Config the peak and nominal outputs
     */
    _talon.configNominalOutputForward(0, kTimeoutMs);
    _talon.configNominalOutputReverse(0, kTimeoutMs);
    _talon.configPeakOutputForward(1, kTimeoutMs);
    _talon.configPeakOutputReverse(-1, kTimeoutMs);

    return _talon;
  }

  private static TalonSRX_4905 initTalonSlave(Config driveConf, String motorName, WPI_TalonSRX master, boolean isInverted) {
    TalonSRX_4905 slaveMotor = new TalonSRX_4905(driveConf.getInt(motorName));
    slaveMotor.configFactoryDefault();
    slaveMotor.follow(master);
    slaveMotor.setInverted(isInverted);

    return slaveMotor;
  }

  public static Vector<TalonSRX_4905> getSpeedControllers() {
      return initializedSpeedControllers;
  }
}