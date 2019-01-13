package frc.robot.Sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Ultrasonic;

public class GyroSensor extends SensorBase<DoubleSensorSrc> {
    AHRS gyro; /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */

    private class GyroSensorSrc extends DoubleSensorSrc {
        public Double getReading () {
            return getDistanceInches();
        }
    }
    private GyroSensorSrc gyroSensorSrc = new GyroSensorSrc();

    public GyroSensor (int ping, int echo){
       
    }
    public double getZangle () {
        return ultraSonic.getRangeInches();
    }
    public DoubleSensorSrc getClosedLoopSrc () {
        return gyroSensorSrc;
    }
}