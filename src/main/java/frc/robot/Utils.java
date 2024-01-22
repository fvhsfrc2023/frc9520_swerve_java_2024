package frc.robot;


import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.hardware.TalonFX;

public class Utils {
    public static double sign(double num) {
        if (num == 0.0)
            return 0.0;
        else if (num < 0.0)
            return -1.0;
        else
            return 1.0;
    }

    public static class MotorUtils {
        public static void configureMotor(TalonFX motor, double P, double I, double D) {
            var configs = new Slot0Configs();
            configs.kP = P;
            configs.kI = I;
            configs.kD = D;
            motor.getConfigurator().apply(configs);
        }
    }
}
