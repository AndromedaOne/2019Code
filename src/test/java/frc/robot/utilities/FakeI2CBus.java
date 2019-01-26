package frc.robot.utilities;

import edu.wpi.first.wpilibj.I2C;

public class FakeI2CBus extends I2C {

  public byte[] fakeBuffer;

  public FakeI2CBus(int address) {
    super(I2C.Port.kOnboard, address);
  }

  public boolean readOnly(byte[] buffer, int count) {
    for (int i = 0; i < buffer.length; i++) {
      if (i % 2 != 0) {
        buffer[i] = 2;
      } else {
        buffer[i] = fakeBuffer[i / 2];
      }
    }

    return true;
  }

  public void setBuffer(byte[] newBuffer) {
    fakeBuffer = newBuffer;
  }

}