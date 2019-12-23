package frc.robot.subsystems.drivetrain.drivecontrol;

import com.typesafe.config.Config;

import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.velocitypidwpi.VelocityPidWPIForTalon;
import frc.robot.subsystems.drivetrain.DriveTrain.RobotGear;
import frc.robot.talonsrx_4905.TalonSRX_4905;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class VelocityWPIPidControl extends DriveControl {

    DifferentialDrive m_differentialDrive;

    public VelocityWPIPidControl(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster) {
        super(leftMaster, rightMaster);
        shiftSpeed(Robot.driveTrain.getGear());
        
        m_differentialDrive = new DifferentialDrive(leftMaster, rightMaster);
    }

    @Override
    public void move(double forwardBackwardSpeed, double rotateAmount, boolean squaredInput) {
        m_differentialDrive.arcadeDrive(forwardBackwardSpeed, rotateAmount, squaredInput);

    }

    @Override
    public void stop() {
        m_differentialDrive.stopMotor();
        m_leftMaster.stopMotor();
        m_rightMaster.stopMotor();
    }

    

    @Override
    public void shiftSpeed(RobotGear robotGear) {
        
        switch(robotGear){
            case HIGHGEAR:
                ((VelocityPidWPIForTalon) m_leftMaster.getTalonSRX_4905Setter()).shiftToHighGear();
                ((VelocityPidWPIForTalon) m_rightMaster.getTalonSRX_4905Setter()).shiftToHighGear();
                break;

            case SLOWHIGHGEAR:
                ((VelocityPidWPIForTalon) m_leftMaster.getTalonSRX_4905Setter()).shiftToHighGear();
                ((VelocityPidWPIForTalon) m_rightMaster.getTalonSRX_4905Setter()).shiftToHighGear();
                break;

            case LOWGEAR:
                ((VelocityPidWPIForTalon) m_leftMaster.getTalonSRX_4905Setter()).shiftToLowGear();
                ((VelocityPidWPIForTalon) m_rightMaster.getTalonSRX_4905Setter()).shiftToLowGear();
                break;
            
            case SLOWLOWGEAR:
                ((VelocityPidWPIForTalon) m_leftMaster.getTalonSRX_4905Setter()).shiftToLowGear();
                ((VelocityPidWPIForTalon) m_rightMaster.getTalonSRX_4905Setter()).shiftToLowGear();
                break;
        }
        

    }

}