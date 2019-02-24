package frc.robot.groupcommands.stilts;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.stilts.*;

public class RaiseOntoLevelThree extends CommandGroup {
    public RaiseOntoLevelThree() {
        addSequential(new RaiseAllLegs());
        //lower intake
        //run intake until we hit front bottom ultrasonic
        addParallel(new RetractFrontLeft());
        addSequential(new RetractFrontRight());
        //lower intake
        //run intake until we hit rear bottom ultrasonic
        addParallel(new RetractRearLeft());
        addSequential(new RetractRearRight());
        //MoveUsingEncoderPID until we hit front ultrasonic
    }
}