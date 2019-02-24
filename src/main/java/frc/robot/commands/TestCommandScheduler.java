package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class TestCommandScheduler extends Command {

    @Override
    protected void initialize() {
        super.initialize();
        TestCommand a = new TestCommand();
        a.start();
    }
    @Override
    protected boolean isFinished() {
        return true;
    }

}