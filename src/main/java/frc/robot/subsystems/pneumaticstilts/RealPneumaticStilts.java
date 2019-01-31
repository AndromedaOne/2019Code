package frc.robot.subsystems.pneumaticstilts;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RealPneumaticStilts extends PneumaticStilts {

    enum RetractorStates {
        Stop, BeginMovingUp, Moving, BeginMovingDown, MovingDown, InchingDelay
    }

    private class StiltLeg {
        // Duty Cycle on solenoid is 5 times a second
        private final long kDelayTime = 201;
        private final long kHoldTime = 200;

        private RetractorStates currentState;
        private long currentDelayTime;
        private long currentHoldTime;
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

        public void move(double speed) {
            System.out.println("Current State = " + currentState);
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
                currentDelayTime = (long) (currentTime + kDelayTime / speed);
                currentHoldTime = currentTime + kHoldTime;
                retractLeg();
                currentState = RetractorStates.Moving;
                break;
            case Moving:
                if (currentTime > currentHoldTime) {
                    stopLeg();
                    currentState = RetractorStates.InchingDelay;
                }
                break;
            case BeginMovingDown:
                currentDelayTime = (long) (currentTime + kDelayTime / -speed);
                currentHoldTime = currentTime + kHoldTime;
                extendLeg();
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

    private static StiltLeg frontLeftStiltLeg;
    private static StiltLeg frontRightStiltLeg;
    private static StiltLeg rearLeftStiltLeg;
    private static StiltLeg rearRightStiltLeg;

    private RealPneumaticStilts() {

        frontLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 0, 1));
        frontRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 2, 3));
        rearLeftStiltLeg = new StiltLeg(new DoubleSolenoid(0, 4, 5));
        rearRightStiltLeg = new StiltLeg(new DoubleSolenoid(0, 6, 7));
    }

    public void move(double frontLeftLeg, double frontRightLeg, 
        double rearLeftLeg, double rearRightLeg)
    {
        frontLeftStiltLeg.move(frontLeftLeg);
        frontRightStiltLeg.move(frontRightLeg);
        rearLeftStiltLeg.move(rearLeftLeg);
        rearRightStiltLeg.move(rearRightLeg);
    }

    public void stopAllLegs()
    {
        frontLeftStiltLeg.stopLeg();
        frontRightStiltLeg.stopLeg();
        rearLeftStiltLeg.stopLeg();
        rearRightStiltLeg.stopLeg();
    }
}