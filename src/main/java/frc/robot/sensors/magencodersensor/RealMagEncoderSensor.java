package frc.robot.sensors.magencodersensor;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RealMagEncoderSensor extends MagEncoderSensor {
  private WPI_TalonSRX talonSpeedController;
  private static final double TICKSTOINCHES = 0.0;

  public RealMagEncoderSensor(WPI_TalonSRX talon) throws noTalonException {
    talonSpeedController = talon;
    if (talon == null) {
      System.out.println("The Talon is Null!!!!!!!");
      throw new noTalonException();
    }
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

  public void putOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putOnLiveWindow(subsystemNameParam, sensorNameParam);
    LiveWindow.add(this);
    this.setName(sensorName);
  }

  public class noTalonException extends Exception {

    private static final long serialVersionUID = 1L;

  }

  public class EncoderDoubleSupplier implements DoubleSupplier {

    @Override
    public double getAsDouble() {
      return getDistanceTicks();
    }

  }

  public class EncoderDoubleConsumer implements DoubleConsumer {

    @Override
    public void accept(double arg0) {
      return;
    }

  }

}