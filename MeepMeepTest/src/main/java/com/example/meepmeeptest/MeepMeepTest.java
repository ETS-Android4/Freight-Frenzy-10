package com.example.meepmeeptest;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.ArrayList;

public class MeepMeepTest {
    public static void main(String[] args) {
        Vector2d BLUE_SHIPPING_HUB = new Vector2d(-12,24);

        Pose2d START_POSE = new Pose2d(5.0, 64.0, Math.PI/2);

        Pose2d WAREHOUSE_TRANSITION = new Pose2d(36, 56, 0);

        double SCORE_ANGLE = Math.toRadians(-120);
        double SCORE_DISTANCE = 20;



        ArrayList<Pose2d> WAREHOUSE_POSES = new ArrayList<>();
        ArrayList<Pose2d> SCORE_ANGLES = new ArrayList<>();

        WAREHOUSE_POSES.add(new Pose2d(48, 56, 0));
        WAREHOUSE_POSES.add(new Pose2d(46,53, Math.toRadians(-10)));

        Pose2d SCORE_POSE = new Pose2d(
                BLUE_SHIPPING_HUB.getX() - SCORE_DISTANCE * Math.cos(SCORE_ANGLE),
                BLUE_SHIPPING_HUB.getY() - SCORE_DISTANCE * Math.sin(SCORE_ANGLE),
                SCORE_ANGLE
        );




        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(START_POSE)
                                .setReversed(true)
                                .splineTo(SCORE_POSE.vec(), SCORE_POSE.getHeading())
                                .setReversed(false)
                                .splineTo(WAREHOUSE_TRANSITION.vec(), WAREHOUSE_TRANSITION.getHeading())
                                .splineTo(WAREHOUSE_POSES.get(0).vec(), WAREHOUSE_POSES.get(0).getHeading())
                                .setReversed(true)
                                .splineTo(WAREHOUSE_TRANSITION.vec(), WAREHOUSE_TRANSITION.getHeading())
                                .splineTo(SCORE_POSE.vec(), SCORE_POSE.getHeading())
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}