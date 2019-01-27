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

  public void putOnLiveWindow(String subsystemNameParam, String sensorNameParam){
    subsystemName = subsystemNameParam;
    sensorName = sensorNameParam;
  }

}