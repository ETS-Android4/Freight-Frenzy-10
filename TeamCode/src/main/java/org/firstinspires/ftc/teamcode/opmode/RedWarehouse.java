package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystem.Carousel;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Lift;
import org.firstinspires.ftc.teamcode.subsystem.RingDetector;

@Config()
@Autonomous
public class RedWarehouse extends LinearOpMode {

    public static Pose2d START_POSE = new Pose2d(15.0, -64.0, -Math.PI/2);
    public static Pose2d WAREHOUSE_POSE = new Pose2d(52, -52,0);

    //    public static double SCORE_ANGLE = -2 * Math.PI / 3;
    public static double SCORE_ANGLE = Math.toRadians(100);
    public static double SCORE_DISTANCE = 28;

    private int position = 1;

    private ElapsedTime timer;

    private Pose2d SCORE_POSE = new Pose2d(
            AutoConstants.RED_SHIPPING_HUB.getX() - SCORE_DISTANCE * Math.cos(SCORE_ANGLE),
            AutoConstants.RED_SHIPPING_HUB.getY() - SCORE_DISTANCE * Math.sin(SCORE_ANGLE),
            SCORE_ANGLE
    );

    private Drivetrain drive;
    private Intake intake;
    private Lift lift;
    private Carousel carousel;
    private RingDetector detector;

    public void runOpMode() throws InterruptedException {
        drive = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        lift = new Lift(hardwareMap);
        carousel = new Carousel(hardwareMap);

        detector = new RingDetector(hardwareMap, "Webcam 1");
        detector.init();

        lift.barrelReady();
        lift.setMaxPower(0.8);

        drive.setPoseEstimate(START_POSE);

        Trajectory deliverTraj = drive.trajectoryBuilder(START_POSE,true)
                .splineTo(SCORE_POSE.vec(), SCORE_POSE.getHeading())
                .addTemporalMarker(0.1, ()->{
                    switch(position) {
                        case 1:
                            lift.setTargetHeight(17);
                            break;
                        case 2:
                            lift.setTargetHeight(7.5);
                            break;
                        case 3:
                            lift.setTargetHeight(1.5);
                            break;
                    }

                    lift.barrelReady();
                })
                .build();

        Trajectory parkTraj = drive.trajectoryBuilder(SCORE_POSE.plus(new Pose2d(0,0,Math.PI)))
                .splineTo(WAREHOUSE_POSE.vec(),WAREHOUSE_POSE.getHeading())
                .addTemporalMarker(0.3, ()->{
                    lift.retractHorizontal();
                    lift.retractBarrel();
                    intake.retract();
                    lift.setTargetHeight(0);
                })
                .addTemporalMarker(1.5, ()->{
                    intake.extend();
//                    intake.setPower(1);
                })
                .build();

        timer = new ElapsedTime();

        telemetry.addData("are we alive?",1);
        telemetry.update();

        while(!isStarted() && !isStopRequested()){
            if(detector.getX() < 70){
                position = 2;
            } else if(detector.getX() > 70 && detector.getX() < 1000){
                position = 1;
            } else {
                position = 3;
            }
            telemetry.addData("position",position);
            telemetry.update();
        }

        detector.close();

        drive.followTrajectoryAsync(deliverTraj);
        waitAsync();
        lift.extendHorizontal();
        waitTimeMillis(1000);
        lift.extendBarrel();
        waitTimeMillis(200);
        drive.followTrajectoryAsync(parkTraj);
        waitAsync();
    }

    public void waitAsync() {
        while(drive.isBusy() && !isStopRequested() && opModeIsActive()) {
            drive.update();
            lift.update();
        }
    }

    public void waitTimeMillis(double millis) {
        timer.reset();
        while(timer.milliseconds() < millis && !isStopRequested() && opModeIsActive()){
            drive.update();
            lift.update();
        }
    }
}