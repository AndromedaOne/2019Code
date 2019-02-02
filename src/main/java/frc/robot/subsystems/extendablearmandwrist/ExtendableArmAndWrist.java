package frc.robot.subsystems.extendablearmandwrist;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class ExtendableArmAndWrist extends Subsystem {

  public abstract void goToHeight(EnumHatchOrCargo hatchOrCargo, EnumArmLevel armLevel);

  public abstract void stow();

  public abstract void move(double forwardBackSpeed, double rotateAmount);

  public abstract void shoulderRotate(double rotateAmount);

}