package frc.robot.sensors.limitswitchsensor;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.sensors.SensorBase;

public abstract class LimitSwitchSensor extends SensorBase{

  public abstract boolean isAtLimit();

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Counter");
    // This needs to be something in order to work; Value is a magical string 
    // that allows this counter to appear on Live Window.
    builder.addBooleanProperty("Value", this::isAtLimit, null);
  }

}