package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.commands.TurnToCompassHeading;
import frc.robot.commands.TurnToDeltaAngle;

public class TurnToDeltaAnglePrecisely extends CommandGroup {
    public TurnToDeltaAnglePrecisely (double desiredAngle, boolean useCompassHeading) {
        if(useCompassHeading){
            addSequential(new TurnToCompassHeading(desiredAngle));
        }else {
            addSequential(new TurnToDeltaAngle(desiredAngle));

        }
    }
    public void initialize (){
        GyroPIDController.getInstance().pidMultiton.setPIDTerms(0.01, 0, 0);
        GyroPIDController.getInstance().pidMultiton.setTolerance(1);


    }  


}