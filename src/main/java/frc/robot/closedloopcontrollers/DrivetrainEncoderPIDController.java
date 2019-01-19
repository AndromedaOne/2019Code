package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.sensors.MagEncoderSensor;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.Robot;

public class DrivetrainEncoderPIDController implements ClosedLoopControllerBase {

    private static DrivetrainEncoderPIDController instance;
    private static WPI_TalonSRX rightRearEncoder;
    private PIDController encoderPID;
    private EncoderPIDIn encoderPIDIn;
    private EncoderPIDOut encoderPIDOut;
    private MagEncoderSensor encoder;
    private double _maxAllowableDelta;
    private boolean useDelay = true;
    private double outputRange = 1;
    private double absoluteTolerance;
    private double p = 0;
    private double i = 0;
    private double d = 0;
    //I did not add an F variable because we have yet to use it

    private DrivetrainEncoderPIDController(){
        rightRearEncoder = Robot.driveTrain.getRightRearEncoder();
        encoder = new MagEncoderSensor(rightRearEncoder);
        encoderPIDIn = new EncoderPIDIn();
        encoderPIDOut = new EncoderPIDOut(_maxAllowableDelta, useDelay);
        encoderPID = new PIDController(p, i, d, encoderPIDIn, encoderPIDOut);
        encoderPID.setOutputRange(-outputRange, outputRange);
        encoderPID.setAbsoluteTolerance(absoluteTolerance);

        System.out.print(" - Added Encoder PID To Live Window - ");
        LiveWindow.add(encoderPID);
        encoderPID.setName("DriveTrain", "Encoder");
    }

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
            double ticks = encoder.getDistanceTicks();
            System.out.println("EncoderTicks: " + ticks);
            return ticks;
        }
    }

    private class EncoderPIDOut implements PIDOutput {

        public EncoderPIDOut(double maxAllowableDelta, boolean delay) {
            _maxAllowableDelta = maxAllowableDelta;
            useDelay = delay;
        }

        @Override
        public void pidWrite(double output) {
           Robot.driveTrain.move(-output, 0);
        }
    }

    // ----P Value
    public void setP(double p) {
        encoderPID.setP(p);
    }
    public double getP(){
        return encoderPID.getP();
    }
    // ----I Value
    public void setI(double i) {
        encoderPID.setI(i);
    }
    
    public double getI() {
        return encoderPID.getI();
    }
    // ----D Value
    public void setD(double d) {
        encoderPID.setD(d);
    }

    public double getD() {
        return encoderPID.getD();
    }

    /**
     * Allows you to set the P I and D of the Drivetrain encoder PID 
     */
    public void setPID(double p, double i, double d) {
        encoderPID.setP(p);
        encoderPID.setI(i);
        encoderPID.setD(d);
    }

    /** 
     * This sets both the max and minimum output range for the Drivetrain
     * Enoder PID
     */
    public void setOutputRange(double range) {
        outputRange = range;
    }

    public void setAbsoluteTolerance(double tolerance) {
        absoluteTolerance = tolerance;
    }

    public static DrivetrainEncoderPIDController getInstance() {
        System.out.println(" --- Asking for Instance --- ");
        if (instance == null) {
            instance = new DrivetrainEncoderPIDController();
        }
        return instance;
    }

    public PIDController getEncoderPID(){
        return encoderPID;
    }

    public void run() {
    }

    /**
     * This method takes in a setpoint in ticks to move the robot using the 
     * encoder PID
     * @param setpoint
     */
    public void enable(double setpoint) {
        encoderPID.setSetpoint(setpoint + encoder.getDistanceTicks());
        encoderPID.enable();
    }

    public void reset() {
        encoderPID.reset();
    }

    public void stop() {
        encoderPID.disable();
    }
    /**
     * This creates a new Encoder PID In and Out as well as a PID Controller
     * using all the passed in parameters. It also set the output Range and the
     *  absolute tolerance.
     */
    public void initialize() {

    }

    public boolean isDone() {
        return encoderPID.onTarget();
    }
}