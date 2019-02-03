package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;
import frc.robot.utilities.POVDirectionNames;


public class IntakeControl extends Command{

    static IntakePositionsEnum intakePositionsEnum = IntakePositionsEnum.STOWED;
    private POVDirectionNames
    public IntakeControl(POVDirectionNames povDirectionNamesParam){
        requires(Robot.intake);
    }
   
    @Override
    protected void initialize(){

    }

    @Override
    protected void execute(){
        Joystick intakeController = Robot.operatorController;
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