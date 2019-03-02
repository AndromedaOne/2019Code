package frc.robot.subsystems.pneumaticstilts;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Robot;

public class RealPneumaticStilts extends PneumaticStilts {

  public static StiltLeg frontStiltLegs;
  public static StiltLeg rearStiltLegs;

  private class StiltLeg {
    private DoubleSolenoid solenoid;

    StiltLeg(DoubleSolenoid mySolenoid) {
      solenoid = mySolenoid;
    }

    public void stopLeg() {
      solenoid.set(DoubleSolenoid.Value.kOff);
    }

    public void retractLeg() {
      solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void extendLeg() {
      solenoid.set(DoubleSolenoid.Value.kForward);
    }

  }

  public RealPneumaticStilts() {

    System.out.println("Using Real Pneumatic Stilt Subsystem");
    Config portConf = Robot.getConfig().getConfig("ports.stilts");
    frontStiltLegs = new StiltLeg(
        new DoubleSolenoid(portConf.getInt("front.forward"), portConf.getInt("front.backward")));
    rearStiltLegs = new StiltLeg(new DoubleSolenoid(portConf.getInt("rear.forward"), portConf.getInt("rear.backward")));

    stopAllLegs();
  }

  public void stopAllLegs() {
    frontStiltLegs.stopLeg();
    rearStiltLegs.stopLeg();
  }

  public void extendFrontLegs() {
    frontStiltLegs.extendLeg();
  }

  public void extendRearLegs() {
    rearStiltLegs.extendLeg();
  }

  public void stopFrontLegs() {
    frontStiltLegs.stopLeg();
  }

  public void stopRearLegs() {
    rearStiltLegs.stopLeg();
  }

  @Override
  public void retractFrontLegs() {
    frontStiltLegs.retractLeg();
  }

  @Override
  public void retractRearLegs() {
    rearStiltLegs.retractLeg();
  }

}