package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.TurnToDeltaAngle;

public class GyroDoubleMove extends CommandGroup{
    public GyroDoubleMove (double desiredAngle){
        addSequential(new TurnToDeltaAngleQuickly(desiredAngle));
        addSequential(new TurnToDeltaAnglePrecisely(desiredAngle));



    }




}