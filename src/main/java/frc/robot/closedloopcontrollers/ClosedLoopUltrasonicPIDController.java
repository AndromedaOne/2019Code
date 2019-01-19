package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.sensors.UltrasonicSensor;
import frc.robot.Robot;

public class ClosedLoopUltrasonicPIDController implements 
    ClosedLoopControllerBase  {

        private static ClosedLoopUltrasonicPIDController instance = 
    new ClosedLoopUltrasonicPIDController();
    private PIDController ultrasonicPID;
    private UltrasonicPIDIn ultrasonicPIDIn;
    private UltrasonicPIDOut ultrasonicPIDOut;
    private double _maxAllowableDelta;
    private boolean useDelay = true;
    private double outputRange = 1;
    private double absoluteTolerance;
    private double _p=0;
    private double _i=0;
    private double _d=0;
    private double _f=0;

    private UltrasonicPIDController() {
        ultrasonic = frc.robot.sensors.UltrasonicSensor
    }

    private class UltrasonicPIDIn implements PIDSource {

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return 0;
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

    public void run(){
    }

    public void reset(){
        ultrasonicPID.reset();
    }

    public void stop(){
        ultrasonicPID.disable();
    }

    public void initialize(){
        ultrasonicPIDIn = new UltrasonicPIDIn();
        ultrasoicPIDOut = new UlrasonicPIDOut();
        ultrasonicPID = new PIDController(_p, _i, _d, encoderPIDIn, 
        encoderPIDOut);
        ultrasonicPID.setOutputRange(-outputRange, outputRange);
        ultrasonicPID.setAbsoluteTolerance(absoluteTolerance);s
        ultrasonicPID.setName("Ultrasonic");

    }

    public boolean isDone(){
        return ultrasonicPID.onTarget();
    }
}