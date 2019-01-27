package frc.robot.sensors.limitswitchsensor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class RealLimitSwitchSensor extends LimitSwitchSensor {
    private DigitalInput limitSwitch;
    private boolean reversedPolarity;

    public RealLimitSwitchSensor(int port, boolean reversedPolarityParam) {
        limitSwitch = new DigitalInput(port);
        reversedPolarity = reversedPolarityParam;
    }

    public boolean isAtLimit() {
        if (reversedPolarity) {
            return !limitSwitch.get();
        }
        return limitSwitch.get();
    }

}