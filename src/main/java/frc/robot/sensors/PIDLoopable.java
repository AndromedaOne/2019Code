package frc.robot.sensors;

public interface PIDLoopable {

  public void reset();

  public double getClosedLoopSrc();

}