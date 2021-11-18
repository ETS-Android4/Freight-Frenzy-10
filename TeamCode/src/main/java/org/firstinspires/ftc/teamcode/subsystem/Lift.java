package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift implements Subsystem {

    DcMotorEx upperMotor, lowerMotor;
    Servo leftLinkage, rightLinkage, barrel;

    //constants
    private static double UP_WINCH_RADIUS = 3.5;
    private static double DOWN_WINCH_RADIUIS = 1.75;

    private static double MOTOR_GEAR_RATIO =  1 + 46./11.;
    private static double MOTOR_BASE_RPM = 6000;
    private static double TICKS_PER_REVOLUTION = 28;


    @Override
    public void init(HardwareMap hardwareMap) {

    }

    @Override
    public void initNoReset(HardwareMap hardwareMap) {

    }

    @Override
    public void update() {

    }

    @Override
    public Object subsystemState() {
        return null;
    }
}
