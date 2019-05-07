package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.commands.TurnToCompassHeading;
import frc.robot.commands.TurnToDeltaAngle;

public class TurnToDeltaAngleQuickly extends CommandGroup {
  public TurnToDeltaAngleQuickly(double desiredAngle, boolean useCompassHeading) {
    if (useCompassHeading) {
      addSequential(new TurnToCompassHeading(desiredAngle));
    } else {
      addSequential(new TurnToDeltaAngle(desiredAngle));

    }
  }

  public void initialize() {

    GyroPIDController.getInstance().pidMultiton.setPIDTerms(GyroPIDController.getInstance().getPForMovingQuickly(),
        GyroPIDController.getInstance().getIForMovingQuickly(), GyroPIDController.getInstance().getDForMovingQuickly());
    GyroPIDController.getInstance().pidMultiton
        .setTolerance(GyroPIDController.getInstance().getToleranceForMovingQuickly());

  }

}