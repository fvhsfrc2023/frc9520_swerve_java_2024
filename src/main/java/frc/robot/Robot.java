// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
//import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSystem;


/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    public final Controller controller = new Controller();
    public final DriveSystem driveSystem = new DriveSystem();

    private final ParallelCommandGroup teleopCommands = new ParallelCommandGroup(new DriveCommand(driveSystem, controller));


    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
    }


    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().schedule(teleopCommands);
    }

    @Override
    public void teleopExit() {
        CommandScheduler.getInstance().cancel(teleopCommands);
    }


    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("theta", controller.getTheta());
        SmartDashboard.putNumber("v", controller.getV());
        SmartDashboard.putNumber("h", controller.getH());
        SmartDashboard.putNumber("z", controller.getZ());

        CommandScheduler.getInstance().run();
    }
}
