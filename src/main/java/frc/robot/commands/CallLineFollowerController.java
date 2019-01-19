package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.LineFollowerController;

/**
 *
 */
public class CallLineFollowerController extends Command {
    private LineFollowerController controller;
    public CallLineFollowerController() {
        controller = new LineFollowerController(Robot.driveTrain, Robot.lineFollowerSensorArray);
        requires(Robot.driveTrain);

    }
    @Override
    protected void initialize() {
        controller.initialize();
    }
    @Override
    protected void execute() {
        controller.run();
    }
    @Override
    protected boolean isFinished() {
        return controller.isDone();
    }
    @Override
    protected void end() {
        controller.stop();
    }
    @Override
    protected void interrupted() {
    }
}