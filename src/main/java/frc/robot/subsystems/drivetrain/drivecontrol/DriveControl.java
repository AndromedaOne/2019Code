package frc.robot.subsystems.drivetrain.drivecontrol;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.TalonSRX_4905;

public abstract class DriveControl {

    TalonSRX_4905 m_leftMaster;
    TalonSRX_4905 m_rightMaster;

    public DriveControl(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster){
        m_leftMaster = leftMaster;
        m_rightMaster = rightMaster;
    }

    public abstract void move(double forwardBackwardSpeed, double rotateAmount, boolean squaredInput);

    public abstract void stop();

    public static DriveControl createDriveControl(DriveControlEnum driveControlEnum, TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster) {
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