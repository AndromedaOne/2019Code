package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;

public class NavXGyroSensor extends SensorBase implements PIDSource {
  AHRS gyro; /* Alternatives: SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
  static NavXGyroSensor instance = new NavXGyroSensor();
  private double initialZAngleReading = 0.0;
  private double initialXAngleReading = 0.0;
  private double initialYAngleReading = 0.0;
  boolean angleReadingSet = false;

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

    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
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

  /**
   * Gets the Z angle and supbracts the initial angle member variable from it.
   * 
   * @return gyro.getAngle() - initialAngleReading
   */
  public double getZAngle() {
    return gyro.getAngle() - initialZAngleReading;
  }

  public double getXAngle() {
    return gyro.getPitch() - initialXAngleReading;
  }

  public double getYAngle() {
    return gyro.getRoll() - initialYAngleReading;
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

  public void reset() {
    initialZAngleReading = getZAngle();
    initialXAngleReading = getXAngle();
    initialYAngleReading = getYAngle();
    System.out.println("Initial angle set to: " + initialZAngleReading);
  }

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