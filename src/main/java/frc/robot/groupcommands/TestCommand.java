package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.groupcommands.armwristcommands.TuckArm;

public class TestCommand extends CommandGroup{
    public TestCommand(){
        
    }
    @Override
    protected void initialize() {
        super.initialize();
        double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
        addSequential(new TuckArm(shoulderAngle, true));
        
    }

    public void execute()
    {
        //System.out.println("Test button pressed");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}