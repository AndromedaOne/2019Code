package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.sensors.MagEncoderSensor;
import frc.robot.Robot;

public class DrivetrainEncoderPIDController implements ClosedLoopControllerBase {

    static DrivetrainEncoderPIDController instance = 
    new DrivetrainEncoderPIDController();

    private PIDController encoderPID;
    private EncoderPIDIn encoderPIDIn;
    private EncoderPIDOut encoderPIDOut;
    private MagEncoderSensor encoder;
    private double _maxAllowableDelta;
    private boolean useDelay = true;
    private double outputRange = 1;
    private double absoluteTolerance;
    private double _p = 0;
    private double _i = 0;
    private double _d = 0;
    //I did not add an F variable because we have yet to use it

    private class EncoderPIDIn implements PIDSource{
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
        }

        @Override
        public PIDSourceType getPIDSourceType() {

            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return encoder.getDistanceTicks();
        }
    }

    private class EncoderPIDOut implements PIDOutput {

        public EncoderPIDOut(double maxAllowableDelta, boolean delay) {
            _maxAllowableDelta = maxAllowableDelta;
            useDelay = delay;
        }

        @Override
        public void pidWrite(double output) {
           Robot.driveTrain.move(output, 0);
        }
    }

    // ----P Value
    public void setP(double p){
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
     * Allows you to set the P I and D of the Drivetrain encoder PID 
     */
    public void setPID(double p, double i, double d){
        _p = p;
        _i = i;
        _d = d;
    }

    /** 
     * This sets both the max and minimum output range for the Drivetrain
     * Enoder PID
     */
    public void setOutputRange(double range){
        outputRange = range;
    }

    public void setAbsoluteTolerance(double tolerance){
        absoluteTolerance = tolerance;
    }

    public static DrivetrainEncoderPIDController getInstance(){
        return instance;
    }

    public void run(){
    }

    /**
     * This method takes in a setpoint in ticks to move the robot using the 
     * encoder PID
     * @param setpoint
     */
    public void enable(double setpoint){
        encoderPID.setSetpoint(setpoint + encoder.getDistanceTicks());
        encoderPID.enable();
    }

    public void reset(){
        encoderPID.reset();
    }

    public void stop(){
        encoderPID.disable();
    }

    public void initialize(){
        encoderPIDIn = new EncoderPIDIn();
        encoderPIDOut = new EncoderPIDOut(_maxAllowableDelta, useDelay);
        encoderPID = new PIDController(_p, _i, _d, encoderPIDIn, encoderPIDOut);
        encoderPID.setOutputRange(-outputRange, outputRange);
        encoderPID.setAbsoluteTolerance(absoluteTolerance);
        encoderPID.setName("DriveTrain", "Encoder");
    }

    public boolean isDone(){
        return encoderPID.onTarget();
    }
}