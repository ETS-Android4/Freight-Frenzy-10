package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
@Config
public class ServoTest extends LinearOpMode {

    public static String name = "barrel";
    public static double position = 1;

    private Servo servo;

    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(Servo.class, name);
        waitForStart();
        while(!isStopRequested() && opModeIsActive()) {
            servo.setPosition(position);
        }
    }
}
