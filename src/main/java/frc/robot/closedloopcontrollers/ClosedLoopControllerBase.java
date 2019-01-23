package frc.robot.closedloopcontrollers;

public interface ClosedLoopControllerBase{

    public void run();

    public void reset();

    public void stop();

    public void initialize();

    public boolean isDone();

    public void enable(double setpoint);
}