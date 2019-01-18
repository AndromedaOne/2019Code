package frc.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitchSensor {
    private DigitalInput limitSwitch;
    private boolean m_reversedPolarity;

    /**
     * 
     * @param port Port number for the DIO port
     * @param reversedPolarity Whether or not the polarity is reversed
     */
    public LimitSwitchSensor(int port, boolean reversedPolarity){
        limitSwitch = new DigitalInput(port);
        m_reversedPolarity = reversedPolarity;
    }

    /**
     * @param port Port number for the DIO port
     */
    public LimitSwitchSensor(int port){
        this(port, false);
    }

    /**
     * @return Whether or not the limit switch has been triggered
     */
    public boolean isAtLimit() {
        if(m_reversedPolarity) {
            return !limitSwitch.get();
        }
        return limitSwitch.get();
    }

}