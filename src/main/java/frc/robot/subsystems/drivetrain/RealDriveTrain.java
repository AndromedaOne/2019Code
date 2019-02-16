package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.commands.TeleOpDrive;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

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

  public boolean getShifterPresentFlag() {
    return shifterPresentFlag;
  }

  private final int kTimeoutMs = 30;
  /* 100% throttle corresponds to 13500 RPM in low gear */
  private final double kMaxSpeedLowGear = 13500;
  /* 100% throttle corresponds to 35500 RPM in high gear */
  private final double kMaxSpeedHighGear = 35500;
  private double maxSpeed = kMaxSpeedLowGear;
  private final int kLowGearPIDSlot = 0;
  private final int kHighGearPIDSlot = 1;
  private int slotIdx = 0;
  /*
   * Implement math according to section 12.4.2 of the TALON SRX Software
   * Reference manual Rev 1.22 Also inspired by
   * https://phoenix-documentation.readthedocs.io/en/latest/ch16_ClosedLoop.html#
   * motion-magic-position-velocity-current-closed-loop-closed-loop
   */
  private final double kLowF = 1023 / kMaxSpeedLowGear;
  private final double kLowP = 1 * (.1 * 1023) / 590; // Measured an error of ~590 on 2/10/19
  private final double kLowI = 0;
  private final double kLowD = 10 * kLowP;

  private final double kHighF = 1023 / kMaxSpeedHighGear;
  private final double kHighP = 1 * (.1 * 1023) / 590; // Measured an error of ~590 on 2/10/19
  private final double kHighI = 0;
  private final double kHighD = 10 * kLowP;

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
  }

  private void setVelocityMode(ArbitraryModeWPI_TalonSRX talon) {
    talon.setControlMode(ControlMode.Velocity);

  }

  // Inspired by
  // https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java
  // and
  private ArbitraryModeWPI_TalonSRX initTalonMaster(Config driveConf, String side) {
    ArbitraryModeWPI_TalonSRX _talon = new ArbitraryModeWPI_TalonSRX(driveConf.getInt(side + "Master"));
    /* Factory Default all hardware to prevent unexpected behaviour */
    _talon.configFactoryDefault();
    _talon.setInverted(driveConf.getBoolean(side + "SideInverted"));
    _talon.setSensorPhase(driveConf.getBoolean(side + "SideSensorInverted"));

    /* Config sensor used for Primary PID [Velocity] */

    _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);

    _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 1, kTimeoutMs);
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
    _talon.config_kF(kLowGearPIDSlot, kLowF, kTimeoutMs);
    _talon.config_kP(kLowGearPIDSlot, kLowP, kTimeoutMs);
    _talon.config_kI(kLowGearPIDSlot, kLowI, kTimeoutMs);
    _talon.config_kD(kLowGearPIDSlot, kLowD, kTimeoutMs);
    /* Config the Velocity closed loop gains in slot1 */
    _talon.config_kF(kHighGearPIDSlot, kHighF, kTimeoutMs);
    _talon.config_kP(kHighGearPIDSlot, kHighP, kTimeoutMs);
    _talon.config_kI(kHighGearPIDSlot, kHighI, kTimeoutMs);
    _talon.config_kD(kHighGearPIDSlot, kHighD, kTimeoutMs);
    return _talon;
  }

  private WPI_TalonSRX initTalonSlave(Config driveConf, String motorName, WPI_TalonSRX master, boolean isInverted) {
    WPI_TalonSRX slaveMotor = new WPI_TalonSRX(driveConf.getInt(motorName));
    slaveMotor.configFactoryDefault();
    slaveMotor.follow(master);
    slaveMotor.setInverted(isInverted);

    return slaveMotor;
  }

  @Override
  public void initDefaultCommand() {

    Config conf = Robot.getConfig();
    Config driveConf = conf.getConfig("ports.driveTrain");
    setDefaultCommand(new TeleOpDrive());
    driveTrainLeftMaster = initTalonMaster(driveConf, "left");
    driveTrainLeftSlave = initTalonSlave(driveConf, "leftSlave", driveTrainLeftMaster,
        driveConf.getBoolean("leftSideInverted"));
    driveTrainRightMaster = initTalonMaster(driveConf, "right");
    driveTrainRightSlave = initTalonSlave(driveConf, "rightSlave", driveTrainRightMaster,
        driveConf.getBoolean("rightSideInverted"));
    differentialDrive = new DifferentialDrive(driveTrainLeftMaster, driveTrainRightMaster);
    setVelocityMode();

    // Gear Shift Solenoid
    if (Robot.getConfig().hasPath("subsystems.driveTrain.shifter")) {
      shifterPresentFlag = true;
      shifterSolenoid = new DoubleSolenoid(driveConf.getInt("pneumatics.forwardChannel"),
          driveConf.getInt("pneumatics.backwardsChannel"));
        }
        shiftToLowGear();
  }

  @Override
  public void periodic() {
  }

  public void move(double forwardBackSpeed, double rotateAmount, boolean squaredInputs) {
    Trace.getInstance().addTrace(true, "move", new TracePair("ForwardBack", forwardBackSpeed),
        new TracePair("Rotate", rotateAmount));
    logMeasurements("Left", driveTrainLeftMaster, forwardBackSpeed, false);
    logMeasurements("Right", driveTrainRightMaster, -forwardBackSpeed, true);
    differentialDrive.arcadeDrive(forwardBackSpeed, rotateAmount, squaredInputs);
  }

  /* String for output */
  StringBuilder _sb = new StringBuilder();
  int _loops = 0;

  private void logMeasurements(String side, WPI_TalonSRX _talon, double targetVelocity, boolean doneMeasuring) {
    /* Get Talon/Victor's current output percentage */
    double motorOutput = _talon.getMotorOutputPercent();

    Trace.getInstance().addTrace(true, "VCMeasure" + side, new TracePair("Percent", (double) motorOutput * 100),
        new TracePair("Speed", (double) _talon.getSelectedSensorVelocity(slotIdx)),
        new TracePair("Error", (double) _talon.getClosedLoopError(slotIdx)),
        new TracePair("Target", (double) _talon.getClosedLoopTarget(slotIdx)));
  }

  public void stop() {
    differentialDrive.stopMotor();
  }

  public WPI_TalonSRX getLeftRearTalon() {
    return driveTrainLeftMaster;
  }

  public void shiftToLowGear() {
    if (shifterSolenoid != null) {
      shifterSolenoid.set(DoubleSolenoid.Value.kReverse); 
    }
    maxSpeed = kMaxSpeedLowGear;
    driveTrainLeftMaster.selectProfileSlot(kLowGearPIDSlot, 0);
    driveTrainRightMaster.selectProfileSlot(kLowGearPIDSlot, 0);
    slotIdx = 0;
  }

  public void shiftToHighGear() {
    if (shifterSolenoid != null) {
      shifterSolenoid.set(DoubleSolenoid.Value.kForward);
      maxSpeed = kMaxSpeedHighGear;
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
