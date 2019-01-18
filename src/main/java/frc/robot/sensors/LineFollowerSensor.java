package frc.robot.sensors;

import edu.wpi.first.wpilibj.I2C;
import frc.robot.utilities.I2CBusDriver;

public class LineFollowerSensor implements PIDLoopable {
    private I2C mI2cBus;
    private byte[] buffer = new byte[16];
    //Distance to sensor array in centimetres
    private final double DistanceToSensor = 10;
    //Distance between sensors in centimetres
    private final double DistanceBtSensors = 0.5;

    /**
     * Takes same parameters as I2CBusDriver() and passes it along to said constructor
     * @param isOnboard
     * @param address The Hexadecimal address of the I2C device
     * @author Owen Salter
     */
    public LineFollowerSensor(boolean isOnboard, int address) {
        mI2cBus = new I2CBusDriver(isOnboard, address).getBus();
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

        mI2cBus.readOnly(buffer, 16);
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] >= 19) {
                boolBuf[i] = true;
            } else {
                boolBuf[i] = false;
            }
        }

        return boolBuf;
    }

    /**
     * Doesn't do anything right now.
     * @return the angle to get to the line in radians
     */
    public double getClosedLoopSrc() {
        /*
            Need to:
            - figure out adj from DistanceToSensor
            - get hyp from adj and op
            - use hyp to calculate angle
            - return angle
        */
        boolean[] boolBuf = new boolean[buffer.length/2];
        int senseCount = 0;
        int adj1;

        boolBuf = isThereLine();
        for (int i = 0; i < boolBuf.length; i++) {
            if (boolBuf[i] == true) {
                senseCount++;
            }
        }
        adj1 = (int) DistanceBtSensors*senseCount;
    }
}