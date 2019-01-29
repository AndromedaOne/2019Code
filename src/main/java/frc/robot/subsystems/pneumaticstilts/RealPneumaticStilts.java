package frc.robot.subsystems.pneumaticstilts;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RealPneumaticStilts extends PneumaticStilts {

    private static DoubleSolenoid frontLeftSolenoid;
    private static DoubleSolenoid frontRightSolenoid;
    private static DoubleSolenoid rearLeftSolenoid;
    private static DoubleSolenoid rearRightSolenoid;

    public RealPneumaticStilts() {
        frontLeftSolenoid = new DoubleSolenoid(0, 0, 1);
        frontRightSolenoid = new DoubleSolenoid(0, 2, 3);
        rearLeftSolenoid = new DoubleSolenoid(0, 4, 5);
        rearRightSolenoid = new DoubleSolenoid(0, 6, 7);
    }

    public void extendFrontLeft() {
        frontLeftSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void extendFrontRight() {
        frontRightSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void extendRearLeft() {
        rearLeftSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void extendRearRight() {
        rearRightSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void stopFrontLeft() {
        frontLeftSolenoid.set(DoubleSolenoid.Value.kOff);
    }

    public void stopFrontRight() {
        frontRightSolenoid.set(DoubleSolenoid.Value.kOff);
    }

    public void stopRearLeft() {
        rearLeftSolenoid.set(DoubleSolenoid.Value.kOff);
    }

    public void stopRearRight() {
        rearRightSolenoid.set(DoubleSolenoid.Value.kOff);
    }
}