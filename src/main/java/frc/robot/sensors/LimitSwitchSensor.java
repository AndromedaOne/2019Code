package frc.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitchSensor {
    private DigitalInput limitSwitch;
    private boolean m_reversedPolarity;

    public LimitSwitchSensor(int port, boolean reversedPolarity){
        limitSwitch = new DigitalInput(port);
        m_reversedPolarity = reversedPolarity;
    }

    public LimitSwitchSensor(int port){
        this(port, false);
    }

    public boolean isAtLimit() {
        if(m_reversedPolarity) {
            return !limitSwitch.get();
        }
        return limitSwitch.get();
    }

}