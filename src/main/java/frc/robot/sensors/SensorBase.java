package frc.robot.sensors;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;

public abstract class SensorBase implements Sendable {

  protected String subsystemName;
  protected String sensorName;

  @Override
  public String getName() {
    return sensorName;
  }

  @Override
  public void setName(String name) {
    sensorName = name;
  }

  @Override
  public String getSubsystem() {
    return subsystemName;
  }

  @Override
  public void setSubsystem(String subsystem) {
    subsystemName = subsystem;
  }

  /**
   * sets the subsystem name and sensor name to the parameters that are passed 
   * in to ensure that a sensor has a name when it appears on live window.
   */
  public void putOnLiveWindow(String subsystemNameParam, String sensorNameParam){
    subsystemName = subsystemNameParam;
    sensorName = sensorNameParam;
  }

}