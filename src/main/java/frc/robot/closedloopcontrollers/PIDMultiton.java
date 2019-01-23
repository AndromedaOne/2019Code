package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.util.Map;

import org.apache.commons.lang3.builder.*;

public class PIDMultiton implements ClosedLoopControllerBase {
    

    private static Map<PIDName, PIDMultiton> instances;

   

    public synchronized PIDMultiton gitInstance(PIDSource source, PIDOutput output, PIDConfiguration config) {

        PIDName name = new PIDName(source, output);
        PIDMultiton instance = instances.getOrDefault(name, new PIDMultiton(name, config));
        if (config.equals(instance.gitConfig())){
         instances.put (name, instance);
         return instance;
        } else {
            throw new IllegalArgumentException("configs much match");
        }

    }
    
   
   
   

    private class PIDName {
        private PIDSource source;
        private PIDOutput output;
        public PIDName(PIDSource source, PIDOutput output){
            this.source = source;
            this.output = output;
        }
        public int hashCode() {
            // you pick a hard-coded, randomly chosen, non-zero, odd number
            // ideally different for each class
            return new HashCodeBuilder(17, 37).
              append(source).
              append(output).
              toHashCode();
        }
    
        public boolean equals(Object obj) {
            if (obj == null) { return false; }
            if (obj == this) { return true; }
            if (obj.getClass() != getClass()) {
              return false;
            }
            PIDName rhs = (PIDName) obj;
            return new EqualsBuilder()
                          .appendSuper(super.equals(obj))
                          .append(source, rhs.source)
                          .append(output, rhs.output)
                          .isEquals();
           }

          
    }
   
    private PIDController encoderPID;
    private PIDConfiguration config;
    private PIDName name;
    private PIDMultiton(PIDName name, PIDConfiguration config) {
        this.name = name;
        this.config = config;
        encoderPID = new PIDController(config.p, config.i, config.d, name.source, name.output);
        encoderPID.setOutputRange(config.minimumOutput, config.maximumOutput);

        encoderPID.setAbsoluteTolerance(config.absoluteTolerance);

        LiveWindow.add(encoderPID);
        encoderPID.setName(config.LiveWindowName, "Encoder");

    } 
    private PIDConfiguration gitConfig() {
        return config;
    }
    @Override
    public void run() {
    }
    @Override
    public void enable(double setpoint) {
        encoderPID.setSetpoint(setpoint + name.source.pidGet());
        encoderPID.enable();
    }
    @Override
    public void reset() {
        encoderPID.reset();
    }
    @Override
    public void stop() {
        encoderPID.disable();
    }
    /**
     * This creates a new Encoder PID In and Out as well as a PID Controller
     * using all the passed in parameters. It also set the output Range and the
     * absolute tolerance.
     */
    @Override
    public void initialize() {

    }
    @Override
    public boolean isDone() {
        return encoderPID.onTarget();
    }
}
