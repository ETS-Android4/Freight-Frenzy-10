package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

@Config
public class Carousel {

    public static double SPEED = 1.0;

    CRServo right, left;

    public Carousel(HardwareMap hardwareMap) {
        right = hardwareMap.get(CRServo.class, "rightCarousel");
        left = hardwareMap.get(CRServo.class, "leftCarousel");
    }

    public void update() {
        return;
    }

    public void setPower(double power) {
        right.setPower(power);
        left.setPower(-power);
    }

    public void scoreDuck() {
        setPower(SPEED);
    }

}
