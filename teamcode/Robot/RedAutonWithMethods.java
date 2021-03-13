        robot.initVuforia();
        robot.initTfod();

        robot.init(hardwareMap);
        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        telemetry.addData("Mode", "calibrating2...");
        telemetry.update();

        while (!isStopRequested() && !robot.imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }

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


            /**
             * Activate TensorFlow Object Detection before we wait for the start command.
             * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
             **/

            if (tfod != null) {
                tfod.activate();


                tfod.setZoom(2.5, 16.0 / 9.0);
            }

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
        }

        /**
         * Initialize the Vuforia localization engine.
         */
        robot.initVuforia();

        /**
         * Initialize the TensorFlow Object Detection engine.
         */
        robot.initTfod();




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

        /**
         The Actual Sequence
         **/

        robot.backward(0.5);
        sleep(1250);
        robot.stop();


        robot.wrist.setPosition(0.2);
        robot.shoulder.setPosition(0.33);

        robot.backward(0.5);
        sleep(250);
        robot.stop();
        sleep(50);

       robot.wobbleLaunch();


        //////////////////////////////
        //shoot   3////////////////////
        robot.shooter.setPower(1000); // Does this set duration or does it just do until you tell
        //it to sleep?
        sleep(1500);
        //////////////////////////////

        robot.backward(0.5);
        sleep(250);
        robot.stop();
        sleep(50);


        //////////////////////////////
        //shoot   1////////////////////
        robot.shooter.setPower(1000);
        sleep(500);
        ////////////////////////////////

        robot.backward(0.5);
        sleep(750);
        robot.stop();
        sleep(50);

        ////////////////////////////////
        //shoot   3////////////////////
        robot.shooter.setPower(1000);
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
        robot.shooter.setPower(1000);
        sleep(500);

        robot.left(0.5);
        sleep(350);
        robot.stop();
        sleep(50);

        //shoot  powershot 2////////////////////
        robot.shooter.setPower(1000);
        sleep(500);


        robot.left(0.5);
        sleep(350);
        robot.stop();
        sleep(50);

        //shoot  powershot 3////////////////////
        robot.shooter.setPower(1000);
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

        /*
        //// drop wobble//////////////////
//        robot.shoulder.setPosition(0);
//        robot.wrist.setPosition(0.2);
//        robot.elbow.setPosition(1);

        robot.shoulder.setPosition(0);
        robot.elbow.setPosition(0.7);
        sleep(500);
        robot.wrist.setPosition(0.65);
        sleep(500);
        robot.elbow.setPosition(0.2);
        sleep(1500);

         */

        wobbleDrop();

        robot.backward(0.5);
        sleep(1250);
        robot.stop();

        robot.hand.setPosition(1);
        robot.hand.setPosition(1);

        sleep(1000);

        robot.forward(0.5);
        sleep(200); //change

        robot.left(0.5);
        sleep(200);

        robot.hand.setPosition(0);
        robot.wobbleLift();
        robot.hand.setPosition(1);

        robot.wobbleRest();
        robot.backward(0.5);
        sleep(200); //change

        robot.right(0.5);
        sleep(200);


        wobbleDrop();

        robot.backward(0.5);
        sleep(1250);
        robot.stop();

        robot.hand.setPosition(1);
        robot.hand.setPosition(0);

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
