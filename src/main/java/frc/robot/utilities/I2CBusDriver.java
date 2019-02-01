package frc.robot.utilities;

import edu.wpi.first.wpilibj.I2C;

public class I2CBusDriver {
  private I2C i2cBus;
  private int mAddress;

  /**
   * Creates a new I2C bus with specified parameters
   * 
   * @param isOnboard is the bus using the port on the RIO or the NavX?
   * @param address The hexadecimal value of the address (0x1E by default)
   * @author Owen Salter
   */
  public I2CBusDriver(boolean isOnboard, int address) {
    mAddress = address;
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