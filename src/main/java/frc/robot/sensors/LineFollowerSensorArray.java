package frc.robot.sensors;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LineFollowerSensorArray {
    private I2C mI2cBus;
    private byte[] buffer = new byte[16];
    //Distance to sensor array in centimetres
    private double mDistanceToSensor = 10;
    //Distance between sensors in centimetres
    private double mDistanceBtSensors = 0.5;
    private int detectionThreshold;

    /**
     * Takes same parameters as I2CBusDriver() and passes it along to said constructor
     * @param i2cBus A prebuilt I2C bus
     * @author Owen Salter
     */
    public LineFollowerSensorArray(I2C i2cBus, int detectionThreshold) {
        mI2cBus = i2cBus;
        this.detectionThreshold = detectionThreshold;
    }

    /**
     * Sets the buffer of values to 0. Not much else to say or do here. It's boring. Move along.
     */
    public void reset() {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = 0;
        }
    }

    /**
     * @return An array of booleans where true means that a line was detected and false means that a line wasn't detected
     * @author Owen Salter
     */
    public boolean[] isThereLine() {
        boolean[] boolBuf = new boolean[buffer.length/2];
       double[] dValues = new double[buffer.length/2];
        
       
        mI2cBus.readOnly(buffer, 16);
        // Step through each even-numbered element in the array
        for (int i = 0; i < buffer.length/2; i++) {
            if(buffer[i * 2] >= 0) {
                dValues[i] = buffer[i * 2];
            } else {
                dValues[i] = buffer[i * 2] + 256;
            }


        }


        SmartDashboard.putNumberArray("LineFollowArray", dValues);
        // Check for whether the line is found
        for (int i = 0; i < dValues.length; i++) {
                if (dValues[i] >= detectionThreshold) {
                    boolBuf[i] = true;
                } else {
                    boolBuf[i] = false;
                }
            } 
        return boolBuf;
    }

    /**
     * Doesn't do anything right now.
     * @return structure containing a boolean of whether the line is found, and the angle in radians
     */
    public LineFollowArraySensorReading getSensorReading() {
        /*
            Need to:
            - figure out adj from DistanceToSensor
            - get hyp from adj and op
            - use hyp to calculate angle
            - return angle
        */
        boolean[] boolBuf = new boolean[buffer.length/2];
        int senseCount = 0;
        double adj1 = 0;

        boolBuf = isThereLine();
        for (int i = 0; i < boolBuf.length; i++) {
            if (boolBuf[i] == true) {
                senseCount++;
            }
        }

        if (boolBuf[0] == true) {
            adj1 = 1.75;
        } else if (boolBuf[1] == true) {
            adj1 = 1.25;
        } else if (boolBuf[2] == true) {
            adj1 = 0.75;
        } else if (boolBuf[3] == true) {
            adj1 = 0.25;
        } else if (boolBuf[4] == true) {
            adj1 = -0.25;
        } else if (boolBuf[5] == true) {
            adj1 = -0.75;
        } else if (boolBuf[6] == true) {
            adj1 = -1.25;
        } else if (boolBuf[7] == true) {
            adj1 = -1.75;
        } 

        double angle = Math.atan2(adj1, mDistanceToSensor);

        LineFollowArraySensorReading sensorReading = new LineFollowArraySensorReading();
        sensorReading.lineAngle = angle;

        if (senseCount != 0) {
            sensorReading.lineFound = true;
        } else {
            sensorReading.lineFound = false;
        }
        return sensorReading;
    }

    public class LineFollowArraySensorReading {
        public boolean lineFound;
        public double lineAngle;
    }

}