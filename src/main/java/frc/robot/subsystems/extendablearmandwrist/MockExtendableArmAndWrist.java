package frc.robot.subsystems.extendablearmandwrist;

public class MockExtendableArmAndWrist extends ExtendableArmAndWrist{

    @Override
    public void moveTopExtendableArmAndWristTalon(double value) {
        System.out.println("Moving the top ExtendableArmAndWrist Talon at: " + value);
    }

    @Override
    public void moveBottomExtendableArmAndWristTalon(double value) {
        System.out.println("Moving the bottom ExtendableArmAndWrist Talon at: " + value);
    }

    @Override
    public void moveShoulderJointTalon(double value) {
        System.out.println("Moving the shoulder Talon at: " + value);
    }

}