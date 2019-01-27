package frc.robot.sensors.limitswitchsensor;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.sensors.SensorBase;

public abstract class LimitSwitchSensor extends SensorBase {

  /**
   * @return trues if the limit switch is at its limit
   */
  public abstract boolean isAtLimit();

  @Override
  /**
   * passes the method for getting the limit to the livewindow and gives the
   * livewindow some parameters to allow it to work.
   */
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Counter");
    // This needs to be something in order to work; Value is a magical string
    // that allows this counter to appear on Live Window.
    builder.addBooleanProperty("Value", this::isAtLimit, null);
  }

}