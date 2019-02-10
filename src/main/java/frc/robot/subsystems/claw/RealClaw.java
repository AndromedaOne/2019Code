package frc.robot.subsystems.claw;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;

public class RealClaw extends Claw {
    VictorSPX leftGripperSPX;
    VictorSPX rightGripperSPX;
    DoubleSolenoid jawSolenoid;

    public RealClaw() {
        Config conf = Robot.getConfig();
        Config clawConf = conf.getConfig("ports.claw");
        leftGripperSPX = new VictorSPX(clawConf.getInt("leftGripper"));
        rightGripperSPX = new VictorSPX(clawConf.getInt("rightGripper"));
        jawSolenoid = new DoubleSolenoid(clawConf.getInt("forwardChannel"), clawConf.getInt("backwardsChannel"));
        
    }

    @Override
    protected void initDefaultCommand() {
        //TODO: Create command to teleop this
    }

    @Override
    public void openClaw() {
        jawSolenoid.set(Value.kForward);
    }

    @Override
    public void stop() {
        leftGripperSPX.set(ControlMode.PercentOutput, 0);
        jawSolenoid.set(Value.kOff);
    }

    @Override
    public void driveIntakeMotors(double speed) {
        leftGripperSPX.set(ControlMode.PercentOutput, speed);
        rightGripperSPX.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public void closeClaw() {
        jawSolenoid.set(Value.kReverse);
    }




}