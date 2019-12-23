package frc.robot.closedloopcontrollers.pidcontrollers.velocitypidwpi;

import com.typesafe.config.Config;

import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.drivecontrol.DriveControlFactory;

public class VelocityPidWPIDriveTrainRightTalon extends VelocityPidWPIForTalon {
    double m_p = 0.0;
    double m_i = 0.0;
    double m_d = 0.0;
    double m_f = 0.0;

    public VelocityPidWPIDriveTrainRightTalon() {
        super(DriveControlFactory.getRightMaster(), 0, 0, 0, 0, "DriveTrain", "WPIVelocityPidRight");
        setPIDF(m_p, m_i, m_d, m_f);
    }

    @Override
    public void shiftToLowGear() {
        setPID("LowGear");
    }

    @Override
    public void shiftToHighGear() {
        setPID("HighGear");
    }

    private void setPID(String gear) {

        Config conf = Robot.getConfig();
        Config driveConf = conf.getConfig("ports.driveTrain");

        m_p = readPIDConfigItem(driveConf, "Left", gear + "P", 0);
        m_i = readPIDConfigItem(driveConf, "Left", gear + "I", 0);
        m_d = readPIDConfigItem(driveConf, "Left", gear + "D", 0);
        m_f = readPIDConfigItem(driveConf, "Left", gear + "F", 0);

        setPIDF(m_p, m_i, m_d, m_f);
        
    }

    private double readPIDConfigItem(Config driveConf, String side, String configItem, double defaultValue) {
        double configValue = defaultValue;
        if (driveConf.hasPath(side + "Side" + configItem)) {
            configValue = driveConf.getDouble(side + "Side" + configItem);
        }
        System.out.println(side + "Side" + configItem + "=" + configValue);
        return configValue;
    }

}