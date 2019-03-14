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
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("AutoMoveArm");
    // Robot.extendableArmAndWrist.goToHeight(hatchOrCargo, level);
  }

  @Override
  protected boolean isFinished() {
    Trace.getInstance().logCommandStop("AutoMoveArm");
    // return Robot.extendableArmAndWrist.moveIsDone();
    return true;
  }

}