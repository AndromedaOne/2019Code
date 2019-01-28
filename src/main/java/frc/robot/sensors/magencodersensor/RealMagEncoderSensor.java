package frc.robot.sensors.magencodersensor;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RealMagEncoderSensor extends MagEncoderSensor {
  private WPI_TalonSRX talonSpeedController;
  private static final double TICKSTOINCHES = 0.0;

  public RealMagEncoderSensor(WPI_TalonSRX talon) {
    talonSpeedController = talon;
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    talon.setSensorPhase(true); /* keep sensor and motor in phase */
  }

  public double getDistanceInches() {
    return getDistanceTicks() * TICKSTOINCHES;
  }

  public double getDistanceTicks() {
    double ticks = talonSpeedController.getSelectedSensorPosition();
    return ticks;
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Ticks:", 
    this::getDistanceTicks);
  }

}