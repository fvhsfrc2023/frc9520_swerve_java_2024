package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveSystemConst;
import frc.robot.Utils.MotorUtils;

import static java.lang.Math.*;

public class DriveSystem extends SubsystemBase {

    public final TalonFX powerMotorFL = new TalonFX(2);
    public final TalonFX powerMotorFR = new TalonFX(7);
    public final TalonFX powerMotorRL = new TalonFX(3);
    public final TalonFX powerMotorRR = new TalonFX(5);
    public final TalonFX[] powerMotors = {
            powerMotorFL,
            powerMotorFR,
            powerMotorRL,
            powerMotorRR
    };

    public final TalonFX thetaMotorFL = new TalonFX(1);
    public final TalonFX thetaMotorFR = new TalonFX(6);
    public final TalonFX thetaMotorRL = new TalonFX(4);
    public final TalonFX thetaMotorRR = new TalonFX(8);
    public final TalonFX[] thetaMotors = {
            thetaMotorFL,
            thetaMotorFR,
            thetaMotorRL,
            thetaMotorRR
    };


    public DriveSystem() {
        for (var motor: powerMotors) {
            MotorUtils.configureMotor(
                    motor,
                    DriveSystemConst.POWER_P,
                    DriveSystemConst.POWER_I,
                    DriveSystemConst.POWER_D
            );
        }

        for (var motor: thetaMotors) {
            MotorUtils.configureMotor(
                    motor,
                    DriveSystemConst.THETA_P,
                    DriveSystemConst.THETA_I,
                    DriveSystemConst.THETA_D
            );
        }
    }


    private double roundTheta(double theta) {
        double sign = theta > 0 ? 1 : -1;
        theta *= sign;
        theta += DriveSystemConst.TALONFX_THETAMOTOR_COEF / 2;
        theta %= DriveSystemConst.TALONFX_THETAMOTOR_COEF;
        theta -= DriveSystemConst.TALONFX_THETAMOTOR_COEF / 2;
        theta *= sign;
        return theta;
    }

    private double calcSpeed(double target, double current) {
        if (abs(current - target) < DriveSystemConst.THETA_DIRECTION_ERROR_ZONE) {
            return 0.0;
        } else {
            double offset = target - current;

            if (abs(offset) > DriveSystemConst.TALONFX_THETAMOTOR_COEF / 2) {
                if (offset > 0) {
                    offset = offset - DriveSystemConst.TALONFX_THETAMOTOR_COEF;
                } else {
                    offset = offset + DriveSystemConst.TALONFX_THETAMOTOR_COEF;
                }
            }

            return min(offset * DriveSystemConst.THETA_DIRECTION_CORRECTION_COEF, DriveSystemConst.THETA_DIRECTION_CORRECTION_MAX);
        }
    }

    private void updateTheta(double theta) {
        SmartDashboard.putNumber("DriveSystem: theta", theta);
        SmartDashboard.putNumber("DriveSystem: thetaMotorFR.position", thetaMotorFR.getPosition().getValue());

        for (var motor: thetaMotors) {
            motor.set(calcSpeed(theta, roundTheta(motor.getPosition().getValue())));
        }
    }

    public void setZero() {
        for (var motor: thetaMotors) {
            motor.setPosition(0.0);
        }
    }

    public void setBrakeMode(boolean brake) {
        for (var motor: thetaMotors) {
            motor.setNeutralMode(brake ? NeutralModeValue.Brake : NeutralModeValue.Coast);
        }
    }

    public void drive(double power, double theta, double offset) {
        updateTheta(theta * DriveSystemConst.TALONFX_THETAMOTOR_COEF / 2);

        if (power == 0.0 && offset != 0.0) {
            power = offset;
            offset = offset > 0 ? 1.0 : -1.0;
        }

        var leftPower = power * min(2 * offset + 1, 1.0);
        var rightPower = power * min(-2 * offset + 1, 1.0);

        SmartDashboard.putNumber("leftPower", leftPower);
        SmartDashboard.putNumber("rightPower", rightPower);

        powerMotorFL.set(leftPower);
        powerMotorRL.set(leftPower);
        powerMotorFR.set(rightPower);
        powerMotorRR.set(rightPower);
    }
}
