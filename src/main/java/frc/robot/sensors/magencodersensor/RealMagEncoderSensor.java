package frc.robot.sensors.magencodersensor;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class RealMagEncoderSensor extends MagEncoderSensor {
  private WPI_TalonSRX talonSpeedController;
  private double initialPosition = 0;
  private double ticksToUnitsConversion;

  /**
   * Sets the talonSpeedController talon to the talon passed in, configures the
   * talon's feedback sensor to be a Mag Encoder and sets the sensor Phase to be
   * true.
   * 
   * @param talon Talon object to attach encoder to
   */
  public RealMagEncoderSensor(WPI_TalonSRX talon, double ticksToUnitsConversionParam) {
    ticksToUnitsConversion = ticksToUnitsConversionParam;
    talonSpeedController = talon;
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    talon.setSensorPhase(true); /* keep sensor and motor in phase */
  }

  @Override
  public double getDistance() {
    double ticks = talonSpeedController.getSelectedSensorPosition();
    return ticks*ticksToUnitsConversion - initialPosition;
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Ticks:", this::getDistance);
  }

  @Override
  public void reset() {
    initialPosition = talonSpeedController.getSelectedSensorPosition();
  }

}