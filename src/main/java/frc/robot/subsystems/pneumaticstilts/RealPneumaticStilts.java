package frc.robot.subsystems.pneumaticstilts;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RealPneumaticStilts extends PneumaticStilts {

  public static StiltLeg frontLeftStiltLeg;
  public static StiltLeg frontRightStiltLeg;
  public static StiltLeg rearLeftStiltLeg;
  public static StiltLeg rearRightStiltLeg;

  private class StiltLeg {
    private DoubleSolenoid solenoid;
    private String stiltLegID;

    StiltLeg(DoubleSolenoid mySolenoid, String myStiltLegID) {
      solenoid = mySolenoid;
      stiltLegID = myStiltLegID;
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

  /*
   * public static StiltLeg frontLeftStiltLeg; public static StiltLeg
   * frontRightStiltLeg; public static StiltLeg rearLeftStiltLeg; public static
   * StiltLeg rearRightStiltLeg;
   */

  public RealPneumaticStilts() {

    frontLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 0, 1), "FL");
    frontRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 2, 3), "FR");
    rearLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 4, 5), "RL");
    rearRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 6, 7), "RR");

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