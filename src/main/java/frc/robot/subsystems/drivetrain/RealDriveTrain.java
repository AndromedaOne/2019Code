package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.commands.TeleOpDrive;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

/* For the Java CTRE Talon API Docs, see this link:
http://www.ctr-electronics.com/downloads/api/java/html/index.html
*/

/**
 *
 */
public class RealDriveTrain extends DriveTrain {
  public static ArbitraryModeWPI_TalonSRX driveTrainLeftMaster;
  public static WPI_TalonSRX driveTrainLeftSlave;
  public static SpeedControllerGroup driveTrainLeftSpeedController;
  public static ArbitraryModeWPI_TalonSRX driveTrainRightMaster;
  public static WPI_TalonSRX driveTrainRightSlave;
  public static SpeedControllerGroup driveTrainRightSpeedController;
  public static DifferentialDrive differentialDrive;
  public static DoubleSolenoid shifterSolenoid;
  private boolean shifterPresentFlag = false;
  private boolean invertTurning = false;
  // private PowerDistributionPanel pdp = new PowerDistributionPanel();

  public boolean getShifterPresentFlag() {
    return shifterPresentFlag;
  }

  private class VelocityPIDParamaters {
    double maxSpeed;
    double p;
    double i;
    double d;

    public VelocityPIDParamaters(Config conf){
      maxSpeed = conf.getDouble("maxSpeed");
      p = conf.getDouble("P");
      i = conf.getDouble("I");
      d = conf.getDouble("D");
    }
  }

  public RealDriveTrain() {
    Config conf = Robot.getConfig();
    Config driveSubConfig = conf.getConfig("subsystems.driveTrain");
    Config drivePortConf = conf.getConfig("ports.driveTrain");
    driveTrainLeftMaster = initTalonMaster(drivePortConf, driveSubConfig, "left");
    driveTrainLeftSlave = initTalonSlave(drivePortConf, "leftSlave", driveTrainLeftMaster,
        drivePortConf.getBoolean("leftSideInverted"));
    driveTrainRightMaster = initTalonMaster(drivePortConf, driveSubConfig, "right");
    driveTrainRightSlave = initTalonSlave(drivePortConf, "rightSlave", driveTrainRightMaster,
        drivePortConf.getBoolean("rightSideInverted"));
    differentialDrive = new DifferentialDrive(driveTrainLeftMaster, driveTrainRightMaster);
    if (conf.hasPath("subsystems.driveTrain.invertTurning")) {
      invertTurning = conf.getBoolean("subsystems.driveTrain.invertTurning");
    }

    // Gear Shift Solenoid
    if (Robot.getConfig().hasPath("subsystems.driveTrain.shifter")) {
      shifterPresentFlag = true;
      shifterSolenoid = new DoubleSolenoid(drivePortConf.getInt("pneumatics.forwardChannel"),
          drivePortConf.getInt("pneumatics.backwardsChannel"));
    }
    if (driveSubConfig.getBoolean("useVelocityMode")) {
      setVelocityMode();
    }
  }

  private final int kTimeoutMs = 30;

  private double lowGearMaxSpeed = 1;
  private double highGearMaxSpeed = 1;

  private double maxSpeed = lowGearMaxSpeed;
  private final int kLowGearPIDSlot = 0;
  private final int kHighGearPIDSlot = 1;
  private int slotIdx = 0;

  /*
   * Implement math according to section 12.4.2 of the TALON SRX Software
   * Reference manual Rev 1.22 Also inspired by
   * https://phoenix-documentation.readthedocs.io/en/latest/ch16_ClosedLoop.html#
   * motion-magic-position-velocity-current-closed-loop-closed-loop
   */

  /**
   * Specialization of WPI_TalonSRX
   * 
   * The WPI_TalonSRX class implements a set() method to set the speed of the
   * TalonSRX. Unfortunately, that method <i>always</i> drives the motor in
   * PercentOutput mode. We need to be able drive the motor in Velocity mode to
   * use the closed loop velocity mode. So we extend WPI_TalonSRX and provide our
   * own set() method which allows us to set the control mode (via the newly added
   * setControlMode() method.)
   */
  class ArbitraryModeWPI_TalonSRX extends WPI_TalonSRX {
    private ControlMode controlMode = ControlMode.PercentOutput;
    private double speed;

    ArbitraryModeWPI_TalonSRX(int deviceNumber) {
      super(deviceNumber);
    }

    /**
     * Set the control mode to be used when driving the motor
     * 
     * @param controlMode Typically ControlMode.PercentOutput or
     * ControlMode.Velocity
     */
    public void setControlMode(ControlMode controlMode) {
      this.controlMode = controlMode;
    }

    @Override
    public void set(double speed) {
      this.speed = speed;
      set(controlMode, speed);
      feed();
    }

    @Override
    public double get() {
      return speed;
    }
  }

  public void setVBusMode() {
    setVBusMode(driveTrainLeftMaster);
    setVBusMode(driveTrainRightMaster);
    differentialDrive.setMaxOutput(1.0);
  }

  private void setVBusMode(ArbitraryModeWPI_TalonSRX talon) {
    talon.setControlMode(ControlMode.PercentOutput);
  }

  public void setVelocityMode() {
    setVelocityMode(driveTrainLeftMaster);
    setVelocityMode(driveTrainRightMaster);
    differentialDrive.setMaxOutput(maxSpeed);
    System.out.println("maxSpeed set to " + maxSpeed);
  }

  private void setVelocityMode(ArbitraryModeWPI_TalonSRX talon) {
    talon.setControlMode(ControlMode.Velocity);

  }

  // Inspired by
  // https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java
  // and
  private ArbitraryModeWPI_TalonSRX initTalonMaster(Config drivePortConf, Config driveSubsystemConf, String side) {
    ArbitraryModeWPI_TalonSRX _talon = new ArbitraryModeWPI_TalonSRX(drivePortConf.getInt(side + "Master"));
    final double closedLoopNeutralToMaxSpeedSeconds = 0.0;

    /* Factory Default all hardware to prevent unexpected behaviour */
    _talon.configFactoryDefault();
    _talon.setInverted(drivePortConf.getBoolean(side + "SideInverted"));
    _talon.setSensorPhase(drivePortConf.getBoolean(side + "SideSensorInverted"));

    /* Config sensor used for Primary PID [Velocity] */

    double lowGearP = 0.0;
    double lowGearI = 0.0;
    double lowGearD = 0.0;
    double highGearP = 0.0;
    double highGearI = 0.0;
    double highGearD = 0.0;

    lowGearMaxSpeed = readPIDConfigItem(drivePortConf, side, "LowGearMaxSpeed", 1);
    lowGearP = readPIDConfigItem(drivePortConf, side, "LowGearP", 0);
    lowGearI = readPIDConfigItem(drivePortConf, side, "LowGearI", 0);
    lowGearD = readPIDConfigItem(drivePortConf, side, "LowGearD", 0);

    highGearMaxSpeed = readPIDConfigItem(drivePortConf, side, "HighGearMaxSpeed", 1);
    highGearP = readPIDConfigItem(drivePortConf, side, "HighGearP", 0);
    highGearI = readPIDConfigItem(drivePortConf, side, "HighGearI", 0);
    highGearD = readPIDConfigItem(drivePortConf, side, "HighGearD", 0);

    _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
    /**
     * Phase sensor accordingly. Positive Sensor Reading should match Green
     * (blinking) Leds on Talon
     * 
     * /* Config the peak and nominal outputs
     */
    _talon.configNominalOutputForward(0, kTimeoutMs);
    _talon.configNominalOutputReverse(0, kTimeoutMs);
    _talon.configPeakOutputForward(1, kTimeoutMs);
    _talon.configPeakOutputReverse(-1, kTimeoutMs);

    /* Config the Velocity closed loop gains in slot0 */
    _talon.config_kF(kLowGearPIDSlot, (1023 / lowGearMaxSpeed), kTimeoutMs);
    _talon.config_kP(kLowGearPIDSlot, lowGearP, kTimeoutMs);
    _talon.config_kI(kLowGearPIDSlot, lowGearI, kTimeoutMs);
    _talon.config_kD(kLowGearPIDSlot, lowGearD, kTimeoutMs);
    _talon.config_IntegralZone(kLowGearPIDSlot, 50, kTimeoutMs);
    /* Config the Velocity closed loop gains in slot1 */
    _talon.config_kF(kHighGearPIDSlot, (1023 / highGearMaxSpeed), kTimeoutMs);
    _talon.config_kP(kHighGearPIDSlot, highGearP, kTimeoutMs);
    _talon.config_kI(kHighGearPIDSlot, highGearI, kTimeoutMs);
    _talon.config_kD(kHighGearPIDSlot, highGearD, kTimeoutMs);
    _talon.config_IntegralZone(kHighGearPIDSlot, 50, kTimeoutMs);
    _talon.configClosedloopRamp(closedLoopNeutralToMaxSpeedSeconds);
    return _talon;
  }

  private double readPIDConfigItem(Config drivePortConf, String side, String configItem, double defaultValue) {
    double configValue = defaultValue;
    if (drivePortConf.hasPath(side + "Side" + configItem)) {
      configValue = drivePortConf.getDouble(side + "Side" + configItem);
    }
    System.out.println(side + "Side" + configItem + "=" + configValue);
    return configValue;
  }

  private WPI_TalonSRX initTalonSlave(Config drivePortConf, String motorName, WPI_TalonSRX master, boolean isInverted) {
    WPI_TalonSRX slaveMotor = new WPI_TalonSRX(drivePortConf.getInt(motorName));
    slaveMotor.configFactoryDefault();
    slaveMotor.follow(master);
    slaveMotor.setInverted(isInverted);

    return slaveMotor;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeleOpDrive());
  }

  @Override
  public void periodic() {
  }

  public void move(double forwardBackSpeed, double rotateAmount, boolean squaredInputs) {
    Trace.getInstance().addTrace(true, "move", new TracePair("ForwardBack", forwardBackSpeed),
        new TracePair("Rotate", rotateAmount));
    // logMeasurements("Left", driveTrainLeftMaster, forwardBackSpeed, false);
    // logMeasurements("Right", driveTrainRightMaster, -forwardBackSpeed, true);
    if (invertTurning) {
      rotateAmount = -rotateAmount;
    }
    differentialDrive.arcadeDrive(forwardBackSpeed, rotateAmount, squaredInputs);
  }

  /* String for output */
  StringBuilder _sb = new StringBuilder();
  int _loops = 0;

  private void logMeasurements(String side, WPI_TalonSRX _talon, double targetVelocity, boolean doneMeasuring) {
    /* Get Talon/Victor's current output percentage */
    if (_talon == null) {
      System.out.println("TALON IS NULL!!! ");
    }
    double motorOutput = _talon.getMotorOutputPercent();
    Trace.getInstance().addTrace(false, "VCMeasure" + side, new TracePair("Percent", (double) motorOutput * 100),
        new TracePair("Speed", (double) _talon.getSelectedSensorVelocity(slotIdx)),
        new TracePair("Error", (double) _talon.getClosedLoopError(slotIdx)),
        new TracePair("Target", (double) _talon.getClosedLoopTarget(slotIdx)));
//        new TracePair("Battery Voltage", (double) _talon.getBusVoltage()),
//        new TracePair("Current Channel 0", (double) pdp.getCurrent(0)),
//        new TracePair("Current Channel 1", (double) pdp.getCurrent(1)),
//        new TracePair("Current Channel 2", (double) pdp.getCurrent(2)),
//        new TracePair("Current Channel 3", (double) pdp.getCurrent(3)));
  }

  public void stop() {
    differentialDrive.stopMotor();
  }

  public WPI_TalonSRX getLeftRearTalon() {
    return driveTrainLeftMaster;
  }

  public void shiftToLowGear() {
    System.out.println("Shifting to low gear");
    if (shifterSolenoid != null) {
      shifterSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    maxSpeed = lowGearMaxSpeed;
    driveTrainLeftMaster.selectProfileSlot(kLowGearPIDSlot, 0);
    driveTrainRightMaster.selectProfileSlot(kLowGearPIDSlot, 0);
    slotIdx = 0;
  }

  public void shiftToHighGear() {
    System.out.println("Shifting to high gear");
    if (shifterSolenoid != null) {
      shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
      maxSpeed = highGearMaxSpeed;
      driveTrainLeftMaster.selectProfileSlot(kHighGearPIDSlot, 0);
      driveTrainRightMaster.selectProfileSlot(kHighGearPIDSlot, 0);
      slotIdx = 1;
    } else {
      System.out.println("NO SHIFTER");
    }
  }

  @Override
  public void changeControlMode(NeutralMode mode) {
    driveTrainLeftMaster.setNeutralMode(mode);
    driveTrainLeftSlave.setNeutralMode(mode);
    driveTrainRightMaster.setNeutralMode(mode);
    driveTrainRightSlave.setNeutralMode(mode);
  }
}
