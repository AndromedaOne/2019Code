package frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers;

public class PIDConfiguration {

  private double m_p;
  private double m_i;
  private double m_d;
  private double m_f;
  private double m_minimumOutput;
  private double m_maximumOutput;
  private double m_absoluteTolerance;
  private String m_liveWindowName;
  private String m_pidName;

  public PIDConfiguration(double p, double i, double d, double f, double minOut, double maxOut,
      double absoluteTolerance, String liveWindowName, String pidName) {
    m_p = p;
    m_i = i;
    m_d = d;
    m_f = f;
    m_minimumOutput = minOut;
    m_maximumOutput = maxOut;
    m_absoluteTolerance = absoluteTolerance;
    m_liveWindowName = liveWindowName;
    m_pidName = pidName;
  }

  public double getP() {
    return m_p;
  }

  public void setP(double pParam) {
    m_p = pParam;
  }

  public double getI() {
    return m_i;
  }

  public void setI(double iParam) {
    m_i = iParam;
  }

  public double getD() {
    return m_d;
  }

  public void setD(double dParam) {
    m_d = dParam;
  }

  public double getf() {
    return m_f;
  }

  public void setf(double fParam) {
    m_f = fParam;
  }

  public double getMinimumOutput() {
    return m_minimumOutput;
  }

  public void setMinimumOutput(double minimumOutputParam) {
    m_minimumOutput = minimumOutputParam;
  }

  public double getMaximumOutput() {
    return m_maximumOutput;
  }

  public void setMaximumOutput(double maximumOutputParam) {
    m_maximumOutput = maximumOutputParam;
  }

  public double getAbsoluteTolerance() {
    return m_absoluteTolerance;
  }

  public void setAbsoluteTolerance(double absoluteToleranceParam) {
    m_absoluteTolerance = absoluteToleranceParam;
  }

  public String getLiveWindowName() {
    return m_liveWindowName;
  }

  public void setLiveWindowName(String liveWindowNameParam) {
    m_liveWindowName = liveWindowNameParam;
  }

  public String getPIDName() {
    return m_pidName;
  }

  public void setPIDName(String pidNameParam) {
    m_pidName = pidNameParam;
  }
}