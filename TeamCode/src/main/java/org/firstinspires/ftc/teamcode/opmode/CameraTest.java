package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.BarcodeDetector;
import org.firstinspires.ftc.teamcode.subsystem.RingDetector;

@TeleOp
public class CameraTest extends LinearOpMode {

    RingDetector detector;

    public void runOpMode() throws InterruptedException {
        detector = new RingDetector(hardwareMap, "Webcam 1");
        detector.init();
        while(!isStarted() && !isStopRequested()){
            telemetry.addData("Rings",detector.getX());
            telemetry.update();
        }
        detector.close();


    }
}
