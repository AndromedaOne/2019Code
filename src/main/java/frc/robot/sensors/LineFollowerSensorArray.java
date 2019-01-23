package frc.robot.sensors;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LineFollowerSensorArray {
    private I2C mI2cBus;
    private byte[] buffer = new byte[16];
    //Default Distance to sensor array in centimetres
    private double distanceToSensor;
    // Distance between sensors in centimetres
    private double distanceBtSensors;
    private int detectionThreshold;

    /**
     * @param i2cBus A prebuilt I2C bus
     * @param detectionThreshold The minimum level required for activation of the sensor
     * @param distanceToSensor The distance from the centre of turning to the sensor
     * @param distanceBtSensors The distance between each sensor
     * @author Owen Salter
     */
    public LineFollowerSensorArray(I2C i2cBus, int detectionThreshold, double distanceToSensor, double distanceBtSensors) {
        mI2cBus = i2cBus;
        this.detectionThreshold = detectionThreshold;
        this.distanceToSensor = distanceToSensor;
        this.distanceBtSensors = distanceBtSensors;
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
                adj1 = getAdjacent(i);
                senseCount++;
            }
        }


        double angle = Math.atan2(adj1, distanceToSensor);
        System.out.println(adj1);
        System.out.println(angle);
        LineFollowArraySensorReading sensorReading = new LineFollowArraySensorReading();
        sensorReading.lineAngle = angle;

        if (senseCount != 0) {
            sensorReading.lineFound = true;
        } else {
            sensorReading.lineFound = false;
        }
        return sensorReading;
    }

    private double getAdjacent(int i) {
        double tempAdj = 0;
        if (i == 0) {
            tempAdj = (4*distanceBtSensors)-(distanceBtSensors/2);
        } else if (i == 1) {
            tempAdj = (3*distanceBtSensors)-(distanceBtSensors/2);
        } else if (i == 2) {
            tempAdj = (2*distanceBtSensors)-(distanceBtSensors/2);
        } else if (i == 3) {
            tempAdj = (1*distanceBtSensors)-(distanceBtSensors/2);
        } else if (i == 4) {
            tempAdj = -(1*distanceBtSensors)+(distanceBtSensors/2);
        } else if (i == 5) {
            tempAdj = -(2*distanceBtSensors)+(distanceBtSensors/2);
        } else if (i == 6) {
            tempAdj = -(3*distanceBtSensors)+(distanceBtSensors/2);
        } else if (i == 7) {
            tempAdj = -(4*distanceBtSensors)+(distanceBtSensors/2);
        }
        return tempAdj;
    }

    public class LineFollowArraySensorReading {
        public boolean lineFound;
        public double lineAngle;
    }

}