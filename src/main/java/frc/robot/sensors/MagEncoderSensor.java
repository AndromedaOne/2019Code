package frc.robot.sensors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MagEncoderSensor implements PIDLoopable {
  private WPI_TalonSRX talonSpeedController;
  private static final double TICKSTOINCHES = 0.0;

  public MagEncoderSensor(WPI_TalonSRX talon, int port) {
    talonSpeedController = talon;
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    talon.setSensorPhase(true); /* keep sensor and motor in phase */
  }

  public double getDistanceInches() {
    return getDistanceTicks() * TICKSTOINCHES;
  }

  public double getDistanceTicks() {
    return talonSpeedController.getSelectedSensorPosition();
  }

  @Override
  public double getClosedLoopSrc() {
    return getDistanceInches();

  }

  @Override
  public void reset() {
    talonSpeedController.setSelectedSensorPosition(0);
    return;
  }
}