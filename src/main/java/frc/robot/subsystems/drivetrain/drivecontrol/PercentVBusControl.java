package frc.robot.subsystems.drivetrain.drivecontrol;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.TalonSRX_4905;
import frc.robot.subsystems.drivetrain.DriveTrain.RobotGear;

public class PercentVBusControl extends DriveControl {

    DifferentialDrive m_differentialDrive;
    double m_maxSpeed = 1.0;

    public PercentVBusControl(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster) {
        super(leftMaster, rightMaster);
        m_differentialDrive = new DifferentialDrive(leftMaster, rightMaster);
        
        setVBusMode();
    }

    @Override
    public void move(double forwardBackwardSpeed, double rotateAmount, boolean squaredInput) {
        m_differentialDrive.arcadeDrive(forwardBackwardSpeed, rotateAmount, squaredInput);

    }

    public void setVBusMode() {
        setVBusMode(m_leftMaster);
        setVBusMode(m_rightMaster);
        m_differentialDrive.setMaxOutput(m_maxSpeed);
    }
    
    private void setVBusMode(TalonSRX_4905 talon) {
        talon.setControlMode(ControlMode.PercentOutput);
    }
      
    @Override
    public void stop() {
        m_differentialDrive.stopMotor();
    }

    @Override
    public void shiftSpeed(RobotGear robotGear) {
        return;
    }
}