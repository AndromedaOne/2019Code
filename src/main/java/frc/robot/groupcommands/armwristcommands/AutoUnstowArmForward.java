package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.IntakeArmControl;
import frc.robot.commands.IntakeArmControl.MoveIntakeArmDirection;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class AutoUnstowArmForward extends CommandGroup {
    
    public AutoUnstowArmForward(){
        addSequential(new RetractArm(14));
        addSequential(new RotateWrist(IntakeReady.setpoint.getWristAngle()));
       /* addParallel(new RotateShoulder(50));
        //Intake is moved to the DOWN position twice because the starting postion is unknown. 
        addSequential(new IntakeArmControl(MoveIntakeArmDirection.DOWN));
        addSequential(new IntakeArmControl(MoveIntakeArmDirection.DOWN));
        addParallel(new RetractArm(IntakeReady.setpoint.getArmRetraction()));
        addSequential(new RotateShoulder(IntakeReady.setpoint.getShoulderAngle()));
    */
    }

}