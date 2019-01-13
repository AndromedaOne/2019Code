package frc.robot.sensors;

public abstract class SensorBase <T extends SensorReadingSrc>{
   
    public abstract void reset();

    public abstract T getClosedLoopSrc();

}