package frc.robot.sensors.linefollowersensor;

public abstract class BaseLineFollowerSensor {

  public abstract boolean[] isThereLine();

  public abstract LineFollowArraySensorReading getSensorReading();
}