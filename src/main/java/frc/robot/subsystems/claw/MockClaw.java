package frc.robot.subsystems.claw;

public class MockClaw extends Claw {

    
    @Override
    protected void initDefaultCommand() {
        System.out.println("Claw initDefaultCommand");
    }

    public void openClaw() {
        System.out.println("Opening Claw");
    }

    public void closeClaw() {
        System.out.println("Closing Claw");
    }
}