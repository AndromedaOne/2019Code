package frc.robot.sensors.ultrasonicsensor;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.sensors.SensorBase;

public abstract class UltrasonicSensor extends SensorBase {

  public abstract double getDistanceInches();

  @Override
  public double pidGet() {
    return getDistanceInches();
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Counter");
    // This needs to be value in order to work; Value is a magical string that
    // allows this counter to appear on Live Window.
    builder.addDoubleProperty("Value", this::getDistanceInches, null);
  }

}