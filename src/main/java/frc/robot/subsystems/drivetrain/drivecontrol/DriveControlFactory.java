package frc.robot.subsystems.drivetrain.drivecontrol;

import com.typesafe.config.Config;

import frc.robot.Robot;

import frc.robot.TalonSRX_4905;
import frc.robot.closedloopcontrollers.pidcontrollers.VelocityWPIPidTalon;

public class DriveControlFactory {

    public static DriveControl create(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster){
        Config conf = Robot.getConfig();
        Config driveConf = conf.getConfig("ports.driveTrain");
        boolean isPercentVBus = driveConf.getBoolean("isPercentVBus");
        if(isPercentVBus){
            leftMaster = new TalonSRX_4905(leftMaster.getDeviceID());
            rightMaster = new TalonSRX_4905(rightMaster.getDeviceID());
            return new PercentVBusControl(leftMaster, rightMaster);
        }
        
        leftMaster = new VelocityWPIPidTalon(leftMaster.getDeviceID(), 0, 0, 0, 0, "DriveTrain", "velocityWPIPidLeftMaster");
        rightMaster = new VelocityWPIPidTalon(rightMaster.getDeviceID(), 0, 0, 0, 0, "DriveTrain", "velocityWPIPidRightMaster");
        return new VelocityWPIPidControl(leftMaster, rightMaster);
        
    } 
}