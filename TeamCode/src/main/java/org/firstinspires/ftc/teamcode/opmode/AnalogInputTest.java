package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

@TeleOp
@Config
public class AnalogInputTest extends LinearOpMode {
    AnalogInput sensor;
    public static String name = "rightDistanceSensor";

    public void runOpMode() throws InterruptedException {
        sensor = hardwareMap.get(AnalogInput.class, name);
        waitForStart();
        while(opModeIsActive()) {
            double inches = sensor.getVoltage() / (3.3 / 1024.);
            telemetry.addData("voltage", inches);
            telemetry.update();
        }
    }
}
