package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.Locale;
import java.util.ArrayList;
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
        
        int n = -1;
        java.util.ArrayList <String>elevatorPos = new java.util.ArrayList<String>(); //array for elevator encoder values
        java.util.ArrayList <String>elevatorSpeed = new java.util.ArrayList<String>(); //array for elevator
        java.util.ArrayList <String>elevatorAccel = new java.util.ArrayList<String>();
        //all arrays are completely empty at start
        elevatorSpeed.add("0"); //element 0 of elevatorVelocity will not be used
        elevatorAccel.add("0"); //element 0 of elevatorAccel will not be used
        elevatorAccel.add("0"); //element 1 of elevatorAccel will not be used
        
        waitForStart();
        runtime.reset();
        robot.resetAngle();

        while (opModeIsActive()) {
            
            ////////////// Elevator/////////////////
            if (gamepad2.left_stick_y > 0.2) {
                robot.elevator.setPower(gamepad2.left_stick_y);
                elevatorPos.add(String.valueOf(robot.elevator.getCurrentPosition)); //add encoder value to the array
                n++; //updating element # so we can reference it later
                if (n > 0) { //we need at least two encoder values to calculate velocity, so this ensures that we have that
                    elevatorSpeed.add(String.valueOf((elevatorPos.get(n)-elevatorPos.get(n-1))/(/*time*/)));
                    if (n > 1) {
                        elevatorAccel.add(String.valueOf((elevatorSpeed.get(n)-elevatorSpeed.get(n-1))/(/*time*/))))
                    }
                }
                //if (robot.elevator.getCurrentPosition() > -1175) {
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
