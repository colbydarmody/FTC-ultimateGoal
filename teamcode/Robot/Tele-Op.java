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
        boolean flickIsTrue = true;

//        robot.hand.setPosition(0);
//        robot.wrist.setPosition(0.3);
//        robot.elbow.setPosition(0.9);
//        robot.shoulder.setPosition(0.65);

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


            // telemetry.update();


            //////////////////////////// DRIVER 2 ------------------ DRIVER 2 /////////////////////


            /////// Shooter ////////////////////
            if (gamepad2.right_trigger > 0.05) {
                robot.shooter.setVelocity(1650);//// was 1850
            } else {
                robot.shooter.setVelocity(0);
            }



            //////////////Flick//////////////////////
            if (gamepad2.right_bumper) {
                robot.flick.setPosition(0.38);//in
                sleep(200);
                robot.flick.setPosition(0);//out
            }


//            if (gamepad2.y) {
//                robot.flick.setPosition(0);//in
//
//            }


              ///////////////////////////Intake//////////////////////////////

//            if (gamepad2.right_bumper) {
//                if (intakeIsTrue) {
//                    robot.intake.setPower(1);
//                    intakeIsTrue = false;
//                } else {
//                    robot.intake.setPower(-1);
//                    intakeIsTrue = true;
//                }
//                sleep(200);
//            }

//

            ////off and up
            if (robot.elevator.getCurrentPosition() > 500) {
                robot.intake.setPower(0);
            }

            //// outake and mid
            if (robot.elevator.getCurrentPosition() > 200 && robot.elevator.getCurrentPosition() < 500) {
                robot.intake.setPower(1);
            }

            //intake and down
            if (robot.elevator.getCurrentPosition() < 200){
                robot.intake.setPower(-1);
            }




            //////////////// Elevator/////////////////


//            if (gamepad2.left_stick_y > Math.abs(0.2)) {
//                    robot.elevator.setPower(gamepad2.left_stick_y);
//            }

            //   || robot.elevator.getCurrentPosition() == 760
            if (gamepad2.left_stick_y > Math.abs(0.2)) {
                while (robot.elevator.getCurrentPosition() < 755) {
                    robot.elevator.setPower(gamepad2.left_stick_y);
                }
                robot.elevator.setPower(0.35);
            }

            if (gamepad2.left_stick_y < Math.abs(0.2)) {
                while (robot.elevator.getCurrentPosition() > 70) {
                    robot.elevator.setPower(gamepad2.left_stick_y);
                }
                robot.elevator.setPower(0.35);
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

            telemetry.addData("Shooter Velocity:", robot.shooter.getVelocity());
            telemetry.addData("Elevator Encoder Position:", robot.elevator.getCurrentPosition());
            telemetry.addData("Right Encoder Position:", robot.fr.getCurrentPosition());
            telemetry.addData("Left Encoder Position:", robot.fl.getCurrentPosition());
            telemetry.addData("Horizontal Encoder Position:", robot.br.getCurrentPosition());
            telemetry.update();


        }
    }
}


