package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants.ControllerConst;

import static frc.robot.Utils.sign;
import static java.lang.Math.*;

public class Controller {
    private final Joystick m_Joystick = new Joystick(ControllerConst.DRIVE_JOYSTICK_PORT); 
    //this is the Joystick varablie that is holding a constant Joystick port


    public Controller() {
        m_Joystick.setXChannel(ControllerConst.X_CHANNEL); //this sets the Joysticks three dimensions to a constant the x axis
        m_Joystick.setYChannel(ControllerConst.Y_CHANNEL); //this sets the Joysticks three dimensions to a constant the y axis
        m_Joystick.setZChannel(ControllerConst.Z_CHANNEL); //this sets the Joysticks three dimensions to a constant the z axis

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

    private double smooth(double startPoint, double currentPoint) {  //This takes the slope and checks it and takes the value of 1 - the starting point of the slope 
        double slope = 1 / (1 - startPoint);

        return currentPoint * slope + -startPoint * slope;
    }

    public double getV() {
        if (abs(m_Joystick.getY()) > ControllerConst.Y_DEAD_ZONE) //this checks the currnet value of the y axis and checks to see if its greater than the dead zone on the controller
            return smooth(ControllerConst.Y_DEAD_ZONE, abs(m_Joystick.getY())) * sign(m_Joystick.getY()) * -1; //this returns the constant dead zone and the abs value of the Y value multi. by the sign of the joystick.Y * -1
        return 0.0; // if nothing is true return 0.0
    }

    public double getH() {
        if (abs(m_Joystick.getX()) > ControllerConst.X_DEAD_ZONE) //this checks the currnet value of the x axis and checks to see if its greater than the dead zone on the controller
            return smooth(ControllerConst.X_DEAD_ZONE, abs(m_Joystick.getX())) * sign(m_Joystick.getX()); //this returns the constant dead zone and the abs value of the X value multi. by sign of the joystick.X
        return 0.0;
    }

    public double getZ() {
        if (abs(m_Joystick.getZ()) > ControllerConst.Z_DEAD_ZONE) ////this checks the currnet value of the z axis and checks to see if its greater than the dead zone on the controller
            return smooth(ControllerConst.Z_DEAD_ZONE, abs(m_Joystick.getZ())) * sign(m_Joystick.getZ()); //this returns the constant dead zone and the abs value of the Z value multi. by sign of the joystick.Z
        return 0.0;
    }

    private double lastTrueTheta = 0.0;
    public double getTrueTheta() {
        if (getH() > 0.0) {
            lastTrueTheta = atan(getV() / getH()) - PI / 2; //if h>0, it gets the arc tan of the y value and div. by the x value sub. by PIE div. by 2
            return lastTrueTheta;
        }

        if (getH() < 0.0) {
            lastTrueTheta = PI + atan(getV() / getH()) - PI / 2; //if h<0 it adds PIE and gets the arc tan of the x value and div. by the x value sub. by PIE div. by 2
            return lastTrueTheta;
        }

        if (getV() == 0.0) //this checks the value of Y and sees if its equal to zero than it returns itself 
            return lastTrueTheta; //should this be also H, as right now there's no case for h==0??

        lastTrueTheta = (getV() >= 0 ? PI / 2 : 3 * PI / 2) - PI / 2; //this checks the returned value and checks if the Y value is greater than or equal to the value of 0 
        //it returns PIE/2 or if not it returns 3 * PIE/2 - PI/2
        return lastTrueTheta;
    }

//    private double theta = 0.0;
    public double getTheta() { return getTrueTheta(); }

    public double getRadius() {
        return sqrt(pow(getV(), 2) + pow(getH(), 2)); //this gets the square root y and then powers the y value twices and adds the value of X power twices
        // I assume that this controls the power levels based on distance from zero on joycon - bucky
    }

    public boolean getButtonB() {
        return m_Joystick.getRawButtonReleased(2); //returns the button relased value
    }

    public boolean getButtonA() {
        return m_Joystick.getRawButtonReleased(1); //this returns gets the relased value of the button when its pressed
    }
}
