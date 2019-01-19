package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.sensors.NavXGyroSensor;
import frc.robot.Robot;

public class GyroPIDControllerLoop implements ClosedLoopControllerBase{

    private static GyroPIDControllerLoop instance = new GyroPIDControllerLoop();
    private NavXGyroSensor gyro = NavXGyroSensor.getInstance();
    private PIDController gyroPID;
    private GyroPIDIn gyroPIDIn;
    private GyroPIDOut gyroPIDOut;
    private double _maxAllowableDelta;
    private boolean useDelay = true;
    private double outputRange = 1;
    private double absoluteTolerance;
    private double _p = 0;
    private double _i = 0;
    private double _d = 0;
    //I did not add an F variable because we have yet to use it

    private GyroPIDControllerLoop(){

    }

    private class GyroPIDIn implements PIDSource{
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
        }

        @Override
        public PIDSourceType getPIDSourceType() {

            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return gyro.getZAngle();
        }
    }

    private class GyroPIDOut implements PIDOutput {

        public GyroPIDOut(double maxAllowableDelta, boolean delay) {
            _maxAllowableDelta = maxAllowableDelta;
            useDelay = delay;
        }

        @Override
        public void pidWrite(double output) {
           Robot.driveTrain.move(output, 0);
        }
    }

// ----P Value
public void setP(double p) {
    _p = p;
}
public double getP(){
    return _p;
}
// ----I Value
public void setI(double i) {
    _i = i;
}

public double getI() {
    return _i;
}
// ----D Value
public void setD(double d) {
    _d = d;
}

public double getD() {
    return _d;
}

/**
 * Allows you to set the P I and D of the Drivetrain gyro PID 
 */
public void setPID(double p, double i, double d) {
    _p = p;
    _i = i;
    _d = d;
}

/** 
 * This sets both the max and minimum output range for the Drivetrain
 * gyro PID
 */
public void setOutputRange(double range) {
    outputRange = range;
}

public void setAbsoluteTolerance(double tolerance) {
    absoluteTolerance = tolerance;
}

public static GyroPIDControllerLoop getInstance() {
    return instance;
}

    public void run(){

    }

    public void enable(double setpoint){
        gyroPID.setSetpoint(setpoint + gyro.getZAngle());
        gyroPID.enable();
    }

    public void reset(){
        gyroPID.reset();
        
    }

    public void stop(){
        gyroPID.disable();

    }

    public void initialize(){
        gyroPIDIn = new GyroPIDIn();
        gyroPIDOut = new GyroPIDOut(_maxAllowableDelta, useDelay);
        gyroPID = new PIDController(_p, _i, _d, gyroPIDIn, gyroPIDOut);
        gyroPID.setOutputRange(-outputRange, outputRange);
        gyroPID.setAbsoluteTolerance(absoluteTolerance);
        gyroPID.setName("DriveTrain", "Gyro");

    }

    public boolean isDone(){
        return gyroPID.onTarget();

    }
}