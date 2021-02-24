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




import android.graphics.Color;
@Autonomous(name="Red Auto", group="Auto")
public class Red_Auto extends LinearOpMode {




    /* Declare OpMode members. */
    Hardware robot = new Hardware();

    private ElapsedTime runtime = new ElapsedTime();




    @Override
    public void runOpMode() throws InterruptedException{

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        telemetry.addData("Mode", "calibrating2...");
        telemetry.update();

        while (!isStopRequested() && !robot.imu.isGyroCalibrated())
        {
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



//            robot.forwardByEncoder(0.5, 9000);

//            robot.forward(0.5);
//            sleep(1000);
//            robot.stop();
//            sleep(100);
//
//
//            robot.right(0.5);
//            sleep(1000);
//            robot.stop();
//            sleep(100);
//
//            robot.backward(0.5);
//            sleep(1000);
//            robot.stop();
//            sleep(100);
//
//            robot.left(0.5);
//            sleep(1000);
//            robot.stop();
//            sleep(100);


            robot.backward(0.5);
            sleep(1250);
            robot.stop();


            robot.wrist.setPosition(0.2);
            robot.shoulder.setPosition(0.33);

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
            robot.shoulder.setPosition(0);
            robot.wrist.setPosition(0.2);
            robot.elbow.setPosition(1);

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
        telemetry.update();

    }

}



