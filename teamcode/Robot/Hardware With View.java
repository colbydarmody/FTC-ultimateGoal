package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class Hardware {

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    public static final String VUFORIA_KEY = "AW2S0oL/////AAABmTeGOCNGIkv0hI5YG06U96iCCZlej8M0P6YGp7XmU8L56MjcFnEFrYr7E+RBQb9Z+IwfIsoVgaSi+3BEyMf7i45HFVCppR+uMNJNpPehQWYgRCJ18hmZRySJwjxE3Iw7rhV8vAIOelPaxog8fxy7WdWgsGtxVn0EjrSMzr0XGqq/vTMoCk8FvuGFAggHT3vKqw0y4/Z1M9AsC39tzybW3eZkl8J8blTGxi3RHvGVvrXjhWcqc84R1ocZjTjoj+BPL9K8Q6JWzEPBPm7yj3iQif2zOPUdyfLfrp7+NuBLasJeD0RCs12/hkiDH6J/Wn4aV/fBoJGaIrIi7ISWSUAbePKKKrAgCesseWxpX+hVGGWc";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    public VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    public TFObjectDetector tfod;

   /*
   Strange programming facts
   no reset(); command. Have to use period.reset();
   */

    static final double INCREMENT = 0.03;     // amount to ramp motor each CYCLE_MS cycle
    static final int CYCLE_MS = 50;     // period of each cycle
    static final double MAX_FWD = 1.0;     // Maximum FWD power applied to motor
    static final double MAX_REV = -1.0;     // Maximum REV power applied to motor

    double rampUpPower = 0; ///// for ramp down
    boolean rampUp = false; //// for ramp down

    /* Public OpMode members. */
    public DcMotor fl = null;
    public DcMotor fr = null;
    public DcMotor bl = null;
    public DcMotor br = null;
    // public DcMotorEx shooter = null;
    //public DcMotor leadScrew = null;

    public Servo hand = null;
    public Servo wrist = null;
    public Servo elbow = null;
    public Servo shoulder = null;

    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, power = .3;


    /* local OpMode members. */
    HardwareMap hwMap = null;
    private final ElapsedTime period = new ElapsedTime();


    // Variables for Drive Speed
    final double FORWARD = 0.5;
    final double BACK = -0.5;
    final int OFF = 0;

    public Hardware() {

    }


    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        fl = hwMap.get(DcMotor.class, "fl");
        fr = hwMap.get(DcMotor.class, "fr");
        bl = hwMap.get(DcMotor.class, "bl");
        br = hwMap.get(DcMotor.class, "br");
        // shooter = hwMap.get(DcMotorEx.class, "shooter");

        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);
        //  shooter.setDirection(DcMotorEx.Direction.FORWARD);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // Set all motors to zero power
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        // shooter.setPower(0);

        // Set all motors to run without encoders.
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        hand = hwMap.get(Servo.class, "hand");
        wrist = hwMap.get(Servo.class, "wrist");
        elbow = hwMap.get(Servo.class, "elbow");
        shoulder = hwMap.get(Servo.class, "shoulder");

        imu = hwMap.get(BNO055IMU.class, "imu");

        hand.setPosition(-0.8);
        wrist.setPosition(0.5);// was 0.5
        elbow.setPosition(1);/// was 0.9
        shoulder.setPosition(0);



        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        imu = hwMap.get(BNO055IMU.class, "imu0");

        imu.initialize(parameters);

        period.reset(); //Why period?
    }



    double cut = 0;

    // Methods
    public void forwardByEncoder(double speed, double distance) {
        period.reset();
        while (((-fr.getCurrentPosition() < distance))) {
            fl.setPower(speed); //was positive for all
            fr.setPower(speed);
            bl.setPower(speed);
            br.setPower(speed);
        }
        fl.setPower(OFF);
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(OFF);
    }

    // Variable speed was FORWARD





    // Method for encoder BACKWARD movement
    public void backwardByEncoder(double speed, double distance) {
        period.reset();
        while (((fr.getCurrentPosition() > distance))) {
            // was -
            fl.setPower(-speed);
            fr.setPower(-speed);
            bl.setPower(-speed);
            br.setPower(-speed);
            // -speed was BACKWARD

        }
        fl.setPower(OFF);
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(OFF);
    }


    // Method for encoder RIGHT movement
    public void rightByEncoder(double speed, double distance) {
        period.reset();
        // distance = -distance;
        while (((br.getCurrentPosition() < distance))) {
            // Note: +speed was FORWARD, -speed was BACK
            fl.setPower(speed); //was +
            fr.setPower(-speed); //was -
            bl.setPower(-speed); //was -
            br.setPower(speed); //was +
        }
        fl.setPower(OFF);
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(OFF);
    }

    // Method for encoder LEFT mpovement
    public void leftByEncoder(double speed, double distance) {
        reset();
        // distance = -distance;
        while (((br.getCurrentPosition() > distance))) {
            // Note: +speed was FORWARD, -speed was BACK
            fl.setPower(-speed); //was -
            fr.setPower(speed); //was +
            bl.setPower(speed); //was +
            br.setPower(-speed); //was -
            // fl.setPower(-Math.abs(speed));
            // fr.setPower(Math.abs(speed));
            // bl.setPower(Math.abs(speed));
            // br.setPower(-Math.abs(speed));
        }
        fl.setPower(OFF);
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(OFF);
    }

    // Method for driving forwards -- default
    public void forward() {
        fl.setPower(FORWARD);
        fr.setPower(FORWARD);
        bl.setPower(FORWARD);
        br.setPower(FORWARD);
    }

    // Method for driving forwards -- select speed
    public void forward(double speed) {
        fl.setPower(Math.abs(speed));
        fr.setPower(Math.abs(speed));
        bl.setPower(Math.abs(speed));
        br.setPower(Math.abs(speed));
    }

    // Method for driving backwards -- default
    public void backward() {
        fl.setPower(BACK);
        fr.setPower(BACK);
        bl.setPower(BACK);
        br.setPower(BACK);
    }

    // Method for driving backwards -- select speed
    public void backward(double speed) {
        fl.setPower(-Math.abs(speed));
        fr.setPower(-Math.abs(speed));
        bl.setPower(-Math.abs(speed));
        br.setPower(-Math.abs(speed));
    }

    // Method for driving left -- default
    public void left() {
        fl.setPower(BACK);
        fr.setPower(FORWARD);
        bl.setPower(FORWARD);
        br.setPower(BACK);
    }

    // Method for driving left -- select speed
    public void left(double speed) {
        fl.setPower(-Math.abs(speed));
        fr.setPower(Math.abs(speed));
        bl.setPower(Math.abs(speed));
        br.setPower(-Math.abs(speed));
    }

    // Method for driving right -- default
    public void right() {
        fl.setPower(FORWARD);
        fr.setPower(BACK);
        bl.setPower(BACK);
        br.setPower(FORWARD);
    }

    // Method for driving right -- select speed
    public void right(double speed) {
        fl.setPower(Math.abs(speed));
        fr.setPower(-Math.abs(speed));
        bl.setPower(-Math.abs(speed));
        br.setPower(Math.abs(speed));
    }

    // Method for driving diagonal forward left -- default
    public void forwardLeft() {
        fl.setPower(FORWARD);
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(FORWARD);
    }

    // Method for driving diagonal forward left -- select speed
    public void forwardLeft(double speed) {
        fl.setPower(Math.abs(speed));
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(Math.abs(speed));
    }

    // Method for driving diagonal back left -- default
    public void backLeft() {
        fl.setPower(OFF);
        fr.setPower(BACK);
        bl.setPower(BACK);
        br.setPower(OFF);
    }

    // Method for driving diagonal back left -- select speed
    public void backLeft(double speed) {
        fl.setPower(OFF);
        fr.setPower(-Math.abs(speed));
        bl.setPower(-Math.abs(speed));
        br.setPower(OFF);
    }

    // Method for driving diagonal forward right
    // Default
    public void forwardRight() {
        fl.setPower(OFF);
        fr.setPower(FORWARD);
        bl.setPower(FORWARD);
        br.setPower(OFF);
    }

    // Method for driving diagonal forward right
    // Select Speed
    public void forwardRight(double speed) {
        fl.setPower(OFF);
        fr.setPower(Math.abs(speed));
        bl.setPower(Math.abs(speed));
        br.setPower(OFF);
    }

    // Method for driving diagonal back right
    // Default
    public void backRight() {
        fl.setPower(BACK);
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(BACK);
    }

    // Method for driving diagonal back right
    // Select Speed
    public void backRight(double speed) {
        fl.setPower(-Math.abs(speed));
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(-Math.abs(speed));
    }

    // Method for spinning left -- default
    public void spinLeft() {
        fl.setPower(BACK);
        fr.setPower(FORWARD);
        bl.setPower(BACK);
        br.setPower(FORWARD);
    }

    // Method for spinning left -- select speed
    public void spinLeft(double speed) {
        fl.setPower(-Math.abs(speed));
        fr.setPower(Math.abs(speed));
        bl.setPower(-Math.abs(speed));
        br.setPower(Math.abs(speed));
    }

    // Method for spinning right -- default
    public void spinRight() {
        fl.setPower(FORWARD);
        fr.setPower(BACK);
        bl.setPower(FORWARD);
        br.setPower(BACK);
    }

    // Method for spinning right -- select speed
    public void spinRight(double speed) {
        fl.setPower(Math.abs(speed));
        fr.setPower(-Math.abs(speed));
        bl.setPower(Math.abs(speed));
        br.setPower(-Math.abs(speed));
    }

    // Method for not moving
    public void stop() {
        fl.setPower(OFF);
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(OFF);
    }

    // Method for not moving
    public void stopExc() throws InterruptedException {
        fl.setPower(OFF);
        fr.setPower(OFF);
        bl.setPower(OFF);
        br.setPower(OFF);
    }


    public void reset() {
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public double getLeftYEncoder() {
        return -br.getCurrentPosition();
    }

    public double getRightYEncoder() {
        return -bl.getCurrentPosition();
    }


    /*
     * Resets the cumulative angle tracking to zero.
     */
    public void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     *
     * @return Angle in degrees. + = left, - = right.
     */
    public double getAngle() {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    /**
     * See if we are moving in a straight line and if not return a power correction value.
     *
     * @return Power adjustment, + is adjust left - is adjust right.
     */
    public double checkDirection() {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     *
     * @param degrees Degrees to turn, + is left - is right
     */

    public void rotate(int degrees, double power) {
        double turnPower;
        double angle = getAngle();
        resetAngle();


        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < angle) {   // turn right
            turnPower = power; // was -
        } else if (degrees > angle) {   // turn left
            turnPower = -power; // was +
        } else return;

        // set power to rotate.
        fl.setPower(turnPower);
        bl.setPower(turnPower);
        fr.setPower(-turnPower);
        br.setPower(-turnPower);

        // rotate until turn is completed.
        if (degrees < angle) {
            // On right turn we have to get off zero first.
            while (getAngle() == angle) {
            }

            while (getAngle() > degrees) {
            }
        } else    // left turn.
            while (getAngle() < degrees) {
            }


        // rotate until turn is completed.
        if (degrees < angle) {
            // On right turn we have to get off zero first.
            while (getAngle() == angle) {
            }

            while (getAngle() > degrees) {
            }
        } else    // left turn.
            while (getAngle() < degrees) {
            }


        // turn the motors off.
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        // wait for rotation to stop.
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    public void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hwMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */



    public void initTfod() {
        int tfodMonitorViewId = hwMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hwMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

}
