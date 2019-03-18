package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.extendablearmandwrist.EnumArmLevel;
import frc.robot.subsystems.extendablearmandwrist.EnumHatchOrCargo;
import frc.robot.telemetries.Trace;

public class AutoMoveArm extends Command {
  protected EnumArmLevel level;
  protected EnumHatchOrCargo hatchOrCargo;

  public AutoMoveArm(EnumArmLevel level, EnumHatchOrCargo hatchOrCargo) {
    this.level = level;
    this.hatchOrCargo = hatchOrCargo;
    requires(Robot.extendableArmAndWrist);
    System.out.println("Beginning execution of automatic arm control.");
    System.out.println("Moving to: " + level.toString() + " with a " + hatchOrCargo.toString());
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("AutoMoveArm");
    // Robot.extendableArmAndWrist.goToHeight(hatchOrCargo, level);
  }

  @Override
  protected boolean isFinished() {
    // return Robot.extendableArmAndWrist.moveIsDone();
    return true;
  }

  protected void end() {
    Trace.getInstance().logCommandStop("AutoMoveArm");
  }

}