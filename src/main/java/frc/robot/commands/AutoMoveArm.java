package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.extendablearmandwrist.EnumArmLevel;
import frc.robot.subsystems.extendablearmandwrist.EnumHatchOrCargo;

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
    Robot.extendableArmAndWrist.goToHeight(hatchOrCargo, level);
  }

  @Override
  protected boolean isFinished() {
    return Robot.extendableArmAndWrist.moveIsDone();
  }

}