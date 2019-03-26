package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class LEDs extends Subsystem {

  protected DigitalOutput red;
  protected DigitalOutput green;
  protected DigitalOutput blue;

  public void clearColor() {
    red.updateDutyCycle(0);
    green.updateDutyCycle(0);
    blue.updateDutyCycle(0);
  }

  protected double validateBrightness(double brightness) {
    if (brightness > 1.0) {
      brightness = 1;
    } else if (brightness < 0) {
      brightness = 0;
    }
    return brightness;
  }

  /**
   * This method takes a brightness value from 0 - 1 for each color
   */
  public void setRGB(double red, double green, double blue) {
    clearColor();
    this.red.updateDutyCycle(validateBrightness(red));
    this.green.updateDutyCycle(validateBrightness(green));
    this.blue.updateDutyCycle(validateBrightness(blue));
  }

  /**
   * This method takes a brightness value from 0 - 1 for red
   */
  public void setRed(double brightness) {
    clearColor();
    red.updateDutyCycle(validateBrightness(brightness));
  }

  /**
   * This method takes a brightness value from 0 - 1 for green
   */
  public void setGreen(double brightness) {
    clearColor();
    green.updateDutyCycle(validateBrightness(brightness));
  }

  /**
   * This method takes a brightness value from 0 - 1 for blue
   */
  public void setBlue(double brightness) {
    clearColor();
    blue.updateDutyCycle(validateBrightness(brightness));
  }

  /**
   * This method takes a brightness value from 0 - 1 for yellow
   */
  public void setWhite(double brightness) {
    clearColor();
    red.updateDutyCycle(validateBrightness(brightness));
    green.updateDutyCycle(validateBrightness(brightness));
    blue.updateDutyCycle(validateBrightness(brightness));
  }

  /**
   * This method takes a brightness value from 0 - 1 for blue
   */
  public void setPurple(double brightness) {
    clearColor();
    System.out.println("Setting purple to: " + validateBrightness(brightness));
    blue.updateDutyCycle(validateBrightness(brightness));
    red.updateDutyCycle(validateBrightness(brightness));
  }

}