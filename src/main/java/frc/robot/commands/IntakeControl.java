package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;


public class IntakeControl extends Command{

    
    public IntakeControl(){
        requires(Robot.intake);
    }
   
    @Override
    protected void initialize(){

    }

    @Override
    protected void execute(){
        Joystick intakeController = Robot.operatorController;
      //  boolean upDButtonPressed = ButtonsEnumerated.
    }

   
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end(){

    }

    @Override
    protected void interrupted(){

    }
}