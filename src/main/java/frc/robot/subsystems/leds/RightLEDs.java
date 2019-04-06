package frc.robot.subsystems.leds;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class RightLEDs extends LEDs {

  public RightLEDs() {
    Config conf = Robot.getConfig();
    red = new DigitalOutput(conf.getInt("subsystems.led.rightRed"));
    red.enablePWM(0);
    green = new DigitalOutput(conf.getInt("subsystems.led.rightGreen"));
    green.enablePWM(0);
    blue = new DigitalOutput(conf.getInt("subsystems.led.rightBlue"));
    blue.enablePWM(0);
    setPurple(1.0);
  }

  @Override
  public void updateLEDs() {
    if (Robot.frontLineSensor.findLine().lineFound && (Robot.frontLineSensor.findLine().lineAngle <= 0)) {
      blinkCounter++;
      if (blinkCounter > (Robot.drivetrainFrontUltrasonic.getMinDistanceInches() * kBlinkSpeedMultiplier) - 13) {
        blinkCounter = 0;
        toggleLEDs();
        if (SmartDashboard.getString("Right", "").equals("")) {
          SmartDashboard.putString("Right", "\u26A0");
        } else {
          SmartDashboard.putString("Right", "");
        }
      }
    } else {
      toggleLEDsOn();
    }
  }

  @Override
  protected void initDefaultCommand() {

  }

}