package frc.robot.utilities;

import edu.wpi.first.wpilibj.I2C;

public class FakeI2CBus extends I2C {

    public FakeI2CBus(int address) {
        super(I2C.Port.kOnboard, address);
    }

    public byte[] readOnly(byte[] buffer, int length) {
        for (int i = 0; i < buffer.length; i++) {
            if (i%2 != 0) {
                buffer[i] = 2;
            }
        }

        buffer[0] = 15;
        buffer[2] = 14;
        buffer[4] = 16;
        buffer[6] = 25;
        buffer[8] = 26;
        buffer[10] = 17;
        buffer[12] = 16;
        buffer[14] = 15;
        buffer[16] = 14;

        return buffer;
    }

}