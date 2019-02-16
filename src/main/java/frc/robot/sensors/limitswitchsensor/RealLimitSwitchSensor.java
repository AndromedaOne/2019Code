package frc.robot.sensors.limitswitchsensor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RealLimitSwitchSensor extends LimitSwitchSensor {
  private DigitalInput limitSwitch;
  private boolean reversedPolarity;


  /**
   * Sets the limit switch to a new DigitalInput with the port specified and
   * records whether or not the limit switch has reversed polarity
   * 
   * @param port Port to use
   * @param reversedPolarityParam Whether or not to reverse the polarity
   */
  public RealLimitSwitchSensor(int port, boolean reversedPolarityParam) {
    limitSwitch = new DigitalInput(port);
    reversedPolarity = reversedPolarityParam;
    limitSwitch.setName("test", "TestSwitch");
    LiveWindow.add(limitSwitch);
  }

  @Override
  public boolean isAtLimit() {
    if (reversedPolarity) {
      return !limitSwitch.get();
    }
    return limitSwitch.get();
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "IsAtLimit:", this::isAtLimit);
  }
}