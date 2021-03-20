package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.List;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Autonomous(name = "Log Tests", group = "Auto")
public class Telemetry_Testing extends LinearOpMode{


    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            "AYQ6ymr/////AAABmV6TulQWSE0qt3HxI7xxzjYZLt7oCOm3yO8U6uCrz/ydu705scek/RWHbSJpjJfqiTSVjL/iOtYUg7jvOcugHNMpQ/9F+0SkxFTmYxe7ts2zfCSRVa2g8N4ttFYIOyFZjfNvUJdfSVGKZy4b/TfsWq17xb5w3B+NOENkayAgTHvl6B68fuXRHocR62EZ42hFtM2fxKW3J1/9+6FxXHYA3HY/4nB1PM1NVk+e/N4Oub3u3prWcnMJFhgp4GNhPgB0AtzPHo1p3jIBy0wSHkw7Gep0Rq+i3j/9fOtJATbkCjjcHC7Jz+RkMyOBG4pSqsZi2F0FHrbekJS2igtWHp+R26m2//7Y5v0DeWtGm7pbp3y0";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;


    /* Declare OpMode members. */
    Hardware robot = new Hardware();

    private final ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        telemetry.addData("Right Encoder Position:", robot.fr.getCurrentPosition());
        telemetry.addData("Left Encoder Position:", robot.fl.getCurrentPosition());
        telemetry.addData("Horizontal Encoder Position:", robot.br.getCurrentPosition());
        telemetry.addData("Elevator Position", robot.elevator.getCurrentPosition());
        telemetry.update();

        RobotLog.d("SomeUsefulPrefixHere:Some Useful Message Here");
         telemetry.log();

        Telemetry.Log.DisplayOrder NEWEST_FIRST;
        //Also can use
        Telemetry.Log.DisplayOrder OLDEST_FIRST;

        telemetry.log();

        telemetry.addLine("Line Test");

        //Integers for numbers
        int ElevatorZero = 0;
        int ElevatorMid = 377;
        int ElevatorMax = 755;

        //String for texts
        String ElevatorZeroT = "Elevator is at Zero";
        String ElevatorMidT = "Elevator is at Mid";
        String ElevatorMaxT = "Elevator is at Max";

        // for boolean
        boolean ElevatorZeroB = true;
        boolean ElevatorMidB = true;
        boolean ElevatorMaxB = true;

        if(ElevatorZeroB = true){
            if(ElevatorZero == 0){
                telemetry.log();
            }
        }
//        if (robot.elevator.getCurrentPosition = 755){
//
//
//        }


    }
}
