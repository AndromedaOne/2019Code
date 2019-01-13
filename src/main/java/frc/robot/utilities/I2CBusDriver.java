package frc.robot.utilities;

import edu.wpi.first.wpilibj.I2C;

public class I2CBusDriver {
    private I2C i2cBus;

    public I2CBusDriver(boolean isOnboard, int address) {
        if (isOnboard) {
            i2cBus = new I2C(I2C.Port.kOnboard, address);
        } else {
            i2cBus = new I2C(I2C.Port.kMXP, address);
        }
    }

    public I2C getBus() {
        return i2cBus;
    }
}