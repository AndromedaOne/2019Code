package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.sensors.MagEncoderSensor;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.telemetries.TracePair;
import frc.robot.telemetries.Trace;
import frc.robot.Robot;

public class DrivetrainEncoderPIDController {

    private static DrivetrainEncoderPIDController instance;
    private static Trace trace; 
    private PIDMultiton encoderPID;
    private EncoderPIDOut encoderPIDOut;
    private MagEncoderSensor encoder;
    private double _maxAllowableDelta;
    private boolean useDelay = true;
    private double outputRange = 1;
    private double absoluteTolerance;
    private double _setpoint;
    private PIDConfiguration pidConfiguration;
    private final double p = 0;
    private final double i = 0;
    private final double d = 0;
    //I did not add an F variable because we have yet to use it

    private DrivetrainEncoderPIDController(){
        trace = Trace.getInstance();
        encoder = new MagEncoderSensor(Robot.driveTrain.driveTrainLeftRearTalon;
        encoder.putOnSmartDashboard("Drivetrain","RightRearEncoder");
        encoderPIDOut = new EncoderPIDOut(_maxAllowableDelta, useDelay);
        pidConfiguration = new PIDConfiguration();
        setPIDConfiguration(pidConfiguration);
        encoderPID = PIDMultiton.getInstance(encoder, encoderPIDOut, pidConfiguration);
    }

    private class EncoderPIDOut implements PIDOutput {

        public EncoderPIDOut(double maxAllowableDelta, boolean delay) {
            _maxAllowableDelta = maxAllowableDelta;
            useDelay = delay;
        }

        @Override
        public void pidWrite(double output) {
            trace.addTrace(true, "Encoder Drivetrain",
            new TracePair("Output", output),
            new TracePair("Setpoint", _setpoint),
            new TracePair("EncoderTicks", encoder.getDistanceTicks()));
            Robot.driveTrain.move(-output, 0);
        }
    }

    public static DrivetrainEncoderPIDController getInstance() {
        System.out.println(" --- Asking for Instance --- ");
        if (instance == null) {
            instance = new DrivetrainEncoderPIDController();
        }
        return instance;
    }

    public PIDMultiton getEncoderPID(){
        return encoderPID;
    }

    /**
     * This method takes in a setpoint in ticks to move the robot using the 
     * encoder PID
     * @param setpoint
     */
    public void setSetpoint(double setpoint) {
        _setpoint = setpoint;
        encoderPID.setSetpoint(setpoint + encoder.getDistanceTicks());
    }
    public void enable(){
        encoderPID.enable();
    }

    public void reset() {
        encoderPID.reset();
    }

    public void stop() {
        encoderPID.stop();
    }

    public boolean isDone() {
        return encoderPID.isDone();
    }

    private void setPIDConfiguration(PIDConfiguration pidConfiguration) {
        pidConfiguration.setP(p);
        pidConfiguration.setI(i);
        pidConfiguration.setD(d);
        pidConfiguration.setAbsoluteTolerance(absoluteTolerance);
        pidConfiguration.setMaximumOutput(outputRange);
        pidConfiguration.setMinimumOutput(-outputRange);
        pidConfiguration.setLiveWindowName("DriveTrain");
    }
}