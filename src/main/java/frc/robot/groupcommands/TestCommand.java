package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestCommand extends CommandGroup{
    public TestCommand(){
        
    }
    @Override
    protected void initialize() {
        super.initialize();
        
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