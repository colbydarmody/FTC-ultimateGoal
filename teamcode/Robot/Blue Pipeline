package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class BluePipeline extends OpenCvPipeline
{
    /*
     * An enum to define the skystone position
     */
    int rings = 0;

    /*
     * Some color constants
     */
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar GREEN = new Scalar(0, 255, 0);

    /*
     * The core values which define the location and size of the sample regions
     */

    // THE FRAME IS 320 x 240 PIXELS.
    // THIS DEFINES THE TOP LEFT OF THE SEARCH AREA
    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(300, 120 - (75/2));///// original 210,  120

    // THIS IS THE SIZE (IN PIXELS) OF THE SEARCH AREA
    static final int REGION_WIDTH = 135;//// was 50
    static final int REGION_HEIGHT = 140;//// wad 75

    // THESE ARE HOW MUCH ORANGE NEEDS TO BE IN FRAME TO BE CONSIDERED ONE OR FOUR RINGS
    final int FOUR_RING_THRESHOLD = 37  ;/// was 70
    final int ONE_RING_THRESHOLD = 20;///// 30

    Point region1_pointA = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x,
            REGION1_TOPLEFT_ANCHOR_POINT.y);
    Point region1_pointB = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

    /*
     * Working variables
     */
    Mat region1_Cb;
    Mat YCrCb = new Mat();
    Mat Cb = new Mat();
    int avg1;

    // Volatile since accessed by OpMode thread w/o synchronization
  //  private volatile Randomization position = Randomization.NONE;

    /*
     * This function takes the RGB frame, converts to YCrCb,
     * and extracts the Cb channel to the 'Cb' variable
     */
    void inputToCb(Mat input){
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Cb, 1);
    }

    Mat region;
    Mat HSV = new Mat();
    void inputToHSV(Mat input){
        Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV);
    }



    @Override
    public void init(Mat firstFrame){
        inputToHSV(firstFrame);

        region = HSV.submat(new Rect(region1_pointA, region1_pointB));
    }

    @Override
    public Mat processFrame(Mat input){
        inputToHSV(input);

        avg1 = (int) Core.mean(region).val[1];

        Imgproc.rectangle(
                input, // Buffer to draw on
                region1_pointA, // First point which defines the rectangle
                region1_pointB, // Second point which defines the rectangle
                GREEN, // The color the rectangle is drawn in
                3); // Thickness of  rectangle lines

      // position = Randomization.NONE; // Record our analysis
        if(avg1 > FOUR_RING_THRESHOLD){
            rings = 4;
        }else if (avg1 > ONE_RING_THRESHOLD){
            rings = 1;
        }else{
            rings = 0;
        }

//            Imgproc.rectangle(
//                    input, // Buffer to draw on
//                    region1_pointA, // First point which defines the rectangle
//                    region1_pointB, // Second point which defines the rectangle
//                    GREEN, // The color the rectangle is drawn in
//                    -1); // Negative thickness means solid fill

        return input;
    }

    public int getAnalysis() {
        return avg1;
    }

    public int getRandomization() {
        return rings;
    }
}
