package frc.robot.utilities;

public class FakeI2CBusDriver extends I2CBusDriver {

    public FakeI2CBusDriver(boolean isOnboard, int address) {
        super(isOnboard, address);
    }

    public byte[] getData() {
        byte[] buffer = new byte[16];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = 0;
        }

        return buffer;
    }

}