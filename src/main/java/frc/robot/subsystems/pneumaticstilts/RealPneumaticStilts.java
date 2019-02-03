package frc.robot.subsystems.pneumaticstilts;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class RealPneumaticStilts extends PneumaticStilts {

  enum RetractorStates {
    Stop, BeginMovingUp, Moving, BeginMovingDown, MovingDown, InchingDelay
  }

  public class StiltLeg {
    private final long kDelayTime = 1000;
    private final long kHoldTime = 10;

    private RetractorStates currentState = RetractorStates.Stop;
    public long currentDelayTime = 0;
    private long currentHoldTime = 0;
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

    public void stabilizedMove(double speed) {
      System.out.println(stiltLegID + " " + currentState + "  " + currentDelayTime + "  " + currentHoldTime);
      long currentTime = System.currentTimeMillis();
      switch (currentState) {
      case Stop:
        stopLeg();
        if (speed > 0) {
          currentState = RetractorStates.BeginMovingUp;
        }
        if (speed < 0) {
          currentState = RetractorStates.BeginMovingDown;
        }
        break;
      case BeginMovingUp:
        if(speed != 0){
          currentDelayTime = (long) (currentTime + kDelayTime / speed);
        }else {
          currentDelayTime = Long.MAX_VALUE;
        }
        currentHoldTime = currentTime + kHoldTime;
        extendLeg();
        currentState = RetractorStates.Moving;
        break;
      case Moving:
        if (currentTime > currentHoldTime) {
          stopLeg();
          currentState = RetractorStates.InchingDelay;
        }
        break;
      case BeginMovingDown:
        if(speed != 0){
          currentDelayTime = (long) (currentTime + kDelayTime / -speed);
        }else {
          currentDelayTime = Long.MAX_VALUE;
        }
        currentHoldTime = currentTime + kHoldTime;
        retractLeg();
        currentState = RetractorStates.Moving;
        break;
      case InchingDelay:
        if (currentTime > currentDelayTime) {
          currentState = RetractorStates.Stop;
        }
        break;
      default:
        currentState = RetractorStates.Stop;
      }

    }

  }

  /*public static StiltLeg frontLeftStiltLeg;
  public static StiltLeg frontRightStiltLeg;
  public static StiltLeg rearLeftStiltLeg;
  public static StiltLeg rearRightStiltLeg;*/

  public RealPneumaticStilts() {

    
    frontLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 0, 1), "FL");
    frontRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 2, 3), "FR");
    rearLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 4, 5), "RL");
    rearRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 6, 7), "RR");
  
    stopAllLegs();
  }

  public StiltLeg getFrontLeftLeg(){
    return frontLeftStiltLeg;
  }

  public StiltLeg getFrontRightLeg() {
    return frontRightStiltLeg;
  }

  public StiltLeg getRearLeftLeg() {
    return rearLeftStiltLeg;
  }

  public StiltLeg getRearRightLeg() {
    return rearRightStiltLeg;
  }

  public void stabilizedMove(double frontLeftLeg, double frontRightLeg, double rearLeftLeg, double rearRightLeg) {

    
    frontLeftStiltLeg.stabilizedMove(frontLeftLeg);
    frontRightStiltLeg.stabilizedMove(frontRightLeg);
    rearLeftStiltLeg.stabilizedMove(rearLeftLeg);
    rearRightStiltLeg.stabilizedMove(rearRightLeg);
    Trace.getInstance().addTrace(true, "Stiltlegs", 
    new TracePair("frontLeft", (double)frontLeftStiltLeg.currentDelayTime),
    new TracePair("frontRight", (double)frontRightStiltLeg.currentDelayTime),
    new TracePair("rearLeft", (double)rearLeftStiltLeg.currentDelayTime),
    new TracePair("rearRight", (double)rearRightStiltLeg.currentDelayTime));
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