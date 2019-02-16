package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Robot;

public class NavXGyroSensor extends SensorBase implements PIDSource {
  AHRS gyro; /* Alternatives: SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
  static NavXGyroSensor instance = new NavXGyroSensor();
  private double initialAngleReading = 0.0;
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
      Config conf = Robot.getConfig();
      Config navXConfig = conf.getConfig("sensors.navx");
      String navXPort = navXConfig.getString("port");
      System.out.println("Creating a NavX Gyro on port: " + navXPort);
      if (navXPort.equals("MXP")) {
        gyro = new AHRS(SPI.Port.kMXP);
      } else if (navXPort.equals("SPI")) {
        gyro = new AHRS(SPI.Port.kOnboardCS0);
      } else {
        System.err.println("ERROR: Unkown NavX Port: " + navXPort);
        return;
      }
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
    return gyro.getAngle() - initialAngleReading;
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