package frc.robot.subsystems.intake;

public class MockIntake extends Intake{



    @Override
    public void rollIntake(double speed) {
        System.out.println("trying to move intake at " + speed);
    }

    @Override
    public void moveToStartPosition() {
        System.out.println("trying to move intake arm to start position"); 
    }
    
    @Override
    public void moveToCargoPosition() {
        System.out.println("trying to move intake arm to cargo position");
    }

    @Override
    public void moveToEndgamePosition() {
        System.out.println("trying to move intake arm to endgame position");
    }

    @Override
    protected void initDefaultCommand() {
	
    }

}