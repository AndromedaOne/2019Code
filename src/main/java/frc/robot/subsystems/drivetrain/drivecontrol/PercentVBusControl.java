package frc.robot.subsystems.drivetrain.drivecontrol;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class PercentVBusControl extends DriveControl {

    DifferentialDrive m_differentialDrive;
    // TODO need to fix this too:
    double m_maxSpeed = 1.0;

    public PercentVBusControl(WPI_TalonSRX leftMaster, WPI_TalonSRX rightMaster) {
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
    
    private void setVBusMode(WPI_TalonSRX talon) {
        // TODO wrong need to fix later
        talon.set(ControlMode.PercentOutput, 0);
    }
      
    @Override
    public void stop() {
        m_differentialDrive.stopMotor();
    }
}