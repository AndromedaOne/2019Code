package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class TestCommand extends Command {

    public TestCommand() {
        System.out.println("CurrentTime: " + System.currentTimeMillis());
    }
    @Override
    protected boolean isFinished() {
        return false;
    }

}