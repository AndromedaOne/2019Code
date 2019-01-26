package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.*;

public class PIDMultiton {

    private static Map<PIDName, PIDMultiton> instances;

    static {
        instances = new HashMap<>();
    }
   

    public static synchronized PIDMultiton getInstance(PIDSource source, PIDOutput output, PIDConfiguration config) {

        PIDName name = new PIDName(source, output);
        PIDMultiton instance = instances.getOrDefault(name, new PIDMultiton(name, config));
        if (config.equals(instance.gitConfig())){
         instances.put (name, instance);
         return instance;
        } else {
            throw new IllegalArgumentException("configs much match");
        }

    }
    
   
   
   

    private static class PIDName {
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
        encoderPID = new PIDController(config.getP(), config.getI(), config.getD(), name.source, name.output);
        encoderPID.setOutputRange(config.getMinimumOutput(), config.getMaximumOutput());

        encoderPID.setAbsoluteTolerance(config.getAbsoluteTolerance());

        LiveWindow.add(encoderPID);
        encoderPID.setName(config.getLiveWindowName(), "Encoder");

    } 
    private PIDConfiguration gitConfig() {
        return config;
    }

    public void setSetpoint(double setpoint) {
        encoderPID.setSetpoint(setpoint + name.source.pidGet());
    }
    public void enable(){
        encoderPID.enable();
    }

    public void reset() {
        encoderPID.reset();
    }

    public void stop() {
        encoderPID.disable();
    }

    public boolean isDone() {
        return encoderPID.onTarget();
    }
}
