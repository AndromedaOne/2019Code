package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;
import frc.robot.utilities.POVDirectionNames;


public class IntakeControl extends Command{

    static IntakePositionsEnum intakePositionsEnum = IntakePositionsEnum.STOWED;
    private POVDirectionNames povDirectionName;
    public IntakeControl(POVDirectionNames povDirectionNamesParam){
        povDirectionName = povDirectionNamesParam;
        requires(Robot.intake);
    }
   
    @Override
    protected void initialize(){
        switch(povDirectionName) {
            case NORTH:
                // do something
            
            case SOUTH:
                // do something else
            
            
        }

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