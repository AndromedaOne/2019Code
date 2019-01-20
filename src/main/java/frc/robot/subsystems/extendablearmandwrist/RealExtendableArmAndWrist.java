package frc.robot.subsystems.extendablearmandwrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class RealExtendableArmAndWrist extends ExtendableArmAndWrist {
    private static RealExtendableArmAndWrist instance;
    private WPI_TalonSRX shoulderJointTalon;
    private WPI_TalonSRX topExtendableArmAndWristTalon;
    private WPI_TalonSRX bottomExtendableArmAndWristTalon;

    public WPI_TalonSRX getShoulderJointTalon() {
        return shoulderJointTalon;
    }

    public WPI_TalonSRX getTopExtendableArmAndWristTalon() {
        return topExtendableArmAndWristTalon;
    }

    public WPI_TalonSRX getBottomExtendableArmAndWristTalon() {
        return bottomExtendableArmAndWristTalon;
    }
    /**
     * Creates all of the talons for the arm, wrist, and shoulder joint
     */
    private RealExtendableArmAndWrist() {
        Config armConf = Robot.getConfig().getConfig("ports.armAndWrist");
        shoulderJointTalon = new WPI_TalonSRX(armConf.getInt("shoulderJointTalon"));
        topExtendableArmAndWristTalon = 
        new WPI_TalonSRX(armConf.getInt("topExtendableArmAndWristTalon"));
        bottomExtendableArmAndWristTalon = 
        new WPI_TalonSRX(armConf.getInt("bottomExtendableArmAndWristTalon"));
    }

    /**
     * @return the instance
     */
    public static RealExtendableArmAndWrist getInstance() {
        if (instance == null) {
            instance = new RealExtendableArmAndWrist();
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

}