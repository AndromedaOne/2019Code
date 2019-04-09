package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.commands.TurnToDeltaAngle;

public class TurnToDeltaAngleQuickly extends CommandGroup {
    public TurnToDeltaAngleQuickly (double desiredAngle) {
        addSequential(new TurnToDeltaAngle(desiredAngle));




    }
    public void initialize (){
        GyroPIDController.getInstance().pidMultiton.setPIDTerms(0, 0, 0);
        GyroPIDController.getInstance().pidMultiton.setTolerance(20);


    }  


}