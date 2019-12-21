package frc.robot.subsystems.drivetrain.drivecontrol;

import frc.robot.TalonSRX_4905;
import frc.robot.subsystems.drivetrain.DriveTrain.RobotGear;

public abstract class DriveControl {

    TalonSRX_4905 m_leftMaster;
    TalonSRX_4905 m_rightMaster;

    public DriveControl(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster){
        m_leftMaster = leftMaster;
        m_rightMaster = rightMaster;
    }

    public abstract void move(double forwardBackwardSpeed, double rotateAmount, boolean squaredInput);

    public abstract void stop();
    
    public abstract void shiftSpeed(RobotGear robotGear);
    
}