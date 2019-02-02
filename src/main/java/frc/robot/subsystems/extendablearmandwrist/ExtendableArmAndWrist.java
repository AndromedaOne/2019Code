package frc.robot.subsystems.extendablearmandwrist;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.TeleopArm;

public abstract class ExtendableArmAndWrist extends Subsystem {

  /**
   * Sets the default command to the teleoperated control command for the arm
   */
  @Override
  protected void initDefaultCommand() {
    // need to create a default command and add it here
    setDefaultCommand(new TeleopArm());
  }

  public abstract void goToHeight(EnumHatchOrCargo hatchOrCargo, EnumArmLevel armLevel);

  public abstract void stow();

  public abstract void move(double forwardBackSpeed, double rotateAmount);

  public abstract void shoulderRotate(double rotateAmount);

}