package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants.ControllerConst;

import static frc.robot.Utils.sign;
import static java.lang.Math.*;

public class Controller {
    private final Joystick m_Joystick = new Joystick(ControllerConst.DRIVE_JOYSTICK_PORT);


    public Controller() {
        m_Joystick.setXChannel(ControllerConst.X_CHANNEL);
        m_Joystick.setYChannel(ControllerConst.Y_CHANNEL);
        m_Joystick.setZChannel(ControllerConst.Z_CHANNEL);

//        new Thread(() -> {
//            while (true) {
//                double offset = getTrueTheta() - theta;
//                if (abs(offset) < ControllerConst.THETA_TURN_PER_FRAME) {
//                    theta += offset;
//                } else if (offset < 0) {
//                    theta -= ControllerConst.THETA_TURN_PER_FRAME;
//                } else {
//                    theta += ControllerConst.THETA_TURN_PER_FRAME;
//                }
//                // to make sure theta is in range [-PI, PI]
//                theta = (theta + 3*PI) % (2*PI) - PI;
//                try {
//                    Thread.sleep(ControllerConst.THETA_UPDATE_TIME);
//                } catch (InterruptedException ignored) { }
//            }
//        }).start();
    }

    private double smooth(double startPoint, double currentPoint) {
        double slope = 1 / (1 - startPoint);

        return currentPoint * slope + -startPoint * slope;
    }

    public double getV() {
        if (abs(m_Joystick.getY()) > ControllerConst.Y_DEAD_ZONE)
            return smooth(ControllerConst.Y_DEAD_ZONE, abs(m_Joystick.getY())) * sign(m_Joystick.getY()) * -1;
        return 0.0;
    }

    public double getH() {
        if (abs(m_Joystick.getX()) > ControllerConst.X_DEAD_ZONE)
            return smooth(ControllerConst.X_DEAD_ZONE, abs(m_Joystick.getX())) * sign(m_Joystick.getX());
        return 0.0;
    }

    public double getZ() {
        if (abs(m_Joystick.getZ()) > ControllerConst.Z_DEAD_ZONE)
            return smooth(ControllerConst.Z_DEAD_ZONE, abs(m_Joystick.getZ())) * sign(m_Joystick.getZ());
        return 0.0;
    }

    private double lastTrueTheta = 0.0;
    public double getTrueTheta() {
        if (getH() > 0.0) {
            lastTrueTheta = atan(getV() / getH()) - PI / 2;
            return lastTrueTheta;
        }

        if (getH() < 0.0) {
            lastTrueTheta = PI + atan(getV() / getH()) - PI / 2;
            return lastTrueTheta;
        }

        if (getV() == 0.0)
            return lastTrueTheta;

        lastTrueTheta = (getV() >= 0 ? PI / 2 : 3 * PI / 2) - PI / 2;
        return lastTrueTheta;
    }

//    private double theta = 0.0;
    public double getTheta() { return getTrueTheta(); }

    public double getRadius() {
        return sqrt(pow(getV(), 2) + pow(getH(), 2));
    }

    public boolean getButtonB() {
        return m_Joystick.getRawButtonReleased(2);
    }

    public boolean getButtonA() {
        return m_Joystick.getRawButtonReleased(1);
    }
}
