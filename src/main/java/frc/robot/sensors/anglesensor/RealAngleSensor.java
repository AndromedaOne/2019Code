package frc.robot.sensors.anglesensor;

import edu.wpi.first.wpilibj.AnalogInput;

public class RealAngleSensor extends AngleSensor {
  private AnalogInput angleSensor;
  private double initialPosition;

  public RealAngleSensor(int port) {
    angleSensor = new AnalogInput(port);
  }

  @Override
  public double getAngle() {
    return angleSensor.getVoltage() - initialPosition;
  }

  @Override
  public void reset() {
    initialPosition = angleSensor.getVoltage();
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Angle:", this::getAngle);
  }
}