package frc.robot.subsystems.pneumaticstilts;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Robot;

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

    Config portConf = Robot.getConfig().getConfig("ports.stilts");
    frontLeftStiltLeg = new StiltLeg(
        new DoubleSolenoid(portConf.getInt("frontLeft.forward"), portConf.getInt("frontLeft.backward")));
    frontRightStiltLeg = new StiltLeg(
        new DoubleSolenoid(portConf.getInt("frontRight.forward"), portConf.getInt("frontRight.backward")));
    rearLeftStiltLeg = new StiltLeg(
        new DoubleSolenoid(portConf.getInt("rearLeft.forward"), portConf.getInt("rearLeft.backward")));
    rearRightStiltLeg = new StiltLeg(
        new DoubleSolenoid(portConf.getInt("rearRight.forward"), portConf.getInt("rearRight.backward")));

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