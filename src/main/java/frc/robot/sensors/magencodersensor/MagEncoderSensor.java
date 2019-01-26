package frc.robot.sensors.magencodersensor;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.sensors.SensorBase;

public abstract class MagEncoderSensor extends SensorBase {

  public abstract double getDistanceInches();

  public abstract double getDistanceTicks();

  @Override
  public double pidGet() {
    return getDistanceTicks();
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Counter");
    // This needs to be value in order to work; Value is a magical string that
    // allows this counter to appear on Live Window.
    builder.addDoubleProperty("Value", this::getDistanceTicks, null);
  }
}