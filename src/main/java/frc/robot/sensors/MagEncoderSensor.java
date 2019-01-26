package frc.robot.sensors;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MagEncoderSensor implements PIDLoopable, PIDSource, Sendable {
    private WPI_TalonSRX talonSpeedController;
    private static final double TICKSTOINCHES = 0.0;
    private String subsystemName;
    private String sensorName;

    public MagEncoderSensor(WPI_TalonSRX talon) {
        talonSpeedController = talon;
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        talon.setSensorPhase(true); /* keep sensor and motor in phase */
    }

    public double getDistanceInches() {
        return getDistanceTicks() * TICKSTOINCHES;
    }

    public double getDistanceTicks() {
        return talonSpeedController.getSelectedSensorPosition();
    }

    @Override
    public double getClosedLoopSrc() {
        return getDistanceInches();

    }

    @Override
    public void reset() {
        talonSpeedController.setSelectedSensorPosition(0);
        return;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return getDistanceTicks();
    }

    public void putOnSmartDashboard(String subsystemNameParam, String sensorNameParam) {
        subsystemName = subsystemNameParam;
        sensorName = sensorNameParam;
        LiveWindow.add(this);
        this.setName(sensorName);
    }

    @Override
    public String getName() {
        return sensorName;
    }

    @Override
    public void setName(String name) {
        sensorName = name;
    }

    @Override
    public String getSubsystem() {
        return subsystemName;
    }

    @Override
    public void setSubsystem(String subsystem) {
        subsystemName = subsystem;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Counter");
        builder.addDoubleProperty(sensorName, this::getDistanceTicks, null);
    }

}