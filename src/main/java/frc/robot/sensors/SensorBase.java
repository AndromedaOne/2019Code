package frc.robot.sensors;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;

public abstract class SensorBase implements PIDSource, Sendable {

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

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {

  }

  @Override
  public PIDSourceType getPIDSourceType() {
    return PIDSourceType.kDisplacement;
  }

  public abstract void putOnSmartDashboard(String subsystemNameParam, String sensorNameParam);

}