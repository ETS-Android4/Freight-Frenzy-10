package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift implements Subsystem {

    DcMotorEx upperMotor, lowerMotor;
    Servo leftLinkage, rightLinkage, barrel;

    //constants in inches and RPM, everything else unitless
    private static double UP_WINCH_RADIUS = 1.75;
    private static double DOWN_WINCH_RADIUIS = 1.75/2.;

    private static double MOTOR_GEAR_RATIO =  1 + 46./11.;
    private static double MOTOR_BASE_RPM = 6000;
    private static double TICKS_PER_REVOLUTION = 28;

    public static double kF;


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

    public static double inchesToEncoderTicks(double inches) {
        return inches / ((UP_WINCH_RADIUS) * Math.PI) * (TICKS_PER_REVOLUTION * MOTOR_GEAR_RATIO);
    }

    public static void main(String[] args) {
        System.out.println("Test case, 18 inches");
        System.out.println(inchesToEncoderTicks(18));
    }
}
