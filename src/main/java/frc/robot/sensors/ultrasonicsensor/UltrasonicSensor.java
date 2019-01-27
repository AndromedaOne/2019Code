package frc.robot.sensors.ultrasonicsensor;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.sensors.SensorBase;

public abstract class UltrasonicSensor extends SensorBase implements PIDSource {

  /**
   * @return the distance in inches from whatever is in front of the Ultrasonic
   */
  public abstract double getDistanceInches();

  
  @Override
  /**
   * Sets the distance in inches returned by the ultrasonic and passes it to a 
   * PID Controller
   */
  public double pidGet() {
    return getDistanceInches();
  }

   
  @Override
  /** 
   * Does not do anything
   */
  public void setPIDSourceType(PIDSourceType pidSource) {}

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
   * passes the method for getting the distance in inches to the livewindow and 
   * gives the livewindow some parameters to allow it to work.
   */
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Counter");
    // This needs to be value in order to work; Value is a magical string that
    // allows this counter to appear on Live Window.
    builder.addDoubleProperty("Value", this::getDistanceInches, null);
  }

}