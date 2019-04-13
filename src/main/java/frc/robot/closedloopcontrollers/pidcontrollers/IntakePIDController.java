
package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveIntakeSafely;
import frc.robot.sensors.anglesensor.AngleSensor;

public class IntakePIDController extends PIDControllerBase {

  private static IntakePIDController instance;
  private IntakePIDOut intakePIDOut;
  private AngleSensor intakeAngleSensor;

  private IntakePIDController() {
    super.absoluteToleranceForQuickMovement = 0.035;
    super.pForMovingQuickly = 0.25;
    super.iForMovingQuickly = 0;
    super.dForMovingQuickly = 0.25;

    super.absoluteToleranceForPreciseMovement = 0.01;
    super.pForMovingPrecisely = 0.125;
    super.iForMovingPrecisely = 0;
    super.dForMovingPrecisely = 0;

    super.outputRange = 1;
    super.subsystemName = "Intake";
    super.pidName = "IntakePID";

    intakeAngleSensor = Robot.intakeAngleSensor;
    intakeAngleSensor.putSensorOnLiveWindow(super.subsystemName, "Intake");
    intakePIDOut = new IntakePIDOut();
    super.setPIDConfiguration(super.pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(intakeAngleSensor, intakePIDOut, super.pidConfiguration);
    intakePIDOut.setContainer(super.pidMultiton);
  }

  private class IntakePIDOut implements PIDOutput {
    PIDMultiton container;

    public void setContainer(PIDMultiton containerParam) {
      container = containerParam;
    }

    @Override
    public void pidWrite(double output) {
      output = -output;
      MoveIntakeSafely.moveIntake(output);
    }
  }

  public static IntakePIDController getInstance() {
    System.out.println(" --- Asking for Intake PID Instance ---");
    if (instance == null) {
      System.out.println("Creating new Intake PID Controller");
      instance = new IntakePIDController();
    }
    return instance;
  }

}