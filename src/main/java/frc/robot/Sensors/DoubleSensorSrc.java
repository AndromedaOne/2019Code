package frc.robot.Sensors;

public class DoubleSensorSrc extends SensorReadingSrc {
    private Double reading;

    public Double getReading (){
        return reading;
    }

    public void setReading (Double newReading){
        reading = newReading;
    }
}