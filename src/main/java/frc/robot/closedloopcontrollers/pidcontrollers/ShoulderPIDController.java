package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.exceptions.ArmOutOfBoundsException;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class ShoulderPIDController extends PIDControllerBase {

  private static ShoulderPIDController instance;
  private ShoulderPIDOut shoulderPIDOut;
  private MagEncoderSensor shoulderEncoder;

  private ShoulderPIDController() {
    super.absoluteTolerance = 3;
    super.p = 2.0 * Math.pow(10, -4);
    super.i = 0;
    super.d = 0;
    super.subsytemName = "Extendable Arm and Wrist";
    super.pidName = "ShoulderPID";

    super.trace = Trace.getInstance();
    shoulderPIDOut = new ShoulderPIDOut();
    super.setPIDConfiguration(super.pidConfiguration);
    shoulderEncoder = Robot.armRotateEncoder1;
    shoulderEncoder.putSensorOnLiveWindow(super.subsytemName, "ShoulderEncoder");
    super.pidMultiton = PIDMultiton.getInstance(shoulderEncoder, shoulderPIDOut, super.pidConfiguration);
    shoulderPIDOut.setContainer(super.pidMultiton);
  }

  private class ShoulderPIDOut implements PIDOutput {
    PIDMultiton container;

    public void setContainer(PIDMultiton containerParam) {
      container = containerParam;
    }

    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "ShoulderPID", new TracePair("Output", output*10000), 
          new TracePair("SetpointTicks", container.getSetpoint()),
          new TracePair("SetpointDegrees", container.getSetpoint()*MoveArmAndWristSafely.SHOULDERTICKSTODEGRESS),
          new TracePair("AngleTicks", shoulderEncoder.pidGet()),
          new TracePair("AngleDegrees", shoulderEncoder.pidGet()*MoveArmAndWristSafely.SHOULDERTICKSTODEGRESS));
      try {
        MoveArmAndWristSafely.move(0, 0, output, MoveArmAndWristSafely.DontUsePIDHold.SHOULDER);
      } catch (ArmOutOfBoundsException e) {
        System.out.println(e.getMessage());
        container.disable();
      }
    }
  }

  public static ShoulderPIDController getInstance() {
    System.out.println(" --- Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Shoulder PID Controller");
      instance = new ShoulderPIDController();
    }
    return instance;
  }

  @Override
  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint/MoveArmAndWristSafely.SHOULDERTICKSTODEGRESS);
  }

}