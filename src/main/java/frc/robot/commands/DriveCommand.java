package frc.robot.commands;//package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Controller;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSystem;

import static java.lang.Math.PI;

public class DriveCommand extends Command {
    private final DriveSystem driveSystem;
    private final Controller controller;

    private boolean brakeMode = true;

    public DriveCommand(DriveSystem _driveSystem, Controller _controller) {
        driveSystem = _driveSystem;
        controller = _controller;
        addRequirements(driveSystem);
    }

    @Override
    public void initialize() {
//        driveSystem.setZero();
    }

    @Override
    public void execute() {
        driveSystem.drive(
                controller.getRadius(),
                controller.getTheta() / PI,
//                controller.getZ(),
                controller.getZ());

        if (controller.getButtonA())
            driveSystem.setZero();

        if (controller.getButtonB()) {
            brakeMode = !brakeMode;
            driveSystem.setBrakeMode(brakeMode);
        }


        SmartDashboard.putBoolean("brakeMode", brakeMode);
        SmartDashboard.putNumber("radius", controller.getRadius());
    }
}
