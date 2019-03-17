package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class LEDs extends Subsystem {

    public abstract void setRGB(double red, double green, double blue);

    public abstract void setRed(double brightness);

    public abstract void setGreen(double brightness);

    public abstract void setBlue(double brightness);

    public abstract void setYellow(double brightness);

    public abstract void setPurple(double brightness);

}