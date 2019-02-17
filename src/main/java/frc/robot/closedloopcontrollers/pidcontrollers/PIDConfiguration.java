package frc.robot.closedloopcontrollers.pidcontrollers;

public class PIDConfiguration {

  private double p;

  public double getP() {
    return p;
  }

  public void setP(double pParam) {
    p = pParam;
  }

  private double i;

  public double getI() {
    return i;
  }

  public void setI(double iParam) {
    i = iParam;
  }

  private double d;

  public double getD() {
    return d;
  }

  public void setD(double dParam) {
    d = dParam;
  }

  private double minimumOutput;

  public double getMinimumOutput() {
    return minimumOutput;
  }

  public void setMinimumOutput(double minimumOutputParam) {
    minimumOutput = minimumOutputParam;
  }

  private double maximumOutput;

  public double getMaximumOutput() {
    return maximumOutput;
  }

  public void setMaximumOutput(double maximumOutputParam) {
    maximumOutput = maximumOutputParam;
  }

  private double absoluteTolerance;

  public double getAbsoluteTolerance() {
    return absoluteTolerance;
  }

  public void setAbsoluteTolerance(double absoluteToleranceParam) {
    absoluteTolerance = absoluteToleranceParam;
  }

  private String liveWindowName;

  public String getLiveWindowName() {
    return liveWindowName;
  }

  public void setLiveWindowName(String liveWindowNameParam) {
    liveWindowName = liveWindowNameParam;
  }

  private String pidName;

  public String getPIDName() {
    return pidName;
  }

  public void setPIDName(String pidNameParam) {
    pidName = pidNameParam;
  }

  /**
   * @param p P-Value
   * @param i I-Value
   * @param d D-Value
   * @param minimumOutput minimum threshold to be output
   * @param maximumOutput upper limit to output amount
   * @param absoluteTolerance
   * @param liveWindowName The name to put on LiveWindow (SmartDashboard,
   * Shuffleboard, etc)
   * @param PIDName name to put in tracefile
   */
  public PIDConfiguration(double p, double i, double d, double minimumOutput, double maximumOutput,
      double absoluteTolerance, String liveWindowName, String PIDName) {
    this.p = p;
    this.i = i;
    this.d = d;
    this.minimumOutput = minimumOutput;
    this.maximumOutput = maximumOutput;
    this.absoluteTolerance = absoluteTolerance;
    this.liveWindowName = liveWindowName;
    this.pidName = PIDName;
  }

  public PIDConfiguration() {
    this.p = 0;
    this.i = 0;
    this.d = 0;
    this.minimumOutput = 0;
    this.maximumOutput = 0;
    this.absoluteTolerance = 0;
    this.liveWindowName = "Unconfigured PID config";
    this.pidName = "Unconfigured";
  }
}