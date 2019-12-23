package frc.robot.subsystems.drivetrain.drivecontrol;

import frc.robot.subsystems.drivetrain.DriveTrain.RobotGear;
import frc.robot.talonsrx_4905.TalonSRX_4905;

public abstract class DriveControl {

    protected TalonSRX_4905 m_leftMaster;
    public TalonSRX_4905 getLeftMaster() {return m_leftMaster;}
    protected TalonSRX_4905 m_rightMaster;
    public TalonSRX_4905 getRightMaster() {return m_rightMaster;}

    public DriveControl(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster){
        m_leftMaster = leftMaster;
        m_rightMaster = rightMaster;
    }

    public abstract void move(double forwardBackwardSpeed, double rotateAmount, boolean squaredInput);

    public abstract void stop();
    
    public abstract void shiftSpeed(RobotGear robotGear);

    
}