package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  public RealDriveTrain() {
    Config conf = Robot.getConfig();
    Config driveConf = conf.getConfig("ports.driveTrain");
    driveTrainLeftMaster = initTalonMaster(driveConf, "left");
    driveTrainLeftSlave = initTalonSlave(driveConf, "leftSlave", driveTrainLeftMaster,
        driveConf.getBoolean("leftSideInverted"));
    driveTrainRightMaster = initTalonMaster(driveConf, "right");
    driveTrainRightSlave = initTalonSlave(driveConf, "rightSlave", driveTrainRightMaster,
        driveConf.getBoolean("rightSideInverted"));
    differentialDrive = new DifferentialDrive(driveTrainLeftMaster, driveTrainRightMaster);
    if (conf.hasPath("subsystems.driveTrain.invertTurning")) {
      invertTurning = conf.getBoolean("subsystems.driveTrain.invertTurning");
    }

    // Gear Shift Solenoid
    if (Robot.getConfig().hasPath("subsystems.driveTrain.shifter")) {
      shifterPresentFlag = true;
      shifterSolenoid = new DoubleSolenoid(driveConf.getInt("pneumatics.forwardChannel"),
          driveConf.getInt("pneumatics.backwardsChannel"));
    }
    if (driveConf.getBoolean("useVelocityMode")) {
      setVelocityMode();
    }
  }

  public enum RobotGear {
    SLOWLOWGEAR, LOWGEAR, SLOWHIGHGEAR, HIGHGEAR
  }

  private RobotGear currentGear = RobotGear.SLOWHIGHGEAR;
  private final int kTimeoutMs = 30;

  private double lowGearMaxSpeed = 1;
  private double highGearMaxSpeed = 1;
  private final double kFirstGearModifier = 0.6;
  private final double kThirdGearModifier = 0.75;
  private final double kAccelerationSlope = 1.0 / 12.5;
  private double previousSpeed = 0;
  private double gearMod = 1;
  private int shifterDelayCounter = 0;
  private int delay = 4;
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

  @Override
  public void setGear(RobotGear gear) {
    currentGear = gear;
    switch (currentGear) {
    case SLOWLOWGEAR:
      gearMod = kFirstGearModifier;
      Robot.leftLeds.setRed(1.0);
      Robot.rightLeds.setRed(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 1);
      shiftToLowGear();
      break;
    case LOWGEAR:
      gearMod = 1;
      Robot.leftLeds.setWhite(1.0);
      Robot.rightLeds.setWhite(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 2);
      shiftToLowGear();
      break;
    case SLOWHIGHGEAR:
      gearMod = kThirdGearModifier;
      Robot.leftLeds.setBlue(1.0);
      Robot.rightLeds.setBlue(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 3);
      shiftToHighGear();
      break;
    case HIGHGEAR:
      gearMod = 1;
      Robot.leftLeds.setGreen(1.0);
      Robot.rightLeds.setGreen(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 4);
      shiftToHighGear();
      break;
    }
  }

  @Override
  public RobotGear getGear() {
    return currentGear;
  }

  public void toggleShifter() {
    switch (currentGear) {
    case SLOWLOWGEAR:
      System.out.println(" - Switching to Slow Low Gear - ");
      setGear(RobotGear.SLOWHIGHGEAR);
      break;
    case LOWGEAR:
      System.out.println(" - Switching to Low Gear - ");
      // This is due to Erics preference to shift from Low Gear to Slow High
      setGear(RobotGear.SLOWHIGHGEAR);
      break;
    case SLOWHIGHGEAR:
      System.out.println(" - Switching to High Gear - ");
      setGear(RobotGear.SLOWLOWGEAR);
      break;
    case HIGHGEAR:
      System.out.println(" - Switching to Slow High Gear - ");
      setGear(RobotGear.LOWGEAR);
      break;
    }
  }

  public void toggleSlowMode() {
    switch (currentGear) {
    case SLOWLOWGEAR:
      System.out.println(" - Switching to Slow Low Gear - ");
      setGear(RobotGear.LOWGEAR);
      break;
    case LOWGEAR:
      System.out.println(" - Switching to Low Gear - ");
      setGear(RobotGear.SLOWLOWGEAR);
      break;
    case SLOWHIGHGEAR:
      System.out.println(" - Switching to High Gear - ");
      setGear(RobotGear.HIGHGEAR);
      break;
    case HIGHGEAR:
      System.out.println(" - Switching to Slow High Gear - ");
      setGear(RobotGear.SLOWHIGHGEAR);
      break;
    }
  }

  // Inspired by
  // https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java
  // and
  private ArbitraryModeWPI_TalonSRX initTalonMaster(Config driveConf, String side) {
    ArbitraryModeWPI_TalonSRX _talon = new ArbitraryModeWPI_TalonSRX(driveConf.getInt(side + "Master"));
    final double closedLoopNeutralToMaxSpeedSeconds = 0.0;

    /* Factory Default all hardware to prevent unexpected behaviour */
    _talon.configFactoryDefault();
    _talon.setInverted(driveConf.getBoolean(side + "SideInverted"));
    _talon.setSensorPhase(driveConf.getBoolean(side + "SideSensorInverted"));

    /* Config sensor used for Primary PID [Velocity] */

    double lowGearP = 0.0;
    double lowGearI = 0.0;
    double lowGearD = 0.0;
    double highGearP = 0.0;
    double highGearI = 0.0;
    double highGearD = 0.0;

    lowGearMaxSpeed = readPIDConfigItem(driveConf, side, "LowGearMaxSpeed", 1);
    lowGearP = readPIDConfigItem(driveConf, side, "LowGearP", 0);
    lowGearI = readPIDConfigItem(driveConf, side, "LowGearI", 0);
    lowGearD = readPIDConfigItem(driveConf, side, "LowGearD", 0);

    highGearMaxSpeed = readPIDConfigItem(driveConf, side, "HighGearMaxSpeed", 1);
    highGearP = readPIDConfigItem(driveConf, side, "HighGearP", 0);
    highGearI = readPIDConfigItem(driveConf, side, "HighGearI", 0);
    highGearD = readPIDConfigItem(driveConf, side, "HighGearD", 0);

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

  private double readPIDConfigItem(Config driveConf, String side, String configItem, double defaultValue) {
    double configValue = defaultValue;
    if (driveConf.hasPath(side + "Side" + configItem)) {
      configValue = driveConf.getDouble(side + "Side" + configItem);
    }
    System.out.println(side + "Side" + configItem + "=" + configValue);
    return configValue;
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
    setDefaultCommand(new TeleOpDrive());
  }

  @Override
  public void periodic() {
  }

  public void move(double forwardBackSpeed, double rotateAmount, boolean squaredInputs) {
    // logMeasurements("Left", driveTrainLeftMaster, forwardBackSpeed, false);
    // logMeasurements("Right", driveTrainRightMaster, -forwardBackSpeed, true);
    if (invertTurning) {
      rotateAmount = -rotateAmount;
    }
    shifterDelayCounter++;
    double requestedSpeed = forwardBackSpeed * gearMod;
    if (requestedSpeed > previousSpeed) {
      previousSpeed += kAccelerationSlope;
      if (previousSpeed > requestedSpeed) {
        previousSpeed = requestedSpeed;
      }
    } else {
      previousSpeed -= kAccelerationSlope;
      if (previousSpeed < requestedSpeed) {
        previousSpeed = requestedSpeed;
      }
    }
    if (shifterDelayCounter >= delay) {
      Robot.driveTrain.changeControlMode(NeutralMode.Brake);
    } else {
      previousSpeed = 0;
      rotateAmount = 0;
    }
    differentialDrive.arcadeDrive(previousSpeed, rotateAmount * gearMod, squaredInputs);
    Trace.getInstance().addTrace(false, "move", new TracePair<>("ForwardBack", previousSpeed),
        new TracePair<>("Rotate", rotateAmount));
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
    Trace.getInstance().addTrace(false, "VCMeasure" + side, new TracePair<>("Percent", (double) motorOutput * 100),
        new TracePair<>("Speed", (double) _talon.getSelectedSensorVelocity(slotIdx)),
        new TracePair<>("Error", (double) _talon.getClosedLoopError(slotIdx)),
        new TracePair<>("Target", (double) _talon.getClosedLoopTarget(slotIdx)));
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
    shifterDelayCounter = 0;
    changeControlMode(NeutralMode.Coast);
  }

  public void shiftToHighGear() {
    System.out.println("Shifting to high gear");
    if (shifterSolenoid != null) {
      shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
      maxSpeed = highGearMaxSpeed;
      driveTrainLeftMaster.selectProfileSlot(kHighGearPIDSlot, 0);
      driveTrainRightMaster.selectProfileSlot(kHighGearPIDSlot, 0);
      slotIdx = 1;
      shifterDelayCounter = 0;
      changeControlMode(NeutralMode.Coast);
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
