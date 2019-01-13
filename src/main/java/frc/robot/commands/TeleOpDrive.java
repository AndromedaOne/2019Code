package frc.robot.commands;

import frc.robot.Robot;

import frc.robot.utilities.EnumeratedRawAxis;
import frc.robot.utilities.ButtonsEnumerated;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleOpDrive extends Command {

    public TeleOpDrive() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	Joystick drivecontroller = Robot.driveController;
    	double forwardBackwardStickValue = -EnumeratedRawAxis.getLeftStickVertical(drivecontroller);
    	double rotateStickValue = EnumeratedRawAxis.getRightStickHorizontal(drivecontroller);
        Robot.driveTrain.move(forwardBackwardStickValue, rotateStickValue);
        
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }
}