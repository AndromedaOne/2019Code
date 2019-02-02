package frc.robot.subsystems.intake;

public class MockIntake extends Intake{

    @Override
    public void rollIntake(double speed) {
        System.out.println("trying to roll intake at " + speed);
    }

    @Override
    public void moveIntake(double speed) {
        System.out.println("trying to move intake at " + speed);
    }

    @Override
    protected void initDefaultCommand() {
	
    }

}