package frc.robot.sensors.magencodersensor;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.sensors.SensorBase;

public abstract class MagEncoderSensor extends SensorBase implements PIDSource {

  /**
   * @return the distance in inches that the MagEncoder has traveled
   */
  public abstract double getDistanceInches();

  /**
   * @return the distance in ticks that the MagEncoder has traveled
   */
  public abstract double getDistanceTicks();

  @Override
  /**
   * gets the distance in ticks to pass to a PID controller
   */
  public double pidGet() {
    return getDistanceTicks();
  }

  @Override
   /** 
   * Does not do anything
   */
  public void setPIDSourceType(PIDSourceType pidSource) {

  }

  @Override
  /**
   * @return kDisplacement because that is what we use for all of our PID 
   * Controllers
   */
  public PIDSourceType getPIDSourceType() {
    return PIDSourceType.kDisplacement;
  }

  @Override
  /**
   * passes the method for getting the distance in ticks to the livewindow and 
   * gives the livewindow some parameters to allow it to work.
   */
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Counter");
    // This needs to be value in order to work; Value is a magical string that
    // allows this counter to appear on Live Window.
    builder.addDoubleProperty("Value", this::getDistanceTicks, null);
  }
}