package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class ShoulderPIDController extends PIDControllerBase {

  private static ShoulderPIDController instance;
  private ShoulderPIDOut shoulderPIDOut;
  private MagEncoderSensor shoulderEncoder;

  private ShoulderPIDController() {
    super.absoluteTolerance = 3;
    super.p = 0;
    super.i = 0;
    super.d = 0;
    super.subsytemName = "Extendable Arm and Wrist";
    super.pidName = "ShoulderPID";

    super.trace = Trace.getInstance();
    shoulderPIDOut = new ShoulderPIDOut();
    super.setPIDConfiguration(super.pidConfiguration);
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
      trace.addTrace(true, "Shoulder PID", new TracePair("Output", output), new TracePair("Setpoint", _setpoint),
          new TracePair("Angle", shoulderEncoder.pidGet()));
      MoveArmAndWristSafely.moveArmWristShoulder(0, 0, output);
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

}