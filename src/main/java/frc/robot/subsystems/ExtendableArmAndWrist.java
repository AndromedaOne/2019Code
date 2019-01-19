package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class ExtendableArmAndWrist extends Subsystem {
    private ExtendableArmAndWrist instance;
    private TalonSRX shoulderJointTalon;
    private TalonSRX topExtendableArmAndWristTalon;
    private TalonSRX bottomExtendableArmAndWristTalon;

    /**
     * Creates all of the talons for the arm, wrist, and shoulder joint
     */
    private ExtendableArmAndWrist() {
        Config armConf = Robot.getConfig().getConfig("ports.driveTrain");
        shoulderJointTalon = new TalonSRX(armConf.getInt("shoulderJointTalon"));
        topExtendableArmAndWristTalon = 
        new TalonSRX(armConf.getInt("topExtendableArmAndWristTalon"));
        bottomExtendableArmAndWristTalon = 
        new TalonSRX(armConf.getInt("bottomExtendableArmAndWristTalon"));
    }

    /**
     * @return the instance
     */
    public ExtendableArmAndWrist getInstance() {
        if (instance == null) {
            instance = new ExtendableArmAndWrist();
        }
        return instance;
    }

    /**
     * Takes a value between -1 and 1 and sets the percent output of the 
     * topExtendableArmAndWristTalon to that value
     */
    public void moveTopExtendableArmAndWristTalon(double value) {
        topExtendableArmAndWristTalon.set(ControlMode.PercentOutput, value);
    }

    /**
     * Takes a value between -1 and 1 and sets the percent output of the 
     * bottomExtendableArmAndWristTalon to that value
     */
    public void moveBottomExtendableArmAndWristTalon(double value) {
        bottomExtendableArmAndWristTalon.set(ControlMode.PercentOutput, value);
    }

    /**
     * Takes a value between -1 and 1 and sets the percent output of the 
     * shoulderJointTalon to that value
     */
    public void moveShoulderJointTalon(double value) {
        shoulderJointTalon.set(ControlMode.PercentOutput, value);
    }

    /**
     * Sets the default command to the teleoperated control command for the arm
     */
    @Override
    protected void initDefaultCommand() {

    }

}