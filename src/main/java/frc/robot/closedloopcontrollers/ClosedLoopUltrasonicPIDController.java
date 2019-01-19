package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.sensors.UltrasonicSensor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
    private Ultrasonic ultrasonic;
    
    private ClosedLoopUltrasonicPIDController(Ultrasonic ultrasonicParameter) {
        ultrasonic = ultrasonicParameter;
        ultrasonicPIDIn = new UltrasonicPIDIn();
        ultrasonicPIDOut = new UltrasonicPIDOut(_maxAllowableDelta, delay);
        ultrasonicPID = new PIDController(p, i, d, ultrasonicPIDIn, 
        ultrasonicPIDOut);
        ultrasonicPID.setOutputRange(3, 5);
        ultrasonicPID.setAbsoluteTolerance(12);
    }

    public ClosedLoopUltrasonicPIDController() {
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
    private class UltrasonicPIDOut implements PIDOutput {
        public UltrasonicPIDOut(double maxAllowableDelta, boolean delay) {
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
        ultrasonicPIDOut = new UltrasonicPIDOut(_d, useDelay);
        ultrasonicPID = new PIDController(_p, _i, _d, ultrasonicPIDIn, 
        ultrasonicPIDOut);
        ultrasonicPID.setOutputRange(-outputRange, outputRange);
        ultrasonicPID.setAbsoluteTolerance(absoluteTolerance);
        ultrasonicPID.setName("Ultrasonic");

    }

    public boolean isDone(){
        return ultrasonicPID.onTarget();
    }

	public static ClosedLoopUltrasonicPIDController getInstance() {
		return null;
	}

	public void setPID(int i, int j, int k) {
	}

	public void setAbsoluteTolerance(int i) {
	}

	public void setOutputRange(int i) {
	}

	public void enable(int i) {
	}
}