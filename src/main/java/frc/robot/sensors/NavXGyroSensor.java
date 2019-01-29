package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class NavXGyroSensor implements PIDLoopable {
  AHRS gyro; /* Alternatives: SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
  static NavXGyroSensor instance = new NavXGyroSensor();
  private double initialZAngleReading = 0.0;
  private double initialXAngleReading = 0.0;
  private double initialYAngleReading = 0.0;
  boolean angleReadingSet = false;

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

  public static NavXGyroSensor getInstance() {
    return instance;
  }

  public double getZAngle() {
    return gyro.getAngle() - initialZAngleReading;
  }

  public double getXAngle(){
    return gyro.getPitch() - initialXAngleReading;
  }
  
  public double getYAngle() {
    return gyro.getRoll() - initialYAngleReading;
  }

  public double getClosedLoopSrc() {
    return getZAngle();

  }

  @Override
  public void reset() {
    initialZAngleReading = getZAngle();
    initialXAngleReading = getXAngle();
    initialYAngleReading = getYAngle();
    System.out.println("Initial angle set to: " + initialZAngleReading);
    angleReadingSet = true;

  }
}