package frc.robot.subsystems.pneumaticstilts;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RealPneumaticStilts extends PneumaticStilts {

  public static StiltLeg frontLeftStiltLeg;
  public static StiltLeg frontRightStiltLeg;
  public static StiltLeg rearLeftStiltLeg;
  public static StiltLeg rearRightStiltLeg;

  private class StiltLeg {
    private DoubleSolenoid solenoid;

    StiltLeg(DoubleSolenoid mySolenoid) {
      solenoid = mySolenoid;
    }

    public void stopLeg() {
      solenoid.set(DoubleSolenoid.Value.kOff);
    }

    public void retractLeg() {
      solenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void extendLeg() {
      solenoid.set(DoubleSolenoid.Value.kReverse);
    }

  }


  public RealPneumaticStilts() {

    frontLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 0, 1));
    frontRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 2, 3));
    rearLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 4, 5));
    rearRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 6, 7));

    stopAllLegs();
  }

  public void stopAllLegs() {
    frontLeftStiltLeg.stopLeg();
    frontRightStiltLeg.stopLeg();
    rearLeftStiltLeg.stopLeg();
    rearRightStiltLeg.stopLeg();
  }

  public void extendFrontLeft() {
    frontLeftStiltLeg.extendLeg();
  }

  public void extendFrontRight() {
    frontRightStiltLeg.extendLeg();
  }

  public void extendRearLeft() {
    rearLeftStiltLeg.extendLeg();
  }

  public void extendRearRight() {
    rearRightStiltLeg.extendLeg();
  }

  public void stopFrontLeft() {
    frontLeftStiltLeg.stopLeg();
  }

  public void stopFrontRight() {
    frontRightStiltLeg.stopLeg();
  }

  public void stopRearLeft() {
    rearLeftStiltLeg.stopLeg();
  }

  public void stopRearRight() {
    rearRightStiltLeg.stopLeg();
  }

  @Override
  public void retractFrontLeft() {
    frontLeftStiltLeg.retractLeg();
  }

  @Override
  public void retractFrontRight() {
    frontRightStiltLeg.retractLeg();
  }

  @Override
  public void retractRearLeft() {
    rearLeftStiltLeg.retractLeg();
  }

  @Override
  public void retractRearRight() {
    rearRightStiltLeg.retractLeg();
  }

}