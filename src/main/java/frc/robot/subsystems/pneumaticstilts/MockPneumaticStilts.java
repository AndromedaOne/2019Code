package frc.robot.subsystems.pneumaticstilts;

public class MockPneumaticStilts extends PneumaticStilts {
    public void move(double frontLeftLeg, double frontRightLeg, 
    double rearLeftLeg, double rearRightLeg) {
        System.out.println("Moving Legs (FL, FR, RL, RR) : " + frontLeftLeg + " " + frontRightLeg + " " 
            + rearLeftLeg + " " + rearRightLeg);
    }

    public void stopAllLegs() {
        System.out.println("Stopping all legs.");
    }
}