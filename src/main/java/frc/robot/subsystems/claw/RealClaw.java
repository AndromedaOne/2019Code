package frc.robot.subsystems.claw;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.Robot;

public abstract class RealClaw extends Claw {
    VictorSPX leftGripperSPX;
    VictorSPX rightGripperSPX;
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
        Config conf = Robot.getConfig();
        Config clawConf = conf.getConfig("ports.claw");
        leftGripperSPX = new VictorSPX(clawConf.getInt("leftGripper"));
        rightGripperSPX = new VictorSPX(clawConf.getInt("rightGripper"));
        jawsSolenoid = new claw(new DoubleSolenoid(0, 0, 1));
    }
    public void driveGripperMotors(double speed) {
        leftGripperSPX.set(ControlMode.PercentOutput, speed);
        rightGripperSPX.set(ControlMode.PercentOutput, speed);
    }


}