package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Intake;

@TeleOp
public class IntakeTest extends LinearOpMode {

    Intake intake;

    @Override
    public void runOpMode() throws InterruptedException {

        intake = new Intake(hardwareMap);
        FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()){
            intake.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("currnet", intake.getCurrent());
            packet.put("status", "alive");
            dashboard.sendTelemetryPacket(packet);
        }

    }
}
