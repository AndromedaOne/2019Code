package frc.robot.subsystems.leds;

import com.typesafe.config.Config;
import edu.wpi.first.wpilibj.DigitalOutput;
import frc.robot.Robot;

public class RealLEDs extends LEDs {

    private DigitalOutput red;
    private DigitalOutput green;
    private DigitalOutput blue;

    public RealLEDs() {
        Config conf = Robot.getConfig();

        red = new DigitalOutput(conf.getInt("susbsystems.led.red"));
        green = new DigitalOutput(conf.getInt("susbsystems.led.green"));
        blue = new DigitalOutput(conf.getInt("susbsystems.led.blue"));

        setPurple(1.0);
    }

    public void clearColor() {
        red.updateDutyCycle(0);
        green.updateDutyCycle(0);
        blue.updateDutyCycle(0);
    }

    private double validateBrightness(double brightness) {
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
    @Override
    public void setRGB(double red, double green, double blue) {
        clearColor();
        this.red.updateDutyCycle(validateBrightness(red));
        this.green.updateDutyCycle(validateBrightness(green));
        this.blue.updateDutyCycle(validateBrightness(blue));
    }
    
    /**
     * This method takes a brightness value from 0 - 1 for yellow
     */
    public void setYellow(double brightness) {
        clearColor();
        red.updateDutyCycle(validateBrightness(brightness));
        green.updateDutyCycle(validateBrightness(brightness));
    }

    /**
     * This method takes a brightness value from 0 - 1 for red
     */
    @Override
    public void setRed(double brightness) {
        clearColor();
        red.updateDutyCycle(validateBrightness(brightness));
    }

    /**
     * This method takes a brightness value from 0 - 1 for green
     */
    @Override
    public void setGreen(double brightness) {
        clearColor();
        green.updateDutyCycle(validateBrightness(brightness));
    }

    /**
     * This method takes a brightness value from 0 - 1 for blue
     */
    @Override
    public void setBlue(double brightness) {
        clearColor();
        blue.updateDutyCycle(validateBrightness(brightness));
    }

    @Override
    public void setPurple(double brightness) {
        clearColor();
        blue.updateDutyCycle(validateBrightness(brightness));
        red.updateDutyCycle(validateBrightness(brightness));
    }

    @Override
    protected void initDefaultCommand() {

    }

}