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

    public RealClaw() {
        Config conf = Robot.getConfig();
        Config clawConf = conf.getConfig("ports.claw");
        leftGripperSPX = new VictorSPX(clawConf.getInt("leftGripper"));
        rightGripperSPX = new VictorSPX(clawConf.getInt("rightGripper"));
        
    }
    public void driveGripperMotors(double speed) {
        leftGripperSPX.set(ControlMode.PercentOutput, speed);
        rightGripperSPX.set(ControlMode.PercentOutput, speed);
    }


}