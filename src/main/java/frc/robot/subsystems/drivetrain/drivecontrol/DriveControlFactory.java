package frc.robot.subsystems.drivetrain.drivecontrol;

import com.typesafe.config.Config;

import frc.robot.Robot;

import frc.robot.TalonSRX_4905;

public class DriveControlFactory {

    public static DriveControl create(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster){
        Config conf = Robot.getConfig();
        Config driveConf = conf.getConfig("ports.driveTrain");
        boolean isPercentVBus = driveConf.getBoolean("isPercentVBus");

        if(isPercentVBus){
            return new PercentVBusControl(leftMaster, rightMaster);
        }
        
        return new VelocityWPIPidControl(leftMaster, rightMaster);
        
    } 
}