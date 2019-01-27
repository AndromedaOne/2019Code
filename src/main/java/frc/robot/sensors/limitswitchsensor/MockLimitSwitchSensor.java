package frc.robot.sensors.limitswitchsensor;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MockLimitSwitchSensor extends LimitSwitchSensor {

    @Override
    public boolean isAtLimit() {
        return false;
    }

    @Override
    public void putOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
        super.putOnLiveWindow(subsystemNameParam, sensorNameParam);
        System.out.println("The Limit Switch named: " + sensorNameParam + 
        " does not exist so it cannot be added to smart Dashboard");
    }
}