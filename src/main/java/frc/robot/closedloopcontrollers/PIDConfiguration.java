package frc.robot.closedloopcontrollers;

public class PIDConfiguration{
    private double p;
    public double getP() {return p;}
    public void setP(double pParam) {p = pParam;}
    
    private double i;
    public double getI() {return i;}
    public void setI(double iParam) {i = iParam;}
    
    private double d;
    public double getD() {return d;}
    public void setD(double dParam) {d = dParam;}

    private double minimumOutput;
    public double getMinimumOutput() {return minimumOutput;}
    public void setMinimumOutput(double minimumOutputParam) {
        minimumOutput = minimumOutputParam;
    }

    private double maximumOutput;
    public double getMaximumOutput() {return maximumOutput;}
    public void setMaximumOutput(double maximumOutputParam) {
        maximumOutput = maximumOutputParam;
    }

    private double absoluteTolerance;
    public double getAbsoluteTolerance() {return absoluteTolerance;}
    public void setAbsoluteTolerance(double absoluteToleranceParam) {
        absoluteTolerance = absoluteToleranceParam;
    }

    private String liveWindowName;
    public String getLiveWindowName() {return liveWindowName;}
    public void setLiveWindowName(String liveWindowNameParam) {
        liveWindowName = liveWindowNameParam;
    }
} 