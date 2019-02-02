package frc.robot.sensors.anglesensor;

public class RealAngleSensor extends AngleSensor {

    @Override
    public double getAngle() {
        // This needs to be filled in correctly later
        return 0;
    }

    @Override
    public void reset() {
        // This needs to be filled in correctly later
    }

    @Override
    public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
        super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Angle:", this::getAngle);
    }
}