package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.Robot;
import frc.robot.sensors.MagEncoderSensor;

public class ExtendableArmAndWristController 
implements ClosedLoopControllerBase {

    private ExtendableArmAndWristController instance;
    private int bottomExtendableArmAndWristEncoderPort = 0;
    private int topExtendableArmAndWristEncoderPort = 1;
    private MagEncoderSensor bottomExtendableArmAndWristEncoder;
    private MagEncoderSensor topExtendableArmAndWristEncoder;

    private BottomExtendableArmAndWristEncoderIn 
    bottomExtendableArmAndWristEncoderIn;
    private BottomExtendableArmAndWristEncoderPIDOut 
    bottomExtendableArmAndWristEncoderPIDOut;
    private PIDController bottomExtendableArmAndWristEncoderPID;
    private Double bottomTalonP = 0.0;
    private Double bottomTalonI = 0.0;
    private Double bottomTalonD = 0.0;

    private TopExtendableArmAndWristEncoderIn 
    topExtendableArmAndWristEncoderIn;
    private TopExtendableArmAndWristEncoderPIDOut 
    topExtendableArmAndWristEncoderPIDOut;
    private PIDController topExtendableArmAndWristEncoderPID;
    private Double topTalonP = 0.0;
    private Double topTalonI = 0.0;
    private Double topTalonD = 0.0;

    private double outputRange = 1.0;
    private double absoluteTolerance = 0.0;

    private final double inchesToTicksExtensionRatio = 0.0;
    private final double inchesToTicksRotationRatio = 0.0;
    /**
     * Sets the two encoders for the two motors on the extendable arm and 
     * creates the two PID controllers
     * @param bottomExtendableArmAndWristEncoder1 
     * @param topExtendableArmAndWristEncoder1
     */
    private ExtendableArmAndWristController(MagEncoderSensor 
    bottomExtendableArmAndWristEncoder1, MagEncoderSensor 
    topExtendableArmAndWristEncoder1) {
        bottomExtendableArmAndWristEncoder = bottomExtendableArmAndWristEncoder1;
        topExtendableArmAndWristEncoder = topExtendableArmAndWristEncoder1;

        bottomExtendableArmAndWristEncoderIn = 
        new BottomExtendableArmAndWristEncoderIn();
        bottomExtendableArmAndWristEncoderPIDOut = 
        new BottomExtendableArmAndWristEncoderPIDOut();
        bottomExtendableArmAndWristEncoderPID = new PIDController(bottomTalonP, 
        bottomTalonI, bottomTalonD, bottomExtendableArmAndWristEncoderIn, 
        bottomExtendableArmAndWristEncoderPIDOut);
        bottomExtendableArmAndWristEncoderPID.setOutputRange(-outputRange, 
        outputRange);
        bottomExtendableArmAndWristEncoderPID.
        setAbsoluteTolerance(absoluteTolerance);
        LiveWindow.add(bottomExtendableArmAndWristEncoderPID);
        bottomExtendableArmAndWristEncoderPID.setName("ArmAndWrist", 
        "bottomEncoderPID");

        topExtendableArmAndWristEncoderIn = 
        new TopExtendableArmAndWristEncoderIn();
        topExtendableArmAndWristEncoderPIDOut = 
        new TopExtendableArmAndWristEncoderPIDOut();
        topExtendableArmAndWristEncoderPID = new PIDController(topTalonP, 
        topTalonI, topTalonD, topExtendableArmAndWristEncoderIn, 
        topExtendableArmAndWristEncoderPIDOut);
        topExtendableArmAndWristEncoderPID.setOutputRange(-outputRange, 
        outputRange);
        topExtendableArmAndWristEncoderPID.
        setAbsoluteTolerance(absoluteTolerance);
        LiveWindow.add(topExtendableArmAndWristEncoderPID);
        bottomExtendableArmAndWristEncoderPID.setName("ArmAndWrist", 
        "topEncoderPID");
    }

    /**
     * @return the instance
     * @param bottomExtendableArmAndWristEncoder1
     * @param topExtendableArmAndWristEncoder1
     */
    public ExtendableArmAndWristController getInstance(MagEncoderSensor 
    bottomExtendableArmAndWristEncoder1, MagEncoderSensor 
    topExtendableArmAndWristEncoder1) {
        if (instance == null) {
            instance = new ExtendableArmAndWristController(
                bottomExtendableArmAndWristEncoder1, 
                topExtendableArmAndWristEncoder1);
        }
        return instance;
    }

    /**
     * Converts the extensionSetpoint from inches to ticks and converts the 
     * wristSetpoint from an angle to ticks. These new values for delta ticks 
     * are added to the current reading of the encoders and then the PID 
     * controllers are enabled with the new ticks setpoints.
     * @param extensionSetpointParam
     * @param wristSetpointParam
     */
    public void enableArmPIDs(double deltaExtensionSetpoint, 
    double deltaWristSetpoint) {
        double deltaTicksExtension = 
        deltaExtensionSetpoint*inchesToTicksExtensionRatio;
        double deltaTicksWrist = deltaWristSetpoint*inchesToTicksRotationRatio;

        double topExtendableArmPIDSetpoint = deltaTicksExtension + 
        deltaTicksWrist/2;
        double bottomExtendableArmPIDSetpoint = deltaTicksExtension - 
        deltaTicksWrist/2;

        double topEncoderCurrentTicks = topExtendableArmAndWristEncoder.
        getDistanceTicks();
        double bottomEncoderCurrentTicks = bottomExtendableArmAndWristEncoder.
        getDistanceTicks();
        topExtendableArmPIDSetpoint += topEncoderCurrentTicks;
        bottomExtendableArmPIDSetpoint += bottomEncoderCurrentTicks;

        topExtendableArmAndWristEncoderPID.
        setSetpoint(topExtendableArmPIDSetpoint);
        bottomExtendableArmAndWristEncoderPID.
        setSetpoint(bottomExtendableArmPIDSetpoint);

        topExtendableArmAndWristEncoderPID.enable();
        bottomExtendableArmAndWristEncoderPID.enable();
    }

    private class BottomExtendableArmAndWristEncoderIn implements PIDSource{
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
        }

        /**
         * Sets the PID source type to displacement
         */
        @Override
        public PIDSourceType getPIDSourceType() {

            return PIDSourceType.kDisplacement;
        }

         /**
         * Gets the current measurement in ticks of the bottom talon encoder
         */
        @Override
        public double pidGet() {
            double ticks = 
            bottomExtendableArmAndWristEncoder.getDistanceTicks();
            return ticks;
        }
    }

    private class BottomExtendableArmAndWristEncoderPIDOut 
    implements PIDOutput {

         /**
         * Sends the output of the PID loop to the bottom Talon's move method
         */
        @Override
        public void pidWrite(double output) {
           Robot.extendableArmAndWrist.
           moveBottomExtendableArmAndWristTalon(output);
        }
    }

    private class TopExtendableArmAndWristEncoderIn implements PIDSource{
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {}

        /**
         * Sets the PID source type to displacement
         */
        @Override
        public PIDSourceType getPIDSourceType() {

            return PIDSourceType.kDisplacement;
        }

        /**
         * Gets the current measurement in ticks of the top talon encoder
         */
        @Override
        public double pidGet() {
            double ticks = topExtendableArmAndWristEncoder.getDistanceTicks();
            return ticks;
        }
    }

    private class TopExtendableArmAndWristEncoderPIDOut implements PIDOutput {

        /**
         * Sends the output of the PID loop to the topTalon's move method
         */
        @Override
        public void pidWrite(double output) {
           Robot.extendableArmAndWrist.
           moveTopExtendableArmAndWristTalon(output);;
        }
    }

    /**
     * This method does not fit; call enable() instead
     */
    @Override
    public void run() {}

    /**
     * Resets both the top talon and bottom talon's PID Controllers
     */
    @Override
    public void reset() {
        topExtendableArmAndWristEncoderPID.reset();
        bottomExtendableArmAndWristEncoderPID.reset();
    }

    /**
     * Disables the top talon and bottom talon's PID Controllers
     */
    @Override
    public void stop() {
        topExtendableArmAndWristEncoderPID.disable();
        bottomExtendableArmAndWristEncoderPID.disable();
    }

    /**
     * There is nothing to initialize because everything is created in the 
     * constructor so this method does nothing
     */
    @Override
    public void initialize() {}

    /**
     * @return true if both the top and bottom talon PIDs are ontarget
     */
    @Override
    public boolean isDone() {
        return topExtendableArmAndWristEncoderPID.onTarget() 
        & bottomExtendableArmAndWristEncoderPID.onTarget();
    }
}