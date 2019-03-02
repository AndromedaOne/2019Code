package frc.robot.sensors.anglesensor;

import edu.wpi.first.wpilibj.AnalogInput;

public class RealAngleSensor extends AngleSensor {
  private AnalogInput angleSensor;

  public RealAngleSensor(int port) {
    angleSensor = new AnalogInput(port);
  }

  @Override
  public double getAngle() {
    double sensorValue = angleSensor.getVoltage();
    if(sensorValue < 0.94) {
      sensorValue = (sensorValue - 0.316) + 2.85;
    }
    return sensorValue;
  }

  @Override
  public void reset() {
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Angle:", this::getAngle);
  }
}