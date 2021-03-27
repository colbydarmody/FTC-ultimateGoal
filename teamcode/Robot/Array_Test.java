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

    @Override
    public void runOpMode() {


        robot.init(hardwareMap);
        // robot.reset();
        sleep(500);

        waitForStart();
        runtime.reset();
        robot.resetAngle();

        while (opModeIsActive()) {

            ////////////// Elevator/////////////////
            if (gamepad2.left_stick_y > 0.2) {
                if (robot.elevator.getCurrentPosition() > -1175) {
                    robot.elevator.setPower(gamepad2.left_stick_y);
                }

            }
            if (gamepad2.left_stick_y < -0.2) {
                if (robot.elevator.getCurrentPosition() < 0) {
                    robot.elevator.setPower(gamepad2.left_stick_y);
                }
            }
            else {
                robot.elevator.setPower(gamepad2.left_stick_y/3);
            }
          
            telemetry.addData("Elevator Encoder Position:", robot.elevator.getCurrentPosition());
            telemetry.update();


        }
    }
}
