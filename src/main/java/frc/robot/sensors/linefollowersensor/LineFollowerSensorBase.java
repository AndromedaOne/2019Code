package frc.robot.sensors.linefollowersensor;

public abstract class LineFollowerSensorBase {

  public abstract boolean[] isThereLine();

  public abstract boolean lineFound();

  public abstract double lineAngle();
}