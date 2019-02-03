package frc.robot.subsystems.claw;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public abstract class RealClaw extends Claw {

    public void openClaw() {
        //run stuff to open claw
        clawIsOpen = true;
    }

    public void closeClaw() {
        //run stuff to close claw
        clawIsOpen = false;
    }

    public void runIntake(double intakeSpeed) {
        
    }

    public void stopIntake() {
        runIntake(0);
    }
}