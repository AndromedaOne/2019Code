package frc.robot.sensors;

import edu.wpi.first.wpilibj.I2C;

public class PololuSensorArray extends LineFollowerSensorArray {
    
    public PololuSensorArray(I2C i2cBus, int detectionThreshold, double distanceToSensor, double distanceBtSensors, int numSensors) {
        super(i2cBus, detectionThreshold, distanceToSensor, distanceBtSensors, numSensors);
    }

    /**
     * This returns null. Don't use
     */
    @Override
    public boolean[] isThereLine() throws NullPointerException {
        boolean[] boolBuf = null;

        return boolBuf;
    }
}