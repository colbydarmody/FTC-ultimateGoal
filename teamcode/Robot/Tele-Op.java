package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.Locale;
//import android.graphics.Color;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop", group = "Tele")

public class Tele_Op extends LinearOpMode {
    Hardware robot = new Hardware();
    private ElapsedTime runtime = new ElapsedTime();

    public void wobbleDrop(){
        robot.elbow.setPosition(0.7);
        sleep(500);
        robot.wrist.setPosition(0.65);
        sleep(500);
        robot.elbow.setPosition(0.2);
        sleep(1500);
    }

    public void wobbleLift(){
        robot.elbow.setPosition(0.7);
        robot.shoulder.setPosition(0.66);
        robot.elbow.setPosition(0.2);
        sleep(1250);
    }

    @Override
    public void runOpMode() {


        robot.init(hardwareMap);
        // robot.reset();
        sleep(500);

        int superPowerReduction = 1;
        int powerReduction = 1;
        double level = -1; /// was 0.66
        double twist = 0.47;
        double manual = 0;

        boolean isUp = false;

        boolean spinIsTrueForward = true;
        boolean spinIsTrueBackward = true;
        boolean clampIsTrue = true;
        boolean revIsTrue = true;
        boolean wristMovement = true;
        boolean elbowMovement = true;
        boolean intakeIsTrue = true;
        boolean elevatorIsTrue = true;
        boolean flickIsTrue = true;

//        robot.hand.setPosition(0);
//        robot.wrist.setPosition(0.3);
//        robot.elbow.setPosition(0.9);
//        robot.shoulder.setPosition(0.65);

        waitForStart();
        runtime.reset();
        robot.resetAngle();

        while (opModeIsActive()) {


            /**
             * DRIVER 1 ------------------ DRIVER 1
             */


//            if (gamepad1.a){
//                robot.lightsaber.setPosition(0.65);
//            } else{
//                robot.lightsaber.setPosition(0);
//            }

            ////////Wobble Controls/////
            if (gamepad1.a){
                wobbleLift();
            }

//            if (gamepad1.y){
//                wobbleDrop();
//            }

            //////Hand Controls/////
            if(gamepad1.x){
                if (clampIsTrue) {
                    robot.hand.setPosition(-1);
                    clampIsTrue = false;
                } else {
                    robot.hand.setPosition(1);
                    clampIsTrue = true;
                }
            }


            if(gamepad1.y){
                if (intakeIsTrue) {
                    robot.intake.setPower(-1);
                    intakeIsTrue = false;
                } else {
                    robot.intake.setPower(0);
                    intakeIsTrue = true;
                }
                sleep(200);
            }

            if (gamepad1.dpad_up) {
                robot.forward(.25); // Need to increase due to the immense weight of the robot
            } else if (gamepad1.dpad_down) {
                robot.backward(.25);
            } else if (gamepad1.dpad_left) {
                robot.left(.25);
            } else if (gamepad1.dpad_right) {
                robot.right(.25);
            } else if (gamepad1.left_bumper) {
                robot.spinRight(0.25);
            } else if (gamepad1.right_bumper) {
                robot.spinLeft(0.25);
            } else if (gamepad1.y) {
                robot.fl.setPower(-gamepad1.left_stick_x / 2 - gamepad1.left_stick_y / 2 + gamepad1.right_stick_x / 2);
                robot.fr.setPower(gamepad1.left_stick_x / 2 - gamepad1.left_stick_y / 2 - gamepad1.right_stick_x / 2);
                robot.bl.setPower(gamepad1.left_stick_x / 2 - gamepad1.left_stick_y / 2 + gamepad1.right_stick_x / 2);
                robot.br.setPower(-gamepad1.left_stick_x / 2 - gamepad1.left_stick_y / 2 - gamepad1.right_stick_x / 2);
            } else {
                robot.fl.setPower(-gamepad1.left_stick_x / 1.33333 - gamepad1.left_stick_y / 1.33333 + gamepad1.right_stick_x / 1.33333);
                robot.fr.setPower(gamepad1.left_stick_x / 1.33333 - gamepad1.left_stick_y / 1.33333 - gamepad1.right_stick_x / 1.33333);
                robot.bl.setPower(gamepad1.left_stick_x / 1.33333 - gamepad1.left_stick_y / 1.33333 + gamepad1.right_stick_x / 1.33333);
                robot.br.setPower(-gamepad1.left_stick_x / 1.33333 - gamepad1.left_stick_y / 1.33333 - gamepad1.right_stick_x / 1.33333);
            }

//            telemetry.addData("Shooter Position:", robot.shooter.getCurrentPosition());
            //       telemetry.addData("Shooter Velocity:", robot.shooter.getVelocity());


            // telemetry.update();


            /**
             DRIVER 2 ------------------ DRIVER 2
             */


            /////// Shooter ////////////////////
            if (gamepad2.right_trigger > 0.05) {
                robot.shooter.setVelocity(1650);//// was 1650
            } else {
                robot.shooter.setVelocity(0);
            }


            //////////////Flick//////////////////////
            if (gamepad2.right_bumper) {
                robot.flick.setPosition(0.33);//in
                sleep(200);
                robot.flick.setPosition(0);//out
            }




            ///////////////////////////Intake//////////////////////////////
//
//            if (gamepad1.right_bumper) {
//                if (intakeIsTrue) {
//                    robot.intake.setPower(-1);
//                    intakeIsTrue = false;
//                } else {
//                    robot.intake.setPower(1);
//                    intakeIsTrue = true;
//                }
//                sleep(200);
//            }



            ////off and up
//            if (robot.elevator.getCurrentPosition() < -900) {
//                robot.intake.setPower(0);
//            }
//
//            //// outake and mid
//            if (robot.elevator.getCurrentPosition() < -200 && robot.elevator.getCurrentPosition() > -900) {
//                robot.intake.setPower(1);
//            }
//
//            //intake and down
//            if (robot.elevator.getCurrentPosition() > -200) {
//                robot.intake.setPower(-1);
//            }
//
//            if (gamepad2.left_bumper) {
//                robot.wobbleLaunch();
//            }

            ////////////// Elevator/////////////////
            if (gamepad2.left_stick_y > 0.2) {
                if (robot.elevator.getCurrentPosition() > -1175) {
                    robot.elevator.setPower(gamepad2.left_stick_y);
                }
                if(robot.elevator.getCurrentPosition() < -1150) {
                    robot.lightsaber.setPosition(0.65);
                    robot.elevator.setPower(0);
                }
            }

            if (gamepad2.left_stick_y < -0.2) {
                if (robot.elevator.getCurrentPosition() < 0) {
                    robot.elevator.setPower(gamepad2.left_stick_y);
                }
                if(robot.elevator.getCurrentPosition() > -1140){
                    robot.lightsaber.setPosition(0);
                }
            }
            else {
                robot.elevator.setPower(gamepad2.left_stick_y/3);
            }

            /////////////////// Shoulder //////////////////////////
            if (gamepad2.dpad_right) {

                robot.shoulder.setPosition(0.66);
            }

            if (gamepad2.dpad_left) {
                robot.shoulder.setPosition(0);
            }


            //////////////// ELbow //////////////////////
            if (gamepad2.dpad_up) {
                robot.elbow.setPosition(0);
            }

            if (gamepad2.dpad_down) {
                robot.elbow.setPosition(1);
            }




            ///////////////////////////////// get ready to grab ////////////////////////////
            if (gamepad2.a) {
                robot.shoulder.setPosition(0);
                robot.elbow.setPosition(0.2);
                robot.wrist.setPosition(0.65);
                robot.hand.setPosition(1);

                }

            /////////////////////////////// Grab   or open/close //////////////////////////
            if (gamepad2.b) {
                if (clampIsTrue) {
                    robot.hand.setPosition(-1);
                    clampIsTrue = false;
                } else {
                    robot.hand.setPosition(1);
                    clampIsTrue = true;
                }
                    sleep(200);  ///  maybe
            }

            ///////////////////////////////// store after pickign up ////////////////////////////  needs work
            if (gamepad2.y) {

                robot.hand.setPosition(-1);
                robot.wrist.setPosition(0.5);// was 0.5
                robot.elbow.setPosition(0.93);/// was 0.9
                robot.shoulder.setPosition(0);

            }

            ///////////////////////////////// get arm over wall //////////////////////////// needs work
            if (gamepad2.x) {
                robot.shoulder.setPosition(0.66);
                robot.elbow.setPosition(0.4);
                robot.wrist.setPosition(0.65);
                robot.hand.setPosition(-1);

            }





            telemetry.addData("Shooter Velocity:", robot.shooter.getVelocity());
            telemetry.addData("Elevator Encoder Position:", robot.elevator.getCurrentPosition());
            telemetry.addData("Right Encoder Position:", robot.fr.getCurrentPosition());
            telemetry.addData("Left Encoder Position:", robot.fl.getCurrentPosition());
            telemetry.addData("Horizontal Encoder Position:", robot.br.getCurrentPosition());
            telemetry.update();


        }
    }
}
