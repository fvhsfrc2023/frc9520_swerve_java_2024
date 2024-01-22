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

    public DriveCommand(DriveSystem _driveSystem, Controller _controller) {
        driveSystem = _driveSystem;
        controller = _controller;
        addRequirements(driveSystem);
    }

    @Override
    public void initialize() {
        driveSystem.setZero();
    }

    @Override
    public void execute() {
        driveSystem.drive(
                controller.getRadius(),
//                Robot.controller.getTheta() / PI / 2,
                controller.getZ(),
                controller.getZ());

        if (controller.getButtonB()) {
            var motor = driveSystem.powerMotorFR;
            motor.setPosition(motor.getPosition().getValue() + Constants.DriveSystemConst.TALONFX_THETAMOTOR_COEF);
        }

        if (controller.getButtonA()) {
            var motor = driveSystem.powerMotorFR;
            motor.setPosition(motor.getPosition().getValue() + Constants.DriveSystemConst.TALONFX_THETAMOTOR_COEF / 8);
        }

        SmartDashboard.putNumber("radius", controller.getRadius());
    }
}
