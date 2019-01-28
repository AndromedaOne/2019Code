package frc.robot.subsystems.pneumaticstilts;

public class MockPneumaticStilts extends PneumaticStilts {

    public void extendFrontLeft() {
        System.out.println("Extending Front Left Stilt Solenoid");
    }

    public void extendFrontRight() {
        System.out.println("Extending Front Right Stilt Solenoid");
    }

    public void extendRearLeft() {
        System.out.println("Extending Rear Left Stilt Solenoid");
    }

    public void extendRearRight() {
        System.out.println("Extending Rear Right Stilt Solenoid");
    }

    public void stopFrontLeft() {
        System.out.println("Stopped Front Left Stilt Solenoid");
    }

    public void stopFrontRight() {
        System.out.println("Stopped Front Right Stilt Solenoid");
    }

    public void stopRearLeft() {
        System.out.println("Stopped Rear Left Stilt Solenoid");
    }

    public void stopRearRight() {
        System.out.println("Stopped Rear Right Stilt Solenoid");
    }
}