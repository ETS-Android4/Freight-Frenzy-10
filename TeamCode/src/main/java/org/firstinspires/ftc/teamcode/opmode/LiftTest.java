package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Lift;

@Config
@TeleOp
public class LiftTest extends LinearOpMode {

    public static double LIFT_TARGET_HEIGHT = 0;
    public void runOpMode() throws InterruptedException {
        Lift lift = new Lift(hardwareMap);
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {

            lift.retractBarrel();
            lift.setPower(-gamepad1.left_stick_y);
//            lift.setTargetHeight (LIFT_TARGET_HEIGHT);
//            lift.update();
            if(gamepad1.a){
                lift.extendHorizontal();
            }
            if(gamepad1.b){
                lift.retractHorizontal();
            }
            telemetry.addData("gamepad",-gamepad1.left_stick_y);
            telemetry.addData("Lift height", lift.getHeight());
            telemetry.addData("Lift ticks position", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}
