package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

@TeleOp
public class DistanceSensorTuningTest extends LinearOpMode {

    AnalogInput rightSensor, leftSensor, frontSensor;
    public static String name = "rightDistanceSensor";

    private Pose2d poseEstimate = new Pose2d();
    public static Vector2d RIGHT_SENSOR_POS = new Vector2d(-1.88976, -8);
    public static Vector2d FRONT_SENSOR_POS = new Vector2d(6.5,-3.5);
    public static Vector2d LEFT_SENSOR_POS = new Vector2d(24./25.4, 8);

    public void runOpMode() throws InterruptedException {
        rightSensor = hardwareMap.get(AnalogInput.class, "rightDistanceSensor");
        leftSensor = hardwareMap.get(AnalogInput.class, "leftDistanceSensor");
        frontSensor = hardwareMap.get(AnalogInput.class, "frontDistanceSensor");



        waitForStart();
        while(opModeIsActive()) {
            double inchesRight = rightSensor.getVoltage() / (3.3 / 1024.);
            double inchesForward = frontSensor.getVoltage() / (3.3 / 1024.);
            double inchesLeft = leftSensor.getVoltage() / (3.3 / 1024.);
            telemetry.addData("Right sensor", inchesRight);
            telemetry.addData("Front sensor", inchesForward);
            telemetry.addData("Left sensor", inchesLeft);
            telemetry.update();
        }
    }
}
