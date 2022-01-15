package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.Carousel;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Lift;

@Config
@TeleOp()
public class StateMachineTeleOp extends LinearOpMode {

    //public static config variables!
    public static double TOP_SCORE_HEIGHT = 17.5;
    public static double MID_SCORE_HEIGHT = 9;
    public static double LOW_SCORE_HEIGHT = 3;
    public static double SHARED_SCORE_HEIGHT = 3;

    public static double INTAKE_TO_TRANSFER_MS = 500;
    public static double TRANSFER_TO_OUTTAKE_MS = 1200;

    private double multi = 1;

    SampleMecanumDrive drive;
    Carousel carousel;
    Lift lift;
    Intake intake;

    GamepadEx gamepadEx1, gamepadEx2;

    ElapsedTime timer;

    public enum State {
        IDLE,
        INTAKING,
        INTAKE_UP,
        SECOND_INTAKE,
        TRANSFERRING,
        SCORING,
        RETRACT_HORIZONTAL
    }

    public enum Level {
        SHARED,
        LOW,
        MID,
        TOP
    }

    public void runOpMode() throws InterruptedException {
        State state = State.IDLE;

        timer = new ElapsedTime();

        //init code
        drive = new SampleMecanumDrive(hardwareMap);
        carousel = new Carousel(hardwareMap);
        lift = new Lift(hardwareMap);
        intake = new Intake(hardwareMap);

        //init gamepads
//        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        Level level = Level.TOP;

        lift.barrelReady();
        lift.setMaxPower(0.9);
        //wait for start
        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {

            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * multi,
                            -gamepad1.left_stick_x * multi,
                            -gamepad1.right_stick_x * multi
                    )
            );

            switch(state) {
                case IDLE:
                    if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A))
                        state = State.INTAKING;
                    lift.retractHorizontal();
                    lift.retractBarrel();
                    intake.retract();
                    break;
                case INTAKING:
                    //constantly intakes
                    intake.setPower(1);
                    intake.extend();

                    //state transition
                    if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)) {
                        timer.reset();
                        intake.retract();
                        intake.setPower(0);
                        state = State.INTAKE_UP;
                    }

                    //until current goes over 8
                    if (intake.getCurrent() > 6.5) {
                        timer.reset();
                        intake.retract();
                        intake.setPower(0);
                        state = State.INTAKE_UP;
                    }
                    break;
                case INTAKE_UP:
                    if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)) {
                        state = State.SECOND_INTAKE;
                        intake.setPower(-1);
                        lift.retractBarrel();
                        lift.retractHorizontal();
                        intake.retract();
                        timer.reset();
                    }
                    if(gamepadEx2.wasJustPressed(GamepadKeys.Button.B)){
                        state = State.INTAKING;
                    }
                    break;
                case SECOND_INTAKE:
                    if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)) {
                        state = State.TRANSFERRING;
                        timer.reset();
                    }
                    lift.retractHorizontal();
                    intake.setPower(-1);
                    intake.retract();
                    if(timer.milliseconds() > 700){
                        lift.barrelReady();
                    }
                    break;
                case TRANSFERRING:
                    if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)) {
                        state = State.SCORING;
                    }
                    intake.setPower(0);
                    switch(level){
                        case TOP:
                            lift.setTargetHeight(TOP_SCORE_HEIGHT);
                            if(timer.milliseconds() > 500)
                                lift.extendHorizontal();
                            break;
                        case MID:
                            lift.setTargetHeight(MID_SCORE_HEIGHT);
                            if(timer.milliseconds() > 500)
                                lift.extendHorizontal();
                            break;
                        case LOW:
                            lift.setTargetHeight(LOW_SCORE_HEIGHT);
                            if(timer.milliseconds() > 300)
                                lift.extendHorizontal();
                            break;
                        case SHARED:
                            lift.setTargetHeight(SHARED_SCORE_HEIGHT);
//                                lift.extendHorizontal();
                            break;
                    }
                    break;
                case SCORING:
                    if(gamepadEx2.wasJustPressed(GamepadKeys.Button.A)) {
                        state = State.RETRACT_HORIZONTAL;
                        lift.retractHorizontal();
                    }

                    lift.extendBarrel();
                    break;
                case RETRACT_HORIZONTAL:
                    if(gamepadEx2.wasJustPressed(GamepadKeys.Button.A)) {
                        state = State.IDLE;
                        lift.retractHorizontal();
                        intake.retract();
                        intake.setPower(0);
                        lift.setTargetHeight(0);
                        lift.retractHorizontal();
                        lift.retractBarrel();
                    }

            }

            if(gamepad1.left_bumper){
                carousel.scoreDuck();
            } else {
                carousel.setPower(0);
            }

            if(gamepad1.right_bumper) {
                multi = 0.4;
            } else {
                multi = 1;
            }

            if(gamepad2.dpad_up)
                level = Level.TOP;
            if(gamepad2.dpad_right)
                level = Level.MID;
            if(gamepad2.dpad_down)
                level = Level.LOW;
            if(gamepad2.dpad_left)
                level = Level.SHARED;

//            gamepadEx1.readButtons();
            gamepadEx2.readButtons();

//            drive.update();
            lift.update();

            telemetry.addData("gamepad x", -gamepad1.left_stick_x);
            telemetry.addData("gamepad y", -gamepad1.left_stick_y);
            telemetry.addData("level",level);
            telemetry.addData("Current state",state);
            telemetry.addData("Drivetrain pose",drive.getPoseEstimate());
            telemetry.update();
        }
    }
}
