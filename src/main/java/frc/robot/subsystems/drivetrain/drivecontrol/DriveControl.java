package frc.robot.subsystems.drivetrain.drivecontrol;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class DriveControl {

    WPI_TalonSRX m_leftMaster;
    WPI_TalonSRX m_rightMaster;

    public DriveControl(WPI_TalonSRX leftMaster, WPI_TalonSRX rightMaster){
        m_leftMaster = leftMaster;
        m_rightMaster = rightMaster;
    }

    public abstract void move(double forwardBackwardSpeed, double rotateAmount, boolean squaredInput);

    public abstract void stop();

    public static DriveControl createDriveControl(DriveControlEnum driveControlEnum, WPI_TalonSRX leftMaster, WPI_TalonSRX rightMaster) {
        switch(driveControlEnum) {
            case VELOCITY:
                return new VelocityControl(leftMaster, rightMaster);
            case PERCENTVBUS:
                return new PercentVBusControl(leftMaster, rightMaster);
            default:
                return new PercentVBusControl(leftMaster, rightMaster);
        }
      
    }
}