package frc.robot.subsystems.drivetrain.drivecontrol;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.TalonSRX_4905;
import frc.robot.subsystems.drivetrain.RealDriveTrain;
import frc.robot.subsystems.drivetrain.DriveTrain.RobotGear;

public class VelocityControl extends DriveControl {

    DifferentialDrive m_differentialDrive;
    // TODO need to fix this too:
    double m_maxSpeed = 1.0;
    private double lowGearMaxSpeed = 1;
    private double highGearMaxSpeed = 1;
    private final double m_closedLoopNeutralToMaxSpeedSeconds = 0.0;

    public VelocityControl(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster) {
        super(leftMaster, rightMaster);
      
        m_differentialDrive = new DifferentialDrive(leftMaster, rightMaster);
        setVelocityMode();
    }

    @Override
    public void move(double forwardBackwardSpeed, double rotateAmount, boolean squaredInput) {
        m_differentialDrive.arcadeDrive(forwardBackwardSpeed, rotateAmount);
    }

    private void setVelocityMode() {
        
        setVelocityMode(m_leftMaster, "left");
        setVelocityMode(m_rightMaster, "right");
        m_differentialDrive.setMaxOutput(m_maxSpeed);
        System.out.println("maxSpeed set to " + m_maxSpeed);
        
      }
    
      private void setVelocityMode(TalonSRX_4905 talon, String side) {
        talon.setControlMode(ControlMode.Velocity);
        setPIDParameters(talon, side);
      }

      private void setPIDParameters(WPI_TalonSRX _talon, String side) {
        /* Config sensor used for Primary PID [Velocity] */

        double lowGearP = 0.0;
        double lowGearI = 0.0;
        double lowGearD = 0.0;
        double highGearP = 0.0;
        double highGearI = 0.0;
        double highGearD = 0.0;

        Config conf = Robot.getConfig();
        Config driveConf = conf.getConfig("ports.driveTrain");

        lowGearMaxSpeed = readPIDConfigItem(driveConf, side, "LowGearMaxSpeed", 1);
        lowGearP = readPIDConfigItem(driveConf, side, "LowGearP", 0);
        lowGearI = readPIDConfigItem(driveConf, side, "LowGearI", 0);
        lowGearD = readPIDConfigItem(driveConf, side, "LowGearD", 0);

        highGearMaxSpeed = readPIDConfigItem(driveConf, side, "HighGearMaxSpeed", 1);
        highGearP = readPIDConfigItem(driveConf, side, "HighGearP", 0);
        highGearI = readPIDConfigItem(driveConf, side, "HighGearI", 0);
        highGearD = readPIDConfigItem(driveConf, side, "HighGearD", 0);

          /* Config the Velocity closed loop gains in slot0 */
        _talon.config_kF(RealDriveTrain.kLowGearPIDSlot, (1023 / lowGearMaxSpeed), DriveControlFactory.kTimeoutMs);
        _talon.config_kP(RealDriveTrain.kLowGearPIDSlot, lowGearP, DriveControlFactory.kTimeoutMs);
        _talon.config_kI(RealDriveTrain.kLowGearPIDSlot, lowGearI, DriveControlFactory.kTimeoutMs);
        _talon.config_kD(RealDriveTrain.kLowGearPIDSlot, lowGearD, DriveControlFactory.kTimeoutMs);
        _talon.config_IntegralZone(RealDriveTrain.kLowGearPIDSlot, 50, DriveControlFactory.kTimeoutMs);
        /* Config the Velocity closed loop gains in slot1 */
        _talon.config_kF(RealDriveTrain.kHighGearPIDSlot, (1023 / highGearMaxSpeed), DriveControlFactory.kTimeoutMs);
        _talon.config_kP(RealDriveTrain.kHighGearPIDSlot, highGearP, DriveControlFactory.kTimeoutMs);
        _talon.config_kI(RealDriveTrain.kHighGearPIDSlot, highGearI, DriveControlFactory.kTimeoutMs);
        _talon.config_kD(RealDriveTrain.kHighGearPIDSlot, highGearD, DriveControlFactory.kTimeoutMs);
        _talon.config_IntegralZone(RealDriveTrain.kHighGearPIDSlot, 50, DriveControlFactory.kTimeoutMs);
        _talon.configClosedloopRamp(m_closedLoopNeutralToMaxSpeedSeconds);
      }
    
    private double readPIDConfigItem(Config driveConf, String side, String configItem, double defaultValue) {
        double configValue = defaultValue;
        if (driveConf.hasPath(side + "Side" + configItem)) {
            configValue = driveConf.getDouble(side + "Side" + configItem);
        }
        System.out.println(side + "Side" + configItem + "=" + configValue);
        return configValue;
    }

    @Override
    public void stop() {
        m_differentialDrive.stopMotor();
    }

  @Override
  public void shiftSpeed(RobotGear robotGear) {
    int speed = 0;
    switch(robotGear) {
      case HIGHGEAR:
        speed = 1;
        break;
      
      case SLOWHIGHGEAR:
        speed = 1;
        break;
    }
    m_leftMaster.selectProfileSlot(speed, 0);
    m_rightMaster.selectProfileSlot(speed, 0);

  }

}