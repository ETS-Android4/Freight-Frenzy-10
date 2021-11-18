package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

public class Carousel implements Subsystem {

    public static double SPEED = 1.0;

    CRServo topRight, bottomRight, topLeft, bottomLeft;

    public Carousel() {

    }

    @Override
    public void init(HardwareMap hardwareMap) {
        topRight = hardwareMap.get(CRServo.class, "trCarousel");
        bottomRight = hardwareMap.get(CRServo.class, "brCarousel");
        topLeft = hardwareMap.get(CRServo.class, "tlCarousel");
        bottomLeft = hardwareMap.get(CRServo.class, "blCarousel");
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
