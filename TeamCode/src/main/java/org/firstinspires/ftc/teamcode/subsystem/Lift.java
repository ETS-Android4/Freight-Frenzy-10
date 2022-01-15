package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.util.InterpLUT;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@Config
public class Lift {

    DcMotorEx upperMotor, lowerMotor;
    Servo leftLinkage, rightLinkage, barrel;

    //changeable static variables for the positions of barrel and linkage
    public static double BARREL_EXTEND_POS = 0.4;
    public static double BARREL_RETRACT_POS = 0.09;
    public static double BARREL_READY_SCORE_POS = 0.09;
    public static double LEFT_LINKAGE_EXTEND_POS = 0.33;
    public static double LEFT_LINKAGE_RETRACT_POS = 0.6;
    public static double RIGHT_LINKAGE_EXTEND_POS = 0.37;
    public static double RIGHT_LINKAGE_RETRACT_POS = 0.22;

    //constants in inches and RPM, everything else unitless
    private static double UP_WINCH_RADIUS = 40./25.4;
    private static double MOTOR_GEAR_RATIO =  1 + 46./11.;
    private static double MOTOR_BASE_RPM = 5960;
    private static double TICKS_PER_REVOLUTION = 28;

    private double MAX_POWER = 1;

    public static double kP = 0.6, kI = 0, kD = 0.01;

    private PIDController pid;

    private int bottomOffset = 0;

    private double targetHeight = 0;
    private double currentHeight = 0;

    public static InterpLUT feedforward;

    public Lift(HardwareMap hardwareMap) {
        upperMotor = hardwareMap.get(DcMotorEx.class, "upperLift");
        lowerMotor = hardwareMap.get(DcMotorEx.class, "lowerLift");
        upperMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        barrel = hardwareMap.get(Servo.class, "barrel");
        leftLinkage = hardwareMap.get(Servo.class,"leftLinkage");
        rightLinkage = hardwareMap.get(Servo.class, "rightLinkage");

        bottomOffset = upperMotor.getCurrentPosition();

        feedforward = new InterpLUT();

        pid = new PIDController(kP, kI, kD);

        feedforward.add(-3,0.2);
        feedforward.add(24, 0.202);

        retractBarrel();
        retractHorizontal();
    }

    public void update() {
        currentHeight = extensionLengthToHeight(encoderTicksToInches(getCurrentPosition()));
        double power = 0;
        if(currentHeight < 3 && targetHeight < 3) {
            power = -0.3;
            bottomOffset = upperMotor.getCurrentPosition();
        } else if(currentHeight < 0.4 && targetHeight < 0.4) {
            power = 0;
        } else {
                power = 0.2;
                power += pid.calculate(currentHeight);
                setPower(power);
        }
    }

    public void extendHorizontal() {
        leftLinkage.setPosition(LEFT_LINKAGE_EXTEND_POS);
        rightLinkage.setPosition(RIGHT_LINKAGE_EXTEND_POS);
    }

    public void retractHorizontal() {
        leftLinkage.setPosition(LEFT_LINKAGE_RETRACT_POS);
        rightLinkage.setPosition(RIGHT_LINKAGE_RETRACT_POS);
    }

    public void extendBarrel() {
        barrel.setPosition(BARREL_EXTEND_POS);
    }

    public void retractBarrel() {
        barrel.setPosition(BARREL_RETRACT_POS);
    }

    public double getHeight() {
        return currentHeight = extensionLengthToHeight(encoderTicksToInches(getCurrentPosition()));
    }

    public int getCurrentPosition() {
        return upperMotor.getCurrentPosition() - bottomOffset;
    }

    public void setTargetHeight(double height) {
        if(Math.abs(targetHeight - height) < 0.5) {
            return;
        }
        targetHeight = height;
        pid.setSetPoint(targetHeight);
    }

    public void setPower(double power) {
        power = Range.clip(power, -0.3,MAX_POWER);
        upperMotor.setPower(-power);
        lowerMotor.setPower(-power);
    }

    public void setMaxPower(double max){
        MAX_POWER = max;
    }

    public static double inchesToEncoderTicks(double inches) {
        return inches / (UP_WINCH_RADIUS * Math.PI) * (TICKS_PER_REVOLUTION * MOTOR_GEAR_RATIO);
    }

    public void barrelReady() {
        barrel.setPosition(BARREL_READY_SCORE_POS);
    }

    public static double encoderTicksToInches(double ticks) {
        return ticks / (TICKS_PER_REVOLUTION * MOTOR_GEAR_RATIO) * UP_WINCH_RADIUS * Math.PI;
    }

    public static double extensionLengthToHeight(double length) {
        return length * Math.sin(Math.toRadians(70));
    }

    public static double heightToExtensionLength(double height) {
        return height / Math.sin(Math.toRadians(70));
    }
}
