// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static java.lang.Math.PI;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
    public static final int CAN_TIMEOUT_MS = 10;

    //The constants used in the DriveSystem subclass
    public static class DriveSystemConst {

        public static final double POWER_P = 2.0;
        public static final double POWER_I = 100.0;
        public static final double POWER_D = 0.02;

        public static final double THETA_P = 2.0;
        public static final double THETA_I = 100.0;
        public static final double THETA_D = 0.02;
        public static final double TALONFX_THETAMOTOR_COEF = 21.65;

        public static final double THETA_DIRECTION_ERROR_ZONE = 0.0001;
        public static final double THETA_DIRECTION_CORRECTION_COEF = 0.1;
        public static final double THETA_DIRECTION_CORRECTION_MAX = 0.5;
    }
     //The constants used in the DriveSystem subclass
    public static class ControllerConst {
        public static final int DRIVE_JOYSTICK_PORT = 0; //this is the joystick varablie checks to see if the joystick is in port 0

        public static final int X_CHANNEL = 0; //this var makes the vaule of this be set to 0 on start
        public static final int Y_CHANNEL = 1; //this var makes the vaule of this be set to 1 on start
        public static final int Z_CHANNEL = 4; //thus var makes the vaule of this be set to 4 on start

        //Deadzone limits for the x, y, & z axes respectively
        public static final double X_DEAD_ZONE = 0.02;
        public static final double Y_DEAD_ZONE = 0.02;
        public static final double Z_DEAD_ZONE = 0.02;

        public static final double R_DEAD_ZONE = 0.05; //this sets the Rotate dead zone to 0.05 for the controller

        //theta constants (believe to associate with gear ratios, may be wrong)
        public static final double THETA_TURN_PER_FRAME = PI / 6;
        public static final long THETA_UPDATE_TIME = 50;
    }
}
