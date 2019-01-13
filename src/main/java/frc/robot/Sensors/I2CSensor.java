package frc.robot.sensors;

import edu.wpi.first.wpilibj.I2C;
import frc.robot.sensors.SensorBase;
import frc.robot.utilities.I2CBusDriver;

public class I2CSensor extends SensorBase {
    private I2C mI2cBus;
    private byte[] buffer = new byte[16];

    /**
     * Takes same parameters as I2CBusDriver() and passes it along to said constructor
     * @param isOnboard
     * @param address
     * @author Owen Salter
     */
    public I2CSensor(boolean isOnboard, int address) {
        mI2cBus = new I2CBusDriver(isOnboard, address).getBus();
    }

    /**
     * @return An array of booleans where true means that a line was detected and false means that a line wasn't detected
     * @author Owen Salter
     */
    public boolean[] whereIsLine() {
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
}