package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake implements Subsystem {
    private DcMotorEx motor;
    private Servo pivot;

    @Override
    public void init(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, "intake");
        pivot = hardwareMap.get(Servo.class,"pivot");
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
