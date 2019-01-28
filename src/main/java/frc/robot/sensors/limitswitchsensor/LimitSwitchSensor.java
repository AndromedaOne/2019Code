package frc.robot.sensors.limitswitchsensor;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.sensors.SensorBase;

public abstract class LimitSwitchSensor extends SensorBase {

  /**
   * @return trues if the limit switch is at its limit
   */
  public abstract boolean isAtLimit();
  
}