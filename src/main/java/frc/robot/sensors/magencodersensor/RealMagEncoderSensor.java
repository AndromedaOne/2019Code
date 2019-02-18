package frc.robot.sensors.magencodersensor;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class RealMagEncoderSensor extends MagEncoderSensor {
  private WPI_TalonSRX talonSpeedController;
  private double initialPosition = 0;
  boolean reverseDirection = false;

  /**
   * Sets the talonSpeedController talon to the talon passed in, configures the
   * talon's feedback sensor to be a Mag Encoder and sets the sensor Phase to be
   * true.
   * 
   * @param talon Talon object to attach encoder to
   */
  public RealMagEncoderSensor(WPI_TalonSRX talon, boolean reverseDirectionParam) {
    reverseDirection = reverseDirectionParam;
    talonSpeedController = talon;
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    talon.setSensorPhase(true); /* keep sensor and motor in phase */
  }

  @Override
  public double getDistanceTicks() {
    double ticks = getPosition();
    return ticks - initialPosition;
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Ticks:", this::getDistanceTicks);
  }

  @Override
  public void reset() {
    initialPosition = getPosition();
  }

  @Override
  public void resetTo(double value) {
    double error = value - getDistanceTicks();
    initialPosition -= error;
  }

  private double getPosition() {
    double ticks = talonSpeedController.getSelectedSensorPosition();
    if (reverseDirection) {
      ticks *= -1.0;
    }
    return ticks;
  }

  @Override
  public double getVelocity() {
    // multiply by ten to get the velocity in ticks per second
    double velocity = talonSpeedController.getSelectedSensorVelocity()*10;
    if(reverseDirection) {
      velocity *=-1.0;
    }
    return velocity;
  }

  @Override
  public double getAbsolutePosition() {
    double absolutePosition = talonSpeedController.getSelectedSensorPosition();
    if(reverseDirection) {
      absolutePosition *= -1.0;
    }
    return absolutePosition;
  }

}