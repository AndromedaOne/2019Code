package frc.robot.sensors.magencodersensor;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class RealMagEncoderSensor extends MagEncoderSensor {
  private WPI_TalonSRX talonSpeedController;
  private double initialPosition = 0;;

  /**
   * Sets the talonSpeedController talon to the talon passed in, configures the
   * talon's feedback sensor to be a Mag Encoder and sets the sensor Phase to be
   * true.
   * 
   * @param talon
   */
  public RealMagEncoderSensor(WPI_TalonSRX talon) {
    talonSpeedController = talon;
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    talon.setSensorPhase(true); /* keep sensor and motor in phase */
  }

  @Override
  public double getDistanceTicks() {
    double ticks = talonSpeedController.getSelectedSensorPosition();
    return ticks - initialPosition;
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Ticks:", this::getDistanceTicks);
  }

  @Override
  public void reset() {
    initialPosition = talonSpeedController.getSelectedSensorPosition();
  }

}