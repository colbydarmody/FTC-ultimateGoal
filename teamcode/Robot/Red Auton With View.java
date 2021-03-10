package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;


import android.graphics.Color;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

@Autonomous(name = "Red Auto Vuforia", group = "Auto")


public class Red_Auton_Vuforia extends LinearOpMode {


    public static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    public static final String LABEL_FIRST_ELEMENT = "Quad";
    public static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            "AYQ6ymr/////AAABmV6TulQWSE0qt3HxI7xxzjYZLt7oCOm3yO8U6uCrz/ydu705scek/RWHbSJpjJfqiTSVjL/iOtYUg7jvOcugHNMpQ/9F+0SkxFTmYxe7ts2zfCSRVa2g8N4ttFYIOyFZjfNvUJdfSVGKZy4b/TfsWq17xb5w3B+NOENkayAgTHvl6B68fuXRHocR62EZ42hFtM2fxKW3J1/9+6FxXHYA3HY/4nB1PM1NVk+e/N4Oub3u3prWcnMJFhgp4GNhPgB0AtzPHo1p3jIBy0wSHkw7Gep0Rq+i3j/9fOtJATbkCjjcHC7Jz+RkMyOBG4pSqsZi2F0FHrbekJS2igtWHp+R26m2//7Y5v0DeWtGm7pbp3y0";


    /* Declare OpMode members. */
    Hardware robot = new Hardware();

    private ElapsedTime runtime = new ElapsedTime();

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() throws InterruptedException {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2.5, 16.0/9.0);
        }

        robot.init(hardwareMap);
        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        telemetry.addData("Mode", "calibrating2...");
        telemetry.update();

        while (!isStopRequested() && !robot.imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }


        // Wait for the game to start (driver presses PLAY)
        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.addData("imu calib status", robot.imu.getCalibrationStatus().toString());
        telemetry.update();
        waitForStart();

        robot.resetAngle();


        robot.reset();


        telemetry.update();

        if (opModeIsActive()) {

            telemetry.addData("Right Encoder Position:", robot.fr.getCurrentPosition());
            telemetry.addData("Left Encoder Position:", robot.fl.getCurrentPosition());
            telemetry.addData("Horizontal Encoder Position:", robot.br.getCurrentPosition());
            telemetry.update();


            /** Wait for the game to begin */
            telemetry.addData(">", "Press Play to start op mode");
            telemetry.update();
            waitForStart();

            if (opModeIsActive()) {
                while (opModeIsActive()) {
                    if (tfod != null) {
                        // getUpdatedRecognitions() will return null if no new information is available since
                        // the last time that call was made.
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            // step through the list of recognitions and display boundary info.
                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());
                            }
                            telemetry.update();
                        }
                    }
                }
            }

            if (tfod != null) {
                tfod.shutdown();
            }


            robot.forward(0.5);
            sleep(1250);
            robot.stop();


//        robot.wrist.setPosition(0.2);
//        robot.shoulder.setPosition(0.33);

            robot.backward(0.5);
            sleep(250);
            robot.stop();
            sleep(50);

            // sleep(2000);


//            robot.shoulder.setPosition(0);
//            sleep(150);
//            robot.wrist.setPosition(0.8);
//            sleep(150);
//            robot.elbow.setPosition(0.3);
//            sleep(1000);
//            robot.hand.setPosition(1);
//
//            sleep(500000);


            //////////////////////////////
            //shoot   3////////////////////
            sleep(1500);
            //////////////////////////////

            robot.backward(0.5);
            sleep(250);
            robot.stop();
            sleep(50);

            //////////////////////////////
            //shoot   1////////////////////
            sleep(500);
            ////////////////////////////////

            robot.backward(0.5);
            sleep(750);
            robot.stop();
            sleep(50);

            ////////////////////////////////
            //shoot   3////////////////////
            sleep(1500);
            //////////////////////////////

            robot.backward(0.5);
            sleep(500);
            robot.stop();
            sleep(50);

            robot.left(0.3);
            sleep(500);
            robot.stop();
            sleep(50);

            //shoot  powershot 1////////////////////
            sleep(500);


            robot.left(0.5);
            sleep(350);
            robot.stop();
            sleep(50);

            //shoot  powershot 2////////////////////
            sleep(500);


            robot.left(0.5);
            sleep(350);
            robot.stop();
            sleep(50);

            //shoot  powershot 3////////////////////
            sleep(500);

            robot.right(0.5);
            sleep(3450);
            robot.stop();
            sleep(50);

            robot.left(0.5);
            sleep(200);
            robot.stop();
            sleep(50);


            /////move to drop wobble
            robot.backward(0.5);
            sleep(2400);
            robot.stop();
            sleep(50);

            //// drop wobble//////////////////
//        robot.shoulder.setPosition(0);
//        robot.wrist.setPosition(0.2);
//        robot.elbow.setPosition(1);

            sleep(1000);


            //// park on line
            robot.forward(0.5);
            sleep(2400);
            robot.stop();
            sleep(50);

            robot.stop();
            sleep(3000);

//            robot.forwardByEncoder(0.25, 4400);
//            Thread.sleep(100);


            telemetry.update();
        }

    }


    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

}

