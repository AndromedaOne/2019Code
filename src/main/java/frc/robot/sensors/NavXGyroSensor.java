package frc.robot.sensors;

import java.util.TimerTask;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class NavXGyroSensor extends SensorBase implements PIDSource {
  AHRS gyro; /* Alternatives: SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
  static NavXGyroSensor instance = new NavXGyroSensor();
  private double initialAngleReading = 0.0;
  boolean angleReadingSet = false;
  private long kInitializeDelay = 3000;
  private long kDefaultPeriod = 50;
  private java.util.Timer controlLoop;
  private double robotAngleCount = 0;

  /**
   * Trys creating the gyro and if it can not then it reports an error to the
   * DriveStation.
   */
  private NavXGyroSensor() {
    try {
      /* Communicate w/navX MXP via the MXP SPI Bus. */
      /* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
      /*
       * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
       * details.
       */
      gyro = new AHRS(SPI.Port.kMXP);

      System.out.println("Created NavX instance");
      // New thread to initialize the initial angle
      controlLoop = new java.util.Timer();
      SetInitialAngleReading task = new SetInitialAngleReading();
      controlLoop.schedule(task, kInitializeDelay, kDefaultPeriod);

    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
    }
  }

  private class SetInitialAngleReading extends TimerTask {

    @Override
    public void run() {
      System.out.println("Setting Initial Gyro Angle");
      if (!isCalibrating()) {
        initialAngleReading = gyro.getAngle();
        cancel();
      }
    }
  }

  /**
   * Gets the instance of the NavXGyroSensor.
   * 
   * @return instance
   */
  public static NavXGyroSensor getInstance() {
    return instance;
  }

  public boolean isCalibrating() {
    return gyro.isCalibrating();
  }

  /**
   * Gets the Z angle and supbracts the initial angle member variable from it.
   * 
   * @return gyro.getAngle() - initialAngleReading
   */
  public double getZAngle() {
    double correctedAngle = gyro.getAngle() - initialAngleReading;
    if ((robotAngleCount % 10) == 0) {
      SmartDashboard.putNumber("Raw Angle", gyro.getAngle());
      SmartDashboard.putNumber("Get Robot Angle", correctedAngle);
    }
    robotAngleCount++;
    Trace.getInstance().addTrace(true, "Gyro", new TracePair("Raw Angle", gyro.getAngle()),
        new TracePair("Corrected Angle", correctedAngle));

    return correctedAngle;
  }

  /**
   * Returns the current compass heading of the robot Between 0 - 360
   */
  public double getCompassHeading() {
    double correctedAngle = getZAngle() % 360;
    if (correctedAngle < 0) {
      correctedAngle += 360;
    }
    return correctedAngle;
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "ZAngle:", this::getZAngle);
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
   * Gets the angle to pass to a PID controller
   */
  public double pidGet() {
    return getZAngle();
  }

}