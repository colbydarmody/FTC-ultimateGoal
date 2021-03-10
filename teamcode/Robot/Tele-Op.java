package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
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

        waitForStart();
        runtime.reset();
        robot.resetAngle();
        robot.reset();
        while (opModeIsActive()) {


            //////////////////////////// DRIVER 1 ------------------ DRIVER 1 //////////////////////


            if (gamepad1.dpad_up) {
                robot.forward(.25);
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
                robot.fl.setPower(-gamepad1.left_stick_x / 2 + gamepad1.left_stick_y / 2 - gamepad1.right_stick_x / 2);
                robot.fr.setPower(gamepad1.left_stick_x / 2 + gamepad1.left_stick_y / 2 + gamepad1.right_stick_x / 2);
                robot.bl.setPower(gamepad1.left_stick_x / 2 + gamepad1.left_stick_y / 2 - gamepad1.right_stick_x / 2);
                robot.br.setPower(-gamepad1.left_stick_x / 2 + gamepad1.left_stick_y / 2 + gamepad1.right_stick_x / 2);
            } else {
                robot.fl.setPower(-gamepad1.left_stick_x / 1.33333 + gamepad1.left_stick_y / 1.33333 - gamepad1.right_stick_x / 1.33333);
                robot.fr.setPower(gamepad1.left_stick_x / 1.33333 + gamepad1.left_stick_y / 1.33333 + gamepad1.right_stick_x / 1.33333);
                robot.bl.setPower(gamepad1.left_stick_x / 1.33333 + gamepad1.left_stick_y / 1.33333 - gamepad1.right_stick_x / 1.33333);
                robot.br.setPower(-gamepad1.left_stick_x / 1.33333 + gamepad1.left_stick_y / 1.33333 + gamepad1.right_stick_x / 1.33333);
            }

//            telemetry.addData("Shooter Position:", robot.shooter.getCurrentPosition());
     //       telemetry.addData("Shooter Velocity:", robot.shooter.getVelocity());

            telemetry.addData("Right Encoder Position:", robot.fr.getCurrentPosition());
            telemetry.addData("Left Encoder Position:", robot.fl.getCurrentPosition());
            telemetry.addData("Horizontal Encoder Position:", robot.br.getCurrentPosition());


           // telemetry.update();


            //////////////////////////// DRIVER 2 ------------------ DRIVER 2 /////////////////////


            /////// Shooter /////////////////
            if (gamepad2.right_trigger > 0.05) {
                robot.shooter.setVelocity(500);
            } else {
                robot.shooter.setVelocity(0);
            }

            telemetry.addData("Shooter Velocity:", robot.shooter.getVelocity());
            telemetry.update();

            //////////////Flick//////////////////////
            if (gamepad2.left_trigger > 0.05) {
                robot.flick.setPosition(0);
            } else {
                robot.flick.setPosition(1);
            }

            //Intake//////////////////////////////
            if (gamepad2.right_bumper) {
                if (intakeIsTrue) {
                    robot.intake.setPower(1);
                    intakeIsTrue = false;
                } else {
                    robot.intake.setPower(-1);
                    intakeIsTrue = true;
                }
                sleep(200);
            }


            /// Elevator/////////////////



            //   || robot.elevator.getCurrentPosition() == 760

            if (gamepad2.left_stick_y > 0.5) {
                while (robot.elevator.getCurrentPosition() < 755){
                robot.elevator.setPower(gamepad2.left_stick_y);
                }
                robot.elevator.setPower(0.2);
            }

            if (gamepad2.left_stick_y < -0.5) {
                while (robot.elevator.getCurrentPosition() > 50){
                    robot.elevator.setPower(gamepad2.left_stick_y);
                }
                robot.elevator.setPower(0);
            }

//            if (gamepad2.left_stick_y > 0.1){
//                robot.elevator.setPower(0.5);
//            } if (-gamepad2.left_stick_y > -0.1){
//                robot.elevator.setPower(-0.5);
//            } else {
//                robot.elevator.setPower(0);
//            }
            //robot.elevator.setPower(gamepad2.left_stick_y);


//            if (gamepad2.left_bumper) {
//                if (elevatorIsTrue) {
//                    robot.elevator.setPower(1);
//                    elevatorIsTrue = false;
//                } else {
//                    robot.elevator.setPower(-1);
//                    elevatorIsTrue = true;
//                }
//
//            }

            telemetry.addData("Elevator Encoder Position:", robot.elevator.getCurrentPosition());


            /////////////////// Shoulder //////////////////////////
            if (gamepad2.dpad_right) {
                robot.shoulder.setPosition(0.5);
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

            /////////////////////////////// Grab //////////////////////////
            if (gamepad2.a) {
                if (clampIsTrue) {
                    robot.hand.setPosition(-1);
                    clampIsTrue = false;
                } else {
                    robot.hand.setPosition(1);
                    clampIsTrue = true;
                }

            }

            ////////////////////// Wrist ////////////////////
            if (gamepad2.b) {
                if (wristMovement) {
                    robot.wrist.setPosition(0.2);
                    wristMovement = false;
                } else {
                    robot.wrist.setPosition(0.8);
                    wristMovement = true;
                }

            }


        }
    }
}


