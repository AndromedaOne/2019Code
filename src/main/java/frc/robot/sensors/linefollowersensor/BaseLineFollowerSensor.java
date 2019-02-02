package frc.robot.sensors.linefollowersensor;

import frc.robot.sensors.linefollowersensor.LineFollowArraySensorReading;

public abstract class BaseLineFollowerSensor {

    public abstract boolean[] isThereLine();

    public abstract LineFollowArraySensorReading getSensorReading();
}