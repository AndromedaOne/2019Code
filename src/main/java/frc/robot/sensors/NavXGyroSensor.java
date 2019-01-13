package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class NavXGyroSensor extends SensorBase<DoubleSensorSrc> {
    AHRS gyro; /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
    static NavXGyroSensor instance = new NavXGyroSensor();
    private NavXGyroSensor(){
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
        return gyro.getAngle();
    }
    
    public DoubleSensorSrc getClosedLoopSrc() {
        return new DoubleSensorSrc(){
        
            @Override
            public Double getReading() {
                return getZAngle();
            }
        };
    }

    @Override
    public void reset() {

    }
}