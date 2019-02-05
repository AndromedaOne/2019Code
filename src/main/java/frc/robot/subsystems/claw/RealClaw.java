package frc.robot.subsystems.claw;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public abstract class RealClaw extends Claw {

    private class claw {

        private DoubleSolenoid solenoid;

        claw(DoubleSolenoid jawsSolenoid) {
            solenoid = jawsSolenoid;
        }

        public void openClaw() {
            solenoid.set(DoubleSolenoid.Value.kForward);
        }
    
        public void closeClaw() {
            solenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }
    private static claw jawsSolenoid;

    public RealClaw() {
        
        jawsSolenoid = new claw(new DoubleSolenoid(0, 0, 1));
    }

}