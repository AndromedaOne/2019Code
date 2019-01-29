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
    
    public abstract void extendFrontLeft();

    public abstract void extendFrontRight();

    public abstract void extendRearLeft();

    public abstract void extendRearRight();

    public abstract void stopFrontLeft();

    public abstract void stopFrontRight();

    public abstract void stopRearLeft();

    public abstract void stopRearRight();


}