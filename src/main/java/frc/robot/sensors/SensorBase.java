package frc.robot.sensors;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public abstract class SensorBase {

  public abstract void putSensorOnLiveWindow(String subsystemNameParam, 
  String sensorNameParam);

  protected void putReadingOnLiveWindow(String subsystem, String readingName, 
  DoubleSupplier doubleSupplier) {
    Sendable sendable = new Sendable() {

      @Override
      public String getName() {
        return readingName;
      }

      @Override
      public void setName(String name) {
        
      }

      @Override
      public String getSubsystem() {
        return subsystem;
      }

      @Override
      public void setSubsystem(String subsystem) {

      }

      @Override
      public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Counter");
        // This needs to be value in order to work; Value is a magical string 
        // that allows this counter to appear on Live Window.
        builder.addDoubleProperty("Value", doubleSupplier, null);
      }

    };
    LiveWindow.add(sendable);
  }

  protected void putReadingOnLiveWindow(String subsystem, String readingName, 
  BooleanSupplier booleanSupplier) {
    Sendable sendable = new Sendable() {

      @Override
      public String getName() {
        return readingName;
      }

      @Override
      public void setName(String name) {
        
      }

      @Override
      public String getSubsystem() {
        return subsystem;
      }

      @Override
      public void setSubsystem(String subsystem) {

      }

      @Override
      public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Input");
        // This needs to be value in order to work; Value is a magical string 
        // that allows this Digital Input to appear on Live Window.
        builder.addBooleanProperty("Value", booleanSupplier, null);
      }

    };
    LiveWindow.add(sendable);
  }

}