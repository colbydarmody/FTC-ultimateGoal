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
            tfod.setZoom(2.1, 18.0 / 10.0);/////was 16/9
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


        //robot.reset();


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

            int stack = 0;
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

                                if (recognition.getLabel() == "Single") {
                                    stack = 1;
                                } else if (recognition.getLabel() == "Quad") {
                                    stack = 4;
                                }
                                telemetry.addData(String.format("Stack height: %d", stack), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());
                            }
                            telemetry.update();
                        }
                    }


                    if (tfod != null) {
                        tfod.shutdown();
                    }


                    if (stack == 0) {
                        robot.forward(0.5);
                        sleep(1250);
                        robot.stop();
                    }

                    if (stack == 1) {


                        robot.wobbleLaunch();////   MAKE SURE ARM IS IN THE RIGHT SPOT

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////   start movement   ////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        robot.right(0.5);/////// 24
                        sleep(1900);
                        robot.stop();

                        robot.backward(0.3);///square against wall
                        sleep(300);
                        robot.stop();

                        robot.left(0.5);///square against wall
                        sleep(200);
                        robot.stop();

                        robot.forward(0.75);//////54
                        sleep(1500);///was 1600
                        robot.forward(0.25);
                        sleep(200);
                        robot.stop();

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////   Spin Up Shooter   ////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        robot.shooter.setVelocity(1650);

                        robot.right(0.5);///square against wall
                        sleep(200);
                        robot.stop();

                        robot.elevator.setPower(-1);
                        boolean done = false;
                        while (!done && opModeIsActive() && !isStopRequested()) {
                            if (robot.elevator.getCurrentPosition() < -1175) {  ////////////////////////// change encoder value
                                done = true;
                            }
                            telemetry.addData("Encoder Value is: ", robot.elevator.getCurrentPosition());
                            telemetry.update();
                        }
                        robot.elevator.setPower(-0.2);
                        robot.stop();


                        robot.lightsaber.setPosition(0.65);
                        sleep(300);
                        robot.elevator.setPower(0);

                        robot.left(0.5);/////// 28
                        sleep(1950);
                        robot.stop();

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////   Flick   ////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        //////shoot 1
                        robot.flick.setPosition(0.33);//in
                        sleep(300);
                        robot.flick.setPosition(0);
                        sleep(300);

                        //////shoot 2
                        robot.flick.setPosition(0.33);//in
                        sleep(300);
                        robot.flick.setPosition(0);
                        sleep(300);

                        //////shoot 3
                        robot.shoulder.setPosition(0.25);
                        robot.flick.setPosition(0.33);//in
                        sleep(300);
                        robot.flick.setPosition(0);
                        sleep(300);

                        robot.shooter.setVelocity(0);

                        robot.shoulder.setPosition(0);//// move shoulder

                        robot.elbow.setPosition(0.7);
                        robot.wrist.setPosition(0.65);

                        robot.left(0.5);///move to wobble position
                        sleep(600);
                        robot.stop();
                        sleep(50);

                        robot.rotate(-4, 0.3);

                        robot.forward(0.5);///move to wobble position
                        sleep(1300);
                        robot.stop();

                        robot.elbow.setPosition(0.2);
                        sleep(1000);

                        robot.hand.setPosition(1);

                        robot.backward(0.75);///move to wobble position
                        sleep(500);
                        robot.stop();

                        sleep(50000);

                    }

                    if (stack == 4) {


                        robot.wobbleLaunch();////   MAKE SURE ARM IS IN THE RIGHT SPOT


                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////   start movement   ////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        robot.right(0.5);/////// 24
                        sleep(1900);
                        robot.stop();

                        robot.backward(0.3);///square against wall
                        sleep(300);
                        robot.stop();

                        robot.left(0.5);///square against wall
                        sleep(200);
                        robot.stop();

                        robot.forward(0.75);//////54
                        sleep(1500);///was 1600
                        robot.forward(0.25);
                        sleep(200);
                        robot.stop();

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////   Spin Up Shooter   ////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        robot.shooter.setVelocity(1650);

                        robot.right(0.5);///square against wall
                        sleep(200);
                        robot.stop();

                        robot.elevator.setPower(-1);
                        boolean done = false;
                        while (!done && opModeIsActive() && !isStopRequested()) {
                            if (robot.elevator.getCurrentPosition() < -1175) {  ////////////////////////// change encoder value
                                done = true;
                            }
                            telemetry.addData("Encoder Value is: ", robot.elevator.getCurrentPosition());
                            telemetry.update();
                        }
                        robot.elevator.setPower(-0.2);
                        robot.stop();


                        robot.lightsaber.setPosition(0.65);
                        sleep(300);
                        robot.elevator.setPower(0);

                        robot.left(0.5);/////// 28
                        sleep(1950);
                        robot.stop();

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //////////////////////////////////////////////   Flick   ////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        //////shoot 1
                        robot.flick.setPosition(0.33);//in
                        sleep(300);
                        robot.flick.setPosition(0);
                        sleep(300);

                        //////shoot 2
                        robot.flick.setPosition(0.33);//in
                        sleep(300);
                        robot.flick.setPosition(0);
                        sleep(300);

                        //////shoot 3
                        robot.flick.setPosition(0.33);//in
                        sleep(300);
                        robot.flick.setPosition(0);
                        sleep(300);

                        robot.shooter.setVelocity(0);

                        robot.shoulder.setPosition(0);//// move shoulder

                        robot.right(0.5);/////// 28 BACK TO WALL
                        sleep(2150);
                        robot.stop();

                        robot.left(0.5);/////// GET OFF WALL
                        sleep(100);
                        robot.stop();

                        robot.elbow.setPosition(0.7);
                        robot.wrist.setPosition(0.65);

                        robot.forward(0.75);//////42
                        sleep(1400);///162
                        robot.stop();


                        robot.elbow.setPosition(0.2);
                        sleep(500);

                        robot.hand.setPosition(1);

                        robot.backward(0.75);//////42
                        sleep(1000);///162
                        robot.stop();

                        sleep(50000);


                        telemetry.update();
                    }

                }
            }


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

