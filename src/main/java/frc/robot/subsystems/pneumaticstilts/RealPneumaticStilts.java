package frc.robot.subsystems.pneumaticstilts;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RealPneumaticStilts extends PneumaticStilts {

    private class StiltLeg {
        private RetractorStates currentState;
        private long currentDelayTime;
        private long currentHoldTime;
        private DoubleSolenoid solenoid;
        

        StiltLeg(DoubleSolenoid mySolenoid) {
            solenoid = mySolenoid;
        }
        public RetractorStates getCurrentState() {
            return currentState;
        }
        public void setCurrentState(RetractorStates newState) {
            currentState = newState;
        }
        public long getCurrentDelayTime() {
            return currentDelayTime;
        }
        public void setCurrentDelayTime(long newDelayTime) {
            currentDelayTime = newDelayTime;
        }
        public long getCurrentHoldTime() {
            return currentHoldTime;
        }
        public void setCurrentHoldTime(long newHoldTime) {
            currentHoldTime = newHoldTime;
        }
        public void extendLeg() {
            solenoid.set(DoubleSolenoid.Value.kForward);
        }
        public void retractLeg() {
            solenoid.set(DoubleSolenoid.Value.kReverse);
        }
        public void stopLeg() {
            solenoid.set(DoubleSolenoid.Value.kOff);
        }
    }

    private static StiltLeg frontLeftStiltLeg;
    private static StiltLeg frontRightStiltLeg;
    private static StiltLeg rearLeftStiltLeg;
    private static StiltLeg rearRightStiltLeg;

    	// Duty Cycle on solenoid is 5 times a second
	private final long kDelayTime = 201;
	private final long kHoldTime = 200;

	enum RetractorStates {
		Stop,
		BeginMovingUp,
		Moving,
		BeginMovingDown,
		MovingDown,
		InchingDelay
    }

    private RealPneumaticStilts() {

        frontLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 0, 1));
        frontRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 2, 3));
        rearLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 4, 5));
        rearRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 6, 7));
    }

    public void move(double speed, StiltLeg leg) {
        System.out.println("Current State = " + leg.getCurrentState());
			long currentTime = System.currentTimeMillis();
			switch (leg.getCurrentState()) {
			case Stop:
				leg.stopLeg();
				if(speed > 0) {
					leg.setCurrentState(RetractorStates.BeginMovingUp);
				}
				if(speed < 0) {
                    leg.setCurrentState(RetractorStates.BeginMovingDown);	
                }
				break;
			case BeginMovingUp:
				leg.setCurrentDelayTime((long) (currentTime + kDelayTime / speed));
				leg.setCurrentHoldTime(currentTime + kHoldTime);
				leg.retractLeg();
				leg.setCurrentState(RetractorStates.Moving);
				break;
			case Moving:
				if(currentTime > leg.getCurrentHoldTime()) {
					leg.stopLeg();
					leg.setCurrentState(RetractorStates.InchingDelay);
				}
				break;
			case BeginMovingDown:
				leg.setCurrentDelayTime((long) (currentTime + kDelayTime / -speed));
				leg.setCurrentHoldTime(currentTime + kHoldTime);
				leg.extendLeg();
				leg.setCurrentState(RetractorStates.Moving);
				break;
			case InchingDelay: 
				if(currentTime > leg.getCurrentDelayTime()) {
					leg.setCurrentState(RetractorStates.Stop);
				}
				break;
			default: 
				leg.setCurrentState(RetractorStates.Stop);
			}
    }

    @Override
    public void retractFrontLeft() {
        frontLeftSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void retractFrontRight() {
        frontRightSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void retractRearLeft() {
        rearLeftSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void retractRearRight() {
        rearRightSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
}