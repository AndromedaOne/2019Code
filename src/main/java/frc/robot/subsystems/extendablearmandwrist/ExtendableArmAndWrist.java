package frc.robot.subsystems.extendablearmandwrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public abstract class ExtendableArmAndWrist extends Subsystem {

    /**
     * Takes a value between -1 and 1 and sets the percent output of the 
     * topExtendableArmAndWristTalon to that value
     */
    public abstract void moveTopExtendableArmAndWristTalon(double value);


    /**
     * Takes a value between -1 and 1 and sets the percent output of the 
     * bottomExtendableArmAndWristTalon to that value
     */
    public abstract void moveBottomExtendableArmAndWristTalon(double value);

    /**
     * Takes a value between -1 and 1 and sets the percent output of the 
     * shoulderJointTalon to that value
     */
    public abstract void moveShoulderJointTalon(double value);

    /**
     * Sets the default command to the teleoperated control command for the arm
     */
    @Override
    protected void initDefaultCommand(){
        // need to create a default command and add it here
    }

}