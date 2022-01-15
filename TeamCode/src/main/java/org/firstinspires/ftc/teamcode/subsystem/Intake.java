package org.firstinspires.ftc.teamcode.subsystem;

import android.service.media.CameraPrewarmService;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class Intake  {
    private DcMotorEx motor;
    private Servo pivot;

    private boolean isExtended = false;

    public static double EXTEND_POS = 0.75;
    public static double RETRACT_POS = 0.23;

    ElapsedTime timer;

    public Intake(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, "intake");
        pivot = hardwareMap.get(Servo.class,"pivot");

        motor.setDirection(DcMotorSimple.Direction.REVERSE);

        retract();

        timer = new ElapsedTime();
    }

    public void extend() {
        pivot.setPosition(EXTEND_POS);
        isExtended = true;
    }

    public void retract() {
        pivot.setPosition(RETRACT_POS);
        isExtended = false;
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public double getCurrent() {
        return motor.getCurrent(CurrentUnit.AMPS);
    }
}
