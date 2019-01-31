package frc.robot.subsystems.pneumaticstilts;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.RaiseRobot;

/**
 * The pneumatic climber subsystem for the robot
 */
public abstract class PneumaticStilts extends Subsystem {

    public void initDefaultCommand() {
       // setDefaultCommand(new RaiseRobot());
    }
    
    public abstract void move(double frontLeftLeg, double frontRightLeg, 
    double rearLeftLeg, double rearRightLeg);

    public abstract void stopAllLegs();

}