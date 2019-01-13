package frc.robot.Sensors;

public class SensorBase <T extends SensorReadingSrc>{
   
    public void reset() {
        return;
    }

    public T getClosedLoopSrc(){
        return null;
    }

}