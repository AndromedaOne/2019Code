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

    public void runIntake(double intakeSpeed) {
        System.out.println("Running Claw intake at speed : " + intakeSpeed);
    }

    
    public void stopIntake() {
        System.out.println("Stopping Claw intake.");
    }

}